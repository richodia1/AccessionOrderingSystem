/**
 * accession2.Struts Jan 18, 2010
 */
package org.iita.accessions2.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.Collection;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.Experiment;
import org.iita.entity.MySqlBaseEntity;
import org.iita.util.PagedResult;

/**
 * Defines methods for Experiment data.
 * 
 * @author mobreza
 */
public interface ExperimentService {

	/**
	 * Return list of all experiments.
	 * 
	 * @return the list containing all Experiments
	 */
	List<Experiment> listExperiments();

	/**
	 * Find.
	 * 
	 * @param experimentId the experiment id
	 * 
	 * @return the experiment
	 */
	Experiment find(Long experimentId);

	/**
	 * Get column descriptions {@link ColumnDescription} for experiment.
	 * 
	 * @param experiment the experiment
	 * 
	 * @return the columns
	 */
	List<ColumnDescription> getColumns(Experiment experiment);

	/**
	 * Get mappings for XLS sheet and experiment.
	 * 
	 * @param sheet the sheet
	 * @param experiment the experiment
	 * 
	 * @return the mappings
	 */
	Mappings getMappings(HSSFSheet sheet, Experiment experiment);

	/**
	 * XLS to ColumnDescription mapping data.
	 * 
	 * @author mobreza
	 */
	public class Mappings {

		/** The xls columns. */
		public List<XLSColumnData> xlsColumns = null;

		/** The mappings. */
		public Hashtable<Integer, Long> mappings = new Hashtable<Integer, Long>();
	}

	/**
	 * XLS column data.
	 * 
	 * @author mobreza
	 */
	public class XLSColumnData {

		/** The name. */
		public String name;

		/** The sample data. */
		public String sampleData;

		/** The data type. */
		public String dataType;

		/** The column num. */
		public int columnNum;
	}

	/**
	 * Try importing data from sheet as per mappings.
	 * 
	 * @param sheet the sheet
	 * @param mappings the mappings
	 * @param experiment the experiment
	 * @param createMissing
	 * @param defaultValues the default values
	 * 
	 * @throws ExperimentImportException the experiment import exception
	 */
	void tryImport(Experiment experiment, HSSFSheet sheet, boolean createMissing, Mappings mappings, Hashtable<String, Object> defaultValues)
			throws ExperimentImportException;

	/**
	 * Try import.
	 * 
	 * @param experiment the experiment
	 * @param mappings the mappings
	 * @param columnNum the column num
	 * @param sheet the sheet
	 * @param createMissing
	 * 
	 * @return the exception
	 */
	Exception tryImport(Experiment experiment, HSSFSheet sheet, boolean createMissing, Mappings mappings, int columnNum);

	/**
	 * List.
	 * 
	 * @param startAt the start at
	 * @param maxResults the max results
	 * 
	 * @return the paged result< experiment>
	 */
	PagedResult<Experiment> list(int startAt, int maxResults);

	/**
	 * Find.
	 * 
	 * @param clazz the clazz
	 * @param id the id
	 * 
	 * @return the object
	 */
	<T> T find(Class<T> clazz, Long id);

	/**
	 * Update.
	 * 
	 * @param data the data
	 */
	void update(MySqlBaseEntity data);

	/**
	 * Gets the mCPD columns.
	 * 
	 * @return the mCPD columns
	 */
	List<ColumnDescription> getMCPDColumns();

	/**
	 * List data.
	 * 
	 * @param experiment the experiment
	 * @param startAt the start at
	 * @param maxResults the max results
	 * 
	 * @return the paged result< object[]>
	 */
	PagedResult<Object[]> listData(Experiment experiment, int startAt, int maxResults);

	/**
	 * @param filters
	 * @param experiment
	 * @param startAt
	 * @param maxResults
	 * @return
	 */
	PagedResult<Object[]> listData(Filters filters, Experiment experiment, int startAt, int maxResults);

	/**
	 * Creates the experiment.
	 * 
	 * @param newExperiment the new experiment
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	void createExperiment(Experiment newExperiment) throws ExperimentException;

	/**
	 * Adds the column description.
	 * 
	 * @param experiment the experiment
	 * @param title the title
	 * @param description the description
	 * @param column the column
	 * @param dataType the data type
	 * @param coded the coded
	 * 
	 * @return the column description
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	ColumnDescription addColumnDescription(Experiment experiment, String title, String description, String column, String dataType, boolean coded)
			throws ExperimentException;

	/**
	 * Adds the coding values.
	 * 
	 * @param experiment the experiment
	 * @param columnDescriptionId the column description id
	 * @param sheet the sheet
	 * @param cellnum the cellnum
	 * 
	 * @throws ExperimentImportException the experiment import exception
	 */
	void addCodingValues(Experiment experiment, HSSFSheet sheet, int cellnum, long columnDescriptionId) throws ExperimentImportException;

	/**
	 * Properly update coding information.
	 * 
	 * @param column the column
	 * @param experiment the experiment
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	void updateCoding(ColumnDescription column, Experiment experiment) throws ExperimentException;

	/**
	 * Gets the experiment data.
	 * 
	 * @param experiment the experiment
	 * @param accession the accession
	 * 
	 * @return the experiment data
	 */
	Dictionary<String, Object> getExperimentData(Experiment experiment, Accession accession);

	/**
	 * Gets the data histogram.
	 * 
	 * @param column the column
	 * 
	 * @return the data histogram
	 */
	DataHistogram getDataHistogram(ColumnDescription column);

	/**
	 * Convert column to coded.
	 * 
	 * @param column the column
	 * 
	 * @throws ExperimentException if column cannot be converted
	 */
	void convertToCoded(ColumnDescription column) throws ExperimentException;

	/**
	 * Convert to decoded.
	 * 
	 * @param column the column
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	void convertToDecoded(ColumnDescription column) throws ExperimentException;

	/**
	 * Gets the unmapped columns.
	 * 
	 * @param experiment the experiment
	 * 
	 * @return the unmapped columns
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	List<ColumnDescription> getUnmappedColumns(Experiment experiment) throws ExperimentException;

	/**
	 * Export to xls.
	 * 
	 * @param accessionIds the accession ids
	 * 
	 * @return the input stream
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	InputStream exportToXLS(List<Long> accessionIds) throws ExperimentException;

	/**
	 * Convert a column to Boolean (<code>true, false</code>) if it contains values as "true", "yes", "Y", "false", "no", "n".
	 * 
	 * @param column the column
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	void convertToBoolean(ColumnDescription column) throws ExperimentException;

	/**
	 * Delete experiment from the system. Will remove references from AccessionExperiment table and drop experiment table for non-system experiments (i.e.
	 * Accession table which is Hibernate managed).
	 * 
	 * @param experiment the experiment to delete
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	void deleteExperiment(Experiment experiment) throws ExperimentException;

	/**
	 * Delete experiment data.
	 * 
	 * @param experiment the experiment
	 * 
	 * @throws ExperimentException the experiment exception
	 */
	void deleteExperimentData(Experiment experiment) throws ExperimentException;

	/**
	 * Delete column description, but leave data in experiment table.
	 * 
	 * @param column the column
	 */
	void deleteColumnDescription(ColumnDescription column) throws ExperimentException;

	/**
	 * Delete column description AND drop column from experiment table.
	 * 
	 * @param column the column
	 * @throws ExperimentException the experiment exception
	 */
	void deleteColumn(ColumnDescription column) throws ExperimentException;

	/**
	 * Download experiment data in XLS format
	 * 
	 * @param experiment
	 * @return
	 * @throws IOException if temporary files cannot be created
	 */
	InputStream downloadData(Experiment experiment) throws IOException;

	/**
	 * @param collection
	 * @return
	 * @throws IOException
	 */
	InputStream downloadData(Collection collection) throws IOException;

	/**
	 * @param currentFilters
	 * @return
	 * @throws IOException
	 */
	InputStream downloadPassportData(Filters currentFilters) throws IOException;

	/**
	 * Get description of a particular column in an experiment table
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	ColumnDescription getColumn(String tableName, String columnName);

	/**
	 * Download GeneSys data format
	 * 
	 * @param collection Collection
	 * @return InputStream with data
	 * @throws IOException
	 */
	InputStream downloadGeneSys(Collection collection) throws IOException;

	/**
	 * Update experiment data of accession
	 * 
	 * @param experiment
	 * @param accession
	 * @param experimentData
	 */
	void update(Experiment experiment, Accession accession, Dictionary<String, Object> experimentData);

	/**
	 * List experiments used by this collection
	 * 
	 * @param collection
	 * @return
	 */
	List<Experiment> listExperiments(Collection collection);

	/**
	 * @param collection
	 * @param experiment
	 * @return
	 * @throws IOException
	 */
	InputStream downloadData(Collection collection, Experiment experiment) throws IOException;

	/**
	 * @param experiment
	 * @param sortOrder
	 */
	void updateSortOrder(Experiment experiment, List<Long> sortOrder);

	/**
	 * Check if image exists for descriptor
	 * 
	 * @param descriptor
	 * @param code
	 * @return
	 */
	boolean descriptorHasImage(ColumnDescription descriptor, String code);

}
