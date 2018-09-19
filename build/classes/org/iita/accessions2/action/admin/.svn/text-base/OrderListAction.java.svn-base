/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.action.admin;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.ordering.Request;
import org.iita.ordering.OrderService;
import org.iita.struts.BaseAction;
import org.iita.util.PagedResult;

import com.opensymphony.xwork2.Action;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class OrderListAction extends BaseAction {
	private OrderService<Request, Accession> orderService;
	private int startAt = 0;
	private PagedResult<Request> paged;
	private static final int maxRecords = 50;

	/**
	 * @param orderService
	 * 
	 */
	public OrderListAction(OrderService<Request, Accession> orderService) {
		this.orderService = orderService;
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
	public PagedResult<Request> getPaged() {
		return this.paged;
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.paged = this.orderService.listOrders(startAt, maxRecords);
		return Action.SUCCESS;
	}
}
