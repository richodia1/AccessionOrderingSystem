/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.ordering;

import javax.persistence.CascadeType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.iita.entity.VersionedEntity;

/**
 * @author mobreza
 * 
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OrderItem<ITEMDETAIL, ORDER> extends VersionedEntity {
	private static final long serialVersionUID = -3966411170755312457L;

	protected ORDER order;
	protected ITEMDETAIL item;
	private double quantity;
	private String uom;

	/**
	 * @return the order
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE }, optional = false)
	public ORDER getOrder() {
		return this.order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(ORDER order) {
		this.order = order;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return this.quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the uom
	 */
	public String getUom() {
		return this.uom;
	}

	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * @return the item
	 */
	@ManyToOne(cascade = {}, optional = false)
	public ITEMDETAIL getItem() {
		return this.item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(ITEMDETAIL item) {
		this.item = item;
	}
}
