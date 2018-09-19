/**
 * 
 */
package org.iita.accessions2.service;

import org.iita.ordering.SmtaProfileModel;

/**
 * @author ken
 *
 */
public interface SmtaProfileService {
	public SmtaProfileModel getProfile(String pid, String password);
}
