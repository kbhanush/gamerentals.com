package database;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class SSHSession {
	
	
	
	//SSH tunnel properties
	static private String dbHostIP 			= "129.144.19.119";
	static private int dbPort				= 1521;
	static private String sshUser 			= "oracle";
	static private int sshPort				= 22;
	static private String privatekeyPath	= "/res/db_ssh_key";
	
	//JSch stuff
	static JSch _jsch =null;
	static Session _sshSession = null;
	
	public static void startSSHSession() throws JSchException{
		//Establish session when session is not active
		if ( _jsch == null || _sshSession == null){
			JSch.setConfig("StrictHostKeyChecking", "no");  
			_jsch =  new JSch();
			
			_jsch.addIdentity(	System.getProperty("user.dir")+privatekeyPath);
			System.out.println("Private key loaded from:"+ privatekeyPath);
			
			_sshSession = _jsch.getSession(sshUser, dbHostIP, sshPort);
			System.out.println("Starting SSH Session..");
			
			_sshSession.connect();
			System.out.println("SSH Session connected");
			
			try{
				System.out.println("Forwarding connections on port "+ dbPort + " to remote SSH host");
				//sshSession.delPortForwardingL("localhost", dbPort);
				_sshSession.setPortForwardingL(dbPort, "localhost", dbPort);
				System.out.println("Ports successfully forwarded");
			}catch(JSchException pfFailEx){
				System.out.println("Port forwards already bound.. continuing..");
			}
		
		}else{
			System.out.println("Session already started, using it!");
		}
	}
		
}

