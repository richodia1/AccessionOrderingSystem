/**
 * accession2.Struts Jan 18, 2010
 */
package org.iita.accessions2.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.Coding;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.ExperimentException;
import org.iita.accessions2.service.ExperimentImportException;
import org.iita.accessions2.service.ExperimentService;
import org.iita.accessions2.service.ExperimentService.Mappings;
import org.iita.accessions2.service.ExperimentService.XLSColumnData;
import org.iita.struts.BaseAction;
import org.iita.struts.FileUploadAction;
import org.iita.util.StringUtil;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * Experiment data action allows users to upload XLS file with experiment data, normalize the data in underlying database, convert columns to coded columns
 * using {@link Coding}.
 * 
 * <p>
 * The process is as follows:
 * </p>
 * <ol>
 * <li>Select or create Experiment, and upload XLS file</li>
 * <li>Experiment data is loaded or created</li>
 * <li>If there are existing columns in the experiment table, those can be mapped to XLS columns</li>
 * <li>Any additional columns must be defined</li>
 * <li>Data is tested against column meta-data</li>
 * <li>Rows are linked against {@link Accession} records using Accession name</li>
 * <li>Data is imported to database</li>
 * </ol>
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class ExperimentDataAction extends BaseAction implements FileUploadAction {
	private static final String EXPERIMENT_UPLOAD = "__EXPERIMENT_UPLOAD";
	private static final String EXPERIMENT_MAPPINGS = "__EXPERIMENT_MAPPING";
	private ExperimentService experimentService;
	private List<Experiment> experiments;
	private Long experimentId = null;
	private Experiment experiment, newExperiment;
	private List<File> uploads;
	private Mappings mappings = null;
	private List<ColumnDescription> columns;
	private boolean createMissing = false;

	private List<Integer> xlsMap = new ArrayList<Integer>();
	private List<Long> colMap = new ArrayList<Long>();

	private Hashtable<Integer, Exception> importErrors = new Hashtable<Integer, Exception>();
	private List<NewColumnInfo> newColumns = null;
	private Integer columnNum = null;

	class NewColumnInfo {
		public XLSColumnData xlsColumn;
		public int xlsColumnNum;
		public String column;
		public String description;
		public String title;
		public String dataType;
		public int codedType;
	}

	/**
	 * @param experimentService
	 * 
	 */
	public ExperimentDataAction(ExperimentService experimentService) {
		this.experimentService = experimentService;
	}

	/**
	 * Get list of all experiments
	 * 
	 * @return the experiments
	 */
	public List<Experiment> getExperiments() {
		return this.experiments;
	}

	/**
	 * @param experimentId the experimentId to set
	 */
	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}

	@Override
	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	@Override
	public void setUploadsContentType(List<String> uploadContentTypes) {
		// ignored
	}

	@Override
	public void setUploadsFileName(List<String> uploadFileNames) {
		// don't need
	}

	public void setNewExperiment(Experiment newExperiment) {
		this.newExperiment = newExperiment;
	}

	/**
	 * @param columnNum the columnNum to set
	 */
	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}

	public Mappings getMappings() {
		return mappings;
	}

	public List<ColumnDescription> getColumns() {
		return columns;
	}

	public ColumnDescription getColumnWithId(long id) {
		for (ColumnDescription column : this.columns) {
			if (column.getId().equals(id))
				return column;
		}
		return null;
	}

	/**
	 * @return the importErrors
	 */
	public Hashtable<Integer, Exception> getImportErrors() {
		return this.importErrors;
	}

	public Experiment getExperiment() {
		return experiment;
	}

	/**
	 * @param xlsMap the xlsMap to set
	 */
	public void setXlsMap(List<Integer> xlsMap) {
		this.xlsMap = xlsMap;
	}

	/**
	 * @param colMap the colMap to set
	 */
	public void setColMap(List<Long> colMap) {
		this.colMap = colMap;
	}

	/**
	 * @return the newColumns
	 */
	public List<NewColumnInfo> getNewColumns() {
		return this.newColumns;
	}

	/**
	 * @param newColumns the newColumns to set
	 */
	public void setNewColumns(List<NewColumnInfo> newColumns) {
		this.newColumns = newColumns;
	}

	@Override
	public void prepare() {
		this.mappings = fetchMappings();
	}

	/**
	 * Default action method: renders experiment selection/creation and file upload form
	 */
	@Override
	public String execute() {
		this.experiments = this.experimentService.listExperiments();
		return Action.SUCCESS;
	}

	/**
	 * Action method to upload a file to this action. The file is parsed and HSSF object put to Session
	 * 
	 * @return {@link Action.ERROR} or "redirect-mapping"
	 */
	@SuppressWarnings("unchecked")
	public String upload() {
		if (this.uploads == null || this.uploads.size() == 0) {
			addActionError("No file uploaded");
			return Action.ERROR;
		}

		if (this.experimentId == null && this.newExperiment != null) {
			LOG.info("Creating new experiment " + this.newExperiment);
			try {
				this.experimentService.createExperiment(this.newExperiment);
			} catch (ExperimentException e) {
				addActionError(e.getMessage());
				return Action.ERROR;
			}
			this.experimentId = this.newExperiment.getId();
		}

		// store workbook to session
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(this.uploads.get(0)));

			// evaluate all formulas!
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				HSSFSheet sheet = workbook.getSheetAt(sheetNum);
				for (Row r : sheet) {
					for (Cell c : r) {
						if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
							evaluator.evaluateInCell(c);
						}
					}
				}
			}

			ActionContext.getContext().getSession().put(EXPERIMENT_UPLOAD, workbook);

			// get experiment
			LOG.info("Loading experiment: " + experimentId);
			this.experiment = this.experimentService.find(this.experimentId);

			mappings = fetchMappings();
			if (mappings == null) {
				// try mapping columns by name/title to XLS files
				Mappings mappings = this.experimentService.getMappings(workbook.getSheetAt(0), experiment);
				// put mappings to session
				ActionContext.getContext().getSession().put(EXPERIMENT_MAPPINGS, mappings);
			} else {
				// try mapping columns by name/title to XLS files
				Mappings newMappings = this.experimentService.getMappings(workbook.getSheetAt(0), experiment);
				if (newMappings.xlsColumns.size() > 0 && newMappings.xlsColumns.size() != mappings.xlsColumns.size()
						&& !newMappings.xlsColumns.get(0).name.equals(mappings.xlsColumns.get(0).name)) {
					mappings = newMappings;
					// put mappings to session
					ActionContext.getContext().getSession().put(EXPERIMENT_MAPPINGS, mappings);
				} else {
					mappings.xlsColumns = newMappings.xlsColumns;
				}
			}

		} catch (FileNotFoundException e) {
			LOG.error(e);
			LOG.error("Error reading XLS file: " + e.getMessage());
		} catch (IOException e) {
			LOG.error(e);
			addActionError("Error reading XLS file: " + e.getMessage());
		}

		return "go-mapping";
	}

	/**
	 * Add automatic mappings
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String defaultmapping() {
		LOG.info("Resetting to default mappings");
		this.experiment = this.experimentService.find(this.experimentId);
		// try mapping columns by name/title to XLS files
		HSSFWorkbook workbook = getWorkbook();
		Mappings mappings = this.experimentService.getMappings(workbook.getSheetAt(0), experiment);
		// put mappings to session
		ActionContext.getContext().getSession().put(EXPERIMENT_MAPPINGS, mappings);

		return "go-mapping";
	}

	/**
	 * Display mappings
	 * 
	 * @return
	 */
	public String mapping() {
		// check if file uploaded and stored in session
		HSSFWorkbook workbook = getWorkbook();
		if (workbook == null) {
			addActionError("File not uploaded or not an XLS Workbook");
			return Action.ERROR;
		}

		// get experiment
		this.experiment = this.experimentService.find(this.experimentId);
		this.columns = this.experimentService.getColumns(this.experiment);

		return "mapping";
	}

	/**
	 * Remove defined mappings
	 * 
	 * @return
	 */
	public String clearmapping() {
		this.experiment = this.experimentService.find(this.experimentId);
		mappings = fetchMappings();
		mappings.mappings.clear();
		return "go-mapping";
	}

	/**
	 * Configure mappings
	 * 
	 * @return
	 */
	public String configure() {
		// the data from form needs to update {@link Mappings}
		if (xlsMap.size() != colMap.size()) {
			addActionError("Error ocurred while mapping columns.");
			return Action.ERROR;
		}

		Mappings mappings = getMappings();
		mappings.mappings.clear();

		boolean newColumnDescription = false;
		for (int i = 0; i < xlsMap.size(); i++) {
			LOG.info("Mapping XLS column " + xlsMap.get(i) + " to " + colMap.get(i));
			if (colMap.get(i) == -1l) {
				newColumnDescription = true;
				LOG.debug("Have to add extra column!");
			}
			mappings.mappings.put(xlsMap.get(i), colMap.get(i));
		}

		this.experiment = this.experimentService.find(this.experimentId);

		if (newColumnDescription)
			return "go-define";
		else
			return "go-preview";
	}

	/**
	 * Define any new columns (with coding and stuff). Any "mapped" column marked as <code>-1</code> needs to be created here.
	 * 
	 * @return
	 */
	public String define() {
		this.experiment = this.experimentService.find(this.experimentId);

		// generate new column data
		this.newColumns = generateNewColumns();

		return "define";
	}

	/**
	 * @return
	 */
	private List<NewColumnInfo> generateNewColumns() {
		List<NewColumnInfo> newColumns = new ArrayList<NewColumnInfo>();
		Mappings mappings = getMappings();
		for (XLSColumnData xlsColumn : mappings.xlsColumns) {
			Long columnDescriptionId = mappings.mappings.get(xlsColumn.columnNum);
			if (columnDescriptionId != null && columnDescriptionId == -1l) {
				NewColumnInfo newColumn = new NewColumnInfo();
				newColumn.codedType = 0;
				newColumn.title = xlsColumn.name;
				newColumn.description = xlsColumn.name;
				newColumn.column = StringUtil.fromHumanToCamel(xlsColumn.name);
				newColumn.dataType = xlsColumn.dataType;
				newColumn.xlsColumnNum = xlsColumn.columnNum;
				newColumn.xlsColumn = xlsColumn;
				newColumns.add(newColumn);
			}
		}
		return newColumns.size() == 0 ? null : newColumns;
	}

	public String addcolumns() {
		this.experiment = this.experimentService.find(this.experimentId);
		Mappings mappings = getMappings();
		if (this.newColumns != null && this.newColumns.size() > 0) {
			for (NewColumnInfo newColumn : this.newColumns) {
				Long columnDescriptionId = mappings.mappings.get(newColumn.xlsColumnNum);
				if (columnDescriptionId != null && columnDescriptionId == -1l) {
					LOG.info("ADD: " + newColumn.column + " as " + newColumn.dataType + " which is " + newColumn.codedType + " and " + newColumn.title);
					ColumnDescription columnDescription;
					try {
						columnDescription = this.experimentService.addColumnDescription(this.experiment, newColumn.title, newColumn.description,
								newColumn.column, newColumn.dataType, newColumn.codedType != 0);
						mappings.mappings.put(newColumn.xlsColumnNum, columnDescription.getId());
					} catch (ExperimentException e) {
						addActionError(e.getMessage());
						return Action.ERROR;
					}
				}
			}
		}
		boolean columnsMissing = false;
		// check if there's still columns missing
		for (Integer xlsColumnNum : mappings.mappings.keySet()) {
			Long columnDescriptionId = mappings.mappings.get(xlsColumnNum);
			if (columnDescriptionId != null && columnDescriptionId == -1l) {
				columnsMissing = true;
				break;
			}
		}

		if (columnsMissing)
			return "go-define";
		else
			return "go-preview";
	}

	public String addcoding() {
		HSSFWorkbook workbook = getWorkbook();
		this.experiment = this.experimentService.find(this.experimentId);
		// need column
		Mappings mappings = getMappings();
		Long columnDescriptionId = mappings.mappings.get(this.columnNum);
		if (columnDescriptionId != null) {
			try {
				this.experimentService.addCodingValues(this.experiment, workbook.getSheetAt(0), this.columnNum, columnDescriptionId);
			} catch (ExperimentImportException e) {
				addActionError(e.getMessage());
				return Action.ERROR;
			}
		}

		return "go-preview";
	}

	public String addallcoding() {
		HSSFWorkbook workbook = getWorkbook();
		this.experiment = this.experimentService.find(this.experimentId);
		this.columns = this.experimentService.getColumns(this.experiment);
		// need column
		Mappings mappings = getMappings();
		for (XLSColumnData xlsColumn : mappings.xlsColumns) {
			Long columnDescriptionId = mappings.mappings.get(xlsColumn.columnNum);
			if (columnDescriptionId != null) {
				ColumnDescription columnDescription = getColumnWithId(columnDescriptionId);
				try {
					if (columnDescription.isCoded())
						this.experimentService.addCodingValues(this.experiment, workbook.getSheetAt(0), xlsColumn.columnNum, columnDescriptionId);
				} catch (ExperimentImportException e) {
					addActionError(e.getMessage());
					return Action.ERROR;
				}
			}
		}

		return "go-preview";
	}

	/**
	 * Preview action shows data and configured mappings
	 */
	public String preview() {
		HSSFWorkbook workbook = getWorkbook();
		this.experiment = this.experimentService.find(this.experimentId);
		this.columns = this.experimentService.getColumns(this.experiment);

		for (Integer columnNum : mappings.mappings.keySet()) {
			Exception result = this.experimentService.tryImport(experiment, workbook.getSheetAt(0), createMissing, mappings, columnNum);
			if (result != null) {
				LOG.info("Putting: " + columnNum + " --> " + result.getMessage());
				this.importErrors.put(columnNum, result);
			}
		}
		return "preview";
	}

	/**
	 * Insert data to database
	 * 
	 * @return
	 */
	public String insert() {
		HSSFWorkbook workbook = getWorkbook();
		this.experiment = this.experimentService.find(this.experimentId);

		try {
			Hashtable<String, Object> defaultValues = new Hashtable<String, Object>();

			// fill defaults
			if (this.experiment.getTableName().equals("Accession")) {

			} else {
				// standard Experiment data
				defaultValues.put("experiment_id", this.experiment.getId());
			}

			this.experimentService.tryImport(experiment, workbook.getSheetAt(0), createMissing, mappings, defaultValues);

			ActionContext.getContext().getSession().remove(EXPERIMENT_MAPPINGS);
		} catch (ExperimentImportException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}

		return "done";
	}

	/**
	 * Get XLS Workbook from session. See {@link #upload()} action where XLS workbook is read
	 * 
	 * @return Workbook from session or <code>null</code>
	 */
	private HSSFWorkbook getWorkbook() {
		Object object = ActionContext.getContext().getSession().get(EXPERIMENT_UPLOAD);
		if (object != null && object instanceof HSSFWorkbook)
			return (HSSFWorkbook) object;

		// otherwise remove whatever's there
		ActionContext.getContext().getSession().remove(EXPERIMENT_UPLOAD);
		return null;
	}

	/**
	 * Get XLS mappings from session. See {@link #upload()} action where mappings are first created
	 * 
	 * @return Workbook from session or <code>null</code>
	 */
	private Mappings fetchMappings() {
		Object object = ActionContext.getContext().getSession().get(EXPERIMENT_MAPPINGS);
		if (object != null && object instanceof Mappings)
			return (Mappings) object;

		// otherwise remove whatever's there
		ActionContext.getContext().getSession().remove(EXPERIMENT_MAPPINGS);
		return null;
	}

	/**
	 * @param createMissing the createMissing to set
	 */
	public void setCreateMissing(boolean createMissing) {
		this.createMissing = createMissing;
	}

	/**
	 * Create missing accessions?
	 * 
	 * @return the createMissing
	 */
	public boolean isCreateMissing() {
		return this.createMissing;
	}
}
