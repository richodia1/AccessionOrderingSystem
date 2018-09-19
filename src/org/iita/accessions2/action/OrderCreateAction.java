/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.ordering.Request;
import org.iita.accessions2.service.SelectionService;
import org.iita.accessions2.service.SmtaProfileService;
import org.iita.ordering.OrderService;
import org.iita.ordering.Person;
import org.iita.ordering.SmtaProfileModel;
import org.iita.security.action.CaptchaValidator;
import org.iita.struts.BaseAction;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class OrderCreateAction extends BaseAction implements CaptchaValidator {
	private SelectionService selectionService;
	private OrderService<Request, Accession> orderService;
	private Request order;
	private ImageCaptchaService captchaService;
	private SmtaProfileModel smtaModel;
	private SmtaProfileService smtaProfileService;
	private String pid;
	private String password;
	
	private String surname;
	private String firstname;
	private String email;
	private String requestorOrg;
	private String requestorOrgType;
	private String requestorAddress;
	private String requestorPostalCode;
	private String requestorCity;
	private String requestorCountry;
	
	/**
	 * @param selectionService
	 * @param orderService
	 * 
	 */
	public OrderCreateAction(SelectionService selectionService, OrderService<Request, Accession> orderService, SmtaProfileService smtaProfileService) {
		this.selectionService = selectionService;
		this.orderService = orderService;
		this.smtaProfileService = smtaProfileService;
	}

	/**
	 * @return the order
	 */
	public Request getOrder() {
		return this.order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Request order) {
		this.order = order;
	}

	public List<Accession> getItems() {
		return this.selectionService.all();
	}

	/**
	 * @see org.iita.struts.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		// need to get order from session
		this.order = this.orderService.getCurrentOrder();
		
		if(this.smtaModel==null){
			if(this.order!=null){
				this.requestorOrgType = this.order.getOrganizationType();
				
				if(this.order.getRequestor().getCountry()!=null)
					this.requestorCountry = this.order.getRequestor().getCountry();
					
				this.firstname = this.order.getRequestor().getFirstName();
				this.surname = this.order.getRequestor().getLastName();				
				this.email = this.order.getRequestor().getEmail();
					
				this.requestorOrg = this.order.getRequestor().getOrganization();
					
				this.requestorAddress = this.order.getRequestor().getAddress();
				this.requestorPostalCode = this.order.getRequestor().getPostalCode();//smtaModel.getTelephone();				
				this.requestorCity = this.order.getRequestor().getCity();
			}
		}
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		if (isNeedCaptcha() && !isCaptchaValid()) {
			addActionError("Captcha control text does not match image");
			return Action.ERROR;
		}

		if (this.order == null) {
			if (this.selectionService.size() > 0) {
				LOG.info("Creating order");
				this.order = this.orderService.createOrder();
			} else {
				addActionError("There are no items in your selection list. Please select your accessions first.");
				return Action.ERROR;
			}
		} else {
			// using existing order
			LOG.info("Have existing order");
		}

		if (this.order.isSmtaAccepted())
			return "getsmta_profile";//return Action.SUCCESS;
		else
			return "smta";
	}
	
	public String getSmtaProfile(){
		
		this.smtaModel = this.smtaProfileService.getProfile(this.pid, this.password);
		
		if(this.smtaModel!=null){
			this.requestorOrgType = this.smtaModel.getType();
				
			if(this.smtaModel.getOrgCountryName()!=null)
				this.requestorCountry = this.smtaModel.getOrgCountryName();
			else if(this.smtaModel.getCountry()!=null)
				this.requestorCountry = this.smtaModel.getCountry();
			else if(this.smtaModel.getCountryName()!=null)
				this.requestorCountry = this.smtaModel.getCountryName();
			else
				this.requestorCountry = this.smtaModel.getShipCountry();
				
			this.firstname = this.smtaModel.getAoName();
			this.surname = this.smtaModel.getAoSurname();				
			this.email = this.smtaModel.getAoEmail();
				
			this.requestorOrg = this.smtaModel.getOrgname();
				
			this.requestorAddress = this.smtaModel.getOrgAddress();
			this.requestorPostalCode = "";//smtaModel.getTelephone();				
			this.requestorCity = "";//smtaModel.getOrgAddress();

			return Action.SUCCESS;
		}else{
			addActionError("SMTA profile login failed! Try again or go to SMTA portal to create new profile.");
			return "getsmta_profile";
		}
	}

	public String acceptSMTA() {
		this.order.setSmtaAccepted(true);
		return "reload";
	}

	public String rejectSMTA() {
		this.order.setSmtaAccepted(false);
		addActionError("Without accepting the SMTA, IITA cannot provide requested germplasm.");
		return "smta";
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "order.requestor.lastName", message = "Last name is required"),
			@RequiredStringValidator(fieldName = "order.requestor.organization", message = "Requestor organization is required"),
			@RequiredStringValidator(fieldName = "order.requestor.email", message = "Requestor email is required"),
			@RequiredStringValidator(fieldName = "order.requestor.country", message = "Requestor country is required"),
			@RequiredStringValidator(fieldName = "order.requestor.address", message = "Shipping address is required") })
	public String update() {
		if (this.order == null) {
			addActionError("You don't have a current order");
			return Action.ERROR;
		}
		this.orderService.updateOrder(this.order);
		return "reload";
	}

	/**
	 * Submit final order
	 * 
	 * @return
	 */

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "order.requestor.lastName", message = "Last name is required"),
			@RequiredStringValidator(fieldName = "order.requestor.organization", message = "Requestor organization is required"),
			@RequiredStringValidator(fieldName = "order.requestor.email", message = "Requestor email is required"),
			@RequiredStringValidator(fieldName = "order.requestor.country", message = "Requestor country is required"),
			@RequiredStringValidator(fieldName = "order.requestor.address", message = "Shipping address is required") })
	public String submit() {
		this.orderService.addItems(this.order, this.selectionService.all());
		this.orderService.submit(this.order);
		this.selectionService.clear();
		return "finished";
	}

	public String cancel() {
		this.orderService.cancel(this.order);
		return "dashboard";
	}

	/**
	 * @see org.iita.security.action.CaptchaValidator#setCaptchaService(com.octo.captcha.service.image.ImageCaptchaService)
	 */
	@Override
	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	/**
	 * @see org.iita.security.action.CaptchaValidator#isCaptchaValid(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isCaptchaValid() {
		ActionContext ac = ActionContext.getContext().getActionInvocation().getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		String captchaId = request.getSession().getId();
		String captchaText = request.getParameter("jcaptcha");
		LOG.debug("Got jcaptcha: " + captchaText);
		try {
			boolean valid = this.captchaService.validateResponseForID(captchaId, captchaText);
			ActionContext.getContext().getSession().put("CAPTCHA_VALIDATED", valid);
			return valid;
		} catch (CaptchaServiceException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean isNeedCaptcha() {
		Object captchaValidated = ActionContext.getContext().getSession().get("CAPTCHA_VALIDATED");
		LOG.debug("Captcha validated: " + captchaValidated);
		if (captchaValidated == null || !(captchaValidated instanceof Boolean) || !((Boolean) captchaValidated).booleanValue())
			return true;
		else
			return false;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param smtaModel the smtaModel to set
	 */
	public void setSmtaModel(SmtaProfileModel smtaModel) {
		this.smtaModel = smtaModel;
	}

	/**
	 * @return the smtaModel
	 */
	public SmtaProfileModel getSmtaModel() {
		return smtaModel;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the requestorOrg
	 */
	public String getRequestorOrg() {
		return requestorOrg;
	}

	/**
	 * @return the requestorOrgType
	 */
	public String getRequestorOrgType() {
		return requestorOrgType;
	}

	/**
	 * @return the requestorAddress
	 */
	public String getRequestorAddress() {
		return requestorAddress;
	}

	/**
	 * @return the requestorPostalCode
	 */
	public String getRequestorPostalCode() {
		return requestorPostalCode;
	}

	/**
	 * @return the requestorCity
	 */
	public String getRequestorCity() {
		return requestorCity;
	}

	/**
	 * @return the requestorCountry
	 */
	public String getRequestorCountry() {
		return requestorCountry;
	}

}
