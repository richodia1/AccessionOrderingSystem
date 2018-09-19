/**
 * accession2.Struts Jan 24, 2010
 */
package org.iita.accessions2.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.Collection;
import org.iita.accessions2.model.Experiment;
import org.iita.security.Authorize;
import org.iita.struts.webfile.ServerFile;
import org.iita.util.PagedResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mobreza
 * 
 */
public class CollectionServiceImpl implements CollectionService {
	private EntityManager entityManager;
	private static final Log LOG = LogFactory.getLog(CollectionServiceImpl.class);
	private ExperimentService experimentService;
	private File accessionImageDirectory;

	/**
	 * @param accessionImageDirectory the accessionImageDirectory to set
	 */
	public void setAccessionImageDirectory(String accessionImageDirectory) {
		this.accessionImageDirectory = new File(accessionImageDirectory);
	}

	/**
	 * @param experimentService the experimentService to set
	 */
	public void setExperimentService(ExperimentService experimentService) {
		this.experimentService = experimentService;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#list(int, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public PagedResult<Collection> list(int startAt, int maxResults) {
		PagedResult<Collection> paged = new PagedResult<Collection>(startAt, maxResults);

		if (Authorize.hasAuthority("ROLE_USER")) {
			// logged in
			paged.setResults(this.entityManager.createQuery("from Collection c order by c.name").setFirstResult(startAt).setMaxResults(maxResults)
					.getResultList());
			if (paged.getResults().size() > 0)
				paged.setTotalHits(((Long) this.entityManager.createQuery("select count(c) from Collection c").getSingleResult()).longValue());
		} else {
			paged.setResults(this.entityManager.createQuery("from Collection c where c.visible=true order by c.name").setFirstResult(startAt).setMaxResults(
					maxResults).getResultList());
			if (paged.getResults().size() > 0)
				paged.setTotalHits(((Long) this.entityManager.createQuery("select count(c) from Collection c where c.visible=true").getSingleResult())
						.longValue());
		}
		return paged;
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#load(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Collection load(Long collectionId) {
		Collection c = this.entityManager.find(Collection.class, collectionId);
		if (c != null && !c.isVisible() && !Authorize.hasAuthority("ROLE_USER")) {
			return null;
		}
		return c;
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#update(org.iita.accessions2.model.Collection)
	 */
	@Override
	@Transactional
	public void update(Collection collection) {
		if (collection.getId() == null)
			this.entityManager.persist(collection);
		else
			this.entityManager.merge(collection);
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#clearCollection(org.iita.accessions2.model.Collection)
	 */
	@Override
	@Transactional
	public void clearCollection(Collection collection) {
		this.entityManager.createQuery("delete from Accession a where a.collection=:collection").setParameter("collection", collection).executeUpdate();
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#applyPrefix(org.iita.accessions2.model.Collection)
	 */
	@Override
	@Transactional
	public void applyPrefix(Collection collection) {
		LOG.info("Applying " + collection.getName() + " to all accessions with prefixes: " + collection.getPrefixes());
		String[] prefixes = collection.getPrefixList();
		for (String prefix : prefixes) {
			prefix = prefix + "%";
			int results = this.entityManager.createQuery("update Accession a set a.collection=:collection where a.name like :prefix").setParameter(
					"collection", collection).setParameter("prefix", prefix).executeUpdate();
			LOG.info("Prefix " + prefix + " contains " + results + " accessions");
		}
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#getCollectionSize(org.iita.accessions2.model.Collection)
	 */
	@Override
	@Transactional(readOnly = true)
	public long getCollectionSize(Collection collection) {
		if (collection == null)
			return 0;

		Long size = (Long) this.entityManager.createQuery("select count(a.id) from Accession a where a.collection=:collection").setParameter("collection",
				collection).getSingleResult();
		return size == null ? 0l : size.longValue();
	}

	/**
	 * @throws IOException
	 * @see org.iita.accessions2.service.CollectionService#downloadCollection(org.iita.accessions2.model.Collection)
	 */
	@Override
	public InputStream downloadCollection(Collection collection) throws IOException {
		return this.experimentService.downloadData(collection);
	}

	/**
	 * @throws IOException
	 * @see org.iita.accessions2.service.CollectionService#downloadCollection(org.iita.accessions2.model.Collection, org.iita.accessions2.model.Experiment)
	 */
	@Override
	public InputStream downloadCollection(Collection collection, Experiment experiment) throws IOException {
		return this.experimentService.downloadData(collection, experiment);
	}

	@Override
	@Transactional(readOnly = true)
	public InputStream downloadGeneSys(Collection collection) throws IOException {
		return this.experimentService.downloadGeneSys(collection);
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#listExperiments(org.iita.accessions2.model.Collection)
	 */
	@Override
	public List<Experiment> listExperiments(Collection collection) {
		return this.experimentService.listExperiments(collection);
	}

	/**
	 * @see org.iita.accessions2.service.CollectionService#listImages(org.iita.accessions2.model.Collection, int, int)
	 */
	@Override
	public PagedResult<ServerFile> listImages(Collection collection, int startAt, int maxRecords) {
		LOG.info("Listing images of collection " + collection.getName());
		try {
			// get folders of accession images (each accession has own folder)
			PagedResult<ServerFile> folders = ServerFile.getServerFiles(this.accessionImageDirectory, collection.getShortName().toLowerCase(), startAt,
					maxRecords);
			List<ServerFile> accessionImages = new ArrayList<ServerFile>();
			for (ServerFile accessionFolder : folders.getResults()) {
				accessionImages.addAll(ServerFile.getServerFiles(this.accessionImageDirectory, String.format("%1$s/%2$s", collection.getShortName()
						.toLowerCase(), accessionFolder.getFileName())));
			}
			folders.setResults(accessionImages);
			if (accessionImages.size() == 0)
				return null;
			return folders;
		} catch (IOException e) {
			LOG.error(e, e);
			return null;
		}
	}
}
