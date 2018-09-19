/**
 * accession2.Struts Jan 21, 2010
 */
package org.iita.accessions2.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * @author mobreza
 * 
 */
public class DataHistogram implements Iterable<Object> {
	/**
	 * 
	 */
	public static final String NULLVALUES = "--NULLVALUES";
	private long total = 0;
	private Hashtable<Object, Long> histogram = new Hashtable<Object, Long>();
	private ArrayList<Object> sorted = null;

	/**
	 * 
	 */
	public DataHistogram() {

	}

	public void accountFor(Object value) {
		if (value == null) {
			value = NULLVALUES;
		}
		if (histogram.contains(value)) {
			long count = histogram.get(value).longValue() + 1;
			histogram.put(value, count);
		} else {
			histogram.put(value, 1l);
		}
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return this.total;
	}

	/**
	 * @return the histogram
	 */
	public Hashtable<Object, Long> getHashtable() {
		finish();
		return this.histogram;
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Object> iterator() {
		finish();
		return this.sorted.iterator();
	}

	public int size() {
		finish();
		return this.sorted.size();
	}
	
	/**
	 * 
	 */
	private void finish() {
		synchronized (this) {
			if (this.sorted != null)
				return;

			this.sorted = new ArrayList<Object>();
			this.sorted.addAll(this.histogram.keySet());
			Collections.sort(this.sorted, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					Long c1 = histogram.get(o1), c2 = histogram.get(o2);
					return -c1.compareTo(c2);
				}
			});

			long tot = 0;
			for (Object value : this.histogram.keySet()) {
				Long count = this.histogram.get(value);
				if (count != null)
					tot += count.longValue();
			}
			this.total = tot;
		}
	}

	/**
	 * @param resultList
	 * @return
	 */
	public static DataHistogram fromResultList(List<Object[]> resultList) {
		DataHistogram hist = new DataHistogram();
		for (Object[] row : resultList) {
			Object value = row[0];
			if (value == null) {
				value = NULLVALUES;
			}
			if (hist.histogram.contains(value)) {
				long count = hist.histogram.get(value).longValue() + ((BigInteger) row[1]).longValue();
				hist.histogram.put(value, count);
			} else {
				hist.histogram.put(value, ((BigInteger) row[1]).longValue());
			}
		}
		hist.finish();
		return hist;
	}
}
