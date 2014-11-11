package utils;

import java.net.InetAddress;

/**
 * 
 * @author Paco
 *
 */
public class networkUtils {
	
	/**
	 * Check the availability of the server
	 * @param serveur
	 * @return
	 */
	public boolean reachServer(String serveur) {
		boolean co = false;
		try {
			co =  InetAddress.getByName(serveur).isReachable(2000);
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return co;
	}

}//end of class
