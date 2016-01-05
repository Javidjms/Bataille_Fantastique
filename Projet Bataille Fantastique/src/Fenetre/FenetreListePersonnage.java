package Fenetre;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Partie.Jeu;
import Partie.Joueur;
/**
 * 
 * Classe affichant la fenêtre permettant de donner un nom aux personnages d'un joueur
 *
 */
public class FenetreListePersonnage extends JFrame {
	private JButton jbouton;
	private Jeu jeu;
	private ArrayList<JLabel> jlabel=new ArrayList<JLabel>();
	private Joueur joueur;
	private JPanel jpanel1,jpanel2;
	private ArrayList<JTextField> jtext= new ArrayList<JTextField>();
	
	/**
	 * Constructeur qui affiche la fenêtre pour nommer les personnages
	 * @param _jeu le controleur du jeu
	 * @param _joueur le joueur qui utilise l'application
	 */
	public FenetreListePersonnage(Jeu _jeu,Joueur _joueur){
		super("Equipe de "+_joueur.getNom());
		jeu = _jeu;
		joueur = _joueur;
		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		this.setLayout(new GridLayout(3,1));
		jpanel1.setLayout(new GridLayout(jeu.getNbPersonnage(),2));
		for(int i=0;i<jeu.getNbPersonnage();i++){
			jlabel.add(new JLabel(""+joueur.getPersonnage(i).getClass().getSimpleName()));
			jpanel1.add(jlabel.get(i));
			jtext.add(new JTextField());
			jpanel1.add(jtext.get(i));
		}
		
		jbouton=new JButton("Suivant");
		jpanel2.add(jbouton);
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new JLabel("Entrer les noms de vos personnages choisis"));
		this.add(jpanel1);
		this.add(jpanel2);
		this.setLocationRelativeTo(null);
		this.getRootPane().setDefaultButton(jbouton);
		this.setVisible(true);
		
		jbouton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){valider();}
				});
	}
	
	/**
	 * Récupère le texte mis dans le JTextField jtext et met à jour les noms des personnages du joueur avec ce texte
	 * Affiche ensuite une nouvelle fenêtre de création de joueur
	 */
	public void valider(){
		for(int i=0;i<jeu.getNbPersonnage();i++){
			joueur.getPersonnage(i).setNom(jtext.get(i).getText());
		}
		this.dispose();
		jeu.afficherFenetreCreationJoueurJ2();
	}

}
