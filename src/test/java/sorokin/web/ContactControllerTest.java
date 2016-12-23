package sorokin.web;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sorokin.entity.ContactEntity;
import sorokin.service.ContactService;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;


public class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactServiceMock;

    @InjectMocks
    private ContactController contactController;

    private List<ContactEntity> contactListTest;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        contactListTest = new ArrayList<>();
        ContactEntity contactEntity1 = new ContactEntity();
        contactEntity1.setId(4158);
        contactEntity1.setName("Vasya");
        contactListTest.add(contactEntity1);
        ContactEntity contactEntity2 = new ContactEntity();
        contactEntity2.setId(4159);
        contactEntity2.setName("Alena");
        contactListTest.add(contactEntity2);
        ContactEntity contactEntity3 = new ContactEntity();
        contactEntity3.setId(4160);
        contactEntity3.setName("Aleksandr");
        contactListTest.add(contactEntity3);
        ContactEntity contactEntity4 = new ContactEntity();
        contactEntity4.setId(4161);
        contactEntity4.setName("Sergey");
        contactListTest.add(contactEntity4);
        ContactEntity contactEntity5 = new ContactEntity();
        contactEntity5.setId(4162);
        contactEntity5.setName("Misha");
        contactListTest.add(contactEntity5);
    }

    @Test
    public void getContactsNormalRequestWork(){
        try {
            when(contactServiceMock.queryContactEntity()).thenReturn(contactListTest);
            mockMvc.perform(get("/hello/contacts?nameFilter=^A.*$"))
                    .andExpect(status().isOk());

            assertEquals(contactListTest.size(), 3);
            assertEquals(contactListTest.get(1).getName(), "Sergey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getContactsNormaAnotherRequestWork(){
        try {
            when(contactServiceMock.queryContactEntity()).thenReturn(contactListTest);
            mockMvc.perform(get("/hello/contacts?nameFilter=^.*[aei].*$"))
                    .andExpect(status().is4xxClientError());
            assertEquals(contactListTest.size(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getContactsNormalEmptyRequestWork(){
        try {
            when(contactServiceMock.queryContactEntity()).thenReturn(contactListTest);
            mockMvc.perform(get("/hello/contacts?nameFilter="))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getContactsNullParameterRequestWork(){
        try {
            when(contactServiceMock.queryContactEntity()).thenReturn(contactListTest);
            mockMvc.perform(get("/hello/contacts"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getContactsEmptyContactsListWork(){
        try {
            when(contactServiceMock.queryContactEntity()).thenReturn(Collections.EMPTY_LIST);
            mockMvc.perform(get("/hello/contacts?nameFilter=^.*[aei].*$"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getContactsNullContactsListRequestWork(){
        try {
            when(contactServiceMock.queryContactEntity()).thenThrow(new RuntimeException(""));
            mockMvc.perform(get("/hello/contacts?nameFilter=^.*[aei].*$"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
