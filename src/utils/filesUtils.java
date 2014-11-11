package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Paco
 *
 */
public class filesUtils {

	/**
	 * Write in a given file
	 * @param path
	 * @param texte
	 * @param overwrite (if false, overwrite the file)
	 */
	public void writeFile(String path, String texte, boolean overwrite)
	{
		try{
			FileWriter fw = new FileWriter(path,overwrite);
			BufferedWriter output = new BufferedWriter(fw);	
			output.write(texte);
			output.flush();	
			output.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Count number of lines in a file
	 * @param path
	 * @return
	 */
	public int readLines(String path){
		String aline = "";int nbr = 0;
		try{
			FileReader ff = new FileReader(path);
			BufferedReader bufread = new BufferedReader(ff);
			while((aline = bufread.readLine())!=null){
				nbr++;
			}
			bufread.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return nbr;
	}
	
	/**
	 * Read a file and return the data
	 * @param path
	 * @return
	 */
	public String[] readFile(String path){
		String[] data = new String[readLines(path)];
		try{
			FileReader ff = new FileReader(path);
			BufferedReader bufread = new BufferedReader(ff);
			for(int i=0;i<readLines(path);i++){
				data[i] = bufread.readLine();
			}
			bufread.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}

}//end of class
