package sorokin.dao.contactsdao;


import sorokin.util.model.Contact;

import java.sql.SQLException;
import java.util.List;

public interface ContactsDao {

    boolean createTableContacts() throws SQLException;
    void insertIntoContacts(List<Contact> contacts) throws SQLException;
}
