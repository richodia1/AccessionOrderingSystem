/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.ordering;

import java.util.List;

import org.iita.accessions2.model.ordering.Request;
import org.iita.accessions2.service.OrderException;
import org.iita.util.PagedResult;


/**
 * @author mobreza
 * 
 */
public interface OrderService<ORDER, ITEM> {

	/**
	 * @param all
	 * @return
	 */
	ORDER createOrder();

	/**
	 * @param order
	 */
	void updateOrder(ORDER order);

	/**
	 * @return
	 */
	ORDER getCurrentOrder();

	/**
	 * @param order
	 * @param all
	 */
	void addItems(ORDER order, List<ITEM> all);

	/**
	 * @param order
	 */
	void submit(ORDER order);

	/**
	 * @param order
	 */
	void cancel(Request order);

	/**
	 * @param startAt
	 * @param maxRecords
	 * @return
	 */
	PagedResult<ORDER> listOrders(int startAt, int maxRecords);

	/**
	 * @param order
	 */
	void saveChanges(ORDER order);

	/**
	 * Validate order having supplied id and the validation key
	 * @param id ID of the order
	 * @param key validation key
	 * @return <code>true</code> if order was successfully valdated, <code>false</code> otherwise
	 * @throws OrderException 
	 */
	Request validateOrder(Long id, String key) throws OrderException;
}
