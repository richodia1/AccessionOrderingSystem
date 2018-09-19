/**
 * accession2.Struts Nov 19, 2010
 */
package org.iita.accessions2.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.iita.struts.BaseAction;
import org.iita.struts.DownloadableStream;
import org.iita.struts.webfile.ServerFile;

import com.opensymphony.xwork2.Action;

/**
 * Struts action to download files from server
 * 
 * @author mobreza
 */
@SuppressWarnings("serial")
public class FileAccessAction extends BaseAction implements DownloadableStream {
	private String uri;
	private String downloadFileName;
	private InputStream downloadStream;
	private File rootDirectory;

	/**
	 * @param rootDirectory the rootDirectory to set
	 */
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = new File(rootDirectory);
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String execute() {
		if (this.uri == null || this.uri.length() == 0)
			throw new RuntimeException("No such file");

		try {
			ServerFile serverFile = ServerFile.getServerFile(this.rootDirectory, this.uri);
			this.downloadFileName = serverFile.getFileName();
			this.downloadStream = new FileInputStream(serverFile.getFile());
			return "download";
		} catch (IOException e) {
			LOG.error(e, e);
			return Action.ERROR;
		}

	}

	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadFileName()
	 */
	@Override
	public String getDownloadFileName() {
		return this.downloadFileName;
	}

	/**
	 * @see org.iita.struts.DownloadableStream#getDownloadStream()
	 */
	@Override
	public InputStream getDownloadStream() {
		return this.downloadStream;
	}

}
