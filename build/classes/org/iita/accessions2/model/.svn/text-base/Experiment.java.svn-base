/**
 * accession2.Struts Jan 18, 2010
 */
package org.iita.accessions2.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.Indexed;
import org.iita.entity.VersionedEntity;
import org.iita.util.StringUtil;

/**
 * <p>
 * Experiment meta-data: title, authors, description, date of experiment, location, country.
 * </p>
 * <p>
 * Experiment data is stored in database in table <code>experiment.tableName</code>. The table is a simple "spreadsheet", linking to two other tables: the
 * Experiment table and the Accession table. Those two columns are always created in additon to all fields contained in the experiment results XLS file. The
 * auto_incremented primary key is a must.
 * </p>
 * 
 * @author mobreza
 */
@Entity
@Indexed
@ClassBridge(impl = org.iita.accessions2.lucene.ExperimentBridge.class)
public class Experiment extends VersionedEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5478334146173044239L;

	/** The title. */
	private String title;

	/** The authors. */
	private String authors;

	/** The description. */
	private String description;

	/** The experiment date. */
	private Date experimentDate;

	/** The location. */
	private String location;

	/** The country. */
	private String country;

	/** The table name. */
	private String tableName;

	/** The columns. */
	private List<ColumnDescription> columns;

	/** The visible. */
	private boolean visible = false;

	/** Is this experiment a system table (Hibernate managed) */
	private boolean systemTable = false;

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	@Column(length = 500)
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the authors.
	 * 
	 * @return the authors
	 */
	@Lob
	public String getAuthors() {
		return this.authors;
	}

	/**
	 * Sets the authors.
	 * 
	 * @param authors the authors to set
	 */
	public void setAuthors(String authors) {
		this.authors = StringUtil.nullOrString(authors);
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	@Lob
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = StringUtil.nullOrString(description);
	}

	/**
	 * Gets the experiment date.
	 * 
	 * @return the experimentDate
	 */
	@Temporal(TemporalType.DATE)
	public Date getExperimentDate() {
		return this.experimentDate;
	}

	/**
	 * Sets the experiment date.
	 * 
	 * @param experimentDate the experimentDate to set
	 */
	public void setExperimentDate(Date experimentDate) {
		this.experimentDate = experimentDate;
	}

	/**
	 * Gets the location.
	 * 
	 * @return the location
	 */
	@Column(length = 200)
	public String getLocation() {
		return this.location;
	}

	/**
	 * Sets the location.
	 * 
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the country.
	 * 
	 * @return the country
	 */
	@Column(length = 200)
	public String getCountry() {
		return this.country;
	}

	/**
	 * Sets the country.
	 * 
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the table name.
	 * 
	 * @return the tableName
	 */
	@Column(length = 100, nullable = false)
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * Sets the table name.
	 * 
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * To string.
	 * 
	 * @return the string
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getTitle()).append(", ").append(getAuthors()).append(" [").append(getId()).append("]");
		return sb.toString();
	}

	/**
	 * Gets the columns.
	 * 
	 * @return the columns
	 */
	@OneToMany(cascade = {}, mappedBy = "experiment")
	@OrderBy("sortIndex")
	public List<ColumnDescription> getColumns() {
		return this.columns;
	}

	/**
	 * Sets the columns.
	 * 
	 * @param columns the columns to set
	 */
	public void setColumns(List<ColumnDescription> columns) {
		this.columns = columns;
	}

	/**
	 * Is Experiment publicly accessible?.
	 * 
	 * @return the visible
	 */
	public boolean isVisible() {
		return this.visible;
	}

	/**
	 * Sets the visible.
	 * 
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Checks if this experiment is system table (i.e. Hibernate managed)
	 * 
	 * @return true, if is system
	 */
	public boolean isSystemTable() {
		return systemTable;
	}

	/**
	 * Sets the system.
	 * 
	 * @param system the new system
	 */
	public void setSystemTable(boolean systemTable) {
		this.systemTable = systemTable;
	}
}
