/**
 * accession2.Struts Jan 18, 2010
 */
package org.iita.accessions2.model;

import java.util.Arrays;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.iita.entity.VersionedEntity;

/**
 * Collection entity contains the basic information relating to collections: name, description, accession prefixes.
 * 
 * @author mobreza
 */
@Entity
public class Collection extends VersionedEntity {
	private static final long serialVersionUID = -3273333067583333911L;

	private String name;
	private String shortName;
	private String description;
	private String prefixes;

	// Internal array of prefixes
	private String[] prefixList;

	/** The visible. */
	private boolean visible = false;
	
	/**
	 * Get collection name
	 * 
	 * @return the name
	 */
	@Column(length = 100, nullable = false, unique = true)
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
	 * Collection short name is used for table name generation and URL
	 * 
	 * @return the shortName
	 */
	@Column(length = 20, nullable = false, unique = true)
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Get collection description
	 * 
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
	 * <p>
	 * Get accession prefixes for this collection. Prefix is used to assign names to new accessions added to this collection as follows:
	 * <code>collection.prefix + "-" + accessionNumber</code>, where accessionNumber is the next accession number for this collection (MAX + 1).
	 * </p>
	 * 
	 * <p>
	 * This field contains the allowed prefixes for a collection (Example: "TDa, TDb, ..." for Yam). This list is then used for Collection identification when
	 * accession data is imported from XLS files.
	 * </p>
	 * 
	 * 
	 * @return the prefixes
	 */
	@Column(length = 100, nullable = false)
	public String getPrefixes() {
		return this.prefixes;
	}

	/**
	 * @param prefixes the prefixes to set
	 */
	public void setPrefixes(String prefixes) {
		this.prefixes = prefixes;
		parsePrefixes();
	}

	/**
	 * Get list of prefixes that apply for this collection
	 * 
	 * @return the prefixList
	 */
	@Transient
	public String[] getPrefixList() {
		return this.prefixList;
	}

	/**
	 * 
	 */
	private void parsePrefixes() {
		if (this.prefixes == null) {
			this.prefixList = null;
		} else {
			this.prefixList = this.prefixes.split("[^\\w]+");
			Arrays.sort(this.prefixList, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.length() - o2.length();
				}
			});
			// need to append dash ("-") to prefixes to get fully qualified matches
			for (int i = 0; i < this.prefixList.length; i++) {
				this.prefixList[i] = this.prefixList[i] + "-";
			}
		}
	}

	/**
	 * Does this collection contain a prefix for accession name?
	 * 
	 * @param accessionName
	 * @return
	 */
	public boolean hasAccessionPrefix(String accessionName) {
		if (this.prefixList == null)
			return false;
		if (accessionName == null || accessionName.length() == 0)
			return false;
		for (int i = 0; i < this.prefixList.length; i++) {
			// System.out.println("Collection " + this.name + " pfx '" + this.prefixList[0] + "' -> " + accessionName);
			if (accessionName.startsWith(this.prefixList[i]))
				return true;
		}
		return false;
	}
	
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return this.visible;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%1$s collection %2$s", this.name, this.visible ? "public" : "private");
	}
}
