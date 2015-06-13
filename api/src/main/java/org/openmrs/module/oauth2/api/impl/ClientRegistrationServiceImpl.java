package org.openmrs.module.oauth2.api.impl;

import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.db.hibernate.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void saveOrUpdateClient(Client client) {
        dao.saveOrUpdate(client);
    }

    @Transactional
    @Override
    public void updateClient(Client client) {
        dao.update(client);
    }


    @Override
    @Transactional(readOnly = true)
    public Client getClient(Integer id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClientsForClientDeveloper(User clientDeveloper) {
        return dao.getAllClientsForClientDeveloper(clientDeveloper);
    }

    @Override
    @Transactional
    public Client unregisterClient(Client client) {
        dao.delete(client);
        return client;
    }

    @Transactional
    @Override
    public void registerNewClient(Client client) {
        saveOrUpdateClient(client);
    }
}
