package org.openmrs.module.oauth2.api.model;

import org.openmrs.module.oauth2.Client;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by OPSKMC on 8/19/15.
 */
@Entity
@Table(name = "oauth2_client_authorities")
public class CustomGrantedAuthority implements GrantedAuthority, Parametrized {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id",  nullable = false, updatable = false, insertable = true)
    private Client client;
    @Basic
    @Column(name = "authority")
    private String authority;

    public CustomGrantedAuthority() {
    }

    public CustomGrantedAuthority(String authority) {
        this.authority = authority;
    }

    public CustomGrantedAuthority(Client client, String authority) {
        this.client = client;
        this.authority = authority;
    }

    @Override

    public String getAuthority() {
        return authority;
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

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getParameter() {
        return authority;
    }


    @Override
    public void setParameter(String parameter) {
        this.authority = parameter;

    }

    @Override
    public String toString() {
        return authority;
    }
}
