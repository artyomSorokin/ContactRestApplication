package sorokin.dao.connection;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import sorokin.util.model.DbConnection;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactoryC3P0 implements ConnectionFactory {

    private ComboPooledDataSource dataSource;
    private static final String DB_DRIVER = "org.h2.Driver";
    private DbConnection dbConnection;


    protected DbConnection readConfig() throws IOException {
        Properties properties = new Properties();
        try(InputStream fis = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(fis);
            dbConnection = new DbConnection();
            dbConnection.setNameDb(properties.getProperty("name_database"));
            dbConnection.setLogin(properties.getProperty("login_database"));
            dbConnection.setPassword(properties.getProperty("password_database"));
        }
        catch (IOException e) {
            throw e;
        }
        return dbConnection;
    }

    public ConnectionFactoryC3P0() throws IOException, PropertyVetoException {
        createConnectionFactory();
    }


    public Connection newConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
        }
        catch (SQLException e){
            throw e;
        }
        return connection;
    }

    public void createConnectionFactory() throws IOException, PropertyVetoException {
        try{
            this.dataSource = new ComboPooledDataSource();
            DbConnection dbConnectionEntry = readConfig();
            dataSource.setDriverClass(DB_DRIVER);
            dataSource.setJdbcUrl(dbConnectionEntry.getNameDb());
            dataSource.setUser(dbConnectionEntry.getLogin());
            dataSource.setPassword(dbConnectionEntry.getPassword());

            dataSource.setMinPoolSize(1);
            dataSource.setAcquireIncrement(1);
            dataSource.setMaxPoolSize(20);

            dataSource.setMaxStatements(180);
            dataSource.setMaxStatementsPerConnection(10);
        } catch (IOException | PropertyVetoException e) {
            throw e;
        }
    }

    public void close() throws SQLException {
        //NOP
    }
}
