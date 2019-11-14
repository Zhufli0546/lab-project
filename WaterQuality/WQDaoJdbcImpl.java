package WaterQuality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class WQDaoJdbcImpl implements WQDao {
	
	private WQJdbcDataSource jdbc;
	private Connection conn;

	public  WQDaoJdbcImpl() throws IOException {
		jdbc = new WQJdbcDataSource();
	}
	
	@Override
	public void add() throws SQLException {
		String sql = "Insert WaterQualityInfo(Qua_id,Code_name,Longitude,Latitude,Qua_cntu,Qua_cl,Qua_ph,Update_date)Values(?,?,?,?,?,?,?,?)";
		PreparedStatement state = conn.prepareStatement(sql);
		state.setString(1, "CS01");
		state.setString(2, "麗山加壓站");
		state.setString(3, "121.574786");
		state.setString(4, "25.085611");
		state.setString(5, "0.06");
		state.setString(6, "0.38");
		state.setString(7, "7.1");
		state.setString(8, "2019-07-23");
		state.execute();
		state.close();
		
		System.out.println("successfully added !");
	}
	
	@Override
	public void delete(String id) throws SQLException{
		String sql ="delete  from WaterQualityInfo where Qua_id = ?";
		PreparedStatement state = conn.prepareStatement(sql);
		state.setString(1,id); 
		state.execute();
		state.close();
		
		System.out.println("successfully deleted !");
	}
	
	@Override
	public void update(String str,String str1,String id) throws SQLException{
		String sql ="update WaterQualityInfo set"+" "+str+"="+str1+" "+"where  Qua_id =?";
		PreparedStatement state = conn.prepareStatement(sql);
		state.setString(1, id);
		state.execute();
		state.close();
		
		System.out.println("successfully updated !");
	}
	@Override
	public void query(String id) throws SQLException {
		String sql = "select * from WaterQualityInfo where Qua_id =?";
		PreparedStatement state = conn.prepareStatement(sql);
		state.setString(1,id);
		ResultSet rs = state.executeQuery();
		if(rs.next()) {
		System.out.println(rs.getInt("id"));
		System.out.println(rs.getString("Qua_id"));
		System.out.println(rs.getString("Code_name"));
		System.out.println(rs.getString("Longitude"));
		System.out.println(rs.getString("Latitude"));
		System.out.println(rs.getString("Qua_cntu"));
		System.out.println(rs.getString("Qua_cl"));
		System.out.println(rs.getString("Qua_ph"));
		System.out.println(rs.getString("Update_date"));
		System.out.println("-------------------------");	
		state.close();
	}
}

	@Override
	
	public void queryDb() throws SQLException {
		String sql = "select * from WaterQualityInfo";
		PreparedStatement state = conn.prepareStatement(sql);
		ResultSet rs = state.executeQuery();
		
		while(rs.next()) {
			System.out.println(rs.getInt("id"));
			System.out.println(rs.getString("Qua_id"));
			System.out.println(rs.getString("Code_name"));
			System.out.println(rs.getString("Longitude"));
			System.out.println(rs.getString("Latitude"));
			System.out.println(rs.getString("Qua_cntu"));
			System.out.println(rs.getString("Qua_cl"));
			System.out.println(rs.getString("Qua_ph"));
			System.out.println(rs.getString("Update_date"));
			System.out.println("-------------------------");	
		}
	}
	
	@Override
	public void ReadXMLDeposit() throws SQLException, ParserConfigurationException, SAXException, IOException {
		
		String sql = "Insert WaterQualityInfo(Qua_id,Code_name,Longitude,Latitude,Qua_cntu,Qua_cl,Qua_ph,Update_date)Values(?,?,?,?,?,?,?,?)";
		PreparedStatement state = conn.prepareStatement(sql);
		
		File f = new File("c:/XML/WQ.XML");   
	     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
	     DocumentBuilder builder = factory.newDocumentBuilder();   
	     Document doc = builder.parse(f);   
	     NodeList nl = doc.getElementsByTagName("qua_data");   
	     
	     for (int i = 0; i < nl.getLength(); i++) { 
		state.setString(1,doc.getElementsByTagName("qua_id").item(i).getFirstChild().getNodeValue());
		state.setString(2,doc.getElementsByTagName("code_name").item(i).getFirstChild().getNodeValue());
		state.setString(3,doc.getElementsByTagName("longitude").item(i).getFirstChild().getNodeValue());
		state.setString(4,doc.getElementsByTagName("latitude").item(i).getFirstChild().getNodeValue());
		state.setString(5,doc.getElementsByTagName("qua_cntu").item(i).getFirstChild().getNodeValue());
		state.setString(6,doc.getElementsByTagName("qua_cl").item(i).getFirstChild().getNodeValue());
		state.setString(7,doc.getElementsByTagName("qua_ph").item(i).getFirstChild().getNodeValue());
		state.setString(8,doc.getElementsByTagName("update_date").item(i).getFirstChild().getNodeValue());
		state.execute();
	   }
	     state.close();
	     System.out.println("Successful access !");
	}
	
	public void deleteDbAll() throws SQLException {
		String sql = "delete from WaterQualityInfo";
		PreparedStatement state = conn.prepareStatement(sql);
		state.execute();
		state.close();
		
		System.out.println("successfully deleted all data!");
	}
	
	@Override
	public void writeJson() throws SQLException, IOException {
		
		String targetPath = "c:/XML/WQ.json";
		List<WQ> wqs = new ArrayList<WQ>();
		
		String sql = "select * from WaterQualityInfo";
		PreparedStatement state = conn.prepareStatement(sql);
		ResultSet rs = state.executeQuery();
		
		while(rs.next()) {
			WQ wq = new WQ();
			wq.setQua_id(rs.getString("Qua_id"));
			wq.setCode_name(rs.getString("Code_name"));
			wq.setLongitude(rs.getString("Longitude"));
			wq.setLatitude(rs.getString("Latitude"));
			wq.setQua_cntu(rs.getString("Qua_cntu"));
			wq.setQua_cl(rs.getString("Qua_cl"));
			wq.setQua_ph(rs.getFloat("Qua_ph"));
			wq.setUpdate_date(rs.getString("Update_date"));
			wqs.add(wq);
		}
		rs.close();
		state.close();
		
		Gson gson = new Gson();
		FileWriter fw = new FileWriter(targetPath);
		gson.toJson(wqs,fw);
		System.out.println("build success !");
	}
	
	@Override
	public void createConn() throws SQLException {
		conn = jdbc.getConnection();	
	}
	
	@Override
	public void closeConn() throws SQLException {
		jdbc.closeConn();	
	}
}
