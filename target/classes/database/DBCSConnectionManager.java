package database;

import java.sql.SQLException;

import com.jcraft.jsch.JSchException;

public class DBCSConnectionManager {

	private static DBCSConnection _dbcs = null;
	
	
	public static DBCSConnection getConnection() throws SQLException, JSchException{
		
		if( _dbcs == null || !_dbcs.isActive()){
			_dbcs = new DBCSConnection();
		}
		
		return _dbcs;
	}
}
