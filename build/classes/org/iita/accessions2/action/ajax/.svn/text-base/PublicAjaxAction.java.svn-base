/**
 * accession2.Struts Jan 20, 2010
 */
package org.iita.accessions2.action.ajax;

import java.util.List;

import org.iita.accessions2.service.SelectionException;
import org.iita.accessions2.service.SelectionService;

import com.googlecode.jsonplugin.annotations.SMDMethod;
import com.opensymphony.xwork2.Action;

/**
 * Public Ajax services
 * 
 * @author mobreza
 */
public class PublicAjaxAction {
	private SelectionService selectionService;

	/**
	 * @param selectionService
	 * 
	 */
	public PublicAjaxAction(SelectionService selectionService) {
		this.selectionService = selectionService;
	}

	/**
	 * Add accession to selection
	 * 
	 * @param accessionId Accession ID
	 * @return <code>true</code> if accession is on selection list
	 * @throws SelectionException
	 */
	@SMDMethod
	public boolean addToSelection(long accessionId) throws SelectionException {
		this.selectionService.add(accessionId);
		return this.selectionService.contains(accessionId);
	}
	
	@SMDMethod
	public boolean addManyToSelection(List<Long> accessionIds) throws SelectionException {
		return this.selectionService.addAll(accessionIds);
	}

	/**
	 * Remove accession from selection
	 * 
	 * @param accessionId Accession ID
	 * @return <code>true</code> if accession is no longer selected
	 */
	@SMDMethod
	public boolean removeFromSelection(long accessionId) {
		this.selectionService.remove(accessionId);
		return !this.selectionService.contains(accessionId);
	}

	public String execute() {
		return Action.SUCCESS;
	}
}
