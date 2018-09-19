/**
 * accession2.Struts Jan 21, 2010
 */
package org.iita.accessions2.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.hibernate.search.bridge.FieldBridge;
import org.iita.accessions2.model.ColumnDescription;
import org.iita.accessions2.model.Experiment;

/**
 * @author mobreza
 *
 */
public class ExperimentBridge implements FieldBridge {

	/**
	 * @see org.hibernate.search.bridge.FieldBridge#set(java.lang.String, java.lang.Object, org.apache.lucene.document.Document, org.apache.lucene.document.Field.Store, org.apache.lucene.document.Field.Index, java.lang.Float)
	 */
	@Override
	public void set(String name, Object value, Document document, Store store, Index index, Float boost) {
		Experiment experiment = (Experiment) value;

		StringBuffer fieldValue = new StringBuffer().append(experiment.getTitle()).append(" ");
		fieldValue.append(experiment.getAuthors()).append(" ");
		fieldValue.append(experiment.getDescription()).append(" ");

		// what else to append? all column names

		for (ColumnDescription columnDescription : experiment.getColumns()) {
			fieldValue.append(columnDescription.getTitle()).append(" ");
			fieldValue.append(columnDescription.getDescription()).append(" ");			
		}
		
		Field field = new Field(name, fieldValue.toString(), store, index);

		if (boost != null)
			field.setBoost(boost);

		document.add(field);

	}

}
