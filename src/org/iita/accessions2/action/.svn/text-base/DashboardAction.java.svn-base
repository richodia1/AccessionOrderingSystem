/**
 * accession2.Struts Mar 2, 2010
 */
package org.iita.accessions2.action;

import java.util.List;

import org.iita.accessions2.model.Collection;
import org.iita.accessions2.service.CollectionService;
import org.iita.struts.BaseAction;

import com.opensymphony.xwork2.Action;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class DashboardAction extends BaseAction {
	private CollectionService collectionService;
	private List<Collection> collections;

	/**
	 * @param collectionService
	 * 
	 */
	public DashboardAction(CollectionService collectionService) {
		this.collectionService = collectionService;
	}

	/**
	 * @return the collections
	 */
	public List<Collection> getCollections() {
		return this.collections;
	}

	public long getCollectionSize(Collection collection) {
		return this.collectionService.getCollectionSize(collection);
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.collections = this.collectionService.list(0, 50).getResults();
		return Action.SUCCESS;
	}
}
