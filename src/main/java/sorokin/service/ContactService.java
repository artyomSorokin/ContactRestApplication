package sorokin.service;


import sorokin.entity.ContactEntity;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ContactService {

    List<ContactEntity> queryContactEntity() throws PropertyVetoException, SQLException, IOException;
}
