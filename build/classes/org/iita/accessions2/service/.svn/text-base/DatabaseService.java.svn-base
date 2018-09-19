/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mobreza
 * 
 */
public interface DatabaseService {

	/**
	 * 
	 */
	void test();

	/**
	 * Return a {@link PreparedStatement} on current JDBC connection
	 * 
	 * @param string
	 * @return
	 * @throws SQLException
	 */
	PreparedStatement prepare(String query) throws SQLException;

	/**
	 * <p>
	 * Execute query and return value of first row, first column if available, <code>null</code> otherwise
	 * </p>
	 * <p>
	 * Example:
	 * </p>
	 * 
	 * <pre>
	 * // get total row count
	 * Long totalRowCount = -1l;
	 * try {
	 * 	totalRowCount = (Long) this.databaseService.getSingleResult(&quot;SELECT COUNT(`id`) FROM &quot; + experiment.getTableName());
	 * } catch (SQLException e1) {
	 * 	LOG.error(e1);
	 * }
	 * </pre>
	 * 
	 * @param string Query to execute
	 * @throws SQLException
	 */
	Object getSingleResult(String string) throws SQLException;

	/**
	 * Check if a table exists in schema
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	boolean tableExists(String tableName) throws SQLException;

	/**
	 * @param tableName Table to which column should be added
	 * @param columnName new column to add
	 * @param afterColumn new column will be added after this column, can be <code>null</code>
	 * @param clazz Type of column
	 * @return
	 * @throws SQLException
	 */
	String addColumn(String tableName, String columnName, String afterColumn, Class<?> clazz) throws SQLException;

	/**
	 * @param pstmtFind
	 * @throws SQLException
	 */
	Object getSingleResult(PreparedStatement pstmtFind) throws SQLException;

	/**
	 * @param tableName
	 * @param columnName
	 * @throws SQLException
	 */
	void dropColumn(String tableName, String columnName) throws SQLException;

	/**
	 * @param tableName
	 * @param clazz
	 * @param convertedColumnName
	 * @param columnName
	 * @throws SQLException
	 */
	void renameColumn(String tableName, String originalColumnName, String newColumnName, Class<?> clazz) throws SQLException;

	/**
	 * Get columns of particular table
	 * 
	 * @param tableName
	 * @throws SQLException
	 */
	List<DatabaseColumn> getColumns(String tableName) throws SQLException;

	/**
	 * Database column information
	 * 
	 * @author mobreza
	 */
	public class DatabaseColumn {
		public String columnName;
		public int sqlType;
		public String typeName;
		public int columnSize;
		public int nullable;
		public String defaultValue;
		public String nullable2;
		public String autoIncrement;

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(columnName).append(" ").append(sqlType).append(" ").append(typeName).append(" nullable=").append(nullable).append(" default=").append(
					defaultValue);
			return sb.toString();
		}
	}

	/**
	 * @param string
	 * @param parameters
	 * @return
	 */
	Object getSingleResult(String string, List<Object> parameters) throws SQLException;
}
