/**
 * accession2.Struts Jan 25, 2010
 */
package org.iita.accessions2.action;

import java.util.List;

import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.service.AccessionService;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.SelectionService;
import org.iita.struts.BaseAction;

/*
 * *
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public abstract class AccessionListAction extends BaseAction {

	protected AccessionService accessionService;
	protected ExperimentService experimentService;
	protected SelectionService selectionService;
	protected List<ColumnDescription> mcpd;

	/**
	 * 
	 */
	public AccessionListAction(AccessionService accessionService, ExperimentService experimentService, SelectionService selectionService) {
		this.accessionService = accessionService;
		this.experimentService = experimentService;
		this.selectionService = selectionService;
	}

	/**
	 * @return the mcpd
	 */
	public List<ColumnDescription> getMcpd() {
		return this.mcpd;
	}
	
	public ColumnDescription getMcpd(String column) {
		for (ColumnDescription columnDescription : this.mcpd) {
			if (columnDescription.getColumn().equalsIgnoreCase(column))
				return columnDescription;
		}
		return null;
	}

	/**
	 * @see org.iita.struts.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		this.mcpd=this.experimentService.getMCPDColumns();
	}


	/**
	 * Utility method to see if accession is selected
	 * 
	 * @param acession
	 * @return
	 */
	public boolean isSelected(Accession accession) {
		return this.selectionService.contains(accession);
	}
}