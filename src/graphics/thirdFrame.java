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
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import database.db;
import docker.dock;

public class thirdFrame extends JFrame implements ActionListener{
	
	//Panels
	private JPanel pan = new JPanel();
	private JPanel pan2 = new JPanel();
	
	//Labels
	JLabel labelDeconnexion = new JLabel("Deconnexion");

	//Boutons
	private JButton boutonDeconnexion = new JButton();
	
	//Useful variables
	private String dockerServeur = null;
	private String dockerRootpass = null;
	private String dockerUserservice = null;
	private String dockerStartsession = null;

	//Db
	private java.sql.Connection conn = null;
	
	/**
	 * Main constructor of the class
	 * @param nom
	 * @param largeur
	 * @param hauteur
	 * @param dock_serveur
	 * @param dock_rootpass
	 * @param dock_user
	 * @param dock_pathdesktop
	 * @param dock_start
	 */
	public thirdFrame(String nom, int largeur, int hauteur,String dockserveur,String dockrootpass,String dockuser,String dockpathdesktop,String dockstart){
		//Set host OS style
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
				
		dockerServeur = dockserveur;
		dockerRootpass = dockrootpass;
		dockerUserservice = dockuser;
		dockerStartsession = dockstart;
		conn = new db().coBDD("root", "test", "password", dockerServeur);
				
		this.setTitle(nom);
		this.setSize(largeur,hauteur);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/docker.png")).getImage());
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0){
				new dock().saveSession(dockerUserservice, dockerRootpass, dockerServeur, "5000", "copybusybox", conn, dockerStartsession);
				new dock().unsetSession(dockerServeur,"5000",dockerRootpass,new dock().getPathdesktop());
				System.exit(0);
			}
		});
				
				
		boutonDeconnexion.setText("Deconnexion");
				
		boutonDeconnexion.addActionListener(this);
				
		GridLayout grid1 = new GridLayout(0,1);
				
		pan.setLayout(grid1);
		pan2.setLayout(grid1);
				
		pan2.add(labelDeconnexion);
		pan2.add(boutonDeconnexion);

		pan.add(pan2);
				
		this.getContentPane().add(new JScrollPane(pan),BorderLayout.CENTER);
				
		this.setVisible(true);
				
		this.setExtendedState(JFrame.ICONIFIED);
	}
	
	/**
	 * Action for buttons 
	 */
	public void actionPerformed(ActionEvent arg0) {
		conn = new db().coBDD("root", "test", "password", dockerServeur);
		
		if(arg0.getSource()==boutonDeconnexion){
			new dock().saveSession(dockerUserservice, dockerRootpass, dockerServeur, "5000", "copybusybox", conn, dockerStartsession);		
			new dock().unsetSession(dockerServeur,"5000",dockerRootpass,new dock().getPathdesktop());
			JOptionPane.showMessageDialog(this,"You are deconnected !","Success", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			new firstFrame("Client Docker 1.0",400,250);
		}//end if bouton	
	}
	
}//end of class
