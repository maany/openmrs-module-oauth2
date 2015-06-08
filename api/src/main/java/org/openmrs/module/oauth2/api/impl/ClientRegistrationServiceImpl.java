package org.openmrs.module.oauth2.api.impl;

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

    @Override
    public void saveOrUpdateClient(Client client) {
        try {
            dao.saveOrUpdate(client);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client client) {
        dao.update(client);
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

    @Transactional
    @Override
    public void registerNewClient(Client client) {

    }


}
