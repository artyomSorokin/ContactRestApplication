package sorokin.util.util;

import sorokin.dao.contactsdao.ContactDaoImpl;
import sorokin.dao.contactsdao.ContactsDao;
import sorokin.util.model.Contact;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FillContactsTableUtil {

    public void fillTable() throws SQLException, IOException, PropertyVetoException {
       final ContactsDao CONTACTS_DAO = new ContactDaoImpl();
        List<Contact> contacts = createListContacts();
        if(CONTACTS_DAO.createTableContacts()){
            createListContacts();
            CONTACTS_DAO.insertIntoContacts(contacts);
        }
    }

    private List<Contact>  createListContacts(){
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(4158,"Vasya"));
        contacts.add(new Contact(4159,"Alena"));
        contacts.add(new Contact(4160,"Aleksandr"));
        contacts.add(new Contact(4161,"Sergey"));
        contacts.add(new Contact(4162,"Misha"));
        return contacts;
    }

}
