/**
 * accession2.Struts Sep 27, 2010
 */
package org.iita.accessions2.action;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.ExperimentService;
import org.iita.struts.BaseAction;

import com.opensymphony.xwork2.Action;

/**
 * @author mobreza
 * 
 */
@SuppressWarnings("serial")
public class AccessionEditAction extends BaseAction {
	private ExperimentService experimentService;
	private Long experimentId;
	private Long id;
	private Accession accession = new Accession();
	private Experiment experiment;
	private List<Experiment> experiments;
	private Dictionary<String, Object> experimentData=new Hashtable<String, Object>();

	/**
	 * @param experimentService
	 * @param accessionService
	 * 
	 */
	public AccessionEditAction(ExperimentService experimentService) {
		this.experimentService = experimentService;
	}

	/**
	 * @param experimentId the experimentId to set
	 */
	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the accession
	 */
	public Accession getAccession() {
		return this.accession;
	}
	
	/**
	 * @return the experiment
	 */
	public Experiment getExperiment() {
		return this.experiment;
	}
	
	/**
	 * @return the experiments
	 */
	public List<Experiment> getExperiments() {
		return this.experiments;
	}
	
	/**
	 * @return the experimentData
	 */
	public Dictionary<String, Object> getExperimentData() {
		return this.experimentData;
	}

	/**
	 * @see org.iita.struts.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		this.experiments=this.experimentService.listExperiments();
		
		if (this.experimentId != null) {
			this.experiment = this.experimentService.find(this.experimentId);
			if (this.experiment == null) {
				addNotificationMessage("Could not load experiment with ID=" + this.experimentId);
			}
		}
		
		if (this.id != null) {
			this.accession = this.experimentService.find(Accession.class, this.id);
			if (this.accession == null) {
				this.id = null;
				addNotificationMessage("Could not find accession with ID=" + this.id);
				this.accession = new Accession();
			} else {
				if (this.experiment!=null)
					this.experimentData=this.experimentService.getExperimentData(experiment, accession);
			}
		}
	}

	/**
	 * Default action: display edit form
	 * 
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		if (this.experiment==null)
			return "experiment";
		return Action.SUCCESS;
	}

	public String update() {
		// update record
		try {
			this.experimentService.update(this.experiment, this.accession, this.experimentData);
			return "reload";
		} catch (Throwable e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
	}

	public String profile() {
		return "profile";
	}
}
