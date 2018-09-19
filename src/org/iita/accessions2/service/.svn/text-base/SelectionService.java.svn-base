/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.service;

import java.util.List;

import org.iita.accessions2.model.Accession;
import org.iita.util.PagedResult;

/**
 * Accession selection service
 * 
 * @author mobreza
 */
public interface SelectionService {
	void add(Accession accession) throws SelectionException;

	void add(long accessionId) throws SelectionException;

	void remove(Accession accession);

	void remove(long accessionId);

	void clear();

	List<Accession> all();
	
	List<Long> allLong();

	PagedResult<Accession> list(int startAt, int maxResults);

	/**
	 * @param accession
	 * @return
	 */
	boolean contains(Accession accession);

	boolean contains(long accessionId);

	/**
	 * @return
	 */
	int size();

	/**
	 * Add accession IDs to selection
	 * 
	 * @param accessionIds
	 * @return
	 * @throws SelectionException 
	 */
	boolean addAll(List<Long> accessionIds) throws SelectionException;
}
