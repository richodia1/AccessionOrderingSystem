/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.action.admin;

import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.DataHistogram;
import org.iita.accessions2.service.ExperimentException;
import org.iita.accessions2.service.ExperimentService;
import org.iita.struts.BaseAction;

import com.opensymphony.xwork2.Action;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class ColumnDescriptionAction extends BaseAction {
	private ExperimentService experimentService;
	private Long columnId = null;
	private Long experimentId = null;
	private Experiment experiment;
	private ColumnDescription column = new ColumnDescription();
	private DataHistogram dataHistogram;

	/**
	 * @param experimentService
	 * 
	 */
	public ColumnDescriptionAction(ExperimentService experimentService) {
		this.experimentService = experimentService;
	}

	/**
	 * @param columnId the columnId to set
	 */
	public void setId(Long columnId) {
		this.columnId = columnId;
	}

	public void setExperiment(Long experimentId) {
		this.experimentId = experimentId;
	}

	/**
	 * @see org.iita.struts.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		if (this.columnId != null)
			this.column = (ColumnDescription) this.experimentService.find(ColumnDescription.class, columnId);
		if (this.experimentId != null)
			this.experiment = this.experimentService.find(this.experimentId);
	}

	/**
	 * @return the column
	 */
	public ColumnDescription getColumn() {
		return this.column;
	}

	/**
	 * @return the dataHistogram
	 */
	public DataHistogram getDataHistogram() {
		return this.dataHistogram;
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		if (this.column.getId() != null)
			this.dataHistogram = this.experimentService.getDataHistogram(this.column);

		return Action.INPUT;
	}

	public String add() {
		this.column.setExperiment(experiment);
		return Action.INPUT;
	}

	public String update() {
		try {
			this.experimentService.updateCoding(this.column, this.experiment);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "refresh";
	}

	public String convertToCoded() {
		try {
			this.experimentService.convertToCoded(this.column);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "refresh";
	}

	public String convertToDecoded() {
		try {
			this.experimentService.convertToDecoded(this.column);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "refresh";
	}

	public String convertToBoolean() {
		try {
			this.experimentService.convertToBoolean(this.column);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "refresh";
	}

	/**
	 * Removes column definition
	 * 
	 * @return
	 */
	public String clear() {
		try {
			this.experimentService.deleteColumnDescription(this.column);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "success";
	}
	
	/**
	 * Removes column from experiment table
	 * 
	 * @return
	 */
	public String drop() {
		try {
			this.experimentService.deleteColumn(this.column);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "success";
	}
}
