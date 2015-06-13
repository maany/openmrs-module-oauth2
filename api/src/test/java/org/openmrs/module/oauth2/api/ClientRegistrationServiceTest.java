package org.openmrs.module.oauth2.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;

/**
 * Created by OPSKMC on 5/5/15.
 */
public class ClientRegistrationServiceTest extends BaseModuleContextSensitiveTest {
    protected static final String CLIENT_INITIAL_DATA_XML = "ClientRegistrationServiceTest-initialData.xml";//"org/openmrs/api/include/LocationServiceTest-initialData.xml";
    protected static final String INITIAL_IN_MEMORY_TESTDATASET_XML = "org/openmrs/include/initialInMemoryTestDataSet.xml";
    private static final String SAMPLE_CLIENT_REDIRECTION_URI = "www.demoapp.com";
    public ClientRegistrationService getService() {
        return Context.getService(ClientRegistrationService.class);
    }

    @Before
    public void runBeforeEachTest() throws Exception {
        executeDataSet(CLIENT_INITIAL_DATA_XML);
        executeDataSet(INITIAL_IN_MEMORY_TESTDATASET_XML);
    }

    @Test
    public void saveOrUpdate_shouldSaveNewClientsUpdateExistingClients() {
        Client client = createSampleClient();
        getService().saveOrUpdateClient(client);
        client = getService().getClient(1);
        Assert.assertNotNull(client);
    }

    @Test
    public void getClient_shouldGetClientById() {
        Client client = getService().getClient(1);
        Assert.assertNotNull(client);
    }

    /**
     * test data has been set in the xml to return nto return Client with clientId = 1
     */
    //TODO add more test data in ClientRegistrationServiceTest-initialData.xml
    @Test
    public void getAllClientsForClientDeveloper_shouldListAllClientsRegisteredByClientDeveloper() {
        User clientDeveloper = Context.getUserService().getUser(4);
        List<Client> clients = getService().getAllClientsForClientDeveloper(clientDeveloper);
        Assert.assertNotNull(clients);
    }
    @Test
    /**
     * @see
     * @verifies
     */
    //TODO add more comparisons besides TEST_NAME. Update operation should not modify id and clientDeveloper? Ask Harsha
    public void updateClient_shouldUpdateExistingClient(){
        String TEST_NAME = "demo name";
        Client client = getService().getClient(1);
        client.setName(TEST_NAME);
        getService().updateClient(client);
        client = getService().getClient(1);
        Assert.assertEquals(TEST_NAME, client.getName());
    }

    /**
     *
     */
    @Test
    public void unregisterClient_shouldUnregisterClient(){
        Client client = getService().getClient(1);
        getService().unregisterClient(client);
        client = getService().getClient(1);
        Assert.assertNull(client);
    }

    /**
     *
     */
    @Test
    public void registerNewClient_shouldCreateANewClientForCurrentLoggedInUser(){

        Client client = createSampleClient();
        getService().registerNewClient(client);
        List<Client> clients = getService().getAllClientsForClientDeveloper(Context.getAuthenticatedUser());
        client = clients.get(clients.size()-1);
        Assert.assertEquals(SAMPLE_CLIENT_REDIRECTION_URI,client.getRedirectionURI());

    }
    private Client createSampleClient(){
        Client client = new Client();
        client.setName("Demo Application");
        client.setClientType(Client.ClientType.WEB_APPLICATION);
        client.setLegalAcceptance(true);
        client.setRedirectionURI(SAMPLE_CLIENT_REDIRECTION_URI);
        return client;
    }

}
