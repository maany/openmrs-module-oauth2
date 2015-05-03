package org.openmrs.module.oauth2;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

import javax.persistence.*;

/**
 * Model (MVC) for Oauth Client.
 */
@Entity
@Table(name="oauth2_client")
public class Client extends BaseOpenmrsData{
    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private Integer clientId;

    @ManyToOne
    @JoinColumn(name = "user")
    private User clientDeveloper;

    @Column(name ="client_name")
    private String name;

    private String desciption;

    @JoinColumn(name = "redirection_uri")
    private String redirectionURI;

    @JoinColumn(name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @JoinColumn(name="client_identifier")
    private String clientIdentifier;

    private String website;

    @JoinColumn(name = "legal_acceptance")
    private boolean legalAcceptance;

    @Override
    public Integer getId() {
        return clientId;
    }

    @Override
    public void setId(Integer id) {
        this.clientId = id;
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @Column(name = "uuid", length = 38, unique = true)
    @Override
    public String getUuid() {
        return super.getUuid();
    }

    public User getClientDeveloper() {
        return clientDeveloper;
    }

    public void setClientDeveloper(User clientDeveloper) {
        this.clientDeveloper = clientDeveloper;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getRedirectionURI() {
        return redirectionURI;
    }

    public void setRedirectionURI(String redirectionURI) {
        this.redirectionURI = redirectionURI;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isLegalAcceptance() {
        return legalAcceptance;
    }

    public void setLegalAcceptance(boolean legalAcceptance) {
        this.legalAcceptance = legalAcceptance;
    }

    private enum ClientType{
        WEB_APPLICATION, USER_AGENT_BASED_APPLICATION,NATIVE_APPLICATION;
    }
}
