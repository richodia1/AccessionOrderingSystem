package org.iita.accessions2.action.genesys;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.genesys2.client.oauth.GenesysApiException;
import org.genesys2.client.oauth.OAuthAuthenticationException;
import org.genesys2.client.oauth.PleaseRetryException;
import org.iita.accessions2.service.AuthenticationRequiredException;
import org.iita.accessions2.service.GenesysService;
import org.iita.accessions2.service.GenesysTokens;
import org.iita.struts.BaseAction;

import com.opensymphony.xwork2.Action;

public class GenesysAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1874676590970761508L;
	private GenesysService genesysService;
	private GenesysTokens genesysTokens;
	private String verifier;
	
	public GenesysTokens getGenesysTokens() {
		return genesysTokens;
	}

	public void setGenesysTokens(GenesysTokens genesysTokens) {
		this.genesysTokens = genesysTokens;
	}

	/**
	 * @param collectionService
	 * 
	 */
	public GenesysAction(GenesysService genesysService) {
		this.genesysService = genesysService;
	}
	
	public String uploadAccession(){
		//TODO take necessary actions to trigger upload to genesys
		this.genesysService.updateAll(genesysTokens);
		addActionMessage("Accession data uploaded successfully!");
		return Action.SUCCESS;
	}
	
	public String checkTokens() {
		if(genesysTokens.getAccessToken()==null){
			// get the Genesys authorization URL
			HttpServletResponse httpServletResponse = ServletActionContext.getResponse(); 
			String authorizationUrl = genesysService.getAuthorizationUrl();
	        System.out.println("Redirecting to : " + authorizationUrl);
			try {
				httpServletResponse.sendRedirect(authorizationUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Action.NONE;
	}
	
	public String verifier(){
		System.out.println(this.verifier);
		try {
			this.genesysService.authorize(genesysTokens, verifier);
		} catch (OAuthAuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PleaseRetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GenesysApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return Action.SUCCESS;
	}

	/**
	 * @param verifier the verifier to set
	 */
	public void setCode(String verifier) {
		this.verifier = verifier;
	}

	/**
	 * @return the verifier
	 */
	public String getCode() {
		return verifier;
	}
}
