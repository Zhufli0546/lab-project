package WaterQuality;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class WQDaoAction {
	public static void main(String[] args) throws IOException, SQLException, 
	ParserConfigurationException,SAXException {
		WQDao FDao = WQDaoFactory.createWQDao();
		FDao.createConn();
		//FDao.add();
		//FDao.delete(1);
		//FDao.update("qua_cntu","0.10",1);
		//FDao.query("CS00");
		//FDao.queryDb();
		//FDao.ReadXMLDeposit();
		//FDao.deleteDbAll();
		FDao.writeJson();
		FDao.closeConn();
	}
}
