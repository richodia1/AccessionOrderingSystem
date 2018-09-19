/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.iita.accessions2.service.AccessionService;
import org.iita.accessions2.service.ExperimentException;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.FilterService;
import org.iita.accessions2.service.Filters;
import org.iita.accessions2.service.SelectionService;
import org.iita.accessions2.service.SingerExportService;
import org.iita.struts.DownloadableStream;
import org.iita.util.PagedResult;

import com.opensymphony.xwork2.Action;

/**
 * Action that displays (raw, filtered) paginated list of accessions.
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class Browse2Action extends AccessionListAction implements DownloadableStream {
	private static final int maxResults = 50;

	private int startAt = 0;
	private PagedResult<Object[]> paged;
	private FilterService filterService;

	private InputStream downloadStream;

	private String downloadFileName;

	private SingerExportService singerService;

	/**
	 * @param accessionService
	 * @param selectionService
	 * @param filterService
	 * @param singerService 
	 * 
	 */
	public Browse2Action(AccessionService accessionService, ExperimentService experimentService, SelectionService selectionService, FilterService filterService, SingerExportService singerService) {
		super(accessionService, experimentService, selectionService);
		this.filterService = filterService;
		this.singerService=singerService;
	}

	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	/**
	 * @return the paged
	 */
	public PagedResult<Object[]> getPaged() {
		return this.paged;
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
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.paged = this.accessionService.listFiltered(this.filterService.getCurrentFilters(), this.startAt, maxResults);
		return Action.SUCCESS;
	}

	public String download() {
		try {
			this.downloadFileName="IITA-Selection-" + String.format("%1$tY-%1$tm-%1$td", new Date()) + ".xls";
			this.downloadStream = this.experimentService.downloadPassportData(this.filterService.getCurrentFilters());
		} catch (IOException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "download";
	}
	
	public String downloadSQL() {
		try {
			this.downloadFileName="IITA-SINGER-" + String.format("%1$tY-%1$tm-%1$td", new Date()) + ".sql";
			this.downloadStream = this.singerService.downloadSQLScript(this.filterService.getCurrentFilters());
		} catch (IOException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "download";
	}


	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadFileName()
	 */
	@Override
	public String getDownloadFileName() {
		return this.downloadFileName;
	}

	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadStream()
	 */
	@Override
	public InputStream getDownloadStream() {
		return this.downloadStream;
	}
}
