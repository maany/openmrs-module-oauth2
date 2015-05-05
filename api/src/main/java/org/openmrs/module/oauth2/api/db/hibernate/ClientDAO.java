package org.openmrs.module.oauth2.api.db.hibernate;

import org.openmrs.module.oauth2.Client;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by OPSKMC on 5/5/15.
 */
@Repository
@Qualifier("clientDAO")
public class ClientDAO extends HibernateOauth2DAO<Client> {
    /**
     * You must call this before using any of the data access methods, since it's not actually
     * possible to write them all with compile-time class information due to use of Generics.
     *
     * @param mappedClass
     */
    protected ClientDAO(Class<Client> mappedClass) {
        super(mappedClass);
    }

    private ClientDAO() {
        super(Client.class);
    }
}
