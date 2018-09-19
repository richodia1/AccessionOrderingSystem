/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.model.ordering;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.iita.ordering.Order;

/**
 * @author mobreza
 * 
 */
@Entity
public class Request extends Order<RequestItem, Requestor, RequestState> {
	private static final long serialVersionUID = 2290798066802815353L;

	private boolean smtaAccepted = false;
	private String pqpsDocumentFile;
	private boolean requiresImportPermit = true;
	private String importPermitFile;
	private String validationKey;
	private String organizationType;
	private String intendedUse;
	private String description;

	/**
	 * @return the smtaAccepted
	 */
	public boolean isSmtaAccepted() {
		return this.smtaAccepted;
	}

	/**
	 * @param smtaAccepted the smtaAccepted to set
	 */
	public void setSmtaAccepted(boolean smtaAccepted) {
		this.smtaAccepted = smtaAccepted;
	}

	/**
	 * @return the pqpsDocumentFile
	 */
	@Column(length = 200)
	public String getPqpsDocumentFile() {
		return this.pqpsDocumentFile;
	}

	/**
	 * @param pqpsDocumentFile the pqpsDocumentFile to set
	 */
	public void setPqpsDocumentFile(String pqpsDocumentFile) {
		this.pqpsDocumentFile = pqpsDocumentFile;
	}

	/**
	 * @return the requiresImportPermit
	 */
	public boolean getRequiresImportPermit() {
		return this.requiresImportPermit;
	}

	/**
	 * @param requiresImportPermit the requiresImportPermit to set
	 */
	public void setRequiresImportPermit(boolean requiresImportPermit) {
		this.requiresImportPermit = requiresImportPermit;
	}

	/**
	 * @return the importPermitFile
	 */
	@Column(length = 200)
	public String getImportPermitFile() {
		return this.importPermitFile;
	}

	/**
	 * @param importPermitFile the importPermitFile to set
	 */
	public void setImportPermitFile(String importPermitFile) {
		this.importPermitFile = importPermitFile;
	}

	/**
	 * @return the validationKey
	 */
	@Column(length = 10)
	public String getValidationKey() {
		return this.validationKey;
	}

	/**
	 * @param validationKey the validationKey to set
	 */
	public void setValidationKey(String validationKey) {
		this.validationKey = validationKey;
	}

	/**
	 * @return the organizationType
	 */
	@Column(length = 150)
	public String getOrganizationType() {
		return this.organizationType;
	}

	/**
	 * @param organizationType the organizationType to set
	 */
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}
	
	/**
	 * @return the description
	 */
	@Lob
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the intendedUse
	 */
	@Lob
	public String getIntendedUse() {
		return this.intendedUse;
	}
	
	/**
	 * @param intendedUse the intendedUse to set
	 */
	public void setIntendedUse(String intendedUse) {
		this.intendedUse = intendedUse;
	}
}
