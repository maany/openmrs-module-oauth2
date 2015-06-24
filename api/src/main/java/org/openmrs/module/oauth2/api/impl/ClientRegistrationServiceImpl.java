package org.openmrs.module.oauth2.api.impl;

import org.openmrs.User;
import org.openmrs.api.context.Context;
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
    public Client saveOrUpdateClient(Client client) {
        return dao.saveOrUpdate(client);
    }

    @Transactional(readOnly = false)
    @Override
    public Client updateClient(Client client) {
        return dao.update(client);
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
    public Client registerNewClient(Client client) {
        client.setClientDeveloper(Context.getAuthenticatedUser());
        return saveOrUpdateClient(client);
    }

    @Override
    public Client.ClientType[] getAllClientTypes() {
        return Client.ClientType.values();
    }

    @Override
    public Client merge(Client client) {
        return dao.merge(client);
    }
}
