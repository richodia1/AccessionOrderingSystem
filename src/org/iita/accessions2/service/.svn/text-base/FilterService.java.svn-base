/**
 * accession2.Struts Mar 5, 2010
 */
package org.iita.accessions2.service;

import java.util.List;

import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.service.Filters.Filter;

/**
 * @author mobreza
 * 
 */
public interface FilterService {
	/**
	 * Get currently active filters
	 * 
	 * @return
	 */
	Filters getCurrentFilters();

	/**
	 * Add a filter to current filters
	 * 
	 * @param tableName
	 * @param column
	 * @param value
	 * @throws FilterException
	 */
	void addFilter(String tableName, String column, Object value) throws FilterException;

	void addFilterRange(String tableName, String column, Object minValue, Object maxValue) throws FilterException;

	/**
	 * Get list of available
	 * 
	 * @return
	 */
	List<Filter> getAvailableFilters();

	/**
	 * Remove all filters
	 */
	void clearFilters();

	/**
	 * Remove filters matching tableName and field. Field can be null, in which case all fields of tableName will be removed.
	 * 
	 * @param tableName
	 * @param field
	 */
	void clearFilters(String tableName, String field);

	/**
	 * Add column for display
	 * 
	 * @param selectedColumn
	 */
	void addDisplayColumn(ColumnDescription selectedColumn);

	/**
	 * Get list of extra columns to display (obtained by examining getFilters().getDisplayColumns())
	 * 
	 * @return
	 */
	List<ColumnDescription> getExtraColumns();

	/**
	 * @param selectedColumn
	 */
	void removeDisplayColumn(ColumnDescription selectedColumn);

	/**
	 * @param tableName
	 * @param column
	 * @param value
	 */
	void clearFilter(String tableName, String column, Object value);
}
