package Fenetre;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Partie.*;

/**
 * 
 * Classe affichant une fenêtre permettant de crée un joueur
 *
 */

 public class FenetreCreerJoueur extends JFrame {
	private JButton jbouton;
	private Jeu jeu;
	private JLabel jlabel1;
	private Joueur joueur;
	private JTextField jtext;
	
	/**
	 * Constructeur qui affiche la fenêtre de création d'un joueur
	 * @param _jeu le controleur du jeu
	 * @param j le joueur qui utilise l'application
	 */
	
	public FenetreCreerJoueur(Jeu _jeu,Joueur j){
		super("Bataille Fantastique");
		jeu = _jeu;
		joueur=j;
		jlabel1=new JLabel("Entrer votre nom");
		jtext=new JTextField();
		jbouton=new JButton("Valider");
		this.setSize(350,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		this.add(jlabel1);
		this.add(jtext);
		this.add(jbouton);
		this.setLocationRelativeTo(null);
		this.getRootPane().setDefaultButton(jbouton);
		this.setVisible(true);
		
		jbouton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){valider();}
				});
		
	}
	/**
	 * Prend le texte mis dans le JTextField jtext et met à jour le nom du joueur avec ce texte
	 * Affiche ensuite la fenêtre du catalogue
	 */
	public void valider(){
		jeu.setnomjoueur(this.joueur,jtext.getText());
		jeu.voirCatalogue(this.joueur);
		this.dispose();	
	}
	

		

}

