/**
 * 
 */
package org.iita.accessions2.action.admin;

import org.iita.accessions2.service.AccessionService;
import org.iita.util.PagedResult;

import com.opensymphony.xwork2.Action;

/**
 * @author ken
 *
 */
public class OrderVariationAction {
	private AccessionService accessionService;
	private static final int maxResults = 50;

	private int startAt = 0;
	private PagedResult<Object[]> paged;
	
	
	public OrderVariationAction(AccessionService accessionService) {
		this.accessionService = accessionService;
	}
	
	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	/**
	 * @return the paged
	 */
	public PagedResult<Object[]> getPaged() {
		return this.paged;
	}
	
	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	public String execute() {
		this.paged = this.accessionService.listOrderVariation(this.startAt, maxResults);
		return Action.SUCCESS;
	}
}
