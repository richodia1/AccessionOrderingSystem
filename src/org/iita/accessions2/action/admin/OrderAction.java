/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.action.admin;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.ordering.Request;
import org.iita.ordering.OrderService;
import org.iita.struts.BaseAction;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @author mobreza
 *
 */
@SuppressWarnings("serial")
public class OrderAction extends BaseAction {
	private OrderService<Request, Accession> orderService;
	private Request order;
	
	/**
	 * @param orderService
	 * 
	 */
	public OrderAction(OrderService<Request, Accession> orderService) {
		this.orderService = orderService;
	}
	
	@TypeConversion(converter="genericConverter")
	public void setId(Request order) {
		this.order=order;
	}
	
	/**
	 * @return the order
	 */
	public Request getOrder() {
		return this.order;
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		return Action.SUCCESS;
	}
	
	public String update() {
		if (this.order == null) {
			addActionError("You don't have a current order");
			return Action.ERROR;
		}
		this.orderService.updateOrder(this.order);
		this.orderService.saveChanges(this.order);
		return "refresh";
	}
}
