/**
 * accession2.Struts Jan 19, 2010
 */
package org.iita.accessions2.model;

/**
 * @author mobreza
 * 
 */
public class CodingException extends Exception {
	private static final long serialVersionUID = 6992425910522841633L;
	private String actualValue;
	private Integer codedValue;

	/**
	 * 
	 */
	public CodingException() {
	}

	/**
	 * @param arg0
	 */
	public CodingException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public CodingException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CodingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 * @param value
	 */
	public CodingException(String arg0, String actualValue) {
		super(arg0);
		this.actualValue=actualValue;
	}
	
	/**
	 * @param string
	 * @param value
	 */
	public CodingException(String arg0, Integer value) {
		super(arg0);
		this.codedValue=value;
	}

	/**
	 * @return the actualValue
	 */
	public String getActualValue() {
		return this.actualValue;
	}
	
	/**
	 * @return the codedValue
	 */
	public Integer getCodedValue() {
		return this.codedValue;
	}
}
