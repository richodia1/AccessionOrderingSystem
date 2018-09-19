/**
 * 
 */
package org.iita.accessions2.service;

/**
 * @author ken
 *
 */
public class AuthenticationRequiredException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8022055140219874567L;

	public AuthenticationRequiredException() {

	}

	public AuthenticationRequiredException(String arg0) {
		super(arg0);
	}

	public AuthenticationRequiredException(Throwable arg0) {
		super(arg0);
	}

	public AuthenticationRequiredException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
