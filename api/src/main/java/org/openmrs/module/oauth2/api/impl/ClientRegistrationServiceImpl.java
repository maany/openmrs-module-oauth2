package org.openmrs.module.oauth2.api.impl;

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
    private ClientDAO clientDAO;

    @Override
    public void saveOrUpdateClient(Client client) {
        try {
            clientDAO.saveOrUpdate(client);
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
        return null;
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

}
