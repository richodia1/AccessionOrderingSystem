/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.action.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.ExperimentException;
import org.iita.accessions2.service.ExperimentService;
import org.iita.struts.BaseAction;
import org.iita.struts.DownloadableStream;
import org.iita.util.PagedResult;
import org.iita.util.StringUtil;

import com.opensymphony.xwork2.Action;

/**
 * The Class ExperimentAction.
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class ExperimentAction extends BaseAction implements DownloadableStream {

	/** The Constant maxResults. */
	private static final int maxResults = 50;

	/** The experiment service. */
	private ExperimentService experimentService;

	/** The experiment. */
	private Experiment experiment;

	/** The experiment id. */
	private Long experimentId;

	/** The paged. */
	private PagedResult<Experiment> paged;

	/** The start at. */
	private int startAt = 0;

	/** The data. */
	private PagedResult<Object[]> data;

	/** The unmapped columns. */
	private List<ColumnDescription> unmappedColumns;

	private InputStream downloadStream;

	private List<Long> sortOrder=new ArrayList<Long>();

	/**
	 * The Constructor.
	 * 
	 * @param experimentService the experiment service
	 */
	public ExperimentAction(ExperimentService experimentService) {
		this.experimentService = experimentService;
	}

	/**
	 * @return the sortOrder
	 */
	public List<Long> getSortOrder() {
		return this.sortOrder;
	}
	
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(List<Long> sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * Sets the start at.
	 * 
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	/**
	 * Sets the id.
	 * 
	 * @param experimentId the experimentId to set
	 */
	public void setId(Long experimentId) {
		this.experimentId = experimentId;
	}

	/**
	 * Gets the paged.
	 * 
	 * @return the paged
	 */
	public PagedResult<Experiment> getPaged() {
		return this.paged;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public PagedResult<Object[]> getData() {
		return this.data;
	}

	/**
	 * Gets the experiment.
	 * 
	 * @return the experiment
	 */
	public Experiment getExperiment() {
		return this.experiment;
	}

	/**
	 * Gets the columns.
	 * 
	 * @return the columns
	 */
	public List<ColumnDescription> getColumns() {
		return this.experimentService.getColumns(this.experiment);
	}

	/**
	 * Gets the unmapped columns.
	 * 
	 * @return the unmapped columns
	 */
	public List<ColumnDescription> getUnmappedColumns() {
		return unmappedColumns;
	}

	/**
	 * Prepare.
	 * 
	 * @see org.iita.struts.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		if (this.experimentId != null)
			this.experiment = this.experimentService.find(this.experimentId);
	}

	/**
	 * Execute.
	 * 
	 * @return the string
	 * 
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.paged = this.experimentService.list(startAt, maxResults);
		return Action.SUCCESS;
	}

	/**
	 * Profile.
	 * 
	 * @return the string
	 */
	public String profile() {
		//this.data = this.experimentService.listData(this.experiment, this.startAt, 10);
		try {
			this.unmappedColumns = this.experimentService.getUnmappedColumns(this.experiment);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
		}
		return Action.SUCCESS;
	}

	/**
	 * Action method to update experiment data.
	 * 
	 * @return the string
	 */
	public String update() {
		this.experimentService.update(this.experiment);
		return "refresh";
	}

	/**
	 * Delete the experiment.
	 * 
	 * @return the string
	 */
	public String delete() {
		try {
			this.experimentService.deleteExperiment(this.experiment);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "go-list";
	}

	/**
	 * Clear experiment data.
	 * 
	 * @return the string
	 */
	public String clear() {
		try {
			this.experimentService.deleteExperimentData(this.experiment);
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "refresh";
	}

	/**
	 * <p>
	 * View experiment data. The action will render a Spreadsheet with
	 * </p>
	 * <ul>
	 * <li>Experiment data columns as headings</li>
	 * <li>Paged rows of data</li>
	 * </ul>
	 * 
	 * @return the string
	 */
	public String viewData() {
		LOG.info("Viewing data for experiment: " + this.experiment);
		this.data = this.experimentService.listData(this.experiment, this.startAt, 25);
		return Action.SUCCESS;
	}

	/**
	 * Download experiment data
	 * 
	 * @return the string
	 */
	public String download() {
		try {
			this.downloadStream=this.experimentService.downloadData(this.experiment);
		} catch (IOException e) {
			addActionError(e.getMessage());
			return Action.SUCCESS;
		}
		return "download";
	}

	/**
	 * Gets the download file name.
	 * 
	 * @return the download file name
	 * 
	 * @see org.iita.struts.DownloadableStream#getDownloadFileName()
	 */
	@Override
	public String getDownloadFileName() {
		return String.format("IITA-%1$s-%2$ty-%2$tm-%2$td.xls", StringUtil.sanitizeFileName(this.experiment.getTitle()), new Date());
	}

	/**
	 * Gets the download stream.
	 * 
	 * @return the download stream
	 * 
	 * @see org.iita.struts.DownloadableStream#getDownloadStream()
	 */
	@Override
	public InputStream getDownloadStream() {
		return this.downloadStream;
	}
	
	/**
	 * Update column sort order
	 * @return
	 */
	public String sort() {
		this.experimentService.updateSortOrder(experiment, this.sortOrder);
		return "refresh";
	}
}
