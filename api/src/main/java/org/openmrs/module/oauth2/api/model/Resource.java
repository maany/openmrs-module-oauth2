package org.openmrs.module.oauth2.api.model;

import org.openmrs.module.oauth2.Client;

import javax.persistence.*;

/**
 * Created by OPSKMC on 8/19/15.
 */
@Entity
@Table(name = "oauth2_client_resource_ids")
public class Resource implements Parametrized {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Basic
    @Column(name = "resource_id")
    private String resource;

    public Resource() {
    }

    public Resource(String resource) {
        this.resource = resource;
    }

    public Resource(Client client, String resource) {
        this.client = client;
        this.resource = resource;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String getParameter() {
        return resource;
    }

    @Override
    public void setParameter(String parameter) {
        this.resource = parameter;
    }
}
