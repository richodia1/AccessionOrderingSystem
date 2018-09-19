/**
 * accession2.Struts Jan 24, 2010
 */
package org.iita.accessions2.action.admin;

import org.iita.accessions2.model.Collection;
import org.iita.accessions2.service.CollectionService;
import org.iita.struts.BaseAction;
import org.iita.util.PagedResult;

import com.opensymphony.xwork2.Action;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class CollectionAction extends BaseAction {
	private static final int maxResults = 20;
	private CollectionService collectionService;
	private Long collectionId = null;
	private Collection collection = new Collection();
	private PagedResult<Collection> paged;
	private int startAt = 0;

	/**
	 * @param collectionService
	 * 
	 */
	public CollectionAction(CollectionService collectionService) {
		this.collectionService = collectionService;
	}

	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	/**
	 * @param collectionId the collectionId to set
	 */
	public void setId(Long collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return the collection
	 */
	public Collection getCollection() {
		return this.collection;
	}

	/**
	 * @return the paged
	 */
	public PagedResult<Collection> getPaged() {
		return this.paged;
	}

	/**
	 * @see org.iita.struts.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		if (this.collectionId != null)
			this.collection = this.collectionService.load(this.collectionId);
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.paged = this.collectionService.list(startAt, maxResults);
		return Action.SUCCESS;
	}

	public String profile() {
		return Action.INPUT;
	}

	public String update() {
		this.collectionService.update(this.collection);
		return "refresh";
	}

	/**
	 * Action to clear all collection accession data
	 * 
	 * @return
	 */
	public String clear() {
		this.collectionService.clearCollection(this.collection);
		return "refresh";
	}

	public String applyPrefix() {
		this.collectionService.applyPrefix(this.collection);
		return "refresh";
	}
}
