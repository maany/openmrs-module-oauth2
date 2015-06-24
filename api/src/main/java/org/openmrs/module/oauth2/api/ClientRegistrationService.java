package org.openmrs.module.oauth2.api;

import org.openmrs.User;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.oauth2.Client;

import java.util.List;

/**
 *
 */

public interface ClientRegistrationService extends OpenmrsService {

    /**
     * update details of the client if exists, else create new client
     *
     * @param client
     * @should
     */
    public Client saveOrUpdateClient(Client client);

    /**
     * @param client
     */
    public Client updateClient(Client client);

    /**
     * @param id
     * @return
     */
    public Client getClient(Integer id);

    /**
     * @param clientDeveloper
     * @return
     */
    public List<Client> getAllClientsForClientDeveloper(User clientDeveloper);

    /**
     * @param client
     * @return
     * @should unregister a client and clear footprint from database
     */
    public Client unregisterClient(Client client);

    /**
     * @param client
     * @should register a new client into the database for currently logged in User {@link org.openmrs.api.context.Context#getAuthenticatedUser()}
     */
    //TODO Feature Discussion : is this required at all? or should saveOrUpdate suffice? Ask Harsha
    public Client registerNewClient(Client client);

    /**
     * This method returns the list of the supported Oauth2 Client Types.
     * Default values are : {WEB_APPLICATION, USER_AGENT_BASED_APPLICATION, NATIVE_APPLICATION}
     *
     * @return
     */
    public Client.ClientType[] getAllClientTypes();

    /**
     * @param client
     * @return
     */
    public Client merge(Client client);


}
