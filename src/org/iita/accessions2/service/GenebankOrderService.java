/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.ordering.Request;
import org.iita.accessions2.model.ordering.RequestItem;
import org.iita.accessions2.model.ordering.RequestState;
import org.iita.accessions2.model.ordering.Requestor;
import org.iita.ordering.OrderService;
import org.iita.service.EmailException;
import org.iita.service.EmailService;
import org.iita.service.TemplatingException;
import org.iita.service.TemplatingService;
import org.iita.util.PagedResult;
import org.iita.util.StringUtil;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author mobreza
 * 
 */
public class GenebankOrderService implements OrderService<Request, Accession> {
	/**
	 * 
	 */
	private static final String ORDERSESSIONKEY = "GENEBANK_ORDER";
	private static final Log LOG = LogFactory.getLog(GenebankOrderService.class);
	private EntityManager entityManager;
	private EmailService emailService;
	private TemplatingService templatingService;
	private String emailSender;
	private JmsTemplate template;
	private Destination destination;
	private Destination replyTo;

	/**
	 * @param emailService the emailService to set
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * @param templatingService the templatingService to set
	 */
	public void setTemplatingService(TemplatingService templatingService) {
		this.templatingService = templatingService;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	/**
	 * @param replyTo the replyTo to set
	 */
	public void setReplyTo(Destination replyTo) {
		this.replyTo = replyTo;
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#createOrder(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Request createOrder() {
		Request request = new Request();
		// requestor
		request.setRequestor(new Requestor());
		// request.setShipTo(new Requestor());
		request.setState(RequestState.NEW);

		LOG.debug("Putting order to session");
		ActionContext.getContext().getSession().put(ORDERSESSIONKEY, request);
		return request;
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#updateOrder(java.lang.Object)
	 */
	@Override
	@Transactional
	public void updateOrder(Request order) {
		// check items for removal
		if (order.getItems() != null)
			for (int i = order.getItems().size() - 1; i >= 0; i--) {
				RequestItem item = order.getItems().get(i);
				if (item.getQuantity() <= 0.0d) {
					this.entityManager.remove(item);
					order.getItems().remove(item);
				} else {
					item.setOrder(order);
				}
			}
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#getCurrentOrder()
	 */
	@Override
	@Transactional
	public Request getCurrentOrder() {
		LOG.debug("Getting order from session.");
		Object currentRequest = ActionContext.getContext().getSession().get(ORDERSESSIONKEY);
		if (currentRequest != null && currentRequest instanceof Request) {
			return (Request) currentRequest;
		}
		return null;
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#addItems(java.lang.Object, java.util.List)
	 */
	@Override
	@Transactional
	public void addItems(Request request, List<Accession> items) {
		request.setItems(new ArrayList<RequestItem>());
		for (Accession accession : items) {
			RequestItem requestItem = new RequestItem();
			requestItem.setOrder(request);
			requestItem.setUom("accession");
			requestItem.setQuantity(1.0d);
			requestItem.setItem(accession);
			request.getItems().add(requestItem);
		}
		this.updateOrder(request);
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#submit(java.lang.Object)
	 */
	@Override
	@Transactional
	public void submit(Request order) {
		order.setValidationKey(StringUtil.getRandomAlphaNumericString(10));
		order.setState(RequestState.VALIDATEEMAIL);
		this.updateOrder(order);
		this.entityManager.persist(order);

		if (this.emailService != null && this.templatingService != null) {
			// need to send email
			Map<String, Object> data = new Hashtable<String, Object>();
			data.put("order", order);
			String body;
			try {
				body = this.templatingService.fillTemplate("order-validate", data);
				this.emailService.sendEmail(this.emailSender, order.getRequestor().getEmail(), "Germplasm order validation", body);
			} catch (TemplatingException e) {
				LOG.error(e, e);
				throw new RuntimeException("An error occured while trying to save your request. Please try again later.");
			} catch (EmailException e) {
				LOG.error(e, e);
				throw new RuntimeException("An error occured while trying to save your request. Please try again later.");
			}
		}

		ActionContext.getContext().getSession().remove(ORDERSESSIONKEY);
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#cancel(org.iita.accessions2.model.ordering.Request)
	 */
	@Override
	public void cancel(Request order) {
		ActionContext.getContext().getSession().remove(ORDERSESSIONKEY);
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#listOrders(int, int)
	 */
	@Override
	@Transactional
	public PagedResult<Request> listOrders(int startAt, int maxRecords) {
		PagedResult<Request> paged = new PagedResult<Request>(startAt, maxRecords);
		paged.setResults(this.entityManager.createQuery("from Request r order by r.lastUpdated desc").setFirstResult(startAt).setMaxResults(maxRecords)
				.getResultList());
		if (paged.getResults().size() > 0)
			paged.setTotalHits(((Long) this.entityManager.createQuery("select count(r) from Request r").getSingleResult()).longValue());

		return paged;
	}

	/**
	 * @see org.iita.accessions2.service.OrderService#saveChanges(java.lang.Object)
	 */
	@Override
	@Transactional
	public void saveChanges(Request order) {
		this.entityManager.merge(order);
	}

	/**
	 * @throws OrderValidationException
	 * @throws OrderException
	 * @see org.iita.ordering.OrderService#validateOrder(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	public Request validateOrder(Long id, String key) throws OrderException {
		Request request = this.entityManager.find(Request.class, id);
		if (request == null) {
			LOG.warn("Cannot validate order #" + id + ". No such order");
			throw new OrderValidationException("Cannot validate order #" + id + ". No such order.");
		}

		if (request.getState() != RequestState.VALIDATEEMAIL) {
			throw new OrderValidationException("Order #" + id + " is not waiting for validation.");
		}

		if (key.equalsIgnoreCase(request.getValidationKey())) {
			request.setState(RequestState.PENDING);
			this.entityManager.merge(request);

			afterOrderValidated(request);

			return request;
		} else {
			LOG.warn("Key " + key + " does not match order #" + id + " validation key.");
		}
		throw new OrderValidationException("Validation key mismatch.");
	}

	/**
	 * Method to be invoked after successful order validation.
	 * 
	 * @param order Validated request
	 * @throws OrderProcessingException
	 */
	protected void afterOrderValidated(final Request order) throws OrderProcessingException {
		if (order == null)
			throw new OrderProcessingException("Null argument passed to method.");
		if (order.getState() != RequestState.PENDING)
			throw new OrderProcessingException("Wrong order state");

		if (this.template != null && this.destination != null) {
			// send order information to Ibadan via JMS
			template.send(destination, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					LOG.info("Sending germplasm request to " + destination.toString());
					TextMessage message = session.createTextMessage("Genebank Order information #" + order.getId());
					message.setLongProperty("orderId", order.getId());

					// enumerate items
					order.getItems().iterator();

					// order details
					XStream xstream = new XStream(new DomDriver());
					xstream.alias("request", Request.class);
					xstream.alias("requestor", Requestor.class);
					xstream.alias("requestitem", RequestItem.class);
					xstream.alias("state", RequestState.class);
					xstream.registerConverter(new Converter() {

						/**
						 * Marshalling used to generate XML representation of object
						 * 
						 * @param arg0
						 * @param writer
						 * @param arg2
						 */
						@Override
						public void marshal(Object arg0, HierarchicalStreamWriter writer, MarshallingContext arg2) {
							RequestItem item = (RequestItem) arg0;
							writer.startNode("quantity");
							writer.setValue(String.format("%1f", item.getQuantity()));
							writer.endNode();
							writer.startNode("uom");
							writer.setValue(item.getUom());
							writer.endNode();
							writer.startNode("accession");
							writer.setValue(item.getItem().getName());
							writer.endNode();
						}

						/**
						 * We're not really unmarshalling anything, so this is just here for fun
						 * 
						 * @param reader
						 * @param arg1
						 * @return
						 */
						@Override
						public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext arg1) {
							RequestItem item = new RequestItem();
							reader.moveDown();
							item.setQuantity(Double.parseDouble(reader.getValue()));
							reader.moveUp();
							reader.moveDown();
							item.setUom(reader.getValue());
							reader.moveUp();
							reader.moveDown();
							@SuppressWarnings("unused")
							String name = reader.getValue();
							item.setItem(null);
							reader.moveUp();
							return item;
						}

						@SuppressWarnings("unchecked")
						@Override
						public boolean canConvert(Class arg0) {
							return arg0.equals(RequestItem.class);
						}
					});

					LOG.debug(xstream.toXML(order));
					message.setStringProperty("xml", xstream.toXML(order));

					Request copy = (Request) xstream.fromXML(xstream.toXML(order));
					LOG.debug("Umarshalled: " + copy);
					for (RequestItem item : copy.getItems()) {
						LOG.debug("Item: " + item.getQuantity() + " " + item.getUom() + " " + item.getItem());
					}

					// other JMS settings
					message.setJMSReplyTo(replyTo);
					message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
					message.setJMSTimestamp(new Date().getTime());
					return message;
				}
			});
		} else {
			LOG.info("JMS not setup. Will not send order notification");
		}

		// send email to requester
		if (this.emailService != null && this.templatingService != null) {
			// need to send email
			Map<String, Object> data = new Hashtable<String, Object>();
			data.put("order", order);
			String body;
			try {
				body = this.templatingService.fillTemplate("order-validated", data);
				this.emailService.sendEmail(this.emailSender, order.getRequestor().getEmail(), "Order validated", body);
				
				//Send to distribution list of Genebank
				this.emailService.sendEmail(this.emailSender, "iita-genebankos@cgiar.org", "Order validated by requester", body);
			} catch (TemplatingException e) {
				LOG.error(e);
				throw new RuntimeException("An error occured while validating your order. Please try again later.");
			} catch (EmailException e) {
				LOG.error(e);
				throw new RuntimeException("An error occured while validating your order. Please try again later.");
			}
		}
	}
}
