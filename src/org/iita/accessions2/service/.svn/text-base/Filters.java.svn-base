/**
 * accession2.Struts Mar 5, 2010
 */
package org.iita.accessions2.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.ColumnDescription;

/**
 * Accession browsing filters
 * 
 * @author mobreza
 */
public class Filters implements Serializable {
	private static final Log LOG = LogFactory.getLog(FilterServiceImpl.class);
	private static final long serialVersionUID = -8960113794632203604L;
	private List<Filter> filters = new ArrayList<Filter>();
	private List<DisplayColumn> displayColumns = new ArrayList<DisplayColumn>();

	/**
	 * @return the filters
	 */
	public List<Filter> getFilters() {
		return this.filters;
	}

	/**
	 * @return the displayColumns
	 */
	public List<DisplayColumn> getDisplayColumns() {
		return this.displayColumns;
	}

	public void clear() {
		this.filters.clear();
	}

	public void clearDisplay() {
		this.displayColumns.clear();
	}

	public void addDisplayColumn(ColumnDescription column) {
		LOG.debug("Adding column to display list: " + column);
		DisplayColumn displayColumn = new DisplayColumn(column);
		if (!this.displayColumns.contains(displayColumn))
			this.displayColumns.add(displayColumn);
		LOG.info("Added column " + displayColumn);
	}

	public void removeDisplayColumn(ColumnDescription column) {
		LOG.debug("Removing column to display list: " + column);
		DisplayColumn displayColumn = new DisplayColumn(column);
		this.displayColumns.remove(displayColumn);
	}

	/**
	 * Remove filters matching specified tableName and field. When field is null, all fields of that tableName are removed.
	 * 
	 * @param tableName Name of table
	 * @param field Field (column) name, or <code>null</code> for all filters of table
	 */
	public void clear(String tableName, String field) {
		for (int i = this.filters.size() - 1; i >= 0; i--) {
			Filter filter = filters.get(i);
			if (filter.getTableName().equals(tableName)) {
				if (field == null) {
					this.filters.remove(filter);
				} else if (field.equals(filter.getColumn())) {
					this.filters.remove(filter);
				}
			}
		}
	}


	/**
	 * @param tableName
	 * @param field
	 * @param value
	 */
	public void clear(String tableName, String field, Object value) {
		for (int i = this.filters.size() - 1; i >= 0; i--) {
			Filter filter = filters.get(i);
			if (filter.getTableName().equals(tableName)) {
				if (field == null) {
					this.filters.remove(filter);
				} else if (field.equals(filter.getColumn())) {
					filter.filterValues.remove(value);
				}
			}
		}
	}

	
	/**
	 * Get list of distinct tables used in filters
	 * 
	 * @return distinct list of tables
	 */
	@Transient
	public List<String> getDistinctTables() {
		ArrayList<String> distinctTables = new ArrayList<String>();
		for (Filter filter : this.filters) {
			if (!distinctTables.contains(filter.getTableName()))
				distinctTables.add(filter.getTableName());
		}
		return distinctTables;
	}

	/**
	 * Return a list of "extra" tables that are not yet included in SQL filter tablers (distinctTables)
	 * 
	 * @return
	 */
	public List<String> getExtraTables() {
		ArrayList<String> extraTables = new ArrayList<String>();
		List<String> distinctTables = getDistinctTables();
		for (DisplayColumn dc : this.displayColumns) {
			if (!distinctTables.contains(dc.getTableName()) && !extraTables.contains(dc.getTableName()))
				extraTables.add(dc.getTableName());
		}
		return extraTables;
	}

	public class Filter implements Serializable {
		private static final long serialVersionUID = 4958229292283431521L;
		private String tableName;
		private String column;
		private List<Object> filterValues = new ArrayList<Object>();

		/**
		 * @return the tableName
		 */
		public String getTableName() {
			return this.tableName;
		}

		/**
		 * @param tableName the tableName to set
		 */
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		/**
		 * @return the column
		 */
		public String getColumn() {
			return this.column;
		}

		/**
		 * @param column the column to set
		 */
		public void setColumn(String column) {
			this.column = column;
		}

		/**
		 * @return the filterValues
		 */
		public List<Object> getFilterValues() {
			return this.filterValues;
		}

		/**
		 * Remove filter values
		 */
		public void clearFilterValues() {
			this.filterValues.clear();
		}

		/**
		 * @param value
		 */
		public void addValue(Object value) {
			if (!this.filterValues.contains(value))
				this.filterValues.add(value);
		}

		public void addRange(Object minValue, Object maxValue) {
			if (minValue != null || maxValue != null) {
				FilterRangeValue rangeValue = new FilterRangeValue(minValue, maxValue);
				this.filterValues.add(rangeValue);
			} else {
				this.filterValues.add(null);
			}
		}
	}

	/**
	 * @param tableName
	 * @return
	 */
	@Transient
	public List<Filter> getFilters(String tableName) {
		List<Filter> filtered = new ArrayList<Filter>();
		for (Filter filter : this.filters) {
			if (filter.tableName.equals(tableName)) {
				filtered.add(filter);
			}
		}
		return filtered;
	}

	/**
	 * @param tableName
	 * @param column
	 * @return
	 */
	@Transient
	public Filter getFilter(String tableName, String column) {
		for (Filter filter : this.filters) {
			if (filter.getTableName().equals(tableName) && filter.getColumn().equals(column))
				return filter;
		}
		return null;
	}

	/**
	 * @param tableName
	 * @param column
	 * @return
	 */
	public Filter addFilter(String tableName, String column) {
		Filter filter = new Filter();
		filter.setTableName(tableName);
		filter.setColumn(column);
		this.filters.add(filter);
		return filter;
	}

	public class FilterRangeValue {
		private Object minValue;
		private Object maxValue;

		public FilterRangeValue(Object minValue, Object maxValue) {
			this.minValue = minValue;
			this.maxValue = maxValue;
		}

		public Object getMinValue() {
			return minValue;
		}

		public void setMinValue(Object minValue) {
			this.minValue = minValue;
		}

		public Object getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(Object maxValue) {
			this.maxValue = maxValue;
		}

		public boolean hasBoth() {
			return this.minValue != null && this.maxValue != null;
		}
	}

	public class DisplayColumn implements Serializable {
		private static final long serialVersionUID = 4477332562412711629L;
		private long columnDescriptionId;
		private String tableName;

		/**
		 * 
		 */
		public DisplayColumn(ColumnDescription column) {
			this.columnDescriptionId = column.getId();
			this.tableName = column.getExperiment().getTableName();
		}

		/**
		 * @return
		 */
		public String getTableName() {
			return this.tableName;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object paramObject) {
			if (paramObject instanceof DisplayColumn)
				return ((DisplayColumn) paramObject).columnDescriptionId == this.columnDescriptionId;
			return super.equals(paramObject);
		}

		/**
		 * @return the columnDescriptionId
		 */
		public long getColumnDescriptionId() {
			return this.columnDescriptionId;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Display column [cd=" + this.columnDescriptionId + "]";
		}
	}

}
