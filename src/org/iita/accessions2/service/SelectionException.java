/**
 * accession2.Struts Jan 20, 2010
 */
package org.iita.accessions2.service;

/**
 * @author mobreza
 * 
 */
public class SelectionException extends Exception {

	private static final long serialVersionUID = -8632856432983935237L;

	/**
	 * 
	 */
	public SelectionException() {
	}

	/**
	 * @param arg0
	 */
	public SelectionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public SelectionException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SelectionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
