package org.openmrs.module.oauth2.api.model;

import org.openmrs.module.oauth2.Client;

import javax.persistence.*;

/**
 * Created by OPSKMC on 8/19/15.
 */
@Entity
@Table(name = "oauth2_client_redirect_uri")
public class RedirectURI implements Parametrized {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Basic
    @Column(name = "redirect_uri")
    private String redirectURI;

    public RedirectURI() {
    }

    public RedirectURI(String redirectURI) {

        this.redirectURI = redirectURI;
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

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    @Override
    public String getParameter() {
        return redirectURI;
    }

    @Override
    public void setParameter(String parameter) {
        this.redirectURI = parameter;
    }

    @Override
    public String toString() {
        return  redirectURI;
    }
}
