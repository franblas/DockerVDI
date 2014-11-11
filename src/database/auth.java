package database;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * 
 * @author Paco
 *
 */
public class auth {

	/**
	 * Authentification of the user 
	 * @param user
	 * @param password
	 * @param conn
	 * @return
	 */
	public boolean authentification(String user, String password, java.sql.Connection conn){
		boolean res = false;
		if(authentificationUser(user,conn)){
			if(authentificationPassword(user,password,conn)){
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * Verify user in the database
	 * @param user
	 * @param conn
	 * @return
	 */
	public boolean authentificationUser(String user, java.sql.Connection conn){
		boolean res = false;
		int count = 0;
		try{
			String demande = "SELECT user FROM users WHERE user='"+user+"'";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					count++;
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		
		if(count >= 1){res=true;}
		if(count < 1){res=false;}
		
		return res;
	}
	
	/**
	 * Verify password in the database for a given user
	 * @param user
	 * @param password
	 * @param conn
	 * @return
	 */
	public boolean authentificationPassword(String user, String password, java.sql.Connection conn){
		boolean res = false;
		try{
			String demande = "SELECT password FROM users WHERE user='"+user+"'";
			java.sql.PreparedStatement prepare = conn.prepareStatement(demande);
			java.sql.ResultSet result = prepare.executeQuery();
			ResultSetMetaData resultMeta = (ResultSetMetaData) result.getMetaData();
			while(result.next()){
				for(int i = 1; i <= resultMeta.getColumnCount(); i++){
					if(result.getString(i).equals(password)){
						res = true;
					}
				}
			}
			result.close();
			prepare.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return res;
	}

}//end of class
