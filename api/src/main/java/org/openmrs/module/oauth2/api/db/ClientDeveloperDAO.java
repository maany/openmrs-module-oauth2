package org.openmrs.module.oauth2.api.db;

import org.openmrs.module.oauth2.ClientDeveloper;

/**
 * Database links for ClientDeveloperService
 */
public interface ClientDeveloperDAO {
    public void saveClientDeveloper(ClientDeveloper clientDeveloper);
    public ClientDeveloper getClientDeveloper(Integer id);
}
