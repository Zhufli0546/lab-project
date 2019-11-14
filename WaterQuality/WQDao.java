package WaterQuality;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface WQDao {
	   //public void insert();
	   public void add() throws SQLException;
	   public void delete(String id) throws SQLException;
	   public void update(String str,String str1,String id) throws SQLException;
	   public void query(String id) throws SQLException;
	   public void queryDb() throws SQLException;
	   public void deleteDbAll() throws SQLException;
	   public void ReadXMLDeposit() throws SQLException, ParserConfigurationException, SAXException, IOException;
	   public void writeJson() throws SQLException, IOException;
	   public void createConn() throws SQLException;
	   public void closeConn() throws SQLException;

}
