package sorokin.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sorokin.entity.ContactEntity;
import sorokin.util.util.FillContactsTableUtil;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ContactServiceImpl implements ContactService{

    private final FillContactsTableUtil fillContactsTableUtil = new FillContactsTableUtil();

    public List<ContactEntity> queryContactEntity() throws PropertyVetoException, SQLException, IOException {
        fillContactsTableUtil.fillTable();
        List<ContactEntity> contactEntityList = new ArrayList<>();
        SessionFactory sessions = new Configuration().configure().buildSessionFactory();
        Session session = sessions.openSession();
        session.beginTransaction();
        List<ContactEntity> contacts = (List<ContactEntity>)session.createQuery("from ContactEntity").list();
        for(ContactEntity contactEntity : contacts){
            contactEntityList.add(contactEntity);
        }
        session.close();
        sessions.close();
        return contactEntityList;
    }



}
