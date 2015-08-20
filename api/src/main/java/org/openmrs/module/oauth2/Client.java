package org.openmrs.module.oauth2;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;
import org.openmrs.module.oauth2.api.model.*;
import org.openmrs.module.oauth2.api.util.ClientSpringOAuthUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Model (MVC) for Oauth Client.
 */
@Entity(name = "client")
@Table(name = "oauth2_client")
public class Client extends BaseOpenmrsData implements ClientDetails {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "client_name")
    @NotEmpty
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(name = "client_identifier")
    private String clientIdentifier;
    @Column(name = "client_secret")
    private String clientSecret;
    @URL
    private String website;

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
    // =============================
    // ClientDetails specific fields
    // =============================

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Resource> resources;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Scope> scopes;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<CustomGrantedAuthority> authorities;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<RedirectURI> registeredRedirectUris;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<AuthorizedGrantType> authorizedGrantTypes;

    /**
     * All additional properties have been defined before-hand. This variable will be deprecated soon
     * It is transient because Hibernate cannot persist ElementCollection of type Object
     * It is being used as a placeholder only, to be returned by the method {@link org.springframework.security.oauth2.provider.ClientDetails#getAdditionalInformation()}
     */
    //@ElementCollection
    @Transient
    private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds;
    @Column(name = "access_token_validity")
    private Integer accessTokenValiditySeconds;

    //=============
    // Constructors
    //=============
    protected Client() {
        setDateCreated(new Date());
        setVoided(false);
    }

    /**
     * @param clientId
     * @param clientSecret
     * @param resourceIds
     * @param scopes
     * @param grantTypes   by default, authorization_code and refresh_token are supported
     * @param authorities
     * @param redirectUris
     */
    public Client(String clientId, String clientSecret, String resourceIds, String scopes, String grantTypes, String authorities,
                  String redirectUris) {
        this();
        this.clientIdentifier = clientId;
        this.clientSecret = clientSecret;

        if (StringUtils.hasText(resourceIds)) {
            this.resources = ClientSpringOAuthUtils.commaDelimitedStringToCollection(resourceIds, this, Resource.class);
        }

        if (StringUtils.hasText(scopes)) {
            this.scopes = ClientSpringOAuthUtils.commaDelimitedStringToCollection(scopes, this, Scope.class);
        }

        if (StringUtils.hasText(grantTypes)) {
            this.authorizedGrantTypes = ClientSpringOAuthUtils.commaDelimitedStringToCollection(grantTypes, this, AuthorizedGrantType.class);
        } else {
            AuthorizedGrantType authorizationCode = new AuthorizedGrantType("authorization_code");
            AuthorizedGrantType refreshToken = new AuthorizedGrantType("refresh_token");
            this.authorizedGrantTypes.add(authorizationCode);
            this.authorizedGrantTypes.add(refreshToken);
        }

        if (StringUtils.hasText(authorities)) {
            this.authorities = ClientSpringOAuthUtils.commaDelimitedStringToCollection(authorities, this, CustomGrantedAuthority.class);
        }

        if (StringUtils.hasText(redirectUris)) {
            this.registeredRedirectUris = ClientSpringOAuthUtils.commaDelimitedStringToCollection(redirectUris, this, RedirectURI.class);
        }
    }

    public enum ClientType {
        WEB_APPLICATION, USER_AGENT_BASED_APPLICATION, NATIVE_APPLICATION;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    // =============================================================================
    // fields parsers to return fields formatted to be used by Spring Security OAuth
    // Overridden methods from ClientDetails interface of spring oauth api
    // =============================================================================

    @Override
    public String getClientId() {
        return clientIdentifier;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public Set<String> getResourceIds() {
        return ClientSpringOAuthUtils.parseResources(resources);
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return this.scopes != null && !this.scopes.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return ClientSpringOAuthUtils.parseScope(scopes);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return ClientSpringOAuthUtils.parseAuthorizedGrantTypes(authorizedGrantTypes);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return ClientSpringOAuthUtils.parseRedirectURIs(registeredRedirectUris);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return ClientSpringOAuthUtils.parseAuthorities(authorities);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    // =============================
    // ClientDetails Getters and Setters
    // =============================

    public Collection<Resource> getResources() {
        return resources;
    }

    public void setResources(Collection<Resource> resources) {
        this.resources = resources;
    }

    public Collection<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(Collection<Scope> scopes) {
        this.scopes = scopes;
    }

    public void setAuthorities(Collection<CustomGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Collection<RedirectURI> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Collection<RedirectURI> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public void setAuthorizedGrantTypes(Collection<AuthorizedGrantType> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    // ================================
    // BaseOpenmrsData getter overrides
    // =================================

    @Access(AccessType.PROPERTY)
    @ManyToOne
    @JoinColumn(name = "creator")
    @Override
    public User getCreator() {
        return super.getCreator();
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "date_created", nullable = false)
    @Override
    public Date getDateCreated() {
        return super.getDateCreated();
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "changed_by")
    @Override
    public User getChangedBy() {
        return super.getChangedBy();
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "date_changed")
    @Override
    public Date getDateChanged() {
        return super.getDateChanged();
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "voided", nullable = false)
    @Override
    public Boolean isVoided() {
        return super.isVoided();
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "date_voided")
    @Override
    public Date getDateVoided() {
        return super.getDateVoided();
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "voided_by")
    @Override
    public User getVoidedBy() {
        return super.getVoidedBy();
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "void_reason", length = 255)
    @Override
    public String getVoidReason() {
        return super.getVoidReason();
    }

    //=======================
    // Object class overrides
    //=======================
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accessTokenValiditySeconds == null) ? 0 : accessTokenValiditySeconds);
        result = prime * result + ((refreshTokenValiditySeconds == null) ? 0 : refreshTokenValiditySeconds);
        result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime * result + ((authorizedGrantTypes == null) ? 0 : authorizedGrantTypes.hashCode());
        result = prime * result + ((clientIdentifier == null) ? 0 : clientIdentifier.hashCode());
        result = prime * result + ((clientSecret == null) ? 0 : clientSecret.hashCode());
        result = prime * result + ((registeredRedirectUris == null) ? 0 : registeredRedirectUris.hashCode());
        result = prime * result + ((resources == null) ? 0 : resources.hashCode());
        result = prime * result + ((scopes == null) ? 0 : scopes.hashCode());
        result = prime * result + additionalInformation.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (accessTokenValiditySeconds != other.accessTokenValiditySeconds)
            return false;
        if (refreshTokenValiditySeconds != other.refreshTokenValiditySeconds)
            return false;
        if (authorities == null) {
            if (other.authorities != null)
                return false;
        } else if (!authorities.equals(other.authorities))
            return false;
        if (authorizedGrantTypes == null) {
            if (other.authorizedGrantTypes != null)
                return false;
        } else if (!authorizedGrantTypes.equals(other.authorizedGrantTypes))
            return false;
        if (clientIdentifier == null) {
            if (other.clientIdentifier != null)
                return false;
        } else if (!clientIdentifier.equals(other.clientIdentifier))
            return false;
        if (clientSecret == null) {
            if (other.clientSecret != null)
                return false;
        } else if (!clientSecret.equals(other.clientSecret))
            return false;
        if (registeredRedirectUris == null) {
            if (other.registeredRedirectUris != null)
                return false;
        } else if (!registeredRedirectUris.equals(other.registeredRedirectUris))
            return false;
        if (resources == null) {
            if (other.resources != null)
                return false;
        } else if (!resources.equals(other.resources))
            return false;
        if (scopes == null) {
            if (other.scopes != null)
                return false;
        } else if (!scopes.equals(other.scopes))
            return false;
        if (additionalInformation == null) {
            if (other.additionalInformation != null)
                return false;
        } else if (!additionalInformation.equals(other.additionalInformation))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Client [clientId=" + clientIdentifier + ", clientSecret=" + clientSecret + ", scope=" + scopes
                + ", resourceIds=" + resources + ", authorizedGrantTypes=" + authorizedGrantTypes
                + ", registeredRedirectUris=" + registeredRedirectUris + ", authorities=" + authorities
                + ", accessTokenValiditySeconds=" + accessTokenValiditySeconds + ", refreshTokenValiditySeconds="
                + refreshTokenValiditySeconds + ", additionalInformation=" + additionalInformation + "]";
    }
}
