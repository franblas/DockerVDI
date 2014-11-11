package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import database.db;
import docker.dock;

/**
 * 
 * @author Paco
 *
 */
public class secondFrame  extends JFrame implements ActionListener{

	//Panels
	private JPanel pan = new JPanel();
	private JPanel pan2 = new JPanel();

	 //Checkbox
	private JCheckBox box[]=new JCheckBox[100];

	 //Boutons
	private JButton boutonLaunchappli = new JButton();
	
	 //Useful variables
	private String dockerServeur = null;
	private String dockerRootpass = null;
	private String dockerUserservice = null;
	private String dockerStartsession = null;

	//Db
	private java.sql.Connection conn = null;
	
	 //Applications variables
	private int count_appli = 0;
	private String[] tab_appli = new String[100];
	
	/**
	 * Main constructor of the class
	 * @param nom
	 * @param largeur
	 * @param hauteur
	 * @param dockserveur
	 * @param dockrootpass
	 * @param dockuser
	 * @param dockstart
	 */
	public secondFrame(String nom, int largeur, int hauteur,String dockserveur,String dockrootpass,String dockuser,String dockstart){
		//Set host OS style
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
				
		conn=new db().coBDD("root","test","password",dockserveur);
		dockerServeur = dockserveur;
		dockerRootpass = dockrootpass;
		dockerUserservice = dockuser;
		dockerStartsession = dockstart;
				
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
				new dock().unsetSession(dockerServeur, "5000", dockerRootpass, new dock().getPathdesktop());
				System.exit(0);
			}
		});

				
		boutonLaunchappli.setText("Launch Applications");
				
		boutonLaunchappli.addActionListener(this);
				
		GridLayout grid2 = new GridLayout(0,2);
		GridLayout grid1 = new GridLayout(0,1);
				
		pan.setLayout(grid1);
		pan2.setLayout(grid2);
				
		String[] bddlistgrpuserr = new db().grpuserBDDList(dockerUserservice,dockerServeur,conn);
		String[] tup = null;
		for(int ll=0;ll<bddlistgrpuserr.length;ll++){	
			for(int l=0;l<new db().appliuserBDDcount(dockerUserservice,dockerServeur,bddlistgrpuserr[ll],conn);l++){
				tup = new db().appliuserBDDList(dockerUserservice,dockerServeur,bddlistgrpuserr[ll],conn);
				box[l]=new JCheckBox(tup[l]);
				box[l].addActionListener(this);
				pan2.add(box[l]);
			}//end for appli		
		}//end for group	
				
		pan2.add(boutonLaunchappli);

		pan.add(pan2);
				
		this.getContentPane().add(new JScrollPane(pan),BorderLayout.CENTER);
				
		this.setVisible(true);
	}//end constructor
	
	/**
	 * Action for buttons 
	 */
	public void actionPerformed(ActionEvent arg0) {		
		String[] tip = null;
		String[] bddlistgrpuser = new db().grpuserBDDList(dockerUserservice,dockerServeur,conn);
		
		if(arg0.getSource()==boutonLaunchappli){
			new dock().copyFolderSave("copybusybox", dockerUserservice, dockerServeur, dockerRootpass);
			new dock().downloadImagesSel(tab_appli, dockerServeur, dockerRootpass);
			this.setVisible(false);
			new thirdFrame("Client Docker 1.0",400,250,dockerServeur,dockerRootpass,dockerUserservice,new dock().getPathdesktop(),dockerStartsession);
		}//end if bouton launchappli

		//Generation action for checkbox
		for(int ii=0;ii<bddlistgrpuser.length;ii++){
			for(int i=0;i<new db().appliuserBDDcount(dockerUserservice,dockerServeur,bddlistgrpuser[ii],conn);i++){
				tip = new db().appliuserBDDList(dockerUserservice,dockerServeur,bddlistgrpuser[ii],conn);
				
				if(((JCheckBox)arg0.getSource()).getText().equals(tip[i]) && ((JCheckBox)arg0.getSource()).isSelected()){
					tab_appli[count_appli]=tip[i];count_appli++;
				}
				
				if(((JCheckBox)arg0.getSource()).getText().equals(tip[i]) && !(((JCheckBox)arg0.getSource()).isSelected())){
					for(int p=0;p<tab_appli.length;p++){
						if(tab_appli[p].equals(tip[i])){
							tab_appli[p]=null;
						}
					}
				}
				
			}//end for appli		
		}//end for group
	}

}//end of class
