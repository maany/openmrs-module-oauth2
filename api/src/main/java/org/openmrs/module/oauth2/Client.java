package org.openmrs.module.oauth2;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

import javax.persistence.*;

/**
 * Model (MVC) for Oauth Client.
 */
@Entity(name = "client")
@Table(name = "oauth2_client")
public class Client extends BaseOpenmrsData {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User clientDeveloper;

    @Column(name = "client_name")
    @NotEmpty
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "redirection_uri")
    @NotEmpty
    @URL
    private String redirectionURI;

    @Column(name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(name = "client_identifier")
    private String clientIdentifier;
    @Column(name = "client_secret")
    private String clientSecret;
    @URL
    private String website;

    @Column(name = "legal_acceptance")
    private boolean legalAcceptance;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
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

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public enum ClientType {
        WEB_APPLICATION, USER_AGENT_BASED_APPLICATION, NATIVE_APPLICATION;
    }
}
