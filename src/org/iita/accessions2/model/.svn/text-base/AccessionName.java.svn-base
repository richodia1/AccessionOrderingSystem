/**
 * accession2.Struts Jan 18, 2010
 */
package org.iita.accessions2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.iita.entity.MySqlBaseEntity;

/**
 * Additional accession name
 * 
 * @author mobreza
 */
@Entity
public class AccessionName extends MySqlBaseEntity {
	private static final long serialVersionUID = -5769453717130797460L;
	private Accession accession;
	private String name;
	private String usedBy;

	/**
	 * Get accession
	 * 
	 * @return the accession
	 */
	@ManyToOne(cascade = {}, optional = false)
	public Accession getAccession() {
		return this.accession;
	}
	
	/**
	 * @param accession the accession to set
	 */
	public void setAccession(Accession accession) {
		this.accession = accession;
	}

	/**
	 * @return the name
	 */
	@Column(length = 500, nullable = false)
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the usedBy
	 */
	@Column(length = 500, nullable = true)
	public String getUsedBy() {
		return this.usedBy;
	}

	/**
	 * @param usedBy the usedBy to set
	 */
	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}

}
