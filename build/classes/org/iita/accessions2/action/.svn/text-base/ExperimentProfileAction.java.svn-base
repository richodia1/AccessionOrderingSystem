/**
 * accession2.Struts Jan 21, 2011
 */
package org.iita.accessions2.action;

import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.ExperimentService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @author mobreza
 *
 */
public class ExperimentProfileAction {
	private ExperimentService experimentService;
	private Experiment experiment;
	
	/**
	 * @param experimentService 
	 * 
	 */
	public ExperimentProfileAction(ExperimentService experimentService) {
		this.experimentService=experimentService;
	}
	
	/**
	 * @return the experiment
	 */
	public Experiment getExperiment() {
		return this.experiment;
	}
	
	/**
	 * @param experiment the experiment to set
	 */
	@TypeConversion(converter="genericConverter")
	public void setId(Experiment experiment) {
		this.experiment = experiment;
	}
	
	public String execute() {
		return Action.SUCCESS;
	}
}
