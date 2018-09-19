/**
 * 
 */
package org.iita.accessions2.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.genesys2.client.oauth.GenesysApiException;
import org.genesys2.client.oauth.GenesysClient;
import org.genesys2.client.oauth.OAuthAuthenticationException;
import org.genesys2.client.oauth.PleaseRetryException;
import org.genesys2.client.oauth.api.GenesysApi;
import org.genesys2.client.oauth.api.accession.AccessionJson;
import org.genesys2.client.oauth.api.accession.CollectingJson;
import org.genesys2.client.oauth.api.accession.GeoJson;
import org.iita.accessions2.model.Accession;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * @author ken
 *
 */
public class GenesysServiceImpl implements GenesysService {
	static final Log LOG = LogFactory.getLog(GenesysServiceImpl.class);
	private EntityManager entityManager;
	private String serverUrl;
	private String clientKey;
	private String clientSecret;
	private String callbackUrl;
	private final String myInstCode = "NGA039";
	private HttpServletResponse response;
	
	
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @param genesysTokens user’s access and refresh tokens from Session
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void updateAll(final GenesysTokens genesysTokens) {
		GenesysClient genesysClient = makeClient(genesysTokens);
		List<Accession> accessions = (List<Accession>) this.entityManager.createQuery("from Accession a where a.name is not null and a.genus is not null order by a.name asc").getResultList();
		// and a.name='TVSu-298'
		ArrayList<AccessionJson> forUpload=new ArrayList<AccessionJson>();
		int counter = 0;
		for (Accession acc : accessions) {
			counter++;
			//LOG.info("Uploading " + counter + " of " + accessions.size() + " records to Genesys");
		        if (forUpload.size() >= 50) {
		                // Upload when 50 records in the list
		                try {
							genesysClient.updateAccessions(myInstCode, forUpload);
							//LOG.info("Counter: " + counter + ", Size uploaded: " + forUpload.size());
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						} catch (GenesysApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}
		                // Clear the list
		                forUpload.clear();
		        }
		        /*
		        Gson g = new Gson();
				String json = g.toJson(makeGenesysRecord(acc));
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("application/json;charset = utf-8");
				response.setHeader("Cache-Control", "no-cache");
		
				PrintWriter pw;
				try {
					pw = response.getWriter();

					pw.print (json);
					pw.flush ();
					pw.close ();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		    
		        forUpload.add(makeGenesysRecord(acc));
		}
	
		// After the loop, check if there’s anything left to upload
		if (forUpload.size() >= 1) {
		        // Upload when there is on or more records in the list
		        try {
		        	//LOG.debug("Size to upload: " + forUpload.size());
					genesysClient.updateAccessions(myInstCode, forUpload);
					//LOG.debug("Size uploaded: " + forUpload.size());
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (GenesysApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        // Clear the list
		        forUpload.clear();
		}
	}
	
	private AccessionJson makeGenesysRecord(Accession acc) {
        AccessionJson j=new AccessionJson();

        // Fill the object with acc data
        j.setAcceNumb(acc.getName());
        j.setInstCode(this.myInstCode);
		j.setGenus(acc.getGenus());
		j.setSpecies(acc.getSpecies());
		j.setSpauthor(acc.getSpAuthor());
		j.setSubtaxa(acc.getSubtaxa());
		j.setSubtauthor(acc.getSubtAuthor());
		
		j.setAcceUrl(acc.getAcceurl());
	
		//j.setUuid(acc.getUuid());
		j.setOrgCty(getCountryName(acc.getOrigCty()));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		if(acc.getAcqDate()!=null)
			j.setAcqDate(sdf.format(acc.getAcqDate()));
		
		//j.setMlsStat(acc.getMlsStat());
		j.setInTrust(acc.getInTrust());
		j.setAvailable(acc.getAvailable());
		
		if(acc.getStorage()!=null){
			String[] stor = acc.getStorage().split(";");
		    Integer[] intArray = new Integer[stor.length];
		    for (int i = 0; i < stor.length; i++) {
		       String numberAsString = getStorageCoded(stor[i].toString().trim());
		       /*
		       if(acc.getName().equalsIgnoreCase("TVu-1")){
		    		LOG.info("ACCESSION: " + acc.getName() + " :-: STORAGE: IN " +  stor[i]);
		    		LOG.info("ACCESSION: " + acc.getName() + " :-: STORAGE: OUT " +  Integer.parseInt(numberAsString));
		       }*/
		       
		       intArray[i] = Integer.parseInt(numberAsString);
		    }
		    j.setStorage(intArray);//(acc.getStorage)); 
		}
	    
		j.setSampStat(getCoding(acc.getSampStat()));//
		j.setDuplSite(acc.getDuplicateSite());
		j.setBredCode(acc.getBredCode());
		j.setAncest(acc.getAncest());
		j.setDonorCode(acc.getDonorCode());
		//j.setDonorNumb(acc.getDonorNumb());
		j.setDonorName(acc.getDonorName());
		
		CollectingJson coll = new CollectingJson();
		
		if(acc.getCollDate()!=null)
			coll.setCollDate(sdf.format(acc.getCollDate()));
		
		//coll.setCollSite(acc.getCollSite());
		coll.setCollNumb(acc.getCollNumb());
		coll.setCollSrc(getCollSrcCoding(acc.getCollSrc()));
		coll.setCollCode(acc.getCollCode());
		coll.setCollName(acc.getCollName());
		//coll.setCollInstAddress(acc.getCollInstAddress));
		//coll.setCollMissId(acc.getCollMissId();
		j.setColl(coll);
		
		GeoJson geo = new GeoJson();
		geo.setLatitude(acc.getLatitude());
		geo.setLongitude(acc.getLongitude());
		geo.setElevation(acc.getElevation());
		//geo.setCoordUncert(acc.getCoordUncert());
		//geo.setCoordDatum(acc.getCoordDatum());
		//geo.setGeorefMeth(acc.getGeorefMeth());
		j.setGeo(geo);
        return j;
	}
	
	@Override
	public void checkTokens(final GenesysTokens genesysTokens) throws AuthenticationRequiredException {
	        GenesysClient genesysClient=makeClient(genesysTokens);
	        try {
				genesysClient.me();
				
				// if that succeeds, you should update tokens
				genesysTokens.setAccessToken(genesysClient.getAccessToken().getToken());
				genesysTokens.setRefreshToken(genesysClient.getRefreshToken().getToken());
			} catch (PleaseRetryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenesysApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private GenesysClient makeClient(final GenesysTokens genesysTokens) {
	        /*if (genesysTokens==null) {
	                throw new NullPointerException("GenesysTokens should not be null!");
	        }*/

	        GenesysClient genesysClient=new GenesysClient();
	        genesysClient.setGenesysApi(new GenesysApi());
	        genesysClient.setBaseUrl(this.serverUrl);
	        
	        /*if(genesysTokens.getAccessToken() == null){
	        	genesysClient.connect(this.clientKey, this.clientSecret, this.callbackUrl);
	        	String verifierCode = null; // whatever user provides
	        	genesysClient.authenticate(verifierCode);
	        }*/
	        
	        if(genesysTokens!=null){
		        if(genesysTokens.getAccessToken() != null){
		        	genesysClient.setAccessToken(genesysTokens.getAccessToken());
		        }
		        
		        if(genesysTokens.getRefreshToken() != null){
		        	genesysClient.setRefreshToken(genesysTokens.getRefreshToken());
		        }
	        }
	        genesysClient.connect(this.clientKey, this.clientSecret, this.callbackUrl);
	        return genesysClient;
	}
	
	private Integer getCoding(Integer codingId){
		/*Coding coding = (Coding) entityManager.createQuery("from Coding where codedValue =: codingId").setParameter("codingId", codingId).getSingleResult();
		if(coding!=null)
			return Integer.valueOf(coding.getCode());
		else
			return null;*/
		if(codingId==null) return null;
		
		Integer sampStat=null;
		
		switch (codingId){
			case 1:	sampStat=300; break;
			case 2: sampStat=410; break;
		    case 3: sampStat=200; break;
		    case 4: sampStat=100; break;
		    case 5: sampStat=500; break;
		    case 6: sampStat=410; break;
		    case 7: sampStat=300; break;
		    case 8: sampStat=999; break;
		    case 9: sampStat=300; break;
		    case 10: sampStat=999; break;
		    case 11: sampStat=999; break;
	        case 15: sampStat=300; break;
		    default: break;
		}
		return sampStat;
	}
	
	private String getStorageCoded(String stor){
		if(stor==null) return null;
		
		String storCode=null;
		
		if (stor.equalsIgnoreCase("Seed collection"))
			storCode="10";
		else if (stor.equalsIgnoreCase("Medium term"))
			storCode="12"; 
		else if (stor.equalsIgnoreCase("Short term"))
		    storCode="11";
		else if (stor.equalsIgnoreCase("Long term"))
		    storCode="13";
		else if (stor.equalsIgnoreCase("Field collection"))
		    storCode="20";
		else if (stor.equalsIgnoreCase("In vitro collection"))
		    storCode="30";
		else if (stor.equalsIgnoreCase("Cryopreserved collection"))
		    storCode="40";
		else if (stor.equalsIgnoreCase("DNA collection"))
		    storCode="50";
		else if (stor.equalsIgnoreCase("Other"))
		    storCode="99";
		else
		    storCode = "0";
		return storCode;
	}
	
	private Integer getCollSrcCoding(Integer codingId){
		/*Coding coding = (Coding) entityManager.createQuery("from Coding where codedValue =: codingId").setParameter("codingId", codingId).getSingleResult();
		if(coding!=null)
			return Integer.valueOf(coding.getCode());
		else
			return null;*/
		if(codingId==null) return null;
		
		Integer collSrc=null;
		
		switch (codingId){
			case 1:	collSrc=21; break;
			case 2: collSrc=40; break;
		    case 3: collSrc=99; break;
		    case 4: collSrc=30; break;
		    case 5: collSrc=10; break;
		    case 6: collSrc=26; break;
		    case 9: collSrc=20; break;
		    case 11: collSrc=20; break;
		    case 19: collSrc=10; break;
		    case 20: collSrc=40; break;
		    case 23: collSrc=24; break;
		    default: break;
		}
		return collSrc;
	}
	
	private String getCountryName(Integer countryId){
		if(countryId==null) return null;
		
		String countryName=null;
		
		switch (countryId){
			case 1:	countryName="NGA"; break;
			case 2: countryName="TGO"; break;
		    case 3: countryName="Togo"; break;
		    case 4: countryName="USA"; break;
		    case 5: countryName="NER"; break;
		    case 6: countryName="CMR"; break;
		    case 7: countryName="GHA"; break;
		    case 8: countryName="BEN"; break;
		    case 9: countryName="ZMB"; break;
		    case 10: countryName="TZA"; break;
		    case 11: countryName="BWA"; break;
		    case 12: countryName="MWI"; break;
		    case 13: countryName="TWN"; break;
		    case 14: countryName="BFA"; break;
		    case 15: countryName="ZWE"; break;
		    case 16: countryName="EGY"; break;
		    case 17: countryName="CAF"; break;
		    case 18: countryName="MLI"; break;
		    case 19: countryName="SEN"; break;
		    case 20: countryName="GIN"; break;
		    case 21: countryName="KEN"; break;
		    case 22: countryName="BRA"; break;
		    case 23: countryName="ZAF"; break;
		    case 24: countryName="TCD"; break;
		    case 25: countryName="BEN"; break;//Republic of Benin
		    case 26: countryName="COG"; break;//Republic of the Congo
		    case 27: countryName="CIV"; break;//Cote d'Ivoire
		    case 28: countryName="UGA"; break;//Uganda
		    case 29: countryName="Not Available"; break;
		    case 30: countryName="CPV"; break;//Cape verde
		    case 31: countryName="ITA"; break;//Italy
		    case 32: countryName="SLE"; break;//Sierra Leone
		    case 33: countryName="IDN"; break;//Indonesia
		    case 34: countryName="GAB"; break;//Gabon
		    case 35: countryName="PHL"; break;//Philippines
		    case 36: countryName="SOM"; break;//Somalia
		    case 37: countryName="SWZ"; break;//Swaziland
		    case 38: countryName="MDG"; break;//Madagascar
		    case 39: countryName="AFG"; break;//Afghanistan
		    case 40: countryName="OMN"; break;//Oman
		    case 41: countryName="LSO"; break;//Lesotho
		    case 42: countryName="COD"; break;//Democratic Republic of the Congo
		    case 43: countryName="COD"; break;//Zaire
		    case 44: countryName="SDN"; break;//Sudan
		    case 45: countryName="BDI"; break;//Burundi
		    case 46: countryName="COG"; break;//Congo
		    case 47: countryName="COL"; break;//Colombia
		    case 48: countryName="PRI"; break;//Puerto Rico
		    case 49: countryName="TUR"; break;//Turkey
		    case 50: countryName="RUS"; break;//Union of Soviet Socialist Republics
		    case 51: countryName="HUN"; break;//Hungary
		    case 52: countryName="HND"; break;//Honduras
		    case 53: countryName="AUS"; break;//Australia
		    case 54: countryName="MOZ"; break;//Mozambique
		    case 55: countryName="IRN"; break;//Iran
		    case 56: countryName="MEX"; break;//Mexico
		    case 57: countryName="CHN"; break;//China
		    case 58: countryName="FRA"; break;//France
		    case 59: countryName="YEM"; break;//Yemen
		    case 60: countryName="GMB"; break;//Gambia
		    case 61: countryName="NAM"; break;//Namibia
		    case 62: countryName="SUR"; break;//Suriname
		    case 63: countryName="PNG"; break;//Papua New Guinea
		    case 64: countryName="LBR"; break;//Liberia
		    case 65: countryName="GBR"; break;//United Kingdom
		    case 66: countryName="CRI"; break;//Costa Rica
		    case 67: countryName="THA"; break;//Thailand
		    case 68: countryName="GNQ"; break;//Equatorial Guinea
		    case 69: countryName="PRY"; break;//Paraguay
		    case 70: countryName="CPV"; break;//Cape Verde
		    case 71: countryName="CHL"; break;//Chile
		    case 72: countryName="CPV"; break;//Cape Verde
		    case 73: countryName="RWA"; break;//Rwanda
		    case 74: countryName="GTM"; break;//Guatemala
		    case 75: countryName="ETH"; break;//Ethiopia
		    case 76: countryName="ARG"; break;//Argentina
		    case 77: countryName="COD"; break;//Democratic Republic of the congo
		    case 78: countryName="BEL"; break;//Belgium
		    case 79: countryName="ISR"; break;//Israel
		    case 80: countryName="PAK"; break;//Pakistan
		    case 81: countryName="LKA"; break;//Sri Lanka
		    case 82: countryName="GLP"; break;//Guadeloupe
		    case 83: countryName="BGD"; break;//Bangladesh
		    case 84: countryName="TWN"; break;//Taiwan
		    case 85: countryName="AGO"; break;//Angola
		    case 86: countryName="SYR"; break;//Syrian Arab Republic
		    case 87: countryName="PRT"; break;//Portugal
		    case 88: countryName="GMB"; break;//Gambia
		    case 89: countryName="UAE"; break;//United Arab Emirates
		    case 90: countryName="VEN"; break;//Venezuela
		    case 91: countryName="JPN"; break;//Japan
		    case 92: countryName="DOM"; break;//Dominican Republic
		    case 93: countryName="NPL"; break;//Nepal
		    case 94: countryName="DZA"; break;//Algeria
		    case 95: countryName="PLW"; break;//Palau
		    case 96: countryName="MYS"; break;//Malaysia
		    case 97: countryName="URY"; break;//Uruguay
		    case 98: countryName="LAO"; break;//Laos
		    case 99: countryName="VNM"; break;//Viet Nam
		    case 100: countryName="NIC"; break;//Nicaragua
		    case 101: countryName="MRT"; break;//Mauritania
		    case 102: countryName="ESP"; break;//Spain
		    case 103: countryName="CUB"; break;//Cuba
		    case 104: countryName="GUY"; break;//Guyana
		    case 105: countryName="GUM"; break;//Guam
		    case 106: countryName="AUT"; break;//Austria
		    case 107: countryName="KEN"; break;//Kenya
		    case 108: countryName="JAM"; break;//Jamaica
		    case 109: countryName="GNB"; break;//Guinea-Bissau
		    case 110: countryName="SLV"; break;//El Salvador
		    case 111: countryName="LBN"; break;//Lebanon
		    case 112: countryName="SGP"; break;//Singapore
		    case 113: countryName="NCL"; break;//New Caledonia
		    case 114: countryName="BLZ"; break;//Belize
		    case 115: countryName="LBR"; break;//Liberia
		    case 116: countryName="PER"; break;//Peru
		    case 117: countryName="TTO"; break;//Trinidad and Tobago
		    case 118: countryName="MWI"; break;//Malawi
		    case 119: countryName="NGA"; break;//Nigeria
		    case 120: countryName="RWA"; break;//Rwanda
		    case 121: countryName="UGA"; break;//Uganda
		    case 123: countryName="NGA (not confirmed)"; break;//Nigeria
		    case 125: countryName="TGO (not confirmed)"; break;//Togo
		    case 126: countryName="COG (not confirmed)"; break;//Republic of the Congo
		    case 129: countryName="BEN (not confirmed)"; break;//Benin
		    case 132: countryName="GMB (not confirmed)"; break;//Gambia
		    case 144: countryName="LBR (not confirmed)"; break;//Liberia
		    case 158: countryName="IRN"; break;//IIran
		    case 193: countryName="PRI"; break;//Puerto Rica
		    case 210: countryName="MLI (not confirmed)"; break;//Mali
		    case 211: countryName="MDG (not confirmed)"; break;//Madagascar
		    case 212: countryName="NER (not confirmed)"; break;//Niger
		    case 213: countryName="COD (not confirmed)"; break;//Democratic Republic of the Congo
		    case 214: countryName="COG (not confirmed)"; break;//Congo
		    case 216: countryName="ZWE (not confirmed)"; break;//Zimbabwe
		    case 218: countryName="BEN (not confirmed)"; break;//Republic of Benin
		    case 219: countryName="TCD (not confirmed)"; break;//Chad
		    case 220: countryName="LSO (not confirmed)"; break;//Lesotho
		    case 221: countryName="ZMB (not confirmed)"; break;//Zambia
		    case 222: countryName="KEN (not confirmed)"; break;//Kenya
		    case 223: countryName="MWI (not confirmed)"; break;//Malawi
		    case 224: countryName="SOM (not confirmed)"; break;//Somalia
		    case 237: countryName="PAN"; break;//Panama
		    default:
		    	break;
		}
		return countryName;
	}

	@Override
	public String getAuthorizationUrl() {
		GenesysClient genesysClient=makeClient(null);
		return genesysClient.getAuthorizationUrl(null);
	}

	@Override
	public void authorize(GenesysTokens genesysTokens, String verifier) throws OAuthAuthenticationException, PleaseRetryException, GenesysApiException {
		// Trade the Request Token and Verifier for the Access Token
		GenesysClient genesysClient = makeClient(genesysTokens);
		genesysClient.authenticate(verifier);
        genesysTokens.setAccessToken(genesysClient.getAccessToken().getToken());
        
        genesysTokens.setRefreshToken(genesysClient.getRefreshToken().getToken());
        System.out.println(genesysClient.me());
	}
}
