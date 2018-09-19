/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.action;

import java.util.Dictionary;
import java.util.List;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.AccessionService;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.SelectionService;
import org.iita.struts.webfile.ServerFile;

import com.opensymphony.xwork2.Action;

@SuppressWarnings("serial")
public class AccessionProfileAction extends AccessionListAction {
	private Accession accession;
	private Long accessionId;
	private String accessionName;
	private List<ServerFile> images;
	
	/**
	 * @param accessionService
	 * @param experimentService
	 * @param selectionService
	 * 
	 */
	public AccessionProfileAction(AccessionService accessionService, ExperimentService experimentService, SelectionService selectionService) {
		super(accessionService, experimentService, selectionService);
	}

	/**
	 * @return the accession
	 */
	public Accession getAccession() {
		return this.accession;
	}

	/**
	 * @param accessionId the accessionId to set
	 */
	public void setId(Long accessionId) {
		this.accessionId = accessionId;
	}
	
	public void setName(String accessionName) {
		this.accessionName=accessionName;
	}

	/**
	 * @return the images
	 */
	public List<ServerFile> getImages() {
		return this.images;
	}
	
	/**
	 * @see org.iita.struts.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		super.prepare();
		
		if (this.accessionId != null)
			this.accession = this.accessionService.find(this.accessionId);
		if (this.accessionName!=null)
			this.accession=this.accessionService.find(this.accessionName);
		
		this.images=this.accessionService.listImages(this.accession);
	}
	
	public Dictionary<String, Object> getExperimentData(Experiment experiment) {
		return this.experimentService.getExperimentData(experiment, this.accession);
	}
	
	public Dictionary<String, Object> getMCPDData() {
		Experiment experiment=this.experimentService.find(1l);
		return this.experimentService.getExperimentData(experiment, this.accession);
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		if (this.accession==null) {
			addActionError("No such accession");
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
}
