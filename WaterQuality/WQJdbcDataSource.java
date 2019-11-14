package WaterQuality;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class WQJdbcDataSource {
	private Properties props;
	private String mySQLUrl,myUser,myPwd;
	private int maxConn;
	private List<Connection> connPools;
	private Connection conn;

	public WQJdbcDataSource() throws IOException {
        this("mysqlserverjdbc.properties");
	}
	
	public WQJdbcDataSource(String config) throws IOException{
        props = new Properties();
        props.load(new FileInputStream(config));
        
        mySQLUrl = props.getProperty("mySQLUrl");
        myUser = props.getProperty("myUser");
        myPwd = props.getProperty("myPwd");
        maxConn = Integer.parseInt(props.getProperty("maxconn"));
        connPools = Collections.synchronizedList(new ArrayList<Connection>());
	}
	public synchronized Connection getConnection() throws SQLException {
		if(connPools.isEmpty()) {
			conn = DriverManager.getConnection(mySQLUrl, myUser, myPwd);
			return conn;
		}else {
			return connPools.remove(connPools.size()-1);
		}

	}
//	public synchronized Connection getConnection(String username, String password) throws SQLException {
//		if(connPools.isEmpty()) {
//			conn = DriverManager.getConnection(mySQLUrl, username, password);
//			return conn;
//		}else {
//			return connPools.remove(connPools.size()-1);
//		}
//	}
//	
	public void closeConn() throws SQLException {
		if(connPools.size()==maxConn) {
			conn.close();
		}else {
			connPools.add(conn);
		}
	}
}
