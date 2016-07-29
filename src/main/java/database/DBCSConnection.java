
package database;

import java.sql.*;


import oracle.jdbc.pool.*;

import javax.servlet.ServletContext;

/*
 *
 * @author Arindam Bose
 */
public class DBCSConnection {
//Database Connection properties
	
	//Database Connection properties
	
	private String dbUser 			= "webapp";
	private String dbPass			= "webapp";
	

	//DB Connection objects
	private Connection _conn;
	private OracleDataSource _ords;
	private ServletContext _context;
	
	public DBCSConnection(ServletContext context) throws SQLException{
	
		_context = context;
		
		//Create Data Source
		_ords = new OracleDataSource();
		_ords.setURL("jdbc:oracle:thin:@//10.196.133.26:1521/PDB1.dbpm25.oraclecloud.internal");
		_ords.setUser(dbUser);
		_ords.setPassword(dbPass);
		
		_conn = DriverManager.getConnection("jdbc:oracle:thin:@//10.196.133.26:1521/PDB1.dbpm25.oraclecloud.internal","webapp","webapp");
		
		
	}
	
	public Connection getConnection() throws SQLException{
		//Lazy Initialization
		if( _conn == null){
			_conn = _ords.getConnection();
		}
		
		
		if( _conn.isClosed()){
			_conn = _ords.getConnection();
		}
		
		System.out.println("Database connection completed successfully");
		return _conn;
	}
	
	public boolean isActive() throws SQLException{
		return !_conn.isClosed();
	}


}
