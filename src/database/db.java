package database;

import java.sql.DriverManager;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * 
 * @author Paco
 *
 */
public class db {
	
	/**
	 * Set the connection to the database
	 * @param user
	 * @param bddname
	 * @param passwd
	 * @param serveur
	 * @return
	 */
	public java.sql.Connection coBDD(String user, String bddname, String passwd, String serveur){
		java.sql.Connection conn=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://"+serveur+":3306/"+bddname;
			conn = DriverManager.getConnection(url, user, passwd);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Catch the display argument of the database for an application
	 * @param name
	 * @param serveur
	 * @param grp
	 * @param conn
	 * @return
	 */
	public boolean[] DisplayappliuserBDDList(String name, String serveur,String grp,java.sql.Connection conn){
		boolean[] user_name = new boolean[appliuserBDDcount(name,serveur,grp,conn)];
		int p=0;
		try {
			String demande = "SELECT display FROM application WHERE "+grp+"=1";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					user_name[p]=result.getBoolean(i);
					p++;
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user_name;
	}
	
	/**
	 * Catch the sound argument of the database for an application
	 * @param name
	 * @param serveur
	 * @param grp
	 * @param conn
	 * @return
	 */
	public boolean[] SoundappliuserBDDList(String name, String serveur,String grp,java.sql.Connection conn){
		boolean[] user_name = new boolean[appliuserBDDcount(name,serveur,grp,conn)];
		int p=0;
		try {
			String demande = "SELECT sound FROM application WHERE "+grp+"=1";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					user_name[p]=result.getBoolean(i);
					p++;
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user_name;
	}
	
	/**
	 * Catch the graphical argument of the database for an application
	 * @param name
	 * @param serveur
	 * @param grp
	 * @param conn
	 * @return
	 */
	public boolean[] GraphappliuserBDDList(String name, String serveur,String grp,java.sql.Connection conn){
		boolean[] user_name = new boolean[appliuserBDDcount(name,serveur,grp,conn)];
		int p=0;
		try {
			String demande = "SELECT graph FROM application WHERE "+grp+"=1";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					user_name[p]=result.getBoolean(i);
					p++;
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user_name;
	}
	
	/**
	 * Catch applications of the database for a given user
	 * @param name
	 * @param serveur
	 * @param grp
	 * @param conn
	 * @return
	 */
	public String[] appliuserBDDList(String name, String serveur,String grp,java.sql.Connection conn){
		String[] user_name = new String[appliuserBDDcount(name,serveur,grp,conn)];
		int p=0;
		try {
			String demande = "SELECT appliname FROM application WHERE "+grp+"=1";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					user_name[p]=result.getString(i);
					p++;
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user_name;
	}

	/**
	 * Count applications of the database for a given user
	 * @param name
	 * @param serveur
	 * @param grp
	 * @param conn
	 * @return
	 */
	public int appliuserBDDcount(String name, String serveur, String grp,java.sql.Connection conn){
		int numberuser = 0;
		try {
			String demande = "SELECT appliname FROM application WHERE "+grp+"=1";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					numberuser++;
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return numberuser;   
	}
		
	/**
	 * Count groups of the database for a given user
	 * @param username
	 * @param serveur
	 * @param conn
	 * @return
	 */
	public int grpuserBDDcount(String username, String serveur,java.sql.Connection conn){
		int numberuser = 0;
		try {
			String demande = "SELECT user FROM usergroup WHERE user='"+username+"'";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					numberuser++;
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return numberuser;   
	}
		
	/**
	 * Catch groups of the database for a given user
	 * @param username
	 * @param serveur
	 * @param conn
	 * @return
	 */
	public String[] grpuserBDDList(String username, String serveur,java.sql.Connection conn){
		String[] user_name = new String[grpuserBDDcount(username,serveur,conn)];
		int p=0;
		try {
			String demande = "SELECT * FROM usergroup WHERE user='"+username+"'";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			while(result.next()){
				user_name[p]=result.getString("group");
				p++;
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user_name;
	}

}//end of class
