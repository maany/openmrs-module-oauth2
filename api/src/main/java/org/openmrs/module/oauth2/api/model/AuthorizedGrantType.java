package org.openmrs.module.oauth2.api.model;

import org.openmrs.module.oauth2.Client;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OPSKMC on 8/19/15.
 */
@Entity
@Table(name = "oauth2_client_grant_types")
public class AuthorizedGrantType implements Parametrized {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id",  nullable = false, updatable = false, insertable = true)
    private Client client;

    public AuthorizedGrantType() {
    }

    public AuthorizedGrantType(Client client, String grantType) {
        this.client = client;
        this.grantType = grantType;
    }

    @Basic
    @Column(name = "authorized_grant_type")
    private String grantType;

    public AuthorizedGrantType(String grantType) {
        this.grantType = grantType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAuthorizedGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @Override
    public String getParameter() {
        return grantType;
    }

    @Override
    public void setParameter(String parameter) {
        this.grantType = parameter;
    }

    @Override
    public String toString() {
        return grantType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.toString().equals(grantType))
            return true;
        else
            return false;
    }

    public static List<AuthorizedGrantType> getAllSupportedGrantTypes() {
        List<AuthorizedGrantType> grantTypes = new ArrayList<AuthorizedGrantType>();
        AuthorizedGrantType authorization_code = new AuthorizedGrantType("authorization_code");
        AuthorizedGrantType implicit = new AuthorizedGrantType("implicit");
        AuthorizedGrantType client_credentials = new AuthorizedGrantType("client_credentials");
        AuthorizedGrantType password = new AuthorizedGrantType("password");
        AuthorizedGrantType refresh_token = new AuthorizedGrantType("refresh_token");
        grantTypes.add(authorization_code);
        grantTypes.add(implicit);
        grantTypes.add(password);
        grantTypes.add(client_credentials);
        grantTypes.add(refresh_token);
        return grantTypes;
    }
}
