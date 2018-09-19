/**
 * accession2.Struts Jun 14, 2010
 */
package org.iita.accessions2.service;

/**
 * Exception thrown during order validation
 * 
 * @author mobreza
 */
public class OrderValidationException extends OrderException {
	private static final long serialVersionUID = 5669060794520584503L;

	/**
	 * @param string
	 */
	public OrderValidationException(String arg0) {
		super(arg0);
	}
}
