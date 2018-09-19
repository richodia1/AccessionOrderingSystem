/**
 * accession2.Struts Mar 5, 2010
 */
package org.iita.accessions2.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.service.Filters.DisplayColumn;
import org.iita.accessions2.service.Filters.Filter;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author mobreza
 * 
 */
public class FilterServiceImpl implements FilterService {
	private static final Log LOG = LogFactory.getLog(FilterServiceImpl.class);
	private EntityManager entityManager;
	
	
	/**
	 * 
	 */
	private static final String SESSION_ACCESSIONFILTERS = "IITA_ACCESSIONFILTERS";

	/**
	 * @param entityManager the entityManager to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @throws FilterException when insufficient data is provided
	 * @see org.iita.accessions2.service.FilterService#addFilter(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void addFilter(String tableName, String column, Object value) throws FilterException {
		if (tableName == null || column == null) {
			throw new FilterException("Not enough data provided to add filter");
		}
		Filters filters = getFiltersFromSession();
		Filter filter = filters.getFilter(tableName, column);
		if (filter == null) {
			filter = filters.addFilter(tableName, column);
		}
		filter.addValue(value);
	}

	@Override
	public void addFilterRange(String tableName, String column, Object minValue, Object maxValue) throws FilterException {
		if (tableName == null || column == null) {
			throw new FilterException("Not enough data provided to add filter");
		}
		Filters filters = getFiltersFromSession();
		Filter filter = filters.getFilter(tableName, column);
		if (filter == null) {
			filter = filters.addFilter(tableName, column);
		}
		filter.addRange(minValue, maxValue);
	}

	/**
	 * @see org.iita.accessions2.service.FilterService#addDisplayColumn(org.iita.accessions2.model.ColumnDescription)
	 */
	@Override
	public void addDisplayColumn(ColumnDescription selectedColumn) {
		if (selectedColumn == null)
			return;
		LOG.info("Adding " + selectedColumn.getTitle() + " to display columns.");
		Filters filters = getFiltersFromSession();
		filters.addDisplayColumn(selectedColumn);
	}
	
	/**
	 * @see org.iita.accessions2.service.FilterService#removeDisplayColumn(org.iita.accessions2.model.ColumnDescription)
	 */
	@Override
	public void removeDisplayColumn(ColumnDescription selectedColumn) {
		if (selectedColumn == null)
			return;
		LOG.info("Removign " + selectedColumn.getTitle() + " to display columns.");
		Filters filters = getFiltersFromSession();
		filters.removeDisplayColumn(selectedColumn);
	}

	/**
	 * @see org.iita.accessions2.service.FilterService#getAvailableFilters()
	 */
	@Override
	public List<Filter> getAvailableFilters() {
		return null;
	}

	/**
	 * @see org.iita.accessions2.service.FilterService#getCurrentFilters()
	 */
	@Override
	public Filters getCurrentFilters() {
		return getFiltersFromSession();
	}

	/**
	 * @see org.iita.accessions2.service.FilterService#getExtraColumns()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ColumnDescription> getExtraColumns() {
		List<ColumnDescription> extras=new ArrayList<ColumnDescription>();
		Filters filters=getCurrentFilters();
		for (DisplayColumn dc : filters.getDisplayColumns()) {
			ColumnDescription cd=this.entityManager.find(ColumnDescription.class, dc.getColumnDescriptionId());
			if (cd==null) {
				// ?? NULL
			} else {
				extras.add(cd);
			}
		}
		return extras;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Filters getFiltersFromSession() {
		Object filters = ActionContext.getContext().getSession().get(SESSION_ACCESSIONFILTERS);
		if (filters == null) {
			filters = new Filters();
			LOG.info("Putting Filters object to session");
			ActionContext.getContext().getSession().put(SESSION_ACCESSIONFILTERS, filters);
		} else {
			LOG.debug("Have existing Filters object in session");
		}
		return (Filters) filters;
	}

	/**
	 * Remove session variable holding filters
	 * 
	 * @see org.iita.accessions2.service.FilterService#clearFilters()
	 */
	@Override
	public void clearFilters() {
		ActionContext.getContext().getSession().remove(SESSION_ACCESSIONFILTERS);
	}

	@Override
	public void clearFilters(String tableName, String field) {
		Filters filters = getFiltersFromSession();
		if (filters == null)
			return;
		filters.clear(tableName, field);
	}
	

	@Override
	public void clearFilter(String tableName, String field, Object value) {
		Filters filters = getFiltersFromSession();
		if (filters == null)
			return;
		filters.clear(tableName, field, value);
	}
	
	

}
