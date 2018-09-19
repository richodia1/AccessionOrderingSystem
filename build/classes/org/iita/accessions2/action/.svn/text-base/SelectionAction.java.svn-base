/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.action;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.service.AccessionService;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.SelectionException;
import org.iita.accessions2.service.SelectionService;
import org.iita.util.PagedResult;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * Action to manage selected accessions list
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class SelectionAction extends AccessionListAction {
	private static final int maxResults = 50;
	private int startAt = 0;
	private long accessionId;

	/**
	 * @param selectionService
	 * 
	 */
	public SelectionAction(AccessionService accessionService, ExperimentService experimentService, SelectionService selectionService) {
		super(accessionService, experimentService, selectionService);
	}
	
	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}
	
	/**
	 * @return the startAt
	 */
	public int getStartAt() {
		return this.startAt;
	}

	/**
	 * @return the paged
	 */
	public PagedResult<Accession> getPaged() {
		return this.selectionService.list(this.startAt, maxResults);
	}

	/**
	 * @param accessionId the accessionId to set
	 */
	public void setAccessionId(long accessionId) {
		this.accessionId = accessionId;
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		return Action.SUCCESS;
	}

	public String remove() {
		if (!this.selectionService.contains(accessionId)) {
			addActionMessage("Accession #" + this.accessionId + " is not on your list. Cannot remove.");
			return Action.SUCCESS;
		} else {
			this.selectionService.remove(accessionId);
		}
		return "reload";
	}

	public String add() {
		if (this.selectionService.contains(accessionId)) {
			addActionMessage("Accession #" + this.accessionId + " is already on your list. Will not add.");
			return Action.SUCCESS;
		} else {
			try {
				this.selectionService.add(accessionId);
			} catch (SelectionException e) {
				addActionError(e.getMessage());
				return Action.ERROR;
			}
		}
		return "reload";
	}
	

	public boolean isNeedCaptcha() {
		Object captchaValidated = ActionContext.getContext().getSession().get("CAPTCHA_VALIDATED");
		LOG.debug("Captcha validated: " + captchaValidated);
		if (captchaValidated == null || !(captchaValidated instanceof Boolean) || !((Boolean) captchaValidated).booleanValue())
			return true;
		else
			return false;
	}
	
	public String clear() {
		this.selectionService.clear();
		return "reload";
	}
}
