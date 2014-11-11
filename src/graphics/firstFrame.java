package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import database.auth;
import database.db;
import docker.dock;

import utils.bashUtils;
import utils.networkUtils;

/**
 * 
 * @author Paco
 *
 */
public class firstFrame extends JFrame implements ActionListener {
	
	//Panels
	private JPanel pan = new JPanel();
	private JPanel pan1 = new JPanel();
	
	//Boutons
	private JButton boutonConnexion = new JButton();
	private JButton boutonLaunchappli = new JButton();
	
	//Text and password fields
	private JTextField textServeur = new JTextField("10.193.244.180");
	private JPasswordField textRootpass = new JPasswordField();
	private JTextField textUserservice = new JTextField();
	private JPasswordField textPasswordservice = new JPasswordField();

	//Back fields
	private String backServeur = null;
	private String backRootpass = null;
	private String backUserservice = null;
	private String backPasswordservice = null;
	private String backStartsession = null;
	
	//Labels
	private JLabel labelServeur = new JLabel("Server");
	private JLabel labelRootpass = new JLabel("Root pass host");
	private JLabel labelUserservice = new JLabel("User");
	private JLabel labelPasswordservice = new JLabel("Password");
	
	/**
	 * Main constructor of the class
	 * @param nom
	 * @param largeur
	 * @param hauteur
	 */
	public firstFrame(String nom, int largeur, int hauteur){
		//Set host OS style
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		this.setTitle(nom);
		this.setSize(largeur,hauteur);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/docker.png")).getImage());
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0){
				//unsetSession(text_serveur.getText(),"5000",text_rootpass.getText(),pathdesktop);
				System.exit(0);
			}
		});
		
		boutonConnexion.setText("Connexion");
		boutonLaunchappli.setText("Launch Session");
		
		boutonConnexion.addActionListener(this);
		boutonLaunchappli.addActionListener(this);
		
		GridLayout grid2 = new GridLayout(0,2);
		GridLayout grid1 = new GridLayout(0,1);
		
		pan.setLayout(grid1);
		pan1.setLayout(grid2);
		
		pan1.add(labelServeur);
		pan1.add(textServeur);
		pan1.add(labelUserservice);
		pan1.add(textUserservice);
		pan1.add(labelPasswordservice);
		pan1.add(textPasswordservice);
		pan1.add(labelRootpass);
		pan1.add(textRootpass);
		pan1.add(boutonConnexion);
		
		pan.add(pan1);
		
		this.getContentPane().add(new JScrollPane(pan),BorderLayout.CENTER);
		
		this.setVisible(true);
	}//end constructor
	
	/**
	 * Action for buttons 
	 */
	public void actionPerformed(ActionEvent arg0) {
		//connexion action
		dock docker = new dock();
		if(arg0.getSource()==boutonConnexion){
			if(textRootpass.getText()!=null && textServeur.getText()!=null && textUserservice.getText()!=null && textPasswordservice.getText()!=null){
				try{
					if(new bashUtils().checkRootPass(textRootpass.getText())){
						if(docker.checkDocker(textRootpass.getText())){
							//db connexion 
							java.sql.Connection conn=new db().coBDD("root","test","password",textServeur.getText());
							//set session
							if(new networkUtils().reachServer(textServeur.getText())){
									if(new auth().authentification(textUserservice.getText(),textPasswordservice.getText(),conn)){									
										docker.setAllAppli(textUserservice.getText(),textServeur.getText(),"5000",textRootpass.getText(),docker.getPathdesktop(),conn);
										setBackStartsession(docker.timesession(textRootpass.getText()));
										setBackServeur(textServeur.getText());
										setBackRootpass(textRootpass.getText());
										setBackUserservice(textUserservice.getText());
										setBackPasswordservice(textPasswordservice.getText());
										this.setVisible(false);
										JOptionPane.showMessageDialog(this,"Success to launch the session :) ","Success", JOptionPane.INFORMATION_MESSAGE);
										new secondFrame("Client Docker 1.0",400,250,getBackServeur(),getBackRootpass(),getBackUserservice(),docker.timesession(textRootpass.getText()));
									}
									else{
										JOptionPane.showMessageDialog(this,"Authentification failed :( ","Error", JOptionPane.ERROR_MESSAGE);
									}//end verif authentification
								}
								else{
									JOptionPane.showMessageDialog(this,"Impossible to reach the server :( ","Error", JOptionPane.ERROR_MESSAGE);
								}//end verif reachserver
						}
						else{
							JOptionPane.showMessageDialog(this,"Ohoh seems Docker is not installed :( ","Error", JOptionPane.ERROR_MESSAGE);
						}//end verif rootpass
					}
					else{
						JOptionPane.showMessageDialog(this,"Wrong root password :( ","Error", JOptionPane.ERROR_MESSAGE);
					}//end verif rootpass
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this,"Fail to launch the session :( ","Error", JOptionPane.ERROR_MESSAGE);
				}	
			}//end verif textfield
		}//end if bouton
	}

	/**
	 * 
	 * @return
	 */
	public String getBackServeur() {
		return backServeur;
	}

	/**
	 * 
	 * @param backServeur
	 */
	public void setBackServeur(String backServeur) {
		this.backServeur = backServeur;
	}

	/**
	 * 
	 * @return
	 */
	public String getBackRootpass() {
		return backRootpass;
	}

	/**
	 * 
	 * @param backRootpass
	 */
	public void setBackRootpass(String backRootpass) {
		this.backRootpass = backRootpass;
	}

	/**
	 * 
	 * @return
	 */
	public String getBackUserservice() {
		return backUserservice;
	}

	/**
	 * 
	 * @param backUserservice
	 */
	public void setBackUserservice(String backUserservice) {
		this.backUserservice = backUserservice;
	}

	/**
	 * 
	 * @return
	 */
	public String getBackPasswordservice() {
		return backPasswordservice;
	}

	/**
	 * 
	 * @param backPasswordservice
	 */
	public void setBackPasswordservice(String backPasswordservice) {
		this.backPasswordservice = backPasswordservice;
	}

	/**
	 * 
	 * @return
	 */
	public String getBackStartsession() {
		return backStartsession;
	}

	/**
	 * 
	 * @param backStartsession
	 */
	public void setBackStartsession(String backStartsession) {
		this.backStartsession = backStartsession;
	}

}//end of class
