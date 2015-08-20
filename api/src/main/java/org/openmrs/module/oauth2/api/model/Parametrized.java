package org.openmrs.module.oauth2.api.model;

import org.openmrs.module.oauth2.Client;

/**
 * Created by OPSKMC on 8/19/15.
 */
public interface Parametrized {
    public String getParameter();

    public void setParameter(String parameter);

    public void setClient(Client client);
}
