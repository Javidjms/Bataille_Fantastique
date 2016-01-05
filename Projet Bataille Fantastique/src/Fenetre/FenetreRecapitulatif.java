package Fenetre;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Partie.Jeu;

/**
 * 
 * Classe affichant la fenêtre récapitulant le nombre de personnages par équipe
 * et la taille du plateau choisis 
 *
 */
public class FenetreRecapitulatif extends JFrame {
	
	private JPanel jpanel1,jpanel3,jpanel4; 
	private JLabel JTaille,JNb;
	private JButton jbouton;
	private Jeu jeu;
	
	/**
	 * Constructeur qui affiche la fenêtre récapitulative
	 * @param _jeu le controleur du jeu
	 */
	public FenetreRecapitulatif(Jeu _jeu){
		super("Récapitulatif");
		jeu=_jeu;
		jbouton = new JButton("Suivant");
		JTaille = new JLabel(" Taille : "+jeu.getTaillePlateauX()+"x"+jeu.getTaillePlateauY());
		JNb = new JLabel(" Nombre de perso : "+jeu.getNbPersonnage());
		jpanel1 =new JPanel();
		jpanel3 =new JPanel();
		jpanel4 =new JPanel();
		jpanel1.add(JNb);
		jpanel3.add(JTaille);
		jpanel4.add(jbouton);
		this.setSize(350,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,2));
		this.add(jpanel1);
		this.add(new JPanel());
		this.add(jpanel3);
		this.add(jpanel4);
		this.setLocationRelativeTo(null);
		this.getRootPane().setDefaultButton(jbouton);
		this.setVisible(true);

		jbouton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){valider();}
				});
		
	}
	
	/**
	 * Affiche la fenêtre permettant la création d'un joueur 
	 */
	public void valider(){
		this.dispose();
		jeu.afficherFenetreCreationJoueurJ1();
	}
}
