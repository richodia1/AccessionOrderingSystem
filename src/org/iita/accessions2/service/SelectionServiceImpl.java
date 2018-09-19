/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.Accession;
import org.iita.util.PagedResult;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author mobreza
 * 
 */
public class SelectionServiceImpl implements SelectionService {
	private static final String SELECTED_ACCESSIONS = "__SELECTED_ACCESSIONS";
	private long maxSize = 2000;
	private static final Log LOG = LogFactory.getLog(SelectionServiceImpl.class);

	private EntityManager entityManager;

	/**
	 * @param entityManager the entityManager to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	private List<Long> getSelectedAccessions() {
		synchronized (this) {
			List<Long> selected = (List<Long>) ActionContext.getContext().getSession().get(SELECTED_ACCESSIONS);
			if (selected == null) {
				LOG.info("Creating new selection list");
				selected = (List<Long>) ActionContext.getContext().getSession().put(SELECTED_ACCESSIONS, new ArrayList<Long>());
			}
			return selected;
		}
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#size()
	 */
	@Override
	public int size() {
		return getSelectedAccessions().size();
	}

	/**
	 * @throws SelectionException when size of list would exceed the maximum allowed size
	 * @see org.iita.accessions2.service.SelectionService#add(org.iita.accessions2.model.Accession)
	 */
	@Override
	public void add(Accession accession) throws SelectionException {
		if (accession == null)
			return;
		List<Long> selection = getSelectedAccessions();

		if (selection.contains(accession.getId())) {
			LOG.debug("List already contains " + accession.getName() + " with id " + accession.getId());
		} else {
			if (selection.size() > this.maxSize)
				throw new SelectionException("You cannot have more than " + maxSize + " items in your selection list.");

			LOG.info("Adding to selection list " + accession.getName() + " with  id " + accession.getId());
			selection.add(accession.getId());
		}
	}

	/**
	 * @throws SelectionException when size of list would exceed the maximum allowed size
	 * @see org.iita.accessions2.service.SelectionService#add(long)
	 */
	@Override
	public void add(long accessionId) throws SelectionException {
		List<Long> selection = getSelectedAccessions();

		if (selection.contains(accessionId)) {
			LOG.debug("List already contains id " + accessionId);
		} else {
			if (selection.size() > this.maxSize)
				throw new SelectionException("You cannot have more than " + maxSize + " items in your selection list.");

			LOG.info("Adding to selection list id " + accessionId);
			selection.add(accessionId);
		}
	}

	/**
	 * @throws SelectionException 
	 * @see org.iita.accessions2.service.SelectionService#addAll(java.util.List)
	 */
	@Override
	public boolean addAll(List<Long> accessionIds) throws SelectionException {
		List<Long> selection = getSelectedAccessions();
		for (Long accessionId : accessionIds) {
			if (!selection.contains(accessionId)) {
				if (selection.size() > this.maxSize)
					throw new SelectionException("You cannot have more than " + maxSize + " items in your selection list.");

				LOG.info("Adding to selection list id " + accessionId);
				selection.add(accessionId);
			}
		}
		return true;
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#all()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Accession> all() {
		List<Long> selection = getSelectedAccessions();
		return this.entityManager.createQuery("from Accession a where a.id in (:list) order by a.name").setParameter("list", selection).getResultList();
	}
	
	/**
	 * @see org.iita.accessions2.service.SelectionService#allLong()
	 */
	@Override
	public List<Long> allLong() {
		return getSelectedAccessions();
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#clear()
	 */
	@Override
	public void clear() {
		List<Long> selection = getSelectedAccessions();
		selection.clear();
		ActionContext.getContext().getSession().remove(SELECTED_ACCESSIONS);
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#list(int, int)
	 */
	@Override
	@Transactional
	public PagedResult<Accession> list(int startAt, int maxResults) {
		List<Long> selection = getSelectedAccessions();
		PagedResult<Accession> paged = new PagedResult<Accession>(startAt, maxResults);
		if (selection!=null && selection.size()>0) {
			paged.setResults(this.entityManager.createQuery("from Accession a where a.id in (:list) order by a.name").setParameter("list", selection)
					.setFirstResult(startAt).setMaxResults(maxResults).getResultList());
			paged.setTotalHits(selection.size());
		}
		return paged;
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#remove(org.iita.accessions2.model.Accession)
	 */
	@Override
	public void remove(Accession accession) {
		if (accession == null)
			return;
		List<Long> selection = getSelectedAccessions();
		if (!selection.contains(accession.getId())) {
			LOG.debug("List does not contain " + accession.getName() + " with id " + accession.getId());
		} else {
			LOG.info("Removing from selection list " + accession.getName() + " with  id " + accession.getId());
			selection.remove(accession.getId());
		}
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#remove(long)
	 */
	@Override
	public void remove(long accessionId) {
		List<Long> selection = getSelectedAccessions();
		if (!selection.contains(accessionId)) {
			LOG.debug("List does not contain id " + accessionId);
		} else {
			LOG.info("Removing from selection list id " + accessionId);
			selection.remove(accessionId);
		}
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#contains(org.iita.accessions2.model.Accession)
	 */
	@Override
	public boolean contains(Accession accession) {
		if (accession == null)
			return false;
		List<Long> selection = getSelectedAccessions();
		if (selection.contains(accession.getId())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see org.iita.accessions2.service.SelectionService#contains(long)
	 */
	@Override
	public boolean contains(long accessionId) {
		List<Long> selection = getSelectedAccessions();
		if (selection.contains(accessionId)) {
			return true;
		} else {
			return false;
		}
	}
}
