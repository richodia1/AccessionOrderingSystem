/**
 * accession2.Struts Mar 1, 2010
 */
package org.iita.accessions2.action;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.iita.accessions2.service.ExperimentException;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.SelectionService;
import org.iita.struts.BaseAction;
import org.iita.struts.DownloadableStream;

import com.opensymphony.xwork2.Action;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class ExportAction extends BaseAction implements DownloadableStream {
	private SelectionService selectionService;
	private ExperimentService experimentService;
	private InputStream downloadStream;

	/**
	 * @param experimentService
	 * @param selectionService
	 * 
	 */
	public ExportAction(ExperimentService experimentService, SelectionService selectionService) {
		this.experimentService = experimentService;
		this.selectionService = selectionService;
	}

	/**
	 * Will export selected Accession data to XLS
	 * 
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		try {
			this.downloadStream = this.experimentService.exportToXLS(this.selectionService.allLong());
		} catch (ExperimentException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadFileName()
	 */
	@Override
	public String getDownloadFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "IITA-Selection-" + sdf.format(new Date()) + ".xls";
	}

	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadStream()
	 */
	@Override
	public InputStream getDownloadStream() {
		return this.downloadStream;
	}

	public int getDownloadSize() {
		return -1;
	}
}
