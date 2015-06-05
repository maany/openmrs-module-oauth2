package org.openmrs.module.oauth2.api.impl;

import org.hibernate.SessionFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.db.hibernate.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by OPSKMC on 5/5/15.
 */

public class ClientRegistrationServiceImpl extends BaseOpenmrsService implements ClientRegistrationService {
    @Autowired
    protected ClientDAO dao;

    public void setDao(ClientDAO dao) {
        this.dao = dao;
    }

    @Override
    public void saveOrUpdateClient(Client client) {
        try {
            dao.saveOrUpdate(client);
        } catch (Exception ex) {
            System.out.println("***** ERROR *****");
            ex.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client client) {

    }

    @Override
    public Client getClient(Integer id) {
        return dao.getById(id);
    }

    @Override
    public List<Client> getAllClientsForClientDeveloper() {
        return null;
    }

    @Override
    public Client unregisterClient(Client client) {
        return null;
    }

    @Override
    public void registerNewClient(Client client) {

    }

    @Override
    public SessionFactory getSessionFactory() {
        return dao.getSessionFactory();
    }
}
