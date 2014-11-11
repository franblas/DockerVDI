package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author Paco
 *
 */
public class bashUtils {

	/**
	 * Run a bash command in sudo mode
	 * @param comm
	 * @param passwd
	 */
	public void runCommandbash(String comm, String passwd){
		String com[] = {"/bin/bash","-c","echo '"+passwd+"' | sudo -S "+comm};
		try {
			Runtime.getRuntime().exec(com);
		} 
		catch (IOException e) {
			e.printStackTrace();System.out.println(e);
		}
	}
	
	/**
	 * Return the output of a bash command in sudo mode
	 * @param comm
	 * @param passwd
	 * @return
	 */
	public String getOutputCommandbash(String comm, String passwd){
		String com[] = {"/bin/bash","-c","echo '"+passwd+"' | sudo -S "+comm};
		String output="";
		try {
			InputStream in = Runtime.getRuntime().exec(com).getInputStream();
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inr);
			String line =null;
			while((line = br.readLine())!=null){
				output+=line;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();System.out.println(e);
		}
		return output;
	}
	
	/**
	 * Check the root password of the host
	 * @param pass
	 * @return
	 */
	public boolean checkRootPass(String pass){
		boolean res = false;
		if(getOutputCommandbash("ls",pass)!=null){
			res = true;
		}
		return res;
	}

}//end of class
