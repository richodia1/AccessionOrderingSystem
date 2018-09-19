/**
 * accession2.Struts Jan 21, 2010
 */
package org.iita.accessions2.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.hibernate.search.bridge.FieldBridge;
import org.iita.accessions2.model.Accession;
import org.iita.accessions2.model.Collection;

/**
 * @author mobreza
 * 
 */
public class AccessionBridge implements FieldBridge {

	/**
	 * Bridge used when re-indexing {@link Accession} objects.
	 * 
	 * @see org.hibernate.search.bridge.FieldBridge#set(java.lang.String, java.lang.Object, org.apache.lucene.document.Document,
	 *      org.apache.lucene.document.Field.Store, org.apache.lucene.document.Field.Index, java.lang.Float)
	 */
	@Override
	public void set(String name, Object value, Document document, Store store, Index index, Float boost) {
		Accession accession = (Accession) value;

		String accnName = accession.getName();
		StringBuffer fieldValue = new StringBuffer().append(accnName).append(" ");
		String[] split = accnName.split("\\-");
		if (split.length>1)
			fieldValue.append(split[0]).append(" ").append(split[1]).append(" ");
		
		Collection collection=accession.getCollection();
		if (collection!=null) {
			fieldValue.append(collection.getName()).append(" ");
		}
		
		addContent(accession.getAccessionId(), fieldValue);
		addContent(accession.getGenus(), fieldValue);
		addContent(accession.getSpecies(), fieldValue);
		addContent(accession.getLocation(), fieldValue);
		addContent(accession.getOrigCty(), fieldValue);
		addContent(accession.getCultivarName(), fieldValue);
		addContent(accession.getRemarks(), fieldValue);

		// what else to append? basically all passport data
		// and then the Experiment data to form the full document

		Field field = new Field(name, fieldValue.toString(), store, index);

		if (boost != null)
			field.setBoost(boost * 2.0f);

		document.add(field);
	}

	/**
	 * @param accession
	 * @param fieldValue
	 */
	private void addContent(Object value, StringBuffer fieldValue) {
		if (value!=null)
			fieldValue.append(value).append(" ");
	}

}
