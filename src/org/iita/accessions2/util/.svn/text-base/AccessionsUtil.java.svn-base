/**
 * accession2.Struts Nov 24, 2010
 */
package org.iita.accessions2.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.FilterService;
import org.iita.accessions2.service.Filters;

/**
 * @author mobreza
 * 
 */
public class AccessionsUtil {
	private static final Log LOG = LogFactory.getLog(AccessionsUtil.class);
	private ExperimentService experimentService;
	private FilterService filterService;

	/**
	 * @param experimentService
	 * @param filterService 
	 * 
	 */
	public AccessionsUtil(ExperimentService experimentService, FilterService filterService) {
		this.experimentService = experimentService;
		this.filterService=filterService;
	}

	/**
	 * Check if image exists for descriptor
	 * 
	 * @param descriptor
	 * @param codedValue
	 * @return
	 */
	public boolean descriptorHasImage(ColumnDescription descriptor, String code) {
		LOG.debug("Checking for descriptor image: " + descriptor + " value=" + code);
		return this.experimentService.descriptorHasImage(descriptor, code);
	}
	
	public String test() {
		return "Matija!!!";
	}
	
	public ColumnDescription getColumn(String tableName, String columnName) {
		LOG.debug("Getting column description: " + tableName + "." + columnName);
		return this.experimentService.getColumn(tableName, columnName);
	}
	
	public Filters getFilters() {
		return this.filterService.getCurrentFilters();
	}
	
	public List<ColumnDescription> getExtraColumns() {
		return this.filterService.getExtraColumns();
	}
}
