/**
 * accession2.Struts Nov 19, 2010
 */
package org.iita.accessions2.action;

import org.iita.accessions2.model.Collection;
import org.iita.accessions2.service.CollectionService;
import org.iita.struts.BaseAction;
import org.iita.struts.webfile.ServerFile;
import org.iita.util.PagedResult;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @author mobreza
 *
 */
@SuppressWarnings("serial")
public class CollectionGalleryAction extends BaseAction {
	private CollectionService collectionService;
	private Collection collection;
	private int startAt=0;
	private int maxRecords=10;
	private PagedResult<ServerFile> images;

	/**
	 * @param collectionService
	 * 
	 */
	public CollectionGalleryAction(CollectionService collectionService) {
		this.collectionService=collectionService;
	}
	
	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}
	
	/**
	 * @param collection the collection to set
	 */
	@TypeConversion(converter="genericConverter")
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	/**
	 * @return the images
	 */
	public PagedResult<ServerFile> getImages() {
		return this.images;
	}
	
	/**
	 * @return the collection
	 */
	public Collection getCollection() {
		return this.collection;
	}
	
	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.images=this.collectionService.listImages(this.collection, this.startAt, this.maxRecords);
		return Action.SUCCESS;
	}
}
