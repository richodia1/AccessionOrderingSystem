package org.iita.accessions2.service;

import org.genesys2.client.oauth.GenesysApiException;
import org.genesys2.client.oauth.OAuthAuthenticationException;
import org.genesys2.client.oauth.PleaseRetryException;


public interface GenesysService {

	public void updateAll(GenesysTokens genesysTokens);

	void checkTokens(GenesysTokens genesysTokens) throws AuthenticationRequiredException;

	public String getAuthorizationUrl();

	public void authorize(GenesysTokens genesysTokens, String verifier) throws OAuthAuthenticationException, PleaseRetryException, GenesysApiException;
}
