/**
 * accession2.Struts Jan 27, 2010
 */
package org.iita.accessions2.service;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;

/*
 * *
 * 
 * @author mobreza
 */
public class LocalizedService implements LocaleProvider {
	static final Log LOG = LogFactory.getLog(LocalizedService.class);
	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(), this);

	/**
	 * 
	 */
	public LocalizedService() {
		super();
	}

	public String getText(String aTextName) {
		return textProvider.getText(aTextName);
	}

	public String getText(String aTextName, String defaultValue) {
		return textProvider.getText(aTextName, defaultValue);
	}

	public String getText(String aTextName, String defaultValue, String obj) {
		return textProvider.getText(aTextName, defaultValue, obj);
	}

	public String getText(String aTextName, List<Object> args) {
		return textProvider.getText(aTextName, args);
	}

	public String getText(String key, String[] args) {
		return textProvider.getText(key, args);
	}

	public String getText(String aTextName, String defaultValue, List<Object> args) {
		return textProvider.getText(aTextName, defaultValue, args);
	}

	public String getText(String key, String defaultValue, String[] args) {
		return textProvider.getText(key, defaultValue, args);
	}

	/**
	 * @see com.opensymphony.xwork2.LocaleProvider#getLocale()
	 */
	@Override
	public Locale getLocale() {
		ActionContext ctx = ActionContext.getContext();
		if (ctx != null) {
			return ctx.getLocale();
		} else {
			LOG.debug("Action context not initialized");
			return null;
		}
	}

}