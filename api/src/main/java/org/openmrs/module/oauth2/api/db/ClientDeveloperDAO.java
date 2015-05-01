package org.openmrs.module.oauth2.api.db;

/**
 * Database links for ClientDeveloperService
 */
public interface ClientDeveloperDAO {
    public void saveClientDeveloper(ClientDeveloper clientDeveloper);
    public ClientDeveloper getClientDeveloper(Integer id);
}
