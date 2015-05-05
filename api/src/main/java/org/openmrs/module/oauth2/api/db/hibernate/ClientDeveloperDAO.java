package org.openmrs.module.oauth2.api.db.hibernate;

import org.openmrs.module.oauth2.ClientDeveloper;

/**
 * Database links for ClientDeveloperService
 */

public class ClientDeveloperDAO extends HibernateOauth2DAO<ClientDeveloper> {

    /**
     * You must call this before using any of the data access methods, since it's not actually
     * possible to write them all with compile-time class information due to use of Generics.
     *
     * @param mappedClass
     */
    protected ClientDeveloperDAO(Class<ClientDeveloper> mappedClass) {
        super(mappedClass);
    }

}
