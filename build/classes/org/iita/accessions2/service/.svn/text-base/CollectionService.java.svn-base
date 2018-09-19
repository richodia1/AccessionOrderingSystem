/**
 * accession2.Struts Jan 24, 2010
 */
package org.iita.accessions2.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.Collection;
import org.iita.accessions2.model.Experiment;
import org.iita.struts.webfile.ServerFile;
import org.iita.util.PagedResult;

/**
 * @author mobreza
 * 
 */
public interface CollectionService {

	/**
	 * @param collectionId
	 * @return
	 */
	Collection load(Long collectionId);

	/**
	 * @param startAt
	 * @param maxResults
	 * @return
	 */
	PagedResult<Collection> list(int startAt, int maxResults);

	/**
	 * @param collection
	 */
	void update(Collection collection);

	/**
	 * Remove accessions of this collection
	 * 
	 * @param collection
	 */
	void clearCollection(Collection collection);

	/**
	 * Find {@link Accession}s with matching prefixes and set collection information.
	 * 
	 * @param collection
	 */
	void applyPrefix(Collection collection);

	/**
	 * Get number of accessions in collection
	 */
	long getCollectionSize(Collection collection);

	/**
	 * @param collection
	 * @return
	 * @throws IOException
	 */
	InputStream downloadCollection(Collection collection) throws IOException;

	/**
	 * @param collection
	 * @return
	 * @throws IOException
	 */
	InputStream downloadGeneSys(Collection collection) throws IOException;

	/**
	 * @param collection
	 * @return
	 */
	List<Experiment> listExperiments(Collection collection);

	/**
	 * @param collection
	 * @param experiment
	 * @return
	 * @throws IOException
	 */
	InputStream downloadCollection(Collection collection, Experiment experiment) throws IOException;

	/**
	 * List collection images
	 * 
	 * @param collection
	 * @param startAt
	 * @param maxRecords
	 * @return
	 */
	PagedResult<ServerFile> listImages(Collection collection, int startAt, int maxRecords);
}
