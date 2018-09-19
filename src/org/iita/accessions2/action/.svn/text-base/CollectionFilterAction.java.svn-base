/**
 * accession2.Struts Mar 17, 2010
 */
package org.iita.accessions2.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iita.accessions2.model.Collection;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.AccessionService;
import org.iita.accessions2.service.CollectionService;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.FilterException;
import org.iita.accessions2.service.FilterService;
import org.iita.accessions2.service.Filters;
import org.iita.accessions2.service.Filters.Filter;
import org.iita.security.Authorize;
import org.iita.struts.BaseAction;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * Manages Collection profile page actions.
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class CollectionFilterAction extends BaseAction {
	private CollectionService collectionService;
	private AccessionService accessionService;
	private FilterService filterService;
	private ExperimentService experimentService;
	private Collection collection;
	private List<Experiment> experiments;
	private ColumnDescription selectedColumn;
	private Object value;
	private Map<Object, Long> matches;
	private long countMatching;

	/**
	 * @param collectionService
	 * @param filterService
	 * @param experimentService
	 * 
	 */
	public CollectionFilterAction(CollectionService collectionService, AccessionService accessionService, FilterService filterService,
			ExperimentService experimentService) {
		this.collectionService = collectionService;
		this.accessionService = accessionService;
		this.filterService = filterService;
		this.experimentService = experimentService;
	}

	/**
	 * @param collection the collection to set
	 */
	@TypeConversion(converter = "genericConverter")
	public void setId(Collection collection) {
		this.collection = collection;
		if (this.collection != null && !this.collection.isVisible() && !Authorize.hasAuthority("ROLE_USER"))
			this.collection = null;
		if (this.collection != null) {
			Filter collectionFilter = this.filterService.getCurrentFilters().getFilter("Accession", "collection_id");
			if (collectionFilter != null && !collectionFilter.getFilterValues().get(0).equals(this.collection.getId())) {
				this.filterService.clearFilters();
				addNotificationMessage("You have selected a different collection -- existing filters were removed.");
			}
		}
	}

	/**
	 * @return the collection
	 */
	public Collection getCollection() {
		return this.collection;
	}

	/**
	 * @return the experiments
	 */
	public List<Experiment> getExperiments() {
		return this.experiments;
	}

	/**
	 * @return the selectedColumn
	 */
	public ColumnDescription getSelectedColumn() {
		return this.selectedColumn;
	}

	/**
	 * @param selectedColumn the selectedColumn to set
	 */
	@TypeConversion(converter = "genericConverter")
	public void setCol(ColumnDescription selectedColumn) {
		this.selectedColumn = selectedColumn;
	}

	/**
	 * Gets the filters.
	 * 
	 * @return the filters
	 */
	public Filters getFilters() {
		return this.filterService.getCurrentFilters();
	}

	public ColumnDescription getColumn(String tableName, String columnName) {
		LOG.debug("Getting column description: " + tableName + "." + columnName);
		return this.experimentService.getColumn(tableName, columnName);
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
	 * Utility method to count the number of filtered accessions that also match selected coding. <code>null</code> can be passed as parameter.
	 * 
	 * @param coding
	 * @return
	 */
	public Long countMatching(Object value) {
		// LOG.warn("Getting matches for: " + coding.getActualValue() + " " + coding.getCodedValue());
		if (this.matches != null) {
			if (value == null)
				return this.matches.get("null");
			return this.matches.get(value.toString());
		}
		return null;
	}

	public long countMatching() {
		return this.countMatching;
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		if (this.collection == null) {
			Filter collectionFilter = this.filterService.getCurrentFilters().getFilter("Accession", "collection_id");
			if (collectionFilter != null) {
				this.collection = this.collectionService.load((Long) collectionFilter.getFilterValues().get(0));
			}
		}

		if (this.collection == null) {
			addActionError("No such collection.");
			return Action.ERROR;
		}

		// First make sure only active Collection is selected
		try {
			this.filterService.clearFilters("Accession", "collection_id");
			this.filterService.addFilter("Accession", "collection_id", this.collection.getId());
		} catch (FilterException e) {
			LOG.error(e, e);
		}

		this.experiments = new ArrayList<Experiment>(this.collectionService.listExperiments(this.collection));
		this.experiments.add(0, this.experimentService.find(1l));

		if (this.selectedColumn != null)
			this.matches = this.accessionService.countMatching(this.filterService.getCurrentFilters(), this.selectedColumn);

		this.countMatching = this.accessionService.countMatching(this.filterService.getCurrentFilters());
		LOG.warn("Done executing!");
		return Action.SUCCESS;
	}

	/**
	 * Clear current filters
	 * 
	 * @return
	 */
	public String clear() {
		this.filterService.clearFilters();
		return "refresh";
	}

	/**
	 * Adds a filter
	 * 
	 * @return the string
	 */
	public String add() {
		if (this.selectedColumn != null)
			try {
				this.filterService.addFilter(this.selectedColumn.getExperiment().getTableName(), this.selectedColumn.getColumn(), this.value);
			} catch (FilterException e) {
				addActionError(e.getMessage());
				return Action.ERROR;
			}
		return "refresh";
	}

	public String remove() {
		if (this.selectedColumn != null)
			this.filterService.clearFilters(this.selectedColumn.getExperiment().getTableName(), this.selectedColumn.getColumn());
	
		return "refresh";
	}
	
	public String remove1() {
		if (this.selectedColumn != null)
				this.filterService.clearFilter(this.selectedColumn.getExperiment().getTableName(), this.selectedColumn.getColumn(), this.value);
			
		return "refresh";
	}

	public String addDisplay() {
		if (this.selectedColumn != null) {
			this.filterService.addDisplayColumn(this.selectedColumn);
		}
		return "refresh";
	}

	public String removeDisplay() {
		if (this.selectedColumn != null) {
			this.filterService.removeDisplayColumn(this.selectedColumn);
		}
		return "refresh";
	}
}
