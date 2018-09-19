/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mobreza
 * 
 */
public class DatabaseServiceImpl implements DatabaseService {
	private static final Log LOG = LogFactory.getLog(DatabaseServiceImpl.class);
	private org.springframework.jdbc.datasource.DriverManagerDataSource dataSource;

	/**
	 * @param dataSource
	 * 
	 */
	public DatabaseServiceImpl(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void test() {
		try {
			Connection connection = DataSourceUtils.getConnection(dataSource);
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet tables = metadata.getTables(null, null, null, null);
			ResultSetMetaData resultsetmeta = tables.getMetaData();
			StringBuilder sb = new StringBuilder();
			int columns = resultsetmeta.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				sb.append(resultsetmeta.getColumnName(i)).append("\t");
			}
			LOG.debug(sb.toString());
			sb.delete(0, sb.length());

			while (tables.next()) {
				for (int i = 1; i <= columns; i++) {
					sb.append(tables.getObject(i)).append("\t");
				}
				LOG.debug(sb.toString());
				sb.delete(0, sb.length());
			}
			tables.close();
		} catch (SQLException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * @throws SQLException
	 * @see org.iita.accessions2.service.DatabaseService#prepare(java.lang.String)
	 */
	@Override
	@Transactional
	public PreparedStatement prepare(String query) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(dataSource);
		return connection.prepareStatement(query);
	}

	/**
	 * @throws SQLException
	 * @see org.iita.accessions2.service.DatabaseService#getSingleResult(java.lang.String)
	 */
	@Override
	@Transactional
	public Object getSingleResult(String sql) throws SQLException {
		return getSingleResult(sql, null);
	}
	
	/**
	 * @throws SQLException 
	 * @see org.iita.accessions2.service.DatabaseService#getSingleResult(java.lang.String, java.util.List)
	 */
	@Override
	@Transactional
	public Object getSingleResult(String sql, List<Object> parameters) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(dataSource);
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		if (parameters!=null) {
			int paramPos = 1;
			for (Object param : parameters)
				pstmt.setObject(paramPos++, param);
		}
		
		ResultSet resultSet = pstmt.executeQuery();
		Object value = null;
		if (resultSet != null && resultSet.next()) {
			value = resultSet.getObject(1);
		}
		resultSet.close();
		pstmt.close();
		return value;
	}

	/**
	 * @throws SQLException
	 * @see org.iita.accessions2.service.DatabaseService#getSingleResult(java.sql.PreparedStatement)
	 */
	@Override
	public Object getSingleResult(PreparedStatement pstmt) throws SQLException {
		ResultSet resultSet = pstmt.executeQuery();
		Object value = null;
		if (resultSet != null && resultSet.next()) {
			value = resultSet.getObject(1);
		}
		resultSet.close();
		return value;
	}

	@Override
	@Transactional
	public boolean tableExists(String tableName) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(dataSource);

		try {
			LOG.debug("Checking for table `" + tableName + "`");
			ResultSet meta = connection.getMetaData().getTables(null, null, tableName, new String[] { "TABLE" });
			boolean tableExists = meta.next();
			meta.close();
			return tableExists;
		} catch (SQLException e) {
			LOG.error(e);
			throw e;
		}
	}
	
	@Override
	@Transactional
	public List<DatabaseColumn> getColumns(String tableName) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(dataSource);
		List<DatabaseColumn> columns=new ArrayList<DatabaseColumn>();
		try {
			ResultSet meta = connection.getMetaData().getColumns(null, null, tableName, null);
			while (meta.next()) {
				DatabaseColumn column=new DatabaseColumn();
				column.columnName=meta.getString(4);
				column.sqlType=meta.getInt(5);
				column.typeName=meta.getString(6);
				column.columnSize=meta.getInt(7);
				column.nullable=meta.getInt(11);
				column.defaultValue=meta.getString(13);
				column.nullable2=meta.getString(18);
				column.autoIncrement=meta.getString(23);
				columns.add(column);
			}
			meta.close();
			return columns;
		} catch (SQLException e) {
			throw e;
		} finally {
			DataSourceUtils.doReleaseConnection(connection, dataSource);
		}
		
	}
	
	/**
	 * @throws SQLException 
	 * @see org.iita.accessions2.service.DatabaseService#dropColumn(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void dropColumn(String tableName, String columnName) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(dataSource);

		StringBuffer query = new StringBuffer();
		query.append("ALTER TABLE `").append(tableName).append("` DROP COLUMN `").append(columnName).append("`;");

		try {
			PreparedStatement pstmt = connection.prepareStatement(query.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOG.error("Cannot alter table: " + e.getMessage());
			throw e;
		} finally {
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
	}
	
	/**
	 * @throws SQLException 
	 * @see org.iita.accessions2.service.DatabaseService#renameColumn(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void renameColumn(String tableName, String originalColumnName, String newColumnName, Class<?> clazz) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(dataSource);

		StringBuffer query = new StringBuffer();
		query.append("ALTER TABLE `").append(tableName).append("` CHANGE COLUMN `").append(originalColumnName).append("` `");
		query.append(newColumnName).append("` ");
		query.append(getDBType(clazz)).append(" DEFAULT NULL;");

		try {
			PreparedStatement pstmt = connection.prepareStatement(query.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOG.error("Cannot alter table: " + e.getMessage());
			throw e;
		} finally {
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
	}

	/**
	 * @return Altered column name if name already exists in table
	 * @throws SQLException
	 * @see org.iita.accessions2.service.DatabaseService#addColumn(java.lang.String, java.lang.String, java.lang.Class)
	 */
	@Override
	@Transactional
	public String addColumn(String tableName, String columnName, String afterColumn, Class<?> clazz) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(dataSource);

		StringBuffer query = new StringBuffer();
		query.append("ALTER TABLE `").append(tableName).append("` ADD COLUMN `").append(columnName).append("` ");
		query.append(getDBType(clazz)).append(" NULL");
		if (afterColumn != null)
			query.append(" AFTER `").append(afterColumn).append("`");
		query.append(";");

		try {
			PreparedStatement pstmt = connection.prepareStatement(query.toString());
			pstmt.executeUpdate();
			return columnName;
		} catch (SQLException e) {
			LOG.error("Cannot alter table: " + e.getMessage());
			throw e;
		} finally {
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
	}

	/**
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	private Object getDBType(Class<?> clazz) throws SQLException {
		if (clazz == String.class)
			return "VARCHAR(200)";
		if (clazz == Long.class)
			return "BIGINT";
		if (clazz == Integer.class)
			return "INT";
		if (clazz == Double.class)
			return "DOUBLE";
		if (clazz == Float.class)
			return "FLOAT";
		if (clazz == Boolean.class)
			return "BIT";
		if (clazz==Date.class)
			return "DATE";
		throw new SQLException("Cannot figure out DB type for Java type " + clazz);
	}
}
