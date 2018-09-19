/**
 * accession2.Struts Jan 18, 2010
 */
package org.iita.accessions2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.iita.entity.VersionedEntity;

/**
 * Column coding values
 * 
 * @author mobreza
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "column_id", "codedValue" }) })
public class Coding extends VersionedEntity {
	private static final long serialVersionUID = 6102038776887023922L;

	private ColumnDescription column;
	private int codedValue;
	private String actualValue;
	private String code;

	/**
	 * @return the column
	 */
	@ManyToOne(cascade = {}, optional = false)
	public ColumnDescription getColumn() {
		return this.column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(ColumnDescription column) {
		this.column = column;
	}

	/**
	 * @return the codedValue
	 */
	public int getCodedValue() {
		return this.codedValue;
	}

	/**
	 * @param codedValue the codedValue to set
	 */
	public void setCodedValue(int codedValue) {
		this.codedValue = codedValue;
	}

	/**
	 * @return the actualValue
	 */
	@Column(length = 500)
	public String getActualValue() {
		return this.actualValue;
	}

	/**
	 * @param actualValue the actualValue to set
	 */
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}

	/**
	 * Get code
	 * 
	 * @return the code
	 */
	@Column(length = 100)
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		if (code != null && code.length() == 0)
			code = null;
		this.code = code;
	}
}
