package org.openmrs.module.oauth2;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by OPSKMC on 5/1/15.
 */
@Entity
@Table(name="oauth2client")
public class ClientApplication {
    private int clientId;
    private int applicationId;
}
