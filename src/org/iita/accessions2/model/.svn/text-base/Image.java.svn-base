/**
 * accession2.Struts Jan 18, 2010
 */
package org.iita.accessions2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.iita.entity.VersionedEntity;

/**
 * Image data stored in DB
 * 
 * @author mobreza
 */
@Entity
public class Image extends VersionedEntity {
	private static final long serialVersionUID = 5501991584668447510L;
	private byte[] data;
	private String name;
	private String description;
	private String author;

	/**
	 * @return the data
	 */
	@Lob
	public byte[] getData() {
		return this.data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the name
	 */
	@Column(length = 200)
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
	 * @return the author
	 */
	@Column(length = 200)
	public String getAuthor() {
		return this.author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

}
