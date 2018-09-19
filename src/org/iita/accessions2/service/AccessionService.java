/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.service;

import java.util.List;
import java.util.Map;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.struts.webfile.ServerFile;
import org.iita.util.PagedResult;

/**
 * @author mobreza
 *
 */
public interface AccessionService {

	/**
	 * @param accessionId
	 * @return
	 */
	Accession find(Long accessionId);

	/**
	 * @param startAt
	 * @param maxResults
	 * @return
	 */
	PagedResult<Accession> list(int startAt, int maxResults);

	/**
	 * @param filters
	 * @param startAt
	 * @param maxResults
	 * @return
	 */
	PagedResult<Accession> list(Filters filters, int startAt, int maxResults);

	/**
	 * @param accessionName
	 * @return
	 */
	Accession find(String accessionName);
	
	/**
	 * Get accession images
	 * @param acession
	 * @return
	 */
	public List<ServerFile> listImages(final Accession accession);
	

	/**
	 * Return a map with the number of filtered accessions for particular column
	 * 
	 * @param coding
	 * @return
	 */
	Map<Object, Long> countMatching(Filters filters, ColumnDescription column);

	/**
	 * @param filters
	 * @return
	 */
	long countMatching(Filters filters);

	/**
	 * @param currentFilters
	 * @param startAt
	 * @param maxResults
	 * @return
	 */
	PagedResult<Object[]> listFiltered(Filters currentFilters, int startAt, int maxResults);

	PagedResult<Object[]> listOrderVariation(int startAt, int maxresults);
}
