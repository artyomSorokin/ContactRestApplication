package sorokin.dao.contactsdao;


import sorokin.dao.connection.ConnectionFactory;
import sorokin.dao.connection.ConnectionFactoryC3P0;
import sorokin.util.model.Contact;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.List;


public class ContactDaoImpl implements ContactsDao{

    private ConnectionFactory connectionFactory;
    private final String CREATE_TABLE = "CREATE table CONTACTS(id BIGINT NOT NULL UNIQUE," +
            "name varchar)";
    private final String INSERT_INTO_CONTACTS = "INSERT INTO CONTACTS(id, name) values(?,?)";

    public ContactDaoImpl() throws IOException, PropertyVetoException {
        this.connectionFactory = new ConnectionFactoryC3P0();
    }


    @Override
    public boolean createTableContacts() throws SQLException {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {

            if(connection == null){
               throw new SQLException("connection is null");
            }
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getTables(null, null, "CONTACTS", null);
            if (res.next()) {
            } else {
                statement.execute(CREATE_TABLE);
                connection.commit();
                return true;
            }
        }catch (SQLException e){
            throw e;
        }
        return false;
    }

    @Override
    public void insertIntoContacts(List<Contact> contacts) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CONTACTS)) {
            for(Contact contact : contacts) {
                preparedStatement.setLong(1, contact.getId());
                preparedStatement.setString(2, contact.getName());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        }
        catch (SQLException e){
            throw e;
        }
    }


    private Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        }
        catch (SQLException e){
            throw e;
        }
        return connection;
    }
}
