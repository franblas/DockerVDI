package docker;

import java.io.File;

import database.db;

import utils.bashUtils;
import utils.filesUtils;

/**
 * 
 * @author Paco
 *
 */
public class dock {
	
	//Useful paths
	private String pathscripts = "/etc/.dockerimg/scripts/";
	private String pathappli = "/etc/.dockerimg/appli/";
	private String pathlogo = "/etc/.dockerimg/logo/";
	//private String userhost = System.getProperty("user.name");
	private String pathdesktop = System.getProperty("user.home")+"/Desktop/";

	/**
	 * Check if docker is installed
	 * @param pass
	 * @return
	 */
	public boolean checkDocker(String pass){
		boolean res = false;
		if(new bashUtils().getOutputCommandbash("dpkg -l | grep docker",pass)!=null){
			res = true;
		}
		return res;
	}
	
	/**
	 * Pull all docker images from repository for a given user
	 * @param user
	 * @param serveur
	 * @param port
	 * @param pass
	 * @param conn
	 */
	public void downloadAllImages(String user, String serveur, String port, String pass,java.sql.Connection conn){
		String[] top = null;
		String[] pot = new db().grpuserBDDList(user,serveur,conn);
		for(int k=0;k<pot.length;k++){
			for(int j=0;j<new db().appliuserBDDcount(user,serveur,pot[k],conn);j++){
				top = new db().appliuserBDDList(user,serveur,pot[k],conn);
				new bashUtils().runCommandbash("xterm -iconic -e sudo docker pull "+serveur+":"+port+"/"+""+top[j],pass);
			}
		}
	}
	
	/**
	 * Unset the session for a given user
	 * @param serveur
	 * @param port
	 * @param pass
	 * @param pathdesktop
	 */
	public void removeAllImages(String serveur, String port, String pass, String pathdesktop){
		String[] plop = new filesUtils().readFile(pathscripts+"applideco");
		new bashUtils().runCommandbash("xterm -iconic -e 'sudo docker stop $(docker ps -a -q);sudo docker rm $(docker ps -a -q)'",pass);
		for(int k=0;k<plop.length;k++){
			new bashUtils().runCommandbash("rm "+pathdesktop+""+plop[k]+".desktop",pass);
			new bashUtils().runCommandbash("xterm -iconic -e sudo docker rmi "+serveur+":"+port+"/"+""+plop[k],pass);
		}
	}

	/**
	 * Write the application file (Desktop entry)
	 * @param appli
	 */
	public void execFile(String appli){
		new filesUtils().writeFile(pathappli+""+appli+".desktop","[Desktop Entry]\n"+
				"Type=Application\n"+
				"Name="+appli+"\n"+
				"Exec=sh /etc/.dockerimg/scripts/run"+appli+".sh\n"+
				"Icon=/etc/.dockerimg/logo/"+appli+".png",false);
	}
	
	/**
	 * Write the application file (Run file)
	 * @param appli
	 * @param displayy
	 * @param soundd
	 * @param graphh
	 */
	public void runAppliFile(String serveur, String port, String appli, boolean displayy, boolean soundd,boolean graphh){
		String display="";
		String sound="";
		String graph="";
		String exec=serveur+":"+port+"/"+appli+" /usr/bin/"+appli+" \n";
		String homeshared = " -v="+System.getProperty("user.home")+":/root:rw ";
		if(displayy==true){
			display=" -e DISPLAY=unix$DISPLAY -v=/tmp/.X11-unix:/tmp/.X11-unix:rw ";
		}
		if(soundd==true){
			sound=" -v=/dev/snd:/dev/snd:rw -e lxc='lxc.cgroup.devices.allow = c 116:*rwm' ";
		}
		if(graphh==true){
			graph=" -v=/dev/dri:/dev/dri:rw -e lxc='lxc.cgroup.devices.allow = c 226:*rwm' ";
		}
		if(appli.equals("supertux2")){
			exec=serveur+":"+port+"/"+appli+" /usr/games/"+appli+" \n";
		}
		new filesUtils().writeFile(pathscripts+"run"+appli+".sh","#!/bin/bash \n"+
				"xhost + \n"+
				"xterm -iconic -e docker run -i -t -rm --networking=true -privileged " +
				display +
				sound +
				graph +
				homeshared +
				""+exec,false);
	}
	
	/**
	 * Set the session for the user
	 * @param userr
	 * @param serveur
	 * @param pass
	 * @param pathdesktopp
	 * @param conn
	 */
	public void setSession(String userr, String serveur, String pass, String pathdesktopp,java.sql.Connection conn) {
		setAllAppli(userr,serveur,"5000",pass,pathdesktopp,conn);
		downloadAllImages(userr,serveur,"5000",pass,conn);
	}
	
	/**
	 * Unset the session for the user
	 * @param serveur
	 * @param port
	 * @param pass
	 * @param pathdesktop
	 */
	public void unsetSession(String serveur, String port, String pass, String pathdesktop) {
		removeAllImages(serveur,port,pass,pathdesktop);
		new bashUtils().runCommandbash("xhost -",pass);
	}
	
	/**
	 * Set files for the session regarding the database
	 * @param user
	 * @param serveur
	 * @param port
	 * @param pass
	 * @param pathdesktop
	 * @param conn
	 */
	public void setAllAppli(String user,String serveur, String port, String pass, String pathdesktop,java.sql.Connection conn){
		String[] top = null;
		String[] bddlistgrpuser = new db().grpuserBDDList(user,serveur,conn);
		boolean[] boo, boo2, boo3 = null;
		new filesUtils().writeFile(pathscripts+"applideco","",false);
		for(int k=0;k<bddlistgrpuser.length;k++){
			for(int j=0;j<new db().appliuserBDDcount(user,serveur,bddlistgrpuser[k],conn);j++){			
			boo = new db().DisplayappliuserBDDList(user,serveur,bddlistgrpuser[k],conn);
			boo2 = new db().SoundappliuserBDDList(user,serveur,bddlistgrpuser[k],conn);
			boo3 = new db().GraphappliuserBDDList(user,serveur,bddlistgrpuser[k],conn);
			top = new db().appliuserBDDList(user,serveur,bddlistgrpuser[k],conn);
			new filesUtils().writeFile(pathscripts+"applideco",top[j]+"\n",true);
			File f = new File(pathlogo+""+top[j]+".png");
			if(!f.exists()){
				new bashUtils().runCommandbash("wget -q -P "+pathlogo+" http://"+serveur+"/images/"+top[j]+".png",pass);
			}
			new bashUtils().runCommandbash("touch "+pathappli+""+top[j]+".desktop",pass);
			new bashUtils().runCommandbash("touch "+pathscripts+"run"+top[j]+".sh",pass);
			new bashUtils().runCommandbash("chmod 777 "+pathscripts+"run"+top[j]+".sh",pass);
			new bashUtils().runCommandbash("chmod 777 "+pathappli+""+top[j]+".desktop",pass);
			execFile(top[j]);
			runAppliFile(serveur,port,top[j],boo[j],boo2[j],boo3[j]);
			new bashUtils().runCommandbash("cp "+pathappli+""+top[j]+".desktop "+pathdesktop+"",pass);
			new bashUtils().runCommandbash("chmod +x "+pathdesktop+""+top[j]+".desktop",pass);
			}
		}
	}
	
	/**
	 * Save all files into a container created and modified during a session
	 * @param userr
	 * @param pass
	 * @param serveur
	 * @param port
	 * @param busyboxname
	 * @param conn
	 * @param start_session
	 */
	public void saveSession(String userr, String pass,String serveur,String port,String busyboxname,java.sql.Connection conn,String start_session){
		int startsession = Integer.parseInt(start_session);
		String stop_session = timesession(pass);
		int stopsession = Integer.parseInt(stop_session);
		int minn = stopsession - startsession;
		String min = new bashUtils().getOutputCommandbash("echo $(date +\"%M\" --date=@"+minn+")",pass);
		String script = "#!/bin/bash \n" +
				"rm /etc/.dockerimg/copyfiles/* \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -mmin -"+min+" -not -path '*/\\.*' >> /etc/.dockerimg/scripts/files.lst \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -mmin -"+min+" -not -path '*/\\.*' -exec mv -f {} /etc/.dockerimg/copyfiles/ \\; \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -cmin -"+min+" -not -path '*/\\.*' >> /etc/.dockerimg/scripts/files.lst \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -cmin -"+min+" -not -path '*/\\.*' -exec mv -f {} /etc/.dockerimg/copyfiles/ \\; \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -cmin "+min+" -not -path '*/\\.*' >> /etc/.dockerimg/scripts/files.lst \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -cmin "+min+" -not -path '*/\\.*' -exec mv -f {} /etc/.dockerimg/copyfiles/ \\; \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -mmin "+min+" -not -path '*/\\.*' >> /etc/.dockerimg/scripts/files.lst \n" +
				"wait $! \n" +
				"find "+System.getProperty("user.home")+" -type f -mmin "+min+" -not -path '*/\\.*' -exec mv -f {} /etc/.dockerimg/copyfiles/ \\; \n" +
				"wait $! \n" +
				"sudo docker run -i -t --networking=true -v=/etc/.dockerimg/copyfiles:/root:rw -v=/etc/.dockerimg/scripts:/listeFolder:rw "+serveur+":"+port+"/"+busyboxname+""+userr+" ./copy.sh \n" +
				"wait $! \n" +
				"sudo docker rmi "+serveur+":"+port+"/"+busyboxname+""+userr+" \n" +
				"wait $! \n" +
				"sudo docker commit $(docker ps -a | grep ./copy.sh | awk '{print $1}') "+serveur+":"+port+"/"+busyboxname+""+userr+" \n" +
				"wait $! \n" +
				"sudo docker push "+serveur+":"+port+"/"+busyboxname+""+userr+" \n" +
				"wait $! \n" +
				"sudo docker rm $(docker ps -a | grep ./copy.sh | awk '{print $1}') \n" +
				"wait $! \n" +
				"rm /etc/.dockerimg/copyfiles/* \n" +
				"wait $! \n" +
				"rm -R "+pathdesktop+"copyFolder/ \n" +
				"wait $! \n" +
				"docker rmi "+serveur+":"+port+"/"+busyboxname+""+userr+" \n" +
				"wait $! \n";
		new filesUtils().writeFile("/etc/.dockerimg/scripts/files.lst","",false);
		new filesUtils().writeFile("/etc/.dockerimg/scripts/savesession.sh",script,false);
		new bashUtils().runCommandbash("xterm -iconic -e sh /etc/.dockerimg/scripts/savesession.sh",pass);
	}
	
	/**
	 * Get timestamp system
	 * @param pass
	 */
	public String timesession(String pass){
		return new bashUtils().getOutputCommandbash("echo $(date +\"%s\")",pass);
	}
	
	/**
	 * Download all applications selected
	 * @param appli
	 * @param dockerserveur
	 * @param dockerrootpass
	 */
	public void downloadImagesSel(String appli[], String dockerserveur, String dockerrootpass){
		for(int k=0;k<appli.length;k++){
			if(appli[k]!=null){
				new bashUtils().runCommandbash("xterm -iconic -e sudo docker pull "+dockerserveur+":"+"5000"+"/"+""+appli[k],dockerrootpass);
			}
		}
	}
	
	/**
	 * Download the container to save files and create the folder on the desktop
	 * @param nameimg
	 * @param userr
	 * @param dockerserveur
	 * @param dockerrootpass
	 */
	public void copyFolderSave(String nameimg, String userr, String dockerserveur, String dockerrootpass){
		String commande = "#!/bin/bash \n" +
						  "docker run -v="+pathdesktop+":/copyFolder:rw -name DATA "+dockerserveur+":5000/"+nameimg+""+userr+" true \n"+
						  "wait $! \n" +
						  "sudo docker cp DATA:/copyFolder "+pathdesktop+" \n" +
						  "wait $! \n" +
						  "docker rm DATA \n" +
						  "wait $! \n";
		new filesUtils().writeFile(pathscripts+"copyfolder.sh",commande,false);
		new bashUtils().runCommandbash("xterm -iconic -e sh "+pathscripts+"copyfolder.sh",dockerrootpass);	
	}	
	
	/**
	 * 
	 * @return
	 */
	public String getPathscripts() {
		return pathscripts;
	}

	/**
	 * 
	 * @param pathscripts
	 */
	public void setPathscripts(String pathscripts) {
		this.pathscripts = pathscripts;
	}

	/**
	 * 
	 * @return
	 */
	public String getPathappli() {
		return pathappli;
	}

	/**
	 * 
	 * @param pathappli
	 */
	public void setPathappli(String pathappli) {
		this.pathappli = pathappli;
	}

	/**
	 * 
	 * @return
	 */
	public String getPathlogo() {
		return pathlogo;
	}

	/**
	 * 
	 * @param pathlogo
	 */
	public void setPathlogo(String pathlogo) {
		this.pathlogo = pathlogo;
	}

	/**
	 * 
	 * @return
	 */
	public String getPathdesktop() {
		return pathdesktop;
	}

	/**
	 * 
	 * @param pathdesktop
	 */
	public void setPathdesktop(String pathdesktop) {
		this.pathdesktop = pathdesktop;
	}
	
}//end of class
