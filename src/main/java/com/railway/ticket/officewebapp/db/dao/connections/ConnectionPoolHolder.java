package com.railway.ticket.officewebapp.db.dao.connections;
import com.railway.ticket.officewebapp.db.Constants;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    private ConnectionPoolHolder() {}

    public static DataSource getDataSource(){
        if(dataSource == null){
            synchronized (ConnectionPoolHolder.class){
                BasicDataSource basicDataSource = new BasicDataSource();
                basicDataSource.setUrl(Constants.URL);
                basicDataSource.setUsername("root");
                basicDataSource.setPassword("Vova778ltym1");
                basicDataSource.setMinIdle(6);
                basicDataSource.setMaxIdle(32);
                basicDataSource.setMaxOpenPreparedStatements(100);
                dataSource = basicDataSource;
            }
        }
        return dataSource;
    }
}
