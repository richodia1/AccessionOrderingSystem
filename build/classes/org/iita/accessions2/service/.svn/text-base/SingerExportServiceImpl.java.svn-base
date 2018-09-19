/**
 * accession2.Struts Mar 31, 2010
 */
package org.iita.accessions2.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iita.accessions2.model.Coding;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.Experiment;
import org.iita.util.DeleteFileAfterCloseInputStream;
import org.iita.util.PagedResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * +--------------+------------+------------------------------------------+-----------+--------------------------+
 * | TABLE_SCHEMA | TABLE_NAME | COLUMN_NAME                              | DATA_TYPE | CHARACTER_MAXIMUM_LENGTH |
 * +--------------+------------+------------------------------------------+-----------+--------------------------+
 * | upload_iita  | ACCESSIONS | AccessionGlobalUniqueIdentifier          | varchar   |                      192 | 
 * | upload_iita  | ACCESSIONS | AccessionLocalUniqueIdentifier           | char      |                       64 | 
 * | upload_iita  | ACCESSIONS | HoldingInstituteFAOCode                  | char      |                        7 | 
 * | upload_iita  | ACCESSIONS | HoldingCollectionCode                    | char      |                       32 | 
 * | upload_iita  | ACCESSIONS | AccessionNumber                          | varchar   |                       64 | 
 * | upload_iita  | ACCESSIONS | SampleNumber                             | varchar   |                       32 | 
 * | upload_iita  | ACCESSIONS | CropName                                 | varchar   |                       32 | 
 * | upload_iita  | ACCESSIONS | TaxonCode                                | varchar   |                       32 | 
 * | upload_iita  | ACCESSIONS | AcquisitionSourceCode                    | enum      |                        2 | 
 * | upload_iita  | ACCESSIONS | AcquisitionSourceNotes                   | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | BiologicalStatusCode                     | enum      |                        3 | 
 * | upload_iita  | ACCESSIONS | BiologicalStatusNotes                    | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | AcquisitionDate                          | varchar   |                        8 | 
 * | upload_iita  | ACCESSIONS | GermplasmStorageType                     | set       |                       23 | 
 * | upload_iita  | ACCESSIONS | GermplasmStorageTypeNotes                | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | NumberOfConservedSamples                 | int       |                     NULL | 
 * | upload_iita  | ACCESSIONS | LastRegenerationDate                     | varchar   |                        8 | 
 * | upload_iita  | ACCESSIONS | AccessionURL                             | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | InTrust                                  | tinyint   |                     NULL | 
 * | upload_iita  | ACCESSIONS | Available                                | tinyint   |                     NULL | 
 * | upload_iita  | ACCESSIONS | OtherAccessionIdentification             | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | SafetyDuplicatesInstituteFAOCode         | char      |                        7 | 
 * | upload_iita  | ACCESSIONS | SafetyDuplicatesInstituteName            | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | CollectingDate                           | varchar   |                        8 | 
 * | upload_iita  | ACCESSIONS | CollectingInstituteFAOCode               | char      |                        7 | 
 * | upload_iita  | ACCESSIONS | CollectingInstituteName                  | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | CollectingMissionCode                    | char      |                       32 | 
 * | upload_iita  | ACCESSIONS | CollectingNumber                         | varchar   |                       64 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteCountryCode                | char      |                        3 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteAdmin1                     | varchar   |                       80 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteAdmin2                     | varchar   |                       80 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteAdmin3                     | varchar   |                       80 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteLocation                   | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteLatitude                   | double    |                     NULL | 
 * | upload_iita  | ACCESSIONS | CollectingSiteLongitude                  | double    |                     NULL | 
 * | upload_iita  | ACCESSIONS | CollectingSiteCoordinatesPrecision       | double    |                     NULL | 
 * | upload_iita  | ACCESSIONS | CollectingSiteCoordinatesSource          | enum      |                        1 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteCoordinatesNotes           | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteCoordinatesSourceReference | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | CollectingSiteElevation                  | smallint  |                     NULL | 
 * | upload_iita  | ACCESSIONS | CollectingSiteElevationPrecision         | smallint  |                     NULL | 
 * | upload_iita  | ACCESSIONS | BreedingDate                             | varchar   |                        8 | 
 * | upload_iita  | ACCESSIONS | BreedingInstituteFAOCode                 | char      |                        7 | 
 * | upload_iita  | ACCESSIONS | BreedingInstituteName                    | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteCountryCode                  | char      |                        3 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteAdmin1                       | varchar   |                       80 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteAdmin2                       | varchar   |                       80 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteAdmin3                       | varchar   |                       80 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteLocation                     | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteLatitude                     | double    |                     NULL | 
 * | upload_iita  | ACCESSIONS | BreedingSiteLongitude                    | double    |                     NULL | 
 * | upload_iita  | ACCESSIONS | BreedingSiteCoordinatesPrecision         | double    |                     NULL | 
 * | upload_iita  | ACCESSIONS | BreedingSiteCoordinatesSource            | enum      |                        1 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteCoordinatesNotes             | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteCoordinatesSourceReference   | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | BreedingSiteElevation                    | smallint  |                     NULL | 
 * | upload_iita  | ACCESSIONS | BreedingSiteElevationPrecision           | smallint  |                     NULL | 
 * | upload_iita  | ACCESSIONS | AncestralData                            | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | DonorCode                                | char      |                       32 | 
 * | upload_iita  | ACCESSIONS | DonorAccessionNumber                     | varchar   |                       64 | 
 * | upload_iita  | ACCESSIONS | AccessionNames                           | text      |                    65535 | 
 * | upload_iita  | ACCESSIONS | AccessionRemarks                         | text      |                    65535 | 
 * +--------------+------------+------------------------------------------+-----------+--------------------------+
 * </pre>
 * 
 * @author mobreza
 * 
 */
public class SingerExportServiceImpl implements SingerExportService {
	private ExperimentService experimentService;
	private static final Log LOG = LogFactory.getLog(SingerExportServiceImpl.class);

	/**
	 * @param experimentService
	 * 
	 */
	public SingerExportServiceImpl(ExperimentService experimentService) {
		this.experimentService = experimentService;
	}

	@Override
	@Transactional(readOnly = true)
	public InputStream downloadSQLScript(Filters filters) throws IOException, ExperimentException {
		Experiment passportData = this.experimentService.find(1l);
		File file = File.createTempFile("singer", ".sql");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		List<ColumnDescription> columns = passportData.getColumns();
		int experimentColumns = columns.size();

		// generate mapping
		Integer[] mapping = getSingerMapping(columns);

		int startAt = 0, maxResults = 500;
		PagedResult<Object[]> data = null;
		do {
			data = this.experimentService.listData(filters, passportData, startAt, maxResults);

			// decode values
			for (int i = 0; i < experimentColumns; i++) {
				ColumnDescription column = columns.get(i);
				if (column.isCoded()) {
					for (Object[] d : data.getResults()) {
						if (d[i + 1] != null) {
							Coding coding = null;
							if (d[i + 1] instanceof Integer)
								coding = column.findCoding((Integer) d[i + 1]);
							else if (d[i + 1] instanceof Long)
								coding = column.findCoding(((Long) d[i + 1]).intValue());

							if (coding != null)
								d[i + 1] = coding.getCode();
						}
					}
				}
			}

			appendToStream(writer, mapping, data.getResults());
			startAt += data.getResults().size();
		} while (data != null && startAt < data.getTotalHits());

		writer.flush();
		writer.close();

		return new DeleteFileAfterCloseInputStream(file);
	}

	/**
	 * <pre>
	 * 		AccessionGlobalUniqueIdentifier
	 * 		AccessionLocalUniqueIdentifier
	 * 		HoldingInstituteFAOCode
	 * 		HoldingCollectionCode
	 * 		AccessionNumber
	 * 		CropName
	 * 		TaxonCode
	 * 		AcquisitionSourceCode
	 * 		BiologicalStatusCode
	 * 		AcquisitionDate
	 * 		AccessionURL
	 * 		InTrust
	 * 		Available
	 * 		CollectingDate
	 * 		CollectingInstituteFAOCode
	 * 		CollectingInstituteName
	 * 		CollectingMissionCode
	 * 		CollectingNumber
	 * 		CollectingSiteCountryCode
	 * 		CollectingSiteLocation
	 * 		CollectingSiteLatitude
	 * 		CollectingSiteLongitude
	 * 		CollectingSiteElevation
	 * 		AncestralData
	 * 		AccessionNames
	 * 		AccessionRemarks
	 * </pre>
	 * 
	 * @param columns
	 * @return
	 * @throws ExperimentException
	 */
	private Integer[] getSingerMapping(List<ColumnDescription> columns) throws ExperimentException {
		ArrayList<Integer> map = new ArrayList<Integer>();

		map.add(findColumnDescription(columns, "collection_id"));
		map.add(findColumnDescription(columns, "accessionName"));
		map.add(findColumnDescription(columns, "grinTaxonomy"));
		map.add(findColumnDescription(columns, "singerTaxonomy"));
		map.add(findColumnDescription(columns, "collSrc"));
		map.add(findColumnDescription(columns, "sampStat"));
		map.add(findColumnDescription(columns, "acqDate"));
		map.add(findColumnDescription(columns, "inTrust"));
		map.add(findColumnDescription(columns, "available"));
		map.add(findColumnDescription(columns, "collDate"));
		map.add(findColumnDescription(columns, "collCode"));
		map.add(findColumnDescription(columns, "collNumb"));
		map.add(findColumnDescription(columns, "origCty"));
		map.add(findColumnDescription(columns, "location"));
		map.add(findColumnDescription(columns, "latitude"));
		map.add(findColumnDescription(columns, "longitude"));
		map.add(findColumnDescription(columns, "elevation"));
		map.add(findColumnDescription(columns, "ancest"));
		map.add(findColumnDescription(columns, "remarks"));

		return map.toArray(new Integer[] {});
	}

	/**
	 * @param columns
	 * @param string
	 * @return
	 * @throws ExperimentException
	 */
	private Integer findColumnDescription(List<ColumnDescription> columns, String columnName) throws ExperimentException {
		for (int index = 0; index < columns.size(); index++) {
			if (columns.get(index).getColumn().equals(columnName))
				return index + 1;
		}
		throw new ExperimentException("Could not find column: " + columnName);
	}

	/**
	 * @param writer
	 * @param columns
	 * @param results
	 * @throws IOException
	 * @throws ExperimentException
	 */
	private void appendToStream(Writer writer, Integer[] mapping, List<Object[]> rows) throws IOException, ExperimentException {
		writer.write("INSERT INTO `ACCESSIONS` (");
		writer.write("`AccessionGlobalUniqueIdentifier`, ");
		writer.write("`AccessionLocalUniqueIdentifier`, ");
		writer.write("`HoldingInstituteFAOCode`, ");
		writer.write("`HoldingCollectionCode`, ");
		writer.write("`AccessionNumber`, ");
		writer.write("`CropName`, ");
		writer.write("`TaxonCode`, ");
		writer.write("`AcquisitionSourceCode`, ");
		writer.write("`BiologicalStatusCode`, ");
		writer.write("`AcquisitionDate`, ");
		writer.write("`AccessionURL`, ");
		writer.write("`InTrust`, ");
		writer.write("`Available`, ");
		writer.write("`CollectingDate`, ");
		writer.write("`CollectingInstituteFAOCode`, ");
		writer.write("`CollectingInstituteName`, ");
		writer.write("`CollectingMissionCode`, ");
		writer.write("`CollectingNumber`, ");
		writer.write("`CollectingSiteCountryCode`, ");
		writer.write("`CollectingSiteLocation`, ");
		writer.write("`CollectingSiteLatitude`, ");
		writer.write("`CollectingSiteLongitude`, ");
		writer.write("`CollectingSiteElevation`, ");
		writer.write("`AncestralData`, ");
		writer.write("`AccessionNames`, ");
		writer.write("`AccessionRemarks`");

		writer.write(") VALUES ");

		int rowCount = 0;
		for (Object[] row : rows) {
			if (rowCount++ > 0)
				writer.write(",");
			writer.write("\n (");
			// globalunique
			writer.write(toSQL("urn:lsid:NGA039:%1$s:%2$s", row[mapping[0]], row[mapping[1]]));
			// LocalUnique
			writer.write(", ");
			writer.write(toSQL("%1$s:%2$s", row[mapping[0]], row[mapping[1]]));
			// holding institute
			writer.write(", ");
			writer.write("'NGA039'");
			// HoldingCollectionCode
			writer.write(", ");
			writer.write(toSQL(row[mapping[0]]));
			// AccessionNumber
			writer.write(", ");
			writer.write(toSQL(row[mapping[1]]));
			// CropName
			writer.write(", null");
			// TaxonCode
			writer.write(", ");
			if (row[mapping[2]] != null)
				writer.write(toSQL(row[mapping[2]]));
			else if (row[mapping[3]] != null)
				writer.write(toSQL("SINGER:%1$d", row[mapping[3]]));
			else
				writer.write("null");
			// AcquisitionSourceCode
			writer.write(", ");
			writer.write(toSQL(row[mapping[4]]));
			// BiologicalStatusCode
			writer.write(", ");
			writer.write(toSQL(row[mapping[5]]));
			// AcquisitionDate
			writer.write(", ");
			writer.write(toSQL(row[mapping[6]]));
			// AccessionURL
			writer.write(", ");
			writer.write(toSQL("http://genebank.iita.org/browse/accession.aspx/%1$s", row[mapping[1]]));
			// InTrust
			writer.write(", ");
			writer.write(toSQL(row[mapping[7]]));
			// Available
			writer.write(", ");
			writer.write(toSQL(row[mapping[8]]));
			// CollectingDate
			writer.write(", ");
			writer.write(toSQL(row[mapping[9]]));
			// CollectingInstituteFAOCode
			writer.write(", null");
			// CollectingInstituteName
			writer.write(", ");
			writer.write(toSQL(row[mapping[10]]));
			// CollectingMissionCode
			writer.write(", null");
			// CollectingNumber
			writer.write(", ");
			writer.write(toSQL(row[mapping[11]]));
			// CollectingSiteCountryCode
			writer.write(", ");
			writer.write(toSQL(row[mapping[12]]));
			// CollectingSiteLocation
			writer.write(", ");
			writer.write(toSQL(row[mapping[13]]));
			// CollectingSiteLatitude
			writer.write(", ");
			writer.write(toSQL(row[mapping[14]]));
			// CollectingSiteLongitude
			writer.write(", ");
			writer.write(toSQL(row[mapping[15]]));
			// CollectingSiteElevation
			writer.write(", ");
			writer.write(toSQL(row[mapping[16]]));
			// AncestralData
			writer.write(", ");
			writer.write(toSQL(row[mapping[17]]));
			// AccessionNames
			writer.write(", null");
			// AccessionRemarks
			writer.write(", ");
			writer.write(toSQL(row[mapping[18]]));

			writer.write(")");
		}

		writer.write(";\n");
	}

	/**
	 * @param object
	 * @return
	 * @throws ExperimentException
	 */
	private String toSQL(Object object) throws ExperimentException {
		if (object == null)
			return "null";
		if (object instanceof String)
			return String.format("'%1$s'", ((String) object).replaceAll("('|\\\\)", "\\\\$1"));
		if (object instanceof Date)
			return String.format("'%1$tY%1$tm%1$td'", object);
		if (object instanceof Long)
			return ((Long) object).toString();
		if (object instanceof Double)
			return ((Double) object).toString();
		if (object instanceof Boolean)
			return ((Boolean) object).booleanValue() ? "1" : "0";

		throw new ExperimentException("Cannot convert type " + object.getClass().getName() + " to SQL");
	}

	/**
	 * @param string
	 * @param object
	 * @param object2
	 * @return
	 */
	private String toSQL(String format, Object... args) {
		try {
			return toSQL(String.format(format, args));
		} catch (Exception e) {
			LOG.error("Error formatting: " + format + ": " + e.getMessage());
			return "null";
		}
	}
}
