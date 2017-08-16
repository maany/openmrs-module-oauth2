package org.openmrs.module.oauth2.api;

import org.openmrs.User;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.Client.ClientType;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class JsonMappableClient implements Serializable {

	private Integer id;
	private String name;
	private String description;
	private ClientType clientType;
	private String clientIdentifier;
	private String clientSecret;
	private String website;
	private String uuid;
	private String creator;
	private Date dateCreated;
	private String changedBy;
	private Date dateChanged;
	private Boolean voided = Boolean.FALSE;
	private Date dateVoided;
	private String voidReason;
	private String voidedBy;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private Set<String> scopes;
	private Set<String> grantType;


	public JsonMappableClient(Client client){
		this.id = client.getId();
		this.name = client.getName();
		this.description = client.getDescription();
		this.clientType = client.getClientType();
		this.clientIdentifier = client.getClientIdentifier();
		this.clientSecret = client.getClientSecret();
		this.website = client.getWebsite();
		this.uuid = client.getUuid();
		this.dateChanged = client.getDateChanged();
		this.dateCreated = client.getDateCreated();
		this.voided = client.getVoided();
		this.dateVoided = client.getDateVoided();
		this.voidReason = client.getVoidReason();
		try{this.creator = client.getCreator().getSystemId();} catch (NullPointerException e){this.creator = null;}
		try{this.changedBy = client.getChangedBy().getSystemId();} catch (NullPointerException e){this.changedBy = null;}
		try{this.voidedBy = client.getVoidedBy().getSystemId();} catch (NullPointerException e){this.voidedBy = null;}
		this.accessTokenValiditySeconds = client.getAccessTokenValiditySeconds();
		this.refreshTokenValiditySeconds = client.getRefreshTokenValiditySeconds();
		this.scopes = client.getScope();
		this.grantType = client.getAuthorizedGrantTypes();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Date getDateChanged() {
		return dateChanged;
	}

	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}

	public Boolean getVoided() {
		return voided;
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public Date getDateVoided() {
		return dateVoided;
	}

	public void setDateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}

	public String getVoidReason() {
		return voidReason;
	}

	public void setVoidReason(String voidReason) {
		this.voidReason = voidReason;
	}

	public String getVoidedBy() {
		return voidedBy;
	}

	public void setVoidedBy(String voidedBy) {
		this.voidedBy = voidedBy;
	}

	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public Set<String> getGrantType() {
		return grantType;
	}
}
