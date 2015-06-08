package org.openmrs.module.oauth2.api.db.hibernate;

import org.hibernate.SessionFactory;
import org.openmrs.module.oauth2.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Database links for ClientDeveloperService
 */
@Repository
public class ClientDeveloperDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public void saveOrUpdate(Client client) {
        sessionFactory.getCurrentSession().saveOrUpdate(client);
    }

}
