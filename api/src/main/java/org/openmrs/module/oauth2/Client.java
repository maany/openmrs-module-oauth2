package org.openmrs.module.oauth2;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

import javax.persistence.*;

/**
 * Created by OPSKMC on 5/1/15.
 */
@Entity
@Table(name="oauth2_client")
public class Client extends BaseOpenmrsData{
    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private Integer clientId;

    @Basic
    private String name;

    @ManyToOne
    @JoinColumn(name = "user")
    private User clientDeveloper;

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
}
