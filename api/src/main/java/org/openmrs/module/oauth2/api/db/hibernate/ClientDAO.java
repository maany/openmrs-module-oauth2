package org.openmrs.module.oauth2.api.db.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.openmrs.User;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.db.Oauth2DAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by OPSKMC on 5/5/15.
 */
@Component
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

    public List<Client> getAllClientsForClientDeveloper(User clientDeveloper) {
        String queryString = "FROM org.openmrs.module.oauth2.Client where creator.userId = :client_developer_id";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(queryString);
        query.setParameter("client_developer_id", clientDeveloper.getId());
        List<Client> clients = (List<Client>) query.list();
        return clients;
    }

    public Client loadClientByClientId(String clientId) {
        List<Client> clients = sessionFactory.getCurrentSession().createQuery("from client where clientIdentifier = :clientId")
                .setParameter("clientId", clientId)
                .list();
        if (clients.isEmpty())
            return null;
        return clients.get(0);
    }
}
