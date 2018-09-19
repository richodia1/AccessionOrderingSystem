/**
 * accession2.Struts Jan 22, 2010
 */
package org.iita.accessions2.action;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.service.SelectionService;
import org.iita.service.SearchService;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class AccessionSearchAction extends org.iita.struts.SearchAction<Accession> {
	private SelectionService selectionService;

	/**
	 * @param searchService
	 * @param selectionService
	 */
	public AccessionSearchAction(SearchService<Object> searchService, SelectionService selectionService) {
		super(searchService);
		this.selectionService = selectionService;
	}

	/**
	 * @see org.iita.struts.SearchAction#getSearchedClass()
	 */
	@Override
	protected Class<?> getSearchedClass() {
		return Accession.class;
	}

	/**
	 * @see org.iita.struts.SearchAction#execute()
	 */
	@Override
	public String execute() {
		String result = super.execute();
		if (getPaged().getTotalHits() == 1)
			return "single";
		else
			return result;
	}

	/**
	 * Utility method to see if accession is selected
	 * 
	 * @param acession
	 * @return
	 */
	public boolean isSelected(Accession accession) {
		return this.selectionService.contains(accession);
	}
}
