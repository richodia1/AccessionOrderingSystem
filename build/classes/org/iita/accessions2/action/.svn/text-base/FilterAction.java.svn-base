/**
 * accession2.Struts Mar 5, 2010
 */
package org.iita.accessions2.action;

import java.util.Date;
import java.util.List;

import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.FilterException;
import org.iita.accessions2.service.FilterService;
import org.iita.accessions2.service.Filters;
import org.iita.struts.BaseAction;
import org.iita.util.StringUtil;

import com.opensymphony.xwork2.Action;

/**
 * This action handles filters configuration page.
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class FilterAction extends BaseAction {

	/** The filter service. */
	private FilterService filterService;

	/** The clear. */
	private boolean clear = false;

	/** The table name. */
	private String tableName;

	/** The field. */
	private String field;

	/** The value. */
	private Object value, value2;

	/** The columns. */
	protected List<ColumnDescription> columns;

	/** The experiment service. */
	private ExperimentService experimentService;

	/** The selected column. */
	private ColumnDescription selectedColumn;

	/**
	 * The Constructor.
	 * 
	 * @param experimentService the experiment service
	 * @param filterService the filter service
	 */
	public FilterAction(FilterService filterService, ExperimentService experimentService) {
		this.filterService = filterService;
		this.experimentService = experimentService;
	}

	/**
	 * When this is set to <code>true</code> all existing filters are first removed before new filter is added.
	 * 
	 * @param clear whether to clear filters list before adding new filter
	 */
	public void setClear(boolean clear) {
		this.clear = clear;
	}

	/**
	 * Sets the experiment.
	 * 
	 * @param experiment the new experiment
	 */
	public void setExperiment(String experiment) {
		this.tableName = StringUtil.nullOrString(experiment);
	}

	/**
	 * Gets the experiment.
	 * 
	 * @return the experiment
	 */
	public String getExperiment() {
		return tableName;
	}

	/**
	 * Sets the field.
	 * 
	 * @param field the new field
	 */
	public void setField(String field) {
		this.field = StringUtil.nullOrString(field);
	}

	/**
	 * Gets the field.
	 * 
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the str value.
	 * 
	 * @param value the new str value
	 */
	public void setStrValue(String value) {
		this.value = value;
	}

	/**
	 * Sets the long value.
	 * 
	 * @param value the new long value
	 */
	public void setLongValue(long value) {
		this.value = value;
	}

	/**
	 * Sets the int value.
	 * 
	 * @param value the new int value
	 */
	public void setIntValue(int value) {
		this.value = value;
	}

	/**
	 * Sets boolean value.
	 * 
	 * @param value the new bool value
	 */
	public void setBoolValue(boolean value) {
		this.value = value;
	}

	/**
	 * Sets the dbl value.
	 * 
	 * @param value the new dbl value
	 */
	public void setDblValue(double value) {
		this.value = value;
	}

	/**
	 * Sets date value
	 * 
	 * @param value
	 */
	public void setDateValue(Date value) {
		this.value = value;
	}

	/**
	 * Sets date value
	 * 
	 * @param value
	 */
	public void setDateValue2(Date value) {
		this.value2 = value;
	}

	/**
	 * Gets the filters.
	 * 
	 * @return the filters
	 */
	public Filters getFilters() {
		return this.filterService.getCurrentFilters();
	}

	/**
	 * Gets the available columns by which data can be filtered.
	 * 
	 * @return the columns
	 */
	public List<ColumnDescription> getColumns() {
		return columns;
	}

	/**
	 * Gets the selected column.
	 * 
	 * @return the selected column
	 */
	public ColumnDescription getSelectedColumn() {
		return selectedColumn;
	}

	public ColumnDescription getColumn(String tableName, String columnName) {
		LOG.debug("Getting column description: " + tableName + "." + columnName);
		return this.experimentService.getColumn(tableName, columnName);
	}

	/**
	 * Display current filters.
	 * 
	 * @return the string
	 * 
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.columns = this.experimentService.getMCPDColumns();
		if (this.tableName != null && this.field != null && this.tableName.equals("Accession")) {
			for (ColumnDescription column : this.columns) {
				if (column.getColumn().equals(this.field))
					this.selectedColumn = column;
			}
		}
		return Action.SUCCESS;
	}

	/**
	 * Apply filters and go to "browse" page.
	 * 
	 * @return the string
	 */
	public String browse() {
		try {
			applyFilters();
		} catch (FilterException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "browse";
	}

	/**
	 * Will add current filter to Filters.
	 * 
	 * @throws FilterException the filter exception
	 */
	private void applyFilters() throws FilterException {
		if (this.clear) {
			this.filterService.clearFilters();
		}

		if (this.tableName != null && this.value != null)
			this.filterService.addFilter(this.tableName, this.field, this.value);
	}

	/**
	 * Remove all currently active filters.
	 * 
	 * @return the string
	 */
	public String clear() {
		if (this.tableName == null && this.field == null)
			this.filterService.clearFilters();
		else
			this.filterService.clearFilters(this.tableName, this.field);
		return "refresh";
	}

	/**
	 * Adds a filter
	 * 
	 * @return the string
	 */
	public String add() {
		if (this.tableName != null && this.field != null)
			try {
				this.filterService.addFilter(this.tableName, this.field, this.value);
			} catch (FilterException e) {
				addActionError(e.getMessage());
				return Action.ERROR;
			}
		return execute();
	}

	/**
	 * Adds a range filter
	 * 
	 * @return the string
	 */
	public String addrange() {
		if (this.tableName != null && this.field != null)
			try {
				this.filterService.addFilterRange(this.tableName, this.field, this.value, this.value2);
			} catch (FilterException e) {
				addActionError(e.getMessage());
				return Action.ERROR;
			}
		return execute();
	}

	/**
	 * Method to clear filters.
	 * 
	 * @return the string
	 */
	public String clearFilters() {
		this.filterService.clearFilters();
		return Action.SUCCESS;
	}
}
