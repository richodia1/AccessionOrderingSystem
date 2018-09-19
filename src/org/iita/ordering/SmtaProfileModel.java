/**
 * 
 */
package org.iita.ordering;

import java.io.Serializable;

/**
 * @author ken
 *
 */
public class SmtaProfileModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6345166642228258001L;
	private String pid;
	private String type;
    private String legalStatus;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String country;
    private String countryName;
    private String telephone;
    private String fax;
    private String orgname;
    private String aoName;
    private String aoSurname;
    private String aoEmail;
    private String orgAddress;
    private String orgCountry;
    private String orgCountryName;
    private String aoTelephone;
    private String aoFax;
    private String shipAddrFlag;
    private String shipAddress;
    private String shipCountry;
    private String shipTelephone;
	
    public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLegalStatus() {
		return legalStatus;
	}
	public void setLegalStatus(String legalStatus) {
		this.legalStatus = legalStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getAoName() {
		return aoName;
	}
	public void setAoName(String aoName) {
		this.aoName = aoName;
	}
	public String getAoSurname() {
		return aoSurname;
	}
	public void setAoSurname(String aoSurname) {
		this.aoSurname = aoSurname;
	}
	public String getAoEmail() {
		return aoEmail;
	}
	public void setAoEmail(String aoEmail) {
		this.aoEmail = aoEmail;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public String getOrgCountry() {
		return orgCountry;
	}
	public void setOrgCountry(String orgCountry) {
		this.orgCountry = orgCountry;
	}
	public String getOrgCountryName() {
		return orgCountryName;
	}
	public void setOrgCountryName(String orgCountryName) {
		this.orgCountryName = orgCountryName;
	}
	public String getAoTelephone() {
		return aoTelephone;
	}
	public void setAoTelephone(String aoTelephone) {
		this.aoTelephone = aoTelephone;
	}
	public String getAoFax() {
		return aoFax;
	}
	public void setAoFax(String aoFax) {
		this.aoFax = aoFax;
	}
	public String getShipAddrFlag() {
		return shipAddrFlag;
	}
	public void setShipAddrFlag(String shipAddrFlag) {
		this.shipAddrFlag = shipAddrFlag;
	}
	public String getShipAddress() {
		return shipAddress;
	}
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	public String getShipCountry() {
		return shipCountry;
	}
	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}
	public String getShipTelephone() {
		return shipTelephone;
	}
	public void setShipTelephone(String shipTelephone) {
		this.shipTelephone = shipTelephone;
	}
	
	@Override
    public String toString() {
		return "SmtaProfileModel [pid="+ pid +",type="+ type +",legalStatus="+ legalStatus+",name=" + name + ", surname=" + surname + ",email="+email+",address="+ address+",country="+country+",countryName="+countryName+",telephone="+telephone+",fax="+fax +",orgname="+ orgname+",aoName="+aoName+",aoEmail="+aoEmail+",orgAddress="+orgAddress+",orgCountry="+orgCountry+"orgCountryName="+orgCountryName+",aoTelephone="+ aoTelephone +",aoFax="+aoFax+",shipAddrFlag="+shipAddrFlag+",shipAddress="+shipAddress+",shipCountry="+shipCountry+",shipTelephone="+shipTelephone+"]";
	}
}
