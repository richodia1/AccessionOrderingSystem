/**
 * accession2.Struts Sep 14, 2010
 */
package org.iita.accessions2.service;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 * @author mobreza
 *
 */
public class CSVWriter {

	/**
	 * @param fos
	 * @param data
	 * @throws IOException 
	 */
	public static void addLine(Writer csvStream, Object ... data) throws IOException {
		for (int i=0; i<data.length; i++) {
			if (i>0)
				csvStream.write(",");
			writeObject(csvStream, data[i]);
		}
		
		csvStream.write("\r\n");
	}

	/**
	 * @param csvStream
	 * @param o
	 * @throws IOException 
	 */
	private static void writeObject(Writer csvStream, Object o) throws IOException {
		if (o==null)
			return;
		if (o instanceof String) {
			writeString(csvStream, (String)o);
		}  else if (o instanceof Date){
			csvStream.write(String.format("%1$tF", o));
		}else {
			csvStream.write(o.toString());
		}
	}

	/**
	 * @param csvStream
	 * @param o
	 * @throws IOException 
	 */
	private static void writeString(Writer csvStream, String o) throws IOException {
		csvStream.write("\"");
		csvStream.write(o);
		csvStream.write("\"");
	}

}
