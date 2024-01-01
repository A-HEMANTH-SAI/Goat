package genericutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;

public class DataBaseUtils {
	
	Connection con;
	
	public void getDBConnection() throws SQLException {
		Driver driver=new Driver();
		DriverManager.registerDriver(driver);
		con=DriverManager.getConnection(IConstants.DB_URL,IConstants.DB_USERNAME,IConstants.DB_PASSWORD);
	}
	
	public String validateDBData(String query,int columnIndex,String expData) throws SQLException {
		Statement s=con.createStatement();
		ResultSet result=s.executeQuery(query);
		boolean flag=false;
		while(result.next())
			if(expData.equalsIgnoreCase(result.getString(columnIndex))) {
				flag=true;
				break;
			}
		if(flag) return expData;
		else return "";
	}
	
	public void closeDBConnection() throws SQLException {
		con.close();
	}

}
