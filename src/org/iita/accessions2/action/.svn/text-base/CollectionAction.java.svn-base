/**
 * accession2.Struts Mar 17, 2010
 */
package org.iita.accessions2.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.iita.accessions2.model.Collection;
import org.iita.accessions2.model.Experiment;
import org.iita.accessions2.service.CollectionService;
import org.iita.security.Authorize;
import org.iita.struts.BaseAction;
import org.iita.struts.DownloadableStream;
import org.iita.util.StringUtil;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * Manages Collection profile page actions.
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class CollectionAction extends BaseAction implements DownloadableStream {
	private CollectionService collectionService;
	private Collection collection;
	private InputStream downloadStream;
	private String downloadFileName;
	private List<Experiment> experiments;
	private Experiment experiment;

	/**
	 * @param collectionService
	 * 
	 */
	public CollectionAction(CollectionService collectionService) {
		this.collectionService = collectionService;
	}

	/**
	 * @param collection the collection to set
	 */
	@TypeConversion(converter = "genericConverter")
	public void setId(Collection collection) {
		this.collection = collection;
		if (this.collection != null && !this.collection.isVisible() && !Authorize.hasAuthority("ROLE_USER"))
			this.collection = null;
	}

	/**
	 * @param experimentId the experimentId to set
	 */
	@TypeConversion(converter = "genericConverter")
	public void setExperimentId(Experiment experimentId) {
		this.experiment = experimentId;
	}

	/**
	 * @return the collection
	 */
	public Collection getCollection() {
		return this.collection;
	}

	public long getCollectionSize() {
		return this.collectionService.getCollectionSize(this.collection);
	}

	/**
	 * @return the experiments
	 */
	public List<Experiment> getExperiments() {
		return this.experiments;
	}

	/**
	 * @see org.iita.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		if (this.collection == null) {
			addActionError("No such collection.");
		} else {
			this.experiments = this.collectionService.listExperiments(this.collection);
		}
		return Action.SUCCESS;
	}

	public String download() {
		try {
			if (this.experiment == null || this.experiment.getId().equals(1l)) {
				this.downloadFileName = "IITA-" + this.collection.getName() + ".xls";
				this.downloadStream = this.collectionService.downloadCollection(this.collection);
			} else {
				this.downloadFileName = "IITA-" + this.collection.getName() + "-" + experiment.getTitle() + ".xls";
				this.downloadStream = this.collectionService.downloadCollection(this.collection, this.experiment);
			}
		} catch (IOException e) {
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return "download";
	}

	public String geneSys() {
		try {
			this.downloadFileName = String.format("GeneSys-%1$s.zip", this.collection.getName());
			this.downloadStream = this.collectionService.downloadGeneSys(this.collection);
		} catch (IOException e) {
			addActionError(e.getMessage());
			LOG.error(e.getMessage(), e);
			return Action.ERROR;
		}
		return "download";
	}

	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadFileName()
	 */
	@Override
	public String getDownloadFileName() {
		return StringUtil.sanitizeFileName(this.downloadFileName);
	}

	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadStream()
	 */
	@Override
	public InputStream getDownloadStream() {
		return this.downloadStream;
	}
}
