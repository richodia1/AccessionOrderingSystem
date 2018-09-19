/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.service;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author mobreza
 * 
 */
@Aspect
public class ApplicationArchitecture {
	@Pointcut("within(org.iita.accessions2.service..*)")
	public void serviceMethod() {
	}
}
