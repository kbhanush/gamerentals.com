package database;

import java.sql.SQLException;



import javax.servlet.ServletContext;

public class DBCSConnectionManager {

	private static DBCSConnection _dbcs = null;
	
	
	public static DBCSConnection getConnection(ServletContext context) throws SQLException{

		if( _dbcs == null || !_dbcs.isActive()){
			_dbcs = new DBCSConnection(context);
		}

		return _dbcs;
	}

	public static DBCSConnection getConnection() throws SQLException{

		ServletContext context = CurrentServletContext.getInstance().getContext();
		if( _dbcs == null || !_dbcs.isActive()){
			_dbcs = new DBCSConnection(context);
		}

		return _dbcs;
	}
}
