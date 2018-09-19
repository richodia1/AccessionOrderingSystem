/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.ordering;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.iita.entity.VersionedEntity;

/**
 * Abstract class containg core Order information
 * 
 * @author mobreza
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Order<ITEM, PERSON, ORDERSTATE> extends VersionedEntity {
	private static final long serialVersionUID = 6299232486806708431L;
	protected PERSON requestor;
	protected PERSON shipTo;
	protected List<ITEM> items;
	protected ORDERSTATE state;

	/**
	 * @return the requestor
	 */
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "requestorId")
	public PERSON getRequestor() {
		return this.requestor;
	}

	/**
	 * @param requestor the requestor to set
	 */
	public void setRequestor(PERSON requestor) {
		this.requestor = requestor;
	}

	/**
	 * @return the shipTo
	 */
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "shipToId")
	public PERSON getShipTo() {
		return this.shipTo;
	}

	/**
	 * @param shipTo the shipTo to set
	 */
	public void setShipTo(PERSON shipTo) {
		this.shipTo = shipTo;
	}

	/**
	 * @return the items
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	@XmlElementWrapper(name = "itemList")
	@XmlElement(name = "item")
	public List<ITEM> getItems() {
		return this.items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<ITEM> items) {
		this.items = items;
	}

	@Enumerated(EnumType.STRING)
	public ORDERSTATE getState() {
		return this.state;
	}

	public void setState(ORDERSTATE state) {
		this.state = state;
	}
}
