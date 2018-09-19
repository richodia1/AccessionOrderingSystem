/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.service;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.ordering.Request;
import org.iita.service.EmailException;
import org.iita.service.EmailService;
import org.iita.service.TemplatingException;
import org.iita.service.TemplatingService;

/**
 * @author mobreza
 * 
 */
public class ApplicationEventsImpl extends LocalizedService implements ApplicationEvents {
	static final Log LOG = LogFactory.getLog(ExperimentServiceImpl.class);
	private EmailService emailService;
	private TemplatingService templatingService;

	/**
	 * @param emailService
	 * @param templatingService
	 * 
	 */
	public ApplicationEventsImpl(EmailService emailService, TemplatingService templatingService) {
		this.emailService = emailService;
		this.templatingService = templatingService;
	}

	/**
	 * @see org.iita.accessions2.service.ApplicationEvents#sendValidationEmail(org.iita.accessions2.model.ordering.Request)
	 */
	@Override
	public void sendValidationEmail(Request order) {
		Map<String, Object> data = new Hashtable<String, Object>();
		data.put("order", order);
		try {
			String body = this.templatingService.fillTemplate("email-validation", data);
			this.emailService.sendEmail(null, order.getRequestor().getEmail(), getText("request.email.validation.subject", "Validate germplasm request"), body);
		} catch (TemplatingException e) {
			LOG.error(e.getMessage());
		} catch (EmailException e) {
			LOG.error("Error sending validation email: " + e.getMessage());
		}
	}

	/**
	 * @see org.iita.accessions2.service.ApplicationEvents#requestValidated(org.iita.accessions2.model.ordering.Request)
	 */
	@Override
	public void requestValidated(Request order) {
		Map<String, Object> data = new Hashtable<String, Object>();
		data.put("order", order);
		try {
			String body = this.templatingService.fillTemplate("email-validated", data);
			this.emailService.sendEmail(null, "m.obreza@cgiar.org", getText("request.email.validated.subject", "Germplasm request validated"), body);
		} catch (TemplatingException e) {
			LOG.error(e.getMessage());
		} catch (EmailException e) {
			LOG.error("Error sending notification email: " + e.getMessage());
		}
	}
}
