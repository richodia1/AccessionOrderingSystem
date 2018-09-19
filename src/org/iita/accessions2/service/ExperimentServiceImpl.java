package org.iita.accessions2.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.Coding;
import org.iita.accessions2.model.CodingException;
import org.iita.accessions2.model.Collection;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.ColumnType;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.DatabaseService.DatabaseColumn;
import org.iita.accessions2.service.Filters.Filter;
import org.iita.entity.MySqlBaseEntity;
import org.iita.security.Authorize;
import org.iita.service.impl.XLSExportService;
import org.iita.service.impl.XLSImportService;
import org.iita.util.DeleteFileAfterCloseInputStream;
import org.iita.util.PagedResult;
import org.iita.util.StringUtil;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.util.XWorkConverter;

/**
 * {@link ExperimentServiceImpl} implements stuff
 * 
 * @author mobreza
 * 
 */
public class ExperimentServiceImpl implements ExperimentService {
	private static final Log LOG = LogFactory.getLog(ExperimentServiceImpl.class);
	private EntityManager entityManager;
	private DatabaseService databaseService;
	private File descriptorImageDirectory;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Set base directory where descriptor images are stored
	 * 
	 * @param descriptorImageDirectory the descriptorImageDirectory to set
	 * @throws IOException
	 */
	public void setDescriptorImageDirectory(String descriptorImageDirectory) throws IOException {
		this.descriptorImageDirectory = new File(descriptorImageDirectory);
		if (!this.descriptorImageDirectory.exists()) {
			LOG.warn("Descriptor image directory does not exist, creating: " + this.descriptorImageDirectory.getAbsolutePath());
			this.descriptorImageDirectory.mkdirs();
		}
		if (!this.descriptorImageDirectory.exists()) {
			throw new IOException("Descriptor image directory does not exist. Could not be created: " + this.descriptorImageDirectory.getAbsolutePath());
		}
		if (!this.descriptorImageDirectory.canRead()) {
			throw new IOException("Descriptor image directory cannot be read: " + this.descriptorImageDirectory.getAbsolutePath());
		}
	}

	/**
	 * @param databaseService the databaseService to set
	 */
	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	@Transactional
	public Experiment find(Long experimentId) {
		return this.entityManager.find(Experiment.class, experimentId);
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#find(java.lang.Class, java.lang.Long)
	 */
	@Override
	@Transactional
	public <T> T find(Class<T> clazz, Long id) {
		return this.entityManager.find(clazz, id);
	}

	/**
	 * This function will create a new Experiment with accompanying database tables
	 * 
	 * @throws ExperimentException when experiment cannot be created
	 */
	@Override
	@Transactional
	public void createExperiment(Experiment newExperiment) throws ExperimentException {
		if (newExperiment.getId() == null && newExperiment.getTableName() == null) {
			if (newExperiment.getTitle() == null || newExperiment.getTitle().trim().length() == 0)
				throw new ExperimentException("Please provide name for new experiment");
			// create empty experiment tables
			String tableName = this.createExperimentTables(newExperiment);
			newExperiment.setTableName(tableName);
			this.entityManager.persist(newExperiment);

			// The table is created with accession_id and accessionName
			// now need to create a ColumnDescription that links those
			ColumnDescription columnDescription = new ColumnDescription();
			columnDescription.setTitle("Accession name");
			columnDescription.setDescription("Accession name is used to link Experiment data to Accessions");
			columnDescription.setColumn("accessionName");
			columnDescription.setDataType("java.lang.String");
			columnDescription.setExperiment(newExperiment);
			columnDescription.setSortIndex(-1.0d);
			columnDescription.setVisible(false);
			columnDescription.setSystemColumn(true);
			this.entityManager.persist(columnDescription);

			columnDescription = new ColumnDescription();
			columnDescription.setTitle("Row");
			columnDescription.setDescription("Row ID");
			columnDescription.setColumn("id");
			columnDescription.setDataType("java.lang.Long");
			columnDescription.setExperiment(newExperiment);
			columnDescription.setSortIndex(-1.0d);
			columnDescription.setVisible(false);
			columnDescription.setSystemColumn(true);
			this.entityManager.persist(columnDescription);

			columnDescription = new ColumnDescription();
			columnDescription.setTitle("Experiment");
			columnDescription.setDescription("Experiment ID");
			columnDescription.setColumn("experiment_id");
			columnDescription.setDataType("java.lang.Long");
			columnDescription.setExperiment(newExperiment);
			columnDescription.setSortIndex(-1.0d);
			columnDescription.setVisible(false);
			columnDescription.setSystemColumn(true);
			this.entityManager.persist(columnDescription);

			columnDescription = new ColumnDescription();
			columnDescription.setTitle("Accession");
			columnDescription.setDescription("Accession ID");
			columnDescription.setColumn("accession_id");
			columnDescription.setDataType("java.lang.Long");
			columnDescription.setExperiment(newExperiment);
			columnDescription.setSortIndex(-1.0d);
			columnDescription.setVisible(false);
			columnDescription.setSystemColumn(true);
			this.entityManager.persist(columnDescription);
		}
	}

	private String createExperimentTables(Experiment newExperiment) throws ExperimentException {
		String proposedName = StringUtil.fromHumanToCamel(newExperiment.getTitle());
		LOG.debug("Proposed table name: " + proposedName);
		int attempt = 0;
		String testName = proposedName;
		try {
			while (this.databaseService.tableExists(testName)) {
				testName = String.format("%1$s%2$d", proposedName, attempt);
				LOG.debug("Proposed table name: " + proposedName);
				if (attempt++ > 20)
					throw new ExperimentException("Failed to create table in 20 tries. Quitting.");
			}
		} catch (SQLException e) {
			LOG.error(e);
			throw new ExperimentException("Failed to check for table. " + e.getMessage(), e);
		}
		LOG.info("Creating table `" + testName + "` for " + newExperiment.getTitle());

		/*
		 * CREATE TABLE `accession2`.`foo1` ( `id` bigint(20) NOT NULL AUTO_INCREMENT, `experiment_id` bigint(20) NOT NULL, `accession_id` bigint(20) NOT NULL,
		 * PRIMARY KEY (`id`), KEY `foo1_exp` (`experiment_id`), KEY `foo1_acc` (`accession_id`), CONSTRAINT `foo1_exp` FOREIGN KEY (`experiment_id`) REFERENCES
		 * `Experiment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT `foo1_acc` FOREIGN KEY (`accession_id`) REFERENCES `Accession` (`id`) ON DELETE
		 * CASCADE ON UPDATE CASCADE ) ENGINE=InnoDB DEFAULT CHARSET=utf8
		 */
		String query = String.format(
				"CREATE TABLE `%1$s` (`id` bigint NOT NULL AUTO_INCREMENT, `experiment_id` bigint NOT NULL, `accession_id` bigint NOT NULL, "
						+ " `accessionName` VARCHAR(100) NOT NULL, "
						+ "PRIMARY KEY (`id`), KEY `%1$s_exp` (`experiment_id`), KEY `%1$s_acc` (`accession_id`), "
						+ "CONSTRAINT `%1$s_exp` FOREIGN KEY (`experiment_id`) REFERENCES `Experiment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE, "
						+ "CONSTRAINT `%1$s_acc` FOREIGN KEY (`accession_id`) REFERENCES `Accession` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=UTF8", testName);
		LOG.info("Create query: " + query);
		PreparedStatement pstmt = null;
		try {
			pstmt = this.databaseService.prepare(query);
		} catch (SQLException e) {
			throw new ExperimentException("Experiment data table creation error: " + e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ExperimentException("Experiment data table creation error: " + e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				LOG.error(e);
			}
		}

		return testName;
	}

	/**
	 * @throws ExperimentException
	 * @see org.iita.accessions2.service.ExperimentService#addColumnDescription(org.iita.accessions2.model.Experiment, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, boolean)
	 */
	@Override
	@Transactional
	public ColumnDescription addColumnDescription(Experiment experiment, String title, String description, String column, String dataType, boolean coded)
			throws ExperimentException {
		// Generate columnDescription record
		ColumnDescription columnDescription = new ColumnDescription();
		columnDescription.setTitle(title);
		columnDescription.setDescription(description);
		columnDescription.setColumn(column);
		if (coded) {
			columnDescription.setDataType("java.lang.Integer");
			columnDescription.setColumnType(ColumnType.CODED);
		} else {
			columnDescription.setDataType(dataType);
			columnDescription.setColumnType(ColumnType.ACTUAL);
		}
		columnDescription.setExperiment(experiment);
		columnDescription.setSortIndex(2);

		// alter table!!!
		String columnName;
		try {

			columnName = this.databaseService.addColumn(experiment.getTableName(), columnDescription.getColumn(), null, Class.forName(columnDescription
					.getDataType()));
			columnDescription.setColumn(columnName);
			this.entityManager.persist(columnDescription);
			return columnDescription;

		} catch (SQLException e) {
			LOG.error("Error adding column `" + columnDescription.getColumn() + "` to table `" + experiment.getTableName() + "`: " + e.getMessage());
			throw new ExperimentException("Error adding column `" + columnDescription.getColumn() + "` to table `" + experiment.getTableName() + "`: "
					+ e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			LOG.error("What class is " + columnDescription.getDataType());
			throw new ExperimentException("What class is " + columnDescription.getDataType());
		}

	}

	/**
	 * @throws ExperimentImportException
	 * @see org.iita.accessions2.service.ExperimentService#addCodingValues(org.iita.accessions2.model.Experiment, org.apache.poi.hssf.usermodel.HSSFSheet,
	 *      java.lang.Integer, java.lang.Long)
	 */
	@Override
	@Transactional
	public void addCodingValues(Experiment experiment, HSSFSheet sheet, int cellnum, long columnDescriptionId) throws ExperimentImportException {
		ColumnDescription columnDescription = this.entityManager.find(ColumnDescription.class, columnDescriptionId);
		if (columnDescription == null)
			throw new ExperimentImportException("Column does not exist");
		if (!columnDescription.isCoded())
			throw new ExperimentImportException("Column `" + columnDescription.getTitle() + "` is not coded. Will not add coding!");

		List<Coding> codings = columnDescription.getCoding();
		Hashtable<Integer, Coding> codedToCoding = new Hashtable<Integer, Coding>();
		Hashtable<String, Coding> decodedToCoding = new Hashtable<String, Coding>();
		for (Coding coding : codings) {
			codedToCoding.put(coding.getCodedValue(), coding);
			decodedToCoding.put(coding.getActualValue(), coding);
		}

		int firstRow = sheet.getFirstRowNum() + 1;
		int lastRow = sheet.getLastRowNum();

		for (int i = firstRow + 1; i <= lastRow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			HSSFCell cell = row.getCell(cellnum);
			if (cell == null)
				continue;
			Object value = XLSImportService.getCellValue(cell);
			if (value instanceof Double) {
				int codedValue = ((Double) value).intValue();
				if (!codedToCoding.containsKey(codedValue)) {
					Coding coding = new Coding();
					coding.setCodedValue(codedValue);
					coding.setActualValue("" + codedValue);
					coding.setColumn(columnDescription);
					columnDescription.getCoding().add(coding);
					codedToCoding.put(coding.getCodedValue(), coding);
					decodedToCoding.put(coding.getActualValue(), coding);
				}
			} else if (value != null) {
				String strvalue = "" + value;
				if (!decodedToCoding.containsKey(strvalue.trim())) {
					Coding coding = new Coding();
					int codedValue = 1;
					while (codedToCoding.containsKey(codedValue))
						codedValue++;
					coding.setCodedValue(codedValue);
					coding.setActualValue(strvalue);
					coding.setColumn(columnDescription);
					columnDescription.getCoding().add(coding);
					codedToCoding.put(coding.getCodedValue(), coding);
					decodedToCoding.put(coding.getActualValue(), coding);
				}
			}
		}
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#list(int, int)
	 */
	@Override
	@Transactional
	public PagedResult<Experiment> list(int startAt, int maxResults) {
		PagedResult<Experiment> paged = new PagedResult<Experiment>(startAt, maxResults);
		paged
				.setResults(this.entityManager.createQuery("from Experiment e order by e.title").setFirstResult(startAt).setMaxResults(maxResults)
						.getResultList());
		if (paged.getResults().size() > 0)
			paged.setTotalHits(((Long) this.entityManager.createQuery("select count(e) from Experiment e").getSingleResult()).longValue());
		return paged;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ColumnDescription> getColumns(Experiment experiment) {
		if (experiment == null)
			return null;
		return this.entityManager.createQuery("from ColumnDescription cd where cd.experiment=? order by cd.sortIndex").setParameter(1, experiment)
				.getResultList();
	}

	private List<ColumnDescription> getVisibleColumns(Experiment experiment) {
		List<ColumnDescription> columns = getColumns(experiment);

		if (columns == null)
			return columns;

		boolean isAuthenticated = Authorize.hasAll("ROLE_USER");

		for (int i = columns.size() - 1; i >= 0; i--)
			if (!isAuthenticated && !columns.get(i).isVisible())
				columns.remove(i);

		return columns;
	}

	@Override
	@Transactional
	public ColumnDescription getColumn(String tableName, String columnName) {
		return (ColumnDescription) this.entityManager.createQuery("from ColumnDescription cd where cd.experiment.tableName=? and cd.column=?").setParameter(1,
				tableName).setParameter(2, columnName).getSingleResult();
	}

	/**
	 * The method will fetch list of columns from underlying database table and compare that to list of existing {@link ColumnDescription} records
	 * 
	 * @throws ExperimentException
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ColumnDescription> getUnmappedColumns(Experiment experiment) throws ExperimentException {
		List<ColumnDescription> columns = getColumns(experiment);
		List<DatabaseColumn> tableColumns;
		try {
			tableColumns = this.databaseService.getColumns(experiment.getTableName());
		} catch (SQLException e) {
			LOG.error(e);
			throw new ExperimentException("Error getting column information: " + e.getMessage(), e);
		}
		List<ColumnDescription> unmappedColumns = new ArrayList<ColumnDescription>();

		for (DatabaseColumn tableColumn : tableColumns) {
			LOG.debug("Checking " + tableColumn.columnName);
			boolean found = false;
			for (ColumnDescription column : columns) {
				if (column.getColumn().equals(tableColumn.columnName)) {
					found = true;
				}
			}

			if ("id".equalsIgnoreCase(tableColumn.columnName))
				found = true;

			if (experiment.getId().equals(1l)) {
				if ("createdBy".equalsIgnoreCase(tableColumn.columnName))
					found = true;
				else if ("createdDate".equalsIgnoreCase(tableColumn.columnName))
					found = true;
				else if ("lastUpdated".equalsIgnoreCase(tableColumn.columnName))
					found = true;
				else if ("lastUpdatedBy".equalsIgnoreCase(tableColumn.columnName))
					found = true;
				else if ("version".equalsIgnoreCase(tableColumn.columnName))
					found = true;
			} else {
				if ("experiment_id".equalsIgnoreCase(tableColumn.columnName))
					found = true;
				else if ("accession_id".equalsIgnoreCase(tableColumn.columnName))
					found = true;
			}

			if (!found) {
				ColumnDescription newColumn = new ColumnDescription();
				newColumn.setTitle(tableColumn.columnName);
				newColumn.setColumn(tableColumn.columnName);
				newColumn.setColumnType(ColumnType.ACTUAL);
				newColumn.setDataType(tableColumn.typeName);
				newColumn.setExperiment(experiment);
				newColumn.setDescription(tableColumn.toString());
				unmappedColumns.add(newColumn);
			}
		}

		return unmappedColumns;
	}

	/**
	 * Generate mappings data for sheet
	 */
	@Override
	public Mappings getMappings(HSSFSheet sheet, Experiment experiment) {
		List<ColumnDescription> columns = this.getColumns(experiment);

		Mappings mappings = new Mappings();
		mappings.xlsColumns = new ArrayList<XLSColumnData>();

		// check first row for names
		HSSFRow row = sheet.getRow(0);
		for (int cellnum = row.getFirstCellNum(); cellnum <= row.getLastCellNum(); cellnum++) {
			XLSColumnData xlsColumn = new XLSColumnData();
			HSSFCell cell = row.getCell(cellnum);
			if (cell == null)
				continue;
			xlsColumn.columnNum = cellnum;
			xlsColumn.name = cell.getRichStringCellValue().getString();
			xlsColumn.sampleData = getSampleData(sheet, cellnum);
			xlsColumn.dataType = "" + getDataType(sheet, cellnum);
			mappings.xlsColumns.add(xlsColumn);

			for (ColumnDescription column : columns) {
				if (column.getTitle().equalsIgnoreCase(xlsColumn.name) && !mappings.mappings.containsValue(column.getId())) {
					mappings.mappings.put(xlsColumn.columnNum, column.getId());
				} else if (column.getColumn().equalsIgnoreCase(xlsColumn.name) && !mappings.mappings.containsValue(column.getId())) {
					mappings.mappings.put(xlsColumn.columnNum, column.getId());
				}
			}
		}

		return mappings;
	}

	private Class<? extends Object> getDataType(HSSFSheet sheet, int cellnum) {
		int firstRow = sheet.getFirstRowNum() + 1;
		int lastRow = sheet.getLastRowNum();
		int count = 0;
		Class<? extends Object> lastType = null;
		for (int i = firstRow + 1; i <= lastRow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			HSSFCell cell = row.getCell(cellnum);
			if (cell == null)
				continue;
			Object value = XLSImportService.getCellValue(cell);
			if (value != null) {
				if (lastType == null) {
					lastType = value.getClass();
				} else if (lastType != value.getClass()) {
					LOG.debug("Type match: " + lastType + " != " + value.getClass());
				}
				lastType = value.getClass();
				// stop at 10 values
				if (count++ > 10)
					return lastType;
			}
		}
		return lastType;
	}

	/**
	 * Get sample data from sheet for column <code>cellnum</code>
	 * 
	 * @param sheet
	 * @param cellnum
	 * @return
	 */
	private String getSampleData(HSSFSheet sheet, int cellnum) {
		List<String> data = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		int firstRow = sheet.getFirstRowNum() + 1;
		int lastRow = sheet.getLastRowNum();
		int count = 0;
		for (int i = firstRow; i <= lastRow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			HSSFCell cell = row.getCell(cellnum);
			if (cell == null)
				continue;
			Object value = XLSImportService.getCellValue(cell);
			if (value != null) {
				String str = value.toString();
				if (!data.contains(str)) {
					if (sb.length() != 0)
						sb.append(", ");
					sb.append(value);

					data.add(str);

					// stop at 10 values
					if (count++ > 10)
						break;
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Experiment> listExperiments() {
		return this.entityManager.createQuery("from Experiment e order by e.title").getResultList();
	}

	/**
	 * Find experiments that are used by this collection
	 * 
	 * @param collection
	 * @return
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Experiment> listExperiments(Collection collection) {
		return this.entityManager.createQuery("select distinct e from Accession a inner join a.experiments e where a.collection=:collection").setParameter(
				"collection", collection).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iita.accessions2.service.ExperimentService#tryImport(org.iita.accessions2.model.Experiment, org.apache.poi.hssf.usermodel.HSSFSheet,
	 * org.iita.accessions2.service.ExperimentService.Mappings, java.util.Hashtable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void tryImport(Experiment experiment, HSSFSheet sheet, boolean createMissing, Mappings mappings, Hashtable<String, Object> defaultValues)
			throws ExperimentImportException {
		List<Collection> collections = this.entityManager.createQuery("from Collection c").getResultList();
		List<ColumnDescription> mappedColumns = new ArrayList<ColumnDescription>();
		List<Integer> xlsColumns = new ArrayList<Integer>();
		int accessionNameColumn = -1;
		long maxAccessionId = -1;

		boolean updatingPassportData = experiment.getTableName().equals("Accession");

		if (updatingPassportData) {
			Long foo = (Long) this.entityManager.createQuery("select max(a.id) from Accession a").getSingleResult();
			if (foo != null)
				maxAccessionId = foo.longValue();

			LOG.info("Maximum ID in Accession table: " + maxAccessionId);
		}

		for (XLSColumnData xlsColumn : mappings.xlsColumns) {
			Long columnDescriptionId = mappings.mappings.get(xlsColumn.columnNum);

			if (columnDescriptionId == null)
				// not mapped
				continue;

			LOG.info("Column " + xlsColumn.name + " is mapped to " + columnDescriptionId);
			ColumnDescription columnDescription = this.entityManager.find(ColumnDescription.class, columnDescriptionId);
			LOG.info("Column description: " + columnDescription.getTitle() + " == " + columnDescription.getDataType());
			mappedColumns.add(columnDescription);
			xlsColumns.add(xlsColumn.columnNum);
			checkAllData(sheet, xlsColumn.columnNum, columnDescription);

			// does this column contain "accessionName"?
			if (columnDescription.getColumn().equals("accessionName")) {
				accessionNameColumn = xlsColumn.columnNum;
				LOG.info("Column " + accessionNameColumn + " contains `Accession Name`");
			}
		}

		// do default values
		List<String> includedDefaults = new ArrayList<String>();
		for (String defaultColumn : defaultValues.keySet()) {
			boolean toInsert = true;
			for (ColumnDescription columnDescription : mappedColumns) {
				if (defaultColumn.equals(columnDescription.getColumn())) {
					toInsert = false;
					break;
				}
			}
			if (toInsert) {
				// to add to insert SQL statement
				includedDefaults.add(defaultColumn);
			}
		}

		//
		// Build "find existing experiment row" query
		Query queryFind = this.entityManager.createNativeQuery(buildFindExperimentRowQuery(experiment));
		// set experiment_id value
		if (!updatingPassportData)
			queryFind.setParameter(2, experiment.getId());

		//
		// Build insert query
		Query queryInsert = this.entityManager.createNativeQuery(buildInsertQuery(experiment, mappedColumns, includedDefaults));

		//
		// Build update query
		Query queryUpdate = this.entityManager.createNativeQuery(buildUpdateQuery(experiment, mappedColumns, includedDefaults));

		//
		// Build find accession
		Query queryAccession = this.entityManager.createQuery("select a.id from Accession a where a.name=?");

		//
		// List of updated accessions
		List<Long> updatedAccessions = new ArrayList<Long>();

		int firstRow = sheet.getFirstRowNum() + 1;
		int lastRow = sheet.getLastRowNum();
		LOG.info("First ROW: " + firstRow + " last ROW: " + lastRow);
		for (int i = firstRow; i <= lastRow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;

			// must find "accessionName" value first
			HSSFCell nameCell = row.getCell(accessionNameColumn);
			String accessionName = (String) XLSImportService.getCellValue(nameCell, String.class);
			if (accessionName == null) {
				// if that is missing from row, we skip row
				LOG.warn("Row " + i + " does not provide accession name. Skipping.");
				continue;
			}
			LOG.info("Row " + i + " is for accession: " + accessionName);

			// find existing experiment data row
			Long existingRowId = null;
			queryFind.setParameter(1, accessionName);
			try {
				BigInteger bigi = (BigInteger) queryFind.getSingleResult();
				if (bigi != null)
					existingRowId = bigi.longValue();
				LOG.debug("Existing row with id=" + existingRowId);
			} catch (NoResultException e) {
				// LOG.debug("No existing row");
			}

			Query query = null;
			if (existingRowId == null) {
				query = queryInsert;
			} else {
				query = queryUpdate;
				query.setParameter("existingId", existingRowId);
				if (updatingPassportData)
					updatedAccessions.add(existingRowId);
			}

			// link to Accession
			if (!updatingPassportData) {
				// find Accession
				Long accessionId = null;
				try {
					accessionId = (Long) queryAccession.setParameter(1, accessionName).getSingleResult();
				} catch (NoResultException e) {

				}

				if (accessionId == null && createMissing) {
					// accession not found
					Accession accession = new Accession();
					accession.setCollection(findCollectionByPrefix(accessionName, collections));
					accession.setName(accessionName);
					this.entityManager.persist(accession);
					accessionId = accession.getId();
					LOG.info("Created accession `" + accessionName + " with id=" + accessionId);
				} else if (accessionId == null && !createMissing) {
					LOG.warn("Not creating Accession with name: " + accessionName);
					continue;
				} else {
					// accession found
					// LOG.debug("Found accession: " + accessionId);
				}
				// accession ID
				query.setParameter(1, accessionId);

				// add accession to list
				updatedAccessions.add(accessionId);
			} else {
				// updating passport data, find collection
				Collection collection = findCollectionByPrefix(accessionName, collections);
				if (collection != null)
					query.setParameter(1, collection.getId());
				else
					query.setParameter(1, null);
			}

			int parameterOffset = updatingPassportData ? 2 : 2;

			// defaults
			for (int j = 0; j < includedDefaults.size(); j++) {
				query.setParameter(j + parameterOffset, defaultValues.get(includedDefaults.get(j)));
			}

			// xls values
			int xlsC = xlsColumns.size();
			for (int j = 0; j < xlsC; j++) {
				Integer cellnum = xlsColumns.get(j);
				ColumnDescription columnDescription = mappedColumns.get(j);
				HSSFCell cell = row.getCell(cellnum);
				Object value = XLSImportService.getCellValue(cell, mappedColumns.get(j).getDataClass());
				try {
					value = columnDescription.convertToCoded(value);
				} catch (CodingException e) {
					throw new ExperimentImportException("Value " + value + " at (" + i + ", " + cellnum + ") for " + columnDescription.getTitle()
							+ " cannot be coded: " + e.getMessage());
				}
				query.setParameter(includedDefaults.size() + j + parameterOffset, value);
			}

			// done with row
			query.executeUpdate();
		}

		if (!updatingPassportData) {
			recreateAccessionExperimentsList(experiment);
		} else {
			// get newly inserted accession ids
			List<Long> newAccessionIds = this.entityManager.createQuery("select a.id from Accession a where a.id>?").setParameter(1, maxAccessionId)
					.getResultList();
			LOG.info("Number of new records in Accession table: " + newAccessionIds.size());
			updatedAccessions.addAll(newAccessionIds);
			// need to reindex updated Accessions
			reindexAccessions(updatedAccessions);
		}

		LOG.info("Done with import.");
	}

	/**
	 * @param experiment
	 * 
	 */
	private void recreateAccessionExperimentsList(Experiment experiment) {
		// need to "map" Accessions to This experiment
		// clean first
		this.entityManager.createNativeQuery("DELETE FROM `AccessionExperiment` WHERE `experiments_id`=?").setParameter(1, experiment.getId()).executeUpdate();
		// create list
		this.entityManager.createNativeQuery(
				"INSERT INTO `AccessionExperiment` (`experiments_id`, `accession_id`) SELECT :experimentId, `accession_id` FROM `" + experiment.getTableName()
						+ "` WHERE `experiment_id`=:experimentId").setParameter("experimentId", experiment.getId()).executeUpdate();
	}

	/**
	 * @param updatedAccessions
	 */
	@Transactional
	private void reindexAccessions(List<Long> updatedAccessions) {
		// need to reindex stuff!
		LOG.info("Indexer to updated some accessions: " + updatedAccessions.size());
		FullTextEntityManager ftEm = Search.createFullTextEntityManager(this.entityManager);

		int totalCount = updatedAccessions.size();
		// reindex lots in batches
		for (int i = 0; i < totalCount; i++) {
			Accession accession = ftEm.find(Accession.class, updatedAccessions.get(i));

			LOG.debug("Indexing " + accession.getName());
			ftEm.index(accession);
		}

		ftEm.close();
	}

	/**
	 * @param accessionName
	 * @param collections
	 * @return
	 */
	private Collection findCollectionByPrefix(String accessionName, List<Collection> collections) {
		for (Collection collection : collections) {
			if (collection.hasAccessionPrefix(accessionName))
				return collection;
		}
		return null;
	}

	private String buildFindExperimentRowQuery(Experiment experiment) {
		StringBuffer query = new StringBuffer();
		if (experiment.getTableName().equals("Accession"))
			query.append("SELECT `id` FROM `").append(experiment.getTableName()).append("` WHERE `accessionName`=?");
		else
			query.append("SELECT `id` FROM `").append(experiment.getTableName()).append("` WHERE `accessionName`=? AND `experiment_id`=?");
		LOG.info("FIND ROW QUERY: " + query.toString());
		return query.toString();
	}

	private String buildFindExperimentRowQueryById(Experiment experiment) {
		StringBuffer query = new StringBuffer();
		if (experiment.getTableName().equals("Accession"))
			query.append("SELECT `id` FROM `").append(experiment.getTableName()).append("` WHERE `id`=?");
		else
			query.append("SELECT `id` FROM `").append(experiment.getTableName()).append("` WHERE `accession_id`=? AND `experiment_id`=?");
		LOG.info("FIND ROW QUERY: " + query.toString());
		return query.toString();
	}

	private String buildInsertQuery(Experiment experiment, List<ColumnDescription> mappedColumns, List<String> includedDefaults) {
		StringBuffer query = new StringBuffer();
		if (experiment.getTableName().equals("Accession"))
			query.append("INSERT INTO `").append(experiment.getTableName()).append("` (`collection_id`, ");
		else
			query.append("INSERT INTO `").append(experiment.getTableName()).append("` (`accession_id`, ");
		// add defaults
		if (includedDefaults != null)
			for (int i = 0; i < includedDefaults.size(); i++) {
				query.append("`").append(includedDefaults.get(i)).append("`, ");
			}
		// add mapped columns
		for (int i = 0; i < mappedColumns.size(); i++) {
			if (includedDefaults.contains(mappedColumns.get(i).getColumn()))
				continue;
			query.append("`").append(mappedColumns.get(i).getColumn()).append("`, ");
		}
		query.delete(query.length() - 2, query.length());

		if (experiment.getTableName().equals("Accession"))
			query.append(") VALUES (?, ");
		else
			query.append(") VALUES (?, ");

		// defaults
		if (includedDefaults != null)
			for (int i = 0; i < includedDefaults.size(); i++) {
				query.append("?, ");
			}

		// mapped columns
		for (int i = 0; i < mappedColumns.size(); i++) {
			query.append("?, ");
		}
		query.delete(query.length() - 2, query.length());

		query.append(")");
		LOG.info("INSERT QUERY: " + query.toString());
		return query.toString();
	}

	private String buildUpdateQuery(Experiment experiment, List<ColumnDescription> mappedColumns, List<String> includedDefaults) {
		StringBuffer query = new StringBuffer();

		if (experiment.getTableName().equals("Accession"))
			query.append("UPDATE `").append(experiment.getTableName()).append("` SET `collection_id`=?, ");
		else
			query.append("UPDATE `").append(experiment.getTableName()).append("` SET `accession_id`=?, ");

		// add defaults
		if (includedDefaults != null)
			for (int i = 0; i < includedDefaults.size(); i++) {
				query.append("`").append(includedDefaults.get(i)).append("`=?, ");
			}
		// add mapped columns
		for (int i = 0; i < mappedColumns.size(); i++) {
			if (includedDefaults.contains(mappedColumns.get(i).getColumn()))
				continue;
			query.append("`").append(mappedColumns.get(i).getColumn()).append("`=?, ");
		}
		query.delete(query.length() - 2, query.length());
		query.append(" WHERE `id`=:existingId");

		LOG.info("UPDATE QUERY: " + query.toString());
		return query.toString();
	}

	/**
	 * @param sheet
	 * @param cellnum
	 * @param columnDescription
	 * @throws ExperimentImportException
	 */
	private void checkAllData(HSSFSheet sheet, int cellnum, ColumnDescription columnDescription) throws ExperimentImportException {
		int firstRow = sheet.getFirstRowNum() + 1;
		int lastRow = sheet.getLastRowNum();
		LOG.info("Checking column " + cellnum + " against " + columnDescription.getTitle());
		for (int i = firstRow + 1; i <= lastRow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			HSSFCell cell = row.getCell(cellnum);
			if (cell == null)
				continue;
			Class<?> dataClass = columnDescription.getDataClass();
			Object value = XLSImportService.getCellValue(cell, dataClass);
			if (value != null) {
				try {
					value = columnDescription.convertToCoded(value);
				} catch (CodingException e) {
					throw new ExperimentImportException("Value " + value + " at (" + i + ", " + cellnum + ") for " + columnDescription.getTitle()
							+ " cannot be coded: " + e.getMessage(), e);
				}

				if (value.getClass() != dataClass) {
					if(value instanceof Integer){
						value = (Object) value;
						
						//value = (Long) value;
					}else{
						LOG.error("Data " + value + " type mismatch: " + value.getClass() + " != " + dataClass);
						throw new ExperimentImportException("Value " + value + " at (" + i + ", " + cellnum + ") is not of type " + dataClass + " but is "
							+ value.getClass());
					}
				}
			}
		}
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#tryImport(org.iita.accessions2.model.Experiment, org.apache.poi.hssf.usermodel.HSSFSheet,
	 *      org.iita.accessions2.service.ExperimentService.Mappings, int)
	 */
	@Override
	public Exception tryImport(Experiment experiment, HSSFSheet sheet, boolean createMissing, Mappings mappings, int columnNum) {
		Long columnDescriptionId = mappings.mappings.get(columnNum);
		if (columnDescriptionId == null)
			return null;
		ColumnDescription columnDescription = this.entityManager.find(ColumnDescription.class, columnDescriptionId);
		try {
			checkAllData(sheet, columnNum, columnDescription);
		} catch (ExperimentImportException e) {
			LOG.error(e.getMessage());
			return e;
		}
		return null;
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#update(org.iita.accessions2.model.ColumnDescription)
	 */
	@Override
	@Transactional
	public void update(MySqlBaseEntity data) {
		if (data == null)
			return;
		if (data.getId() != null)
			this.entityManager.merge(data);
		else
			this.entityManager.persist(data);
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#getMCPDColumns()
	 */
	@Override
	@Transactional
	public List<ColumnDescription> getMCPDColumns() {
		Experiment mcpd = this.find(1l);
		return this.getColumns(mcpd);
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#listData(org.iita.accessions2.model.Experiment, int, int)
	 */
	@Override
	@Transactional
	public PagedResult<Object[]> listData(Experiment experiment, int startAt, int maxResults) {
		return listData(null, experiment, startAt, maxResults);
	}

	/**
	 * @param filters
	 * @param experiment
	 * @param startAt
	 * @param maxResults
	 * @return
	 */
	@Override
	public PagedResult<Object[]> listData(Filters filters, Experiment experiment, int startAt, int maxResults) {
		// get total row count
		Long totalRowCount = -1l;
		List<Object> parameters = new ArrayList<Object>();

		// need to build query
		StringBuffer query = new StringBuffer();
		List<ColumnDescription> columns = getVisibleColumns(experiment);
		query.append("SELECT `id`, ");
		for (int i = 0; i < columns.size(); i++) {
			ColumnDescription columnDescription = columns.get(i);
			if (i > 0)
				query.append(", ");
			query.append("`").append(columnDescription.getColumn()).append("`");
		}
		// table
		query.append(" FROM ").append(experiment.getTableName());

		StringBuffer totalQuery = new StringBuffer("SELECT COUNT(`id`) FROM ");
		totalQuery.append(experiment.getTableName());
		if (filters != null) {
			List<Filter> tableFilters = filters.getFilters(experiment.getTableName());
			if (tableFilters.size() > 0) {
				// total filters
				totalQuery.append(" where 0=0 ");
				for (Filter filter : tableFilters) {
					totalQuery.append(" and (");
					int i = 0;
					for (Object value : filter.getFilterValues()) {
						if (i++ > 0)
							totalQuery.append(" or ");
						totalQuery.append(experiment.getTableName()).append(".").append(filter.getColumn()).append("=?");
						parameters.add(value);
					}
					totalQuery.append(")");
				}

				// query filters
				query.append(" where 0=0 ");
				for (Filter filter : tableFilters) {
					query.append(" and (");
					int i = 0;
					for (@SuppressWarnings("unused")
					Object value : filter.getFilterValues()) {
						if (i++ > 0)
							query.append(" or ");
						query.append(experiment.getTableName()).append(".").append(filter.getColumn()).append("=?");
					}
					query.append(")");
				}
			}
		}

		// paging
		query.append(" LIMIT ").append(startAt).append(", ").append(maxResults);

		try {
			LOG.info("Total query: " + totalQuery.toString());
			totalRowCount = (Long) this.databaseService.getSingleResult(totalQuery.toString(), parameters);
		} catch (SQLException e1) {
			LOG.error(e1);
		}

		LOG.info("Experiment data query: " + query.toString());

		// fetch data from databaseService
		PreparedStatement pstmt = null;
		try {
			pstmt = this.databaseService.prepare(query.toString());
			if (parameters != null) {
				int paramPos = 1;
				for (Object param : parameters)
					pstmt.setObject(paramPos++, param);
			}
			LOG.info("Statement ready");
		} catch (SQLException e) {
			LOG.error(e);
			return null;
		}
		try {
			LOG.info("Executing query");
			ResultSet resultSet = pstmt.executeQuery();
			ResultSetMetaData meta = resultSet.getMetaData();
			int columnCount = meta.getColumnCount();
			// LOG.info("Got " + columnCount + " columns of data");
			PagedResult<Object[]> data = new PagedResult<Object[]>(startAt, maxResults);
			ArrayList<Object[]> rowList = new ArrayList<Object[]>();
			data.setResults(rowList);
			data.setTotalHits(totalRowCount);
			while (resultSet.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 0; i < columnCount; i++)
					rowData[i] = resultSet.getObject(i + 1);
				rowList.add(rowData);
				// LOG.debug("Added row.");
			}
			resultSet.close();
			pstmt.close();
			return data;

		} catch (SQLException e) {
			LOG.error(e);
			return null;
		}
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#listData(org.iita.accessions2.model.Experiment, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Dictionary<String, Object> getExperimentData(Experiment experiment, Accession accession) {
		// late load column information
		List<ColumnDescription> columns = experiment.getColumns();

		// need to build query
		StringBuffer query = new StringBuffer();
		query.append("SELECT `id`, ");
		for (int i = 0; i < columns.size(); i++) {
			if (i > 0)
				query.append(", ");
			query.append("`").append(columns.get(i).getColumn()).append("`");
		}
		// table
		query.append(" FROM ").append(experiment.getTableName());
		// filtering
		if (experiment.getId() == 1l)
			query.append(" WHERE `id`=? ");
		else
			query.append(" WHERE `accession_id`=? AND `experiment_id`=? ");

		LOG.info("Accession Experiment data query: " + query.toString());

		// fetch data from databaseService
		Query querySelect = this.entityManager.createNativeQuery(query.toString());
		querySelect.setParameter(1, accession.getId());
		if (experiment.getId() != 1l)
			querySelect.setParameter(2, experiment.getId());
		querySelect.setMaxResults(1);

		List<Object[]> results = querySelect.getResultList();

		Dictionary<String, Object> accessionData = new Hashtable<String, Object>();
		if (results.size() > 0) {
			Object[] rowData = results.get(0);
			int columnCount = rowData.length;
			for (int i = 0; i < columnCount; i++) {
				if (i == 0)
					accessionData.put("id", rowData[i]);
				else if (rowData[i] != null) {
					LOG.debug("Putting " + columns.get(i - 1) + " == " + rowData[i]);
					accessionData.put(columns.get(i - 1).getColumn(), rowData[i]);
				}
			}
		}

		return accessionData;
	}

	/**
	 * @throws ExperimentException
	 * @see org.iita.accessions2.service.ExperimentService#updateCoding(org.iita.accessions2.model.ColumnDescription)
	 */
	@Override
	@Transactional
	public void updateCoding(ColumnDescription column, Experiment experiment) throws ExperimentException {
		List<Coding> codings = column.getCoding();
		if (codings != null)
			for (int i = codings.size() - 1; i >= 0; i--) {
				Coding coding = codings.get(i);
				LOG.debug("Checking coded value: " + coding.getCodedValue() + " -- " + coding.getActualValue());
				coding.setColumn(column);
				coding.setActualValue(coding.getActualValue().trim());
				if (coding.getActualValue().length() == 0) {
					removeCoding(column.getExperiment(), column, coding);
					codings.remove(coding);
				} else {
					// check if this matches any other name starting from 0 to this index
					for (int j = 0; j < i; j++) {
						Coding prevCoding = codings.get(j);
						if (prevCoding.getActualValue().equals(coding.getActualValue())) {
							LOG.info("Coded value: " + coding.getActualValue() + " matches " + prevCoding.getActualValue() + " will now merge");
							// same actual value. Need to merge "coding" to "prevCoding"
							mergeCoding(experiment, column, coding, prevCoding);
							codings.remove(coding);
							// break this loop
							break;
						}
					}
				}
			}

		if (column.getExperiment() == null && experiment != null) {
			column.setExperiment(experiment);
			this.entityManager.persist(column);
		} else {
			this.entityManager.merge(column);
		}
	}

	/**
	 * @param codingToMerge Coding to merge to destination
	 * @param codingDestination Destination coding
	 */
	@Transactional
	private void mergeCoding(Experiment experiment, ColumnDescription column, Coding codingToMerge, Coding codingDestination) {
		Query query = this.entityManager.createNativeQuery(String.format("SELECT COUNT(`%2$s`) FROM `%1$s` WHERE `%2$s`=?", experiment.getTableName(), column
				.getColumn()));
		BigInteger rows = (BigInteger) query.setParameter(1, codingToMerge.getCodedValue()).getSingleResult();
		if (rows.longValue() > 0) {
			LOG.warn("There are " + rows + " rows in " + experiment.getTableName() + "." + column.getColumn() + ". Merging to "
					+ codingDestination.getCodedValue());
			Query mergeQuery = this.entityManager.createNativeQuery(String.format("UPDATE `%1$s` SET `%2$s`=? WHERE `%2$s`=?", experiment.getTableName(),
					column.getColumn()));
			mergeQuery.setParameter(1, codingDestination.getCodedValue());
			mergeQuery.setParameter(2, codingToMerge.getCodedValue());
			int results = mergeQuery.executeUpdate();
			LOG.info("Total of " + results + " rows were merged to " + codingDestination.getActualValue());
			this.entityManager.remove(codingToMerge);
		} else {
			// no values, nothing to do
			this.entityManager.remove(codingToMerge);
		}
	}

	/**
	 * @param experiment
	 * @param column
	 * @param coding
	 * @throws ExperimentException
	 */
	private void removeCoding(Experiment experiment, ColumnDescription column, Coding coding) throws ExperimentException {
		Query query = this.entityManager.createNativeQuery(String.format("SELECT COUNT(`%2$s`) FROM `%1$s` WHERE `%2$s`=?", experiment.getTableName(), column
				.getColumn()));
		BigInteger rows = (BigInteger) query.setParameter(1, coding.getCodedValue()).getSingleResult();
		if (rows.longValue() > 0) {
			LOG.warn("There are " + rows + " rows in " + experiment.getTableName() + "." + column.getColumn() + ". Refusing to remove value: "
					+ coding.getActualValue());
			this.entityManager.refresh(coding);
			throw new ExperimentException("There are " + rows + " rows in " + experiment.getTableName() + "." + column.getColumn()
					+ ". Refusing to remove Coding.");
		} else {
			this.entityManager.remove(coding);
		}
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#getDataHistogram(org.iita.accessions2.model.ColumnDescription)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public DataHistogram getDataHistogram(ColumnDescription column) {
		if (column == null || column.getExperiment() == null)
			return null;
		Query query = this.entityManager.createNativeQuery(String.format("SELECT `%2$s`, COUNT(`%2$s`) FROM `%1$s` GROUP BY `%2$s`", column.getExperiment()
				.getTableName(), column.getColumn()));
		return DataHistogram.fromResultList(query.getResultList());
	}

	/**
	 * Method will convert a column to a coded column (integer type) and store current values as Codings
	 * 
	 * @throws ExperimentException
	 * 
	 * @see org.iita.accessions2.service.ExperimentService#convertToCoded(org.iita.accessions2.model.ColumnDescription)
	 */
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void convertToCoded(ColumnDescription column) throws ExperimentException {
		String tableName = column.getExperiment().getTableName();
		String columnName = column.getColumn();

		LOG.info("Converting column `" + tableName + "`.`" + columnName + "` to coded column");

		if (column.isCoded())
			throw new ExperimentException("Column " + column + " is already coded. Not doing anything.");

		// first use DataHistogram to create Coding entries
		DataHistogram histogram = getDataHistogram(column);

		LOG.info("There are " + (100 - 100.0d * histogram.size() / histogram.getTotal()) + "% same values in this column");

		if (column.getDataType().equals("java.lang.Boolean"))
			column.getCoding().clear();

		// get current coding
		List<Coding> codings = column.getCoding();

		// build lists
		Hashtable<Integer, Coding> codedToCoding = new Hashtable<Integer, Coding>();
		Hashtable<String, Coding> decodedToCoding = new Hashtable<String, Coding>();
		for (Coding coding : codings) {
			codedToCoding.put(coding.getCodedValue(), coding);
			decodedToCoding.put(coding.getActualValue(), coding);
		}

		LOG.info("Column currently has " + codings.size() + " coded values. New values will be appended to this list");
		Integer lastCodedValue = null;

		for (Object value : histogram) {
			if (value == DataHistogram.NULLVALUES)
				continue;

			LOG.debug("Adding " + value + " to coding");
			if (decodedToCoding.containsKey(value)) {
				LOG.warn("Coding already contains value for " + value + ". Skipping.");
			} else {
				Coding coding = new Coding();
				int codedValue = lastCodedValue == null ? 1 : lastCodedValue.intValue();
				while (codedToCoding.containsKey(codedValue))
					codedValue++;
				lastCodedValue = codedValue;
				coding.setActualValue("" + value);
				coding.setCodedValue(codedValue);
				coding.setColumn(column);
				column.getCoding().add(coding);
				codedToCoding.put(coding.getCodedValue(), coding);
				decodedToCoding.put(coding.getActualValue(), coding);
			}
		}

		for (Coding coding : codings) {
			LOG.debug("Coding: " + coding.getCodedValue() + " --> " + coding.getActualValue());
		}

		String convertedColumnName = columnName + "CONV";

		// add new column next to current column of type Integer
		try {
			convertedColumnName = this.databaseService.addColumn(tableName, convertedColumnName, columnName, Integer.class);
			LOG.info("Created temporary column " + convertedColumnName);
		} catch (SQLException e) {
			LOG.error(e);
			throw new ExperimentException("Cannot add temporary column to convert data", e);
		}

		Query migrateQuery = this.entityManager
				.createNativeQuery("UPDATE `" + tableName + "` SET `" + convertedColumnName + "`=? WHERE `" + columnName + "`=?");
		// insert values to temporary column
		for (Coding coding : codings) {
			migrateQuery.setParameter(1, coding.getCodedValue());

			if (column.getDataType().equalsIgnoreCase("java.lang.Boolean")) {
				migrateQuery.setParameter(2, Boolean.parseBoolean(coding.getActualValue()));
			} else {
				migrateQuery.setParameter(2, coding.getActualValue());
			}

			int results = migrateQuery.executeUpdate();
			LOG.debug("Converted '" + coding.getActualValue() + "' to " + coding.getCodedValue() + " resulted in " + results + " updates.");
		}

		// drop existing column
		try {
			this.databaseService.dropColumn(tableName, columnName);
			this.databaseService.renameColumn(tableName, convertedColumnName, columnName, Integer.class);
		} catch (SQLException e) {
			LOG.error(e);
			throw new ExperimentException("Cannot drop and rename column " + e.getMessage(), e);
		}

		column.setColumnType(ColumnType.CODED);
		column.setDataType("java.lang.Integer");
		this.updateCoding(column, null);
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#convertToDecoded(org.iita.accessions2.model.ColumnDescription)
	 */
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void convertToDecoded(ColumnDescription column) throws ExperimentException {
		String tableName = column.getExperiment().getTableName();
		String columnName = column.getColumn();

		LOG.info("Converting column `" + tableName + "`.`" + columnName + "` to coded column");

		if (!column.isCoded())
			throw new ExperimentException("Column " + column + " is not coded. Not doing anything.");

		// first create new String column
		String convertedColumnName = columnName + "CONV";
		try {
			this.databaseService.addColumn(tableName, convertedColumnName, columnName, String.class);
		} catch (SQLException e) {
			LOG.error("Error creating temporary column: " + e.getMessage());
			throw new ExperimentException("Error creating temporary column: " + e.getMessage(), e);
		}

		// get current coding
		List<Coding> codings = column.getCoding();

		// migrate data
		Query migrateQuery = this.entityManager
				.createNativeQuery("UPDATE `" + tableName + "` SET `" + convertedColumnName + "`=? WHERE `" + columnName + "`=?");
		// insert values to temporary column
		for (Coding coding : codings) {
			migrateQuery.setParameter(1, coding.getActualValue());
			migrateQuery.setParameter(2, coding.getCodedValue());
			int results = migrateQuery.executeUpdate();
			LOG.debug("Converted '" + coding.getCodedValue() + "' to " + coding.getActualValue() + " resulted in " + results + " updates.");
			coding.setActualValue("");
		}

		// drop existing column
		try {
			this.databaseService.dropColumn(tableName, columnName);
			this.databaseService.renameColumn(tableName, convertedColumnName, columnName, String.class);
		} catch (SQLException e) {
			LOG.error(e);
			throw new ExperimentException("Cannot drop and rename column " + e.getMessage(), e);
		}

		column.setColumnType(ColumnType.ACTUAL);
		column.setDataType("java.lang.String");

		this.entityManager.merge(column);
		for (Coding coding : column.getCoding()) {
			this.entityManager.remove(coding);
		}
	}

	/**
	 * @throws ExperimentException
	 * @see org.iita.accessions2.service.ExperimentService#exportToXLS(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public InputStream exportToXLS(List<Long> accessionIds) throws ExperimentException {
		LOG.info("Exporting " + accessionIds.size() + " accessions to XLS");
		XLSExportService exportService = new XLSExportService();

		// build query
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		Experiment passportData = (Experiment) this.entityManager.createQuery("from Experiment e where e.tableName='Accession'").getSingleResult();
		List<Experiment> experiments = this.entityManager.createQuery(
				"select distinct(e) from Accession a inner join a.experiments e where a.id in (:accessions)").setParameter("accessions", accessionIds)
				.getResultList();

		List<ColumnDescription> columns = new ArrayList<ColumnDescription>();
		List<String> headings = new ArrayList<String>();
		int i = 0;
		for (ColumnDescription column : passportData.getColumns()) {
			// don't display hidden columns
			if (!column.isVisible())
				continue;

			if (i++ > 0)
				sb.append(", ");
			sb.append("a.").append(column.getColumn()).append("");
			// sb.append(" as {a.").append(column.getColumn()).append("}");
			columns.add(column);
			headings.add(column.getTitle());
		}

		int j = 0;
		for (Experiment experiment : experiments) {
			LOG.info("Experiment: " + experiment + " is public? " + experiment.isVisible());

			// don't display hidden experiment data to anonymous users
			if (!experiment.isVisible() && !Authorize.hasAuthority("ROLE_USER")) {
				LOG.info("Skipping experiment " + experiment.getTitle());
				continue;
			}

			for (ColumnDescription column : experiment.getColumns()) {
				// don't display hidden columns
				if (!column.isVisible())
					continue;

				if (i++ > 0)
					sb.append(", ");
				sb.append("e").append(j).append(".").append(column.getColumn()).append("");
				sb.append(" as e").append(j).append("_").append(column.getColumn()).append("");
				columns.add(column);
				headings.add(column.getTitle());
			}
			j++;
		}

		sb.append(" from ").append(passportData.getTableName()).append(" a");

		i = 0;
		for (Experiment experiment : experiments) {
			sb.append(" left outer join ").append(experiment.getTableName()).append(" e").append(i).append("");
			sb.append(" on e").append(i).append(".accession_id=a.id ");
			i++;
		}

		sb.append(" where a.id in (");
		for (i = 0; i < accessionIds.size(); i++)
			sb.append(i == 0 ? "" : ",").append("?");
		sb.append(")");

		LOG.info("Query: " + sb.toString());

		// get Accession data
		List<Object[]> data = null;
		PreparedStatement nativeQ = null;
		try {
			nativeQ = this.databaseService.prepare(sb.toString());
		} catch (SQLException e1) {
			LOG.error(e1);
		}
		try {
			for (i = 0; i < accessionIds.size(); i++)
				nativeQ.setLong(i + 1, accessionIds.get(i));
		} catch (SQLException e1) {
			LOG.error(e1);
		}
		data = new ArrayList<Object[]>();
		int columnCount = columns.size();
		ResultSet res = null;
		try {
			res = nativeQ.executeQuery();
		} catch (SQLException e1) {
			LOG.error(e1, e1);
		}
		try {
			while (res.next()) {
				Object[] o = new Object[columnCount];
				for (i = 0; i < columnCount; i++)
					o[i] = res.getObject(i + 1);
				data.add(o);
			}
		} catch (SQLException e1) {
			LOG.error(e1);
		}

		// Query query = this.entityManager.createNativeQuery(sb.toString());
		// data = query.setParameter("accessions", accessionIds).getResultList();

		for (Object o[] : data) {
			// System.err.print("Exporting: ");
			for (j = 0; j < columns.size(); j++) {
				ColumnDescription column = columns.get(j);
				if (column.isCoded() && o[j] != null) {
					Coding coding = column.findCoding(getInteger(o[j]));
					if (coding != null)
						o[j] = coding.getActualValue();
					else
						LOG.warn("Missing coded value " + o[j] + " for column " + column.getTitle());
				}
				// System.err.print(o[j]);
				// System.err.print(", ");
			}
			// System.err.println();
		}

		try {
			InputStream templateStream = ExperimentServiceImpl.class.getClassLoader().getResourceAsStream("IITA-Selection.xls");
			return exportService.exportToStream(templateStream, headings.toArray(new String[] {}), data);
		} catch (IOException e) {
			throw new ExperimentException("Error exporting: " + e.getMessage());
		}
	}

	/**
	 * @param object
	 * @return
	 */
	private Integer getInteger(Object numVal) {
		if (numVal == null)
			return null;
		if (numVal instanceof Long)
			return ((Long) numVal).intValue();
		if (numVal instanceof Integer)
			return (Integer) numVal;
		throw new RuntimeException("Cannot convert type: " + numVal.getClass() + " to Integer");
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#convertToBoolean(org.iita.accessions2.model.ColumnDescription)
	 */
	@Override
	@Transactional
	public void convertToBoolean(ColumnDescription column) throws ExperimentException {
		if (!column.isCoded())
			throw new ExperimentException("Column must be 'coded' before it can be converted to Boolean column.");
		if (column.getCoding().size() != 2)
			throw new ExperimentException("Column must have exacly two coded values to be converted to Boolean column.");

		List<Coding> codings = column.getCoding();

		for (Coding coding : codings) {
			if (!coding.getActualValue().toLowerCase().matches("^yes|y|true|present|no|n|false|absent$"))
				throw new ExperimentException("Column actual value " + coding.getActualValue() + " is not one of yes|y|true|present|no|n|false|absent.");
		}

		String tableName = column.getExperiment().getTableName();
		String columnName = column.getColumn();
		String convertedColumnName = column.getColumn() + "CONV";

		// add new column next to current column of type Integer
		try {
			convertedColumnName = this.databaseService.addColumn(tableName, convertedColumnName, columnName, Boolean.class);
			LOG.info("Created temporary column " + convertedColumnName);
		} catch (SQLException e) {
			LOG.error(e);
			throw new ExperimentException("Cannot add temporary column to convert data", e);
		}

		Query migrateQuery = this.entityManager
				.createNativeQuery("UPDATE `" + tableName + "` SET `" + convertedColumnName + "`=? WHERE `" + columnName + "`=?");
		// insert values to temporary column
		for (Coding coding : codings) {
			Boolean booleanValue = null;
			if (coding.getActualValue().toLowerCase().matches("^yes|y|true|present"))
				booleanValue = true;
			else if (coding.getActualValue().toLowerCase().matches("^no|n|false|absent$"))
				booleanValue = false;

			migrateQuery.setParameter(1, booleanValue);
			migrateQuery.setParameter(2, coding.getCodedValue());
			int results = migrateQuery.executeUpdate();
			LOG.debug("Converted '" + coding.getActualValue() + "' to " + booleanValue + " resulted in " + results + " updates.");
		}

		for (Coding coding : column.getCoding()) {
			this.entityManager.remove(coding);
		}
		column.getCoding().clear();

		// drop existing column
		try {
			this.databaseService.dropColumn(tableName, columnName);
			this.databaseService.renameColumn(tableName, convertedColumnName, columnName, Boolean.class);
		} catch (SQLException e) {
			LOG.error(e);
			throw new ExperimentException("Cannot drop and rename column " + e.getMessage(), e);
		}

		column.setColumnType(ColumnType.ACTUAL);
		column.setDataType("java.lang.Boolean");
		this.updateCoding(column, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iita.accessions2.service.ExperimentService#deleteExperiment(org.iita.accessions2.model.Experiment)
	 */
	@Override
	@Transactional
	public void deleteExperiment(Experiment experiment) throws ExperimentException {
		deleteExperimentData(experiment);

		if (experiment.isSystemTable()) {
			LOG.warn("Experiment " + experiment.getTitle() + " is marked as system table, will not drop tables.");
		} else {
			try {
				dropExperimentTables(experiment);
			} catch (SQLException e) {
				throw new ExperimentException("Could not drop experiment tables: " + e.getMessage(), e);
			}
		}

		LOG.info("Removing experiment: " + experiment.getTitle());
		this.entityManager.remove(experiment);
	}

	/**
	 * Delete experiment data.
	 * 
	 * @param experiment Experiment to clear
	 * 
	 * @throws SQLException when data deletion exception occurs
	 */
	@Transactional
	@Override
	public void deleteExperimentData(Experiment experiment) throws ExperimentException {
		try {
			if (!this.databaseService.tableExists(experiment.getTableName())) {
				LOG.info("Experiment table " + experiment.getTableName() + " does not exist. Nothing to do.");
				return;
			}
		} catch (SQLException e) {
			throw new ExperimentException("Could not check if table exists: " + e.getMessage(), e);
		}

		LOG.debug("Deleting data from " + experiment.getTableName());
		PreparedStatement pstmt;
		try {
			pstmt = this.databaseService.prepare(String.format("DELETE FROM `%1$s`", experiment.getTableName()));
			int updates = pstmt.executeUpdate();
			LOG.info("Deleted " + updates + " from " + experiment.getTableName());
			pstmt = this.databaseService.prepare(String.format("TRUNCATE `%1$s`", experiment.getTableName()));
			pstmt.executeUpdate();
			LOG.info("Truncated table " + experiment.getTableName());
		} catch (SQLException e) {
			throw new ExperimentException("Could not delete data from " + experiment.getTableName() + ": " + e.getMessage(), e);
		}
	}

	/**
	 * Drop experiment tables.
	 * 
	 * @param experiment Experiment
	 * 
	 * @throws SQLException when foreign keys or table cannot be dropped
	 */
	@Transactional
	private void dropExperimentTables(Experiment experiment) throws SQLException {
		if (experiment.isSystemTable()) {
			throw new SQLException("Will not drop experiment table marked as system table.");
		}

		if (!this.databaseService.tableExists(experiment.getTableName())) {
			LOG.info("Experiment table " + experiment.getTableName() + " does not exist. Nothing to do.");
			return;
		}

		LOG.debug("Dropping foreign key constraints of " + experiment.getTableName());
		// drop constraints
		PreparedStatement pstmt = this.databaseService.prepare(String.format("ALTER TABLE `%1$s` DROP FOREIGN KEY `%1$s_acc`, DROP FOREIGN KEY `%1$s_exp`;",
				experiment.getTableName()));
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOG.info("Could not remove foreign keys: " + e.getMessage());
		}

		// drop table
		LOG.debug("Dropping table " + experiment.getTableName());
		pstmt = this.databaseService.prepare(String.format("DROP TABLE `%1$s`;", experiment.getTableName()));
		pstmt.executeUpdate();
	}

	@Override
	@Transactional
	public void deleteColumn(ColumnDescription column) throws ExperimentException {
		deleteColumnDescription(column);
		try {
			dropExperimentColumn(column);
		} catch (SQLException e) {
			throw new ExperimentException("Could not drop column: " + e.getMessage(), e);
		}
	}

	/**
	 * Drop experiment column.
	 * 
	 * @param column the column
	 * @throws SQLException
	 */
	private void dropExperimentColumn(ColumnDescription column) throws SQLException {
		if (column.getExperiment().isSystemTable()) {
			throw new SQLException("Will not drop column from experiment marked as system table.");
		}
		LOG.info("Dropping column " + column.getExperiment().getTableName() + "." + column.getColumn());
		PreparedStatement pstmt = this.databaseService.prepare(String.format("ALTER TABLE `%1$s` DROP COLUMN `%2$s`;", column.getExperiment().getTableName(),
				column.getColumn()));
		pstmt.executeUpdate();
	}

	@Override
	@Transactional
	public void deleteColumnDescription(ColumnDescription column) throws ExperimentException {
		LOG.info("Deleting column description " + column.getTitle() + " from " + column.getExperiment().getTitle());
		this.entityManager.remove(column);
	}

	/**
	 * @throws IOException
	 * @see org.iita.accessions2.service.ExperimentService#downloadData(org.iita.accessions2.model.Collection)
	 */
	@Override
	@Transactional(readOnly = true)
	public InputStream downloadData(Collection collection) throws IOException {
		Experiment passportData = this.find(1l);
		Filters collectionFilters = new Filters();
		Filter filter = collectionFilters.addFilter("Accession", "collection_id");
		filter.addValue(collection.getId());
		return downloadData(passportData, collectionFilters);
	}

	/**
	 * @throws IOException
	 * @see org.iita.accessions2.service.ExperimentService#downloadData(org.iita.accessions2.model.Collection, org.iita.accessions2.model.Experiment)
	 */
	@Override
	@Transactional(readOnly = true)
	public InputStream downloadData(Collection collection, Experiment experiment) throws IOException {
		Filters collectionFilters = new Filters();
		Filter filter = collectionFilters.addFilter("Accession", "collection_id");
		filter.addValue(collection.getId());
		return downloadData(experiment, collectionFilters);
	}

	/**
	 * Download GeneSys data format
	 * 
	 * @param collection Collection
	 * @param what "data", "experiment", "traits"
	 * @return InputStream with data
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public InputStream downloadGeneSys(Collection collection) throws IOException {
		Filters collectionFilters = new Filters();
		Filter filter = collectionFilters.addFilter("Accession", "collection_id");
		filter.addValue(collection.getId());

		// make temporary dir
		File tmpDir = File.createTempFile("genesys", "dir");
		if (!tmpDir.exists())
			throw new IOException("Could not create temporary file");

		tmpDir.delete();

		tmpDir.mkdir();

		LOG.info("Got temporary GeneSys directory: " + tmpDir);
		if (!tmpDir.isDirectory())
			throw new IOException("Temporary directory could not be created");

		// find experiments
		List<Experiment> experiments = this.entityManager.createQuery("from Experiment e where e.id>1").getResultList();
		List<Experiment> experimentsUsed = new ArrayList<Experiment>();
		for (Experiment experiment : experiments) {
			if (!experiment.isVisible())
				continue;

			BigInteger count = (BigInteger) this.entityManager.createNativeQuery(
					"select count(*) from " + experiment.getTableName() + " D inner join Accession A on A.id=D.accession_id where A.collection_id=?")
					.setParameter(1, collection.getId()).getSingleResult();
			LOG.debug("Got " + count + " accessions in " + experiment);

			if (count.longValue() > 0)
				experimentsUsed.add(experiment);
		}

		// add passport data
		experimentsUsed.add(0, this.entityManager.find(Experiment.class, 1l));

		LOG.info("Have " + experimentsUsed.size() + " experiments");

		// create Experiments file
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(tmpDir, "Experiments.csv")));
		CSVWriter.addLine(bw, "Experiment Title", "Starting date", "Finishing date", "Location", "Longitude", "Latitude", "Altitude", "Description");
		for (Experiment experiment : experimentsUsed) {
			LOG.debug("Writing experiment data");
			CSVWriter.addLine(bw, experiment.getTitle(), experiment.getExperimentDate(), null, experiment.getLocation(), null, null, null, experiment
					.getDescription());
		}
		bw.flush();
		bw.close();

		bw = new BufferedWriter(new FileWriter(new File(tmpDir, "Traits.csv")));
		CSVWriter.addLine(bw, "Trait Name", "Method description", "Unit", "Codes");
		// coded stuff like this: "interpretation of the code used to explain the data e.g.  B = Black ; W = White".")
		for (Experiment experiment : experimentsUsed) {
			for (ColumnDescription cd : experiment.getColumns()) {
				if (!cd.isVisible())
					continue;

				createGenesysData(tmpDir, cd, collection);

				CSVWriter.addLine(bw, cd.getColumn(), cd.getDescription(), cd.getPostfix(), getGeneSysCodes(cd));
			}
		}
		bw.flush();
		bw.close();

		File zipFile = new File(tmpDir.getParentFile(), tmpDir.getName() + ".zip");
		ZipArchiveOutputStream zos = new ZipArchiveOutputStream(zipFile);
		zos.setComment("GeneSys data");
		zos.setLevel(8);

		int len = 2048;
		byte[] buf = new byte[len];

		for (File file : tmpDir.listFiles()) {
			ZipArchiveEntry ae = new ZipArchiveEntry(file, file.getName());
			zos.putArchiveEntry(ae);
			FileInputStream fis = new FileInputStream(file);
			int pos = 0;
			while ((pos = fis.read(buf, 0, len)) > 0)
				zos.write(buf, 0, pos);
			fis.close();
			zos.closeArchiveEntry();
			LOG.debug("Deleting " + file);
			file.delete();
		}
		zos.flush();
		zos.close();

		tmpDir.delete();
		return new DeleteFileAfterCloseInputStream(zipFile);
	}

	/**
	 * @param tmpDir
	 * @param cd
	 * @throws IOException
	 */
	private void createGenesysData(File tmpDir, ColumnDescription column, Collection collection) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(tmpDir, String.format("%1$s_%2$s.csv", collection.getShortName(), column.getColumn()))));
		CSVWriter.addLine(bw, "Accession", "Observation");
		List<Object[]> data = getData(collection, column);
		for (Object[] d : data) {
			CSVWriter.addLine(bw, d[0], d[1]);
		}
		bw.flush();
		bw.close();
	}

	/**
	 * Returns a List of Object[2] arrays, containing accession name + column value
	 * 
	 * @param collection
	 * @param column
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getData(Collection collection, ColumnDescription column) {
		Experiment experiment = column.getExperiment();
		if (experiment.getId().longValue() == 1l)
			return this.entityManager.createNativeQuery(
					"select A.accessionName, A.`" + column.getColumn() + "` from Accession A where A.collection_id=? and A.`" + column.getColumn()
							+ "` is not null order by A.accessionName").setParameter(1, collection.getId()).getResultList();
		else
			return this.entityManager.createNativeQuery(
					"select A.accessionName, D.`" + column.getColumn() + "` from " + experiment.getTableName()
							+ " D inner join Accession A on D.accession_id=A.id where A.collection_id=? and D.`" + column.getColumn()
							+ "` is not null order by A.accessionName").setParameter(1, collection.getId()).getResultList();
	}

	/**
	 * For coded columns, this methods return a string in GeneSys format
	 * 
	 * @param cd
	 * @return
	 */
	private String getGeneSysCodes(ColumnDescription cd) {
		if (!cd.isCoded())
			return null;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Coding coding : cd.getCoding()) {
			if (i++ > 0)
				sb.append("; ");
			sb.append(coding.getCodedValue()).append(" = ").append(coding.getActualValue());
		}
		return sb.toString();
	}

	/**
	 * @throws IOException
	 * @see org.iita.accessions2.service.ExperimentService#downloadPassportData(org.iita.accessions2.service.Filters)
	 */
	@Override
	@Transactional
	public InputStream downloadPassportData(Filters filters) throws IOException {
		Experiment passportData = this.find(1l);
		return downloadData(passportData, filters);
	}

	/**
	 * @param passportData
	 * @param collectionFilters
	 * @return
	 * @throws IOException
	 */
	private InputStream downloadData(Experiment experiment, Filters filters) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheetDecoded = wb.createSheet(experiment.getTitle());
		HSSFSheet sheetCoded = wb.createSheet("Coded " + experiment.getTitle());
		HSSFSheet sheetCoding = wb.createSheet("Coding information");

		List<ColumnDescription> columns = this.getVisibleColumns(experiment);
		int experimentColumns = columns.size();
		List<String> headings = new ArrayList<String>();
		headings.add("ID");
		for (ColumnDescription columnDescription : columns) {
			headings.add(columnDescription.getTitle());
		}
		String[] headArray = headings.toArray(new String[] {});
		XLSExportService.fillSheet(sheetCoded, headArray, null);
		XLSExportService.fillSheet(sheetDecoded, headArray, null);

		int startAt = 0, maxResults = 500;
		PagedResult<Object[]> data = null;
		do {
			data = listData(filters, experiment, startAt, maxResults);

			XLSExportService.appendToSheet(sheetCoded, data.getResults());

			// decode values
			for (int i = 0; i < experimentColumns; i++) {
				ColumnDescription column = columns.get(i);

				if (column.isCoded()) {
					for (Object[] d : data.getResults()) {
						if (d[i + 1] != null) {
							if (d[i + 1] instanceof Integer)
								d[i + 1] = column.decodeShort((Integer) d[i + 1]);
							else if (d[i + 1] instanceof Long)
								d[i + 1] = column.decodeShort(((Long) d[i + 1]).intValue());
						}
					}
				}
			}

			XLSExportService.appendToSheet(sheetDecoded, data.getResults());
			startAt += data.getResults().size();
		} while (data != null && startAt < data.getTotalHits());

		// Coding information
		List<Object[]> codingInformation = new ArrayList<Object[]>();
		for (ColumnDescription column : columns) {
			Object[] row = new Object[11];
			row[0] = column.getColumn();
			row[1] = column.getTitle();
			row[2] = column.getDescription();
			row[3] = column.isCoded();
			row[4] = column.getDataType();
			row[5] = column.getPrefix();
			row[6] = column.getPostfix();
			row[7] = column.getColumnType() == null ? null : column.getColumnType().name();
			if (column.isCoded()) {
				for (Coding coding : column.getCoding()) {
					row[8] = coding.getCodedValue();
					row[9] = coding.getActualValue();
					row[10] = coding.getCode();
					codingInformation.add(row);
					row = row.clone();
					row[8] = row[9] = row[10] = null;
				}
			} else {
				codingInformation.add(row);
			}
		}
		XLSExportService.fillSheet(sheetCoding, new String[] { "Column", "Title", "Description", "Coded?", "Data type", "Prefix", "Postfix", "Type",
				"Coded value", "Decoded value", "Code" }, codingInformation);

		File file = File.createTempFile("export", "xls");
		FileOutputStream fs = new FileOutputStream(file);
		wb.write(fs);
		fs.flush();
		fs.close();
		return new DeleteFileAfterCloseInputStream(file);
	}

	/**
	 * @throws IOException
	 * @see org.iita.accessions2.service.ExperimentService#downloadData(org.iita.accessions2.model.Experiment)
	 */
	@Override
	@Transactional(readOnly = true)
	public InputStream downloadData(Experiment experiment) throws IOException {
		return downloadData(experiment, null);
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#update(org.iita.accessions2.model.Experiment, org.iita.accessions2.model.Accession,
	 *      java.util.Dictionary)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public void update(Experiment experiment, Accession accession, Dictionary<String, Object> experimentData) {
		boolean updatingPassportData = experiment.getId().equals(1l);

		List<Collection> collections = this.entityManager.createQuery("from Collection c").getResultList();
		if (accession.getId() == null) {
			// persist accession
			LOG.info("Persisting new accession");
			accession.setName((String) experimentData.get("accessionName"));
			accession.setCollection(findCollectionByPrefix(accession.getName(), collections));
			if (accession.getCollection()==null) {
				LOG.warn("No collection with prefix for: " + accession.getName());
				throw new RuntimeException("No collection with prefix for " + accession.getName());
			}
			this.entityManager.persist(accession);
		}

		Query queryFind = this.entityManager.createNativeQuery(buildFindExperimentRowQueryById(experiment));
		queryFind.setParameter(1, accession.getId());
		// set experiment_id value
		if (!updatingPassportData)
			queryFind.setParameter(2, experiment.getId());

		// include experiment_id if not updating MCPD
		List<String> includedDefaults = new ArrayList<String>();
		if (!updatingPassportData) {
			includedDefaults.add("experiment_id");
			//includedDefaults.add("accessionName");
		}

		// Build insert query
		Query queryInsert = this.entityManager.createNativeQuery(buildInsertQuery(experiment, experiment.getColumns(), includedDefaults));
		// Build update query
		Query queryUpdate = this.entityManager.createNativeQuery(buildUpdateQuery(experiment, experiment.getColumns(), includedDefaults));

		// find existing experiment row
		queryFind.setParameter(1, accession.getId());
		if (!updatingPassportData)
			queryFind.setParameter(2, experiment.getId());

		LOG.debug("Searching for existing row");
		BigInteger rowId = null;
		try {
			rowId = (BigInteger) queryFind.getSingleResult();
		} catch (NoResultException e) {

		}

		LOG.info("Existing row id: " + rowId);
		boolean inserting = rowId == null;

		Query query = inserting ? queryInsert : queryUpdate;

		int param = 1;

		if (updatingPassportData)
			query.setParameter(param++, accession.getCollection().getId());
		else {
			query.setParameter(param++, accession.getId());
			query.setParameter(param++, experiment.getId());
			//query.setParameter(param++, accession.getName());
		}

		XWorkConverter conv = XWorkConverter.getInstance();
		for (ColumnDescription column : experiment.getColumns()) {
			Object converted = conv.convertValue(null, experimentData.get(column.getColumn()), column.getDataClass());
			// "" --> null
			if (converted instanceof String && ((String) converted).trim().length() == 0)
				converted = null;
			if (converted != null)
				LOG.debug("Converted: " + converted + " type=" + converted.getClass().getName());
			query.setParameter(param++, converted);
		}

		if (!inserting)
			query.setParameter("existingId", rowId);

		query.executeUpdate();

		if (!updatingPassportData)
			recreateAccessionExperimentsList(experiment);

		this.entityManager.refresh(accession);

		List<Long> updatedAccessions = new ArrayList<Long>();
		updatedAccessions.add(accession.getId());
		reindexAccessions(updatedAccessions);
	}

	/**
	 * Update experiment column sort order. Column IDs are passed in ordered list <code>sortOrder</code>
	 * 
	 * @see org.iita.accessions2.service.ExperimentService#updateSortOrder(org.iita.accessions2.model.Experiment, java.util.List)
	 */
	@Override
	@Transactional
	public void updateSortOrder(Experiment experiment, List<Long> sortOrder) {
		LOG.info("Updating sort order: " + experiment);
		LOG.info("ID list: " + sortOrder);
		List<ColumnDescription> columns = experiment.getColumns();
		double order = 0;
		for (Long id : sortOrder) {
			for (ColumnDescription column : columns) {
				if (column.getId().equals(id)) {
					LOG.info("Column " + column + " is next");
					column.setSortIndex(order);
					order += 0.1;
					this.entityManager.merge(column);
					break;
				}
			}
		}
	}

	/**
	 * @see org.iita.accessions2.service.ExperimentService#descriptorHasImage(org.iita.accessions2.model.ColumnDescription, int)
	 */
	@Override
	public boolean descriptorHasImage(ColumnDescription descriptor, String code) {
		Experiment experiment = descriptor.getExperiment();
		File descriptorImage = new File(this.descriptorImageDirectory, String.format("%2$s%1$s%3$s_%4$s.jpg", File.separator, experiment.getTableName()
				.toLowerCase(), descriptor.getColumn(), code));
		LOG.debug("Checking for descriptor image: " + descriptorImage.getAbsolutePath() + " ? " + descriptorImage.exists());
		return descriptorImage.exists();
	}
}
