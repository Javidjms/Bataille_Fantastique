package Fenetre;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Partie.Jeu;

/**
 *Classe affichant la fenêtre principale permettant de demarrer une nouvelle partie 
 *ou de charger une partie préalablement sauvegardée
 */
public class FenetrePrincipale extends JFrame {

	private JPanel jpanel0,jpanel1;
	private JButton jbouton1,jbouton2;
	private Jeu jeu;
	private JButton boutonMenuIcone=new JButton();
	
	/**
	 * Constructeur qui affiche  la fenêtre principale 
	 * @param _jeu le controleur du jeu
	 */
	public FenetrePrincipale(Jeu _jeu){
		super("Bataille Fantastique");
		jeu = _jeu;
		jpanel0=new JPanel();
		jpanel1=new JPanel();
		jpanel0.add(boutonMenuIcone);
		boutonMenuIcone.setBackground(Color.white);
		boutonMenuIcone.setIcon(new ImageIcon("image/menu.png"));
		jpanel1.setLayout(new GridLayout(1,2));
		jbouton1=new JButton("Demarrer une nouvelle partie");
		jbouton2=new JButton("Charger une partie");
		this.setSize(500,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		jpanel1.add(jbouton1);
		jpanel1.add(jbouton2);
		this.add(jpanel0);
		this.add(jpanel1);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		jbouton1.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){demarre();}
				});
		jbouton2.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){charger();}
			});
	}
		/**
		 * Demarre une nouvelle partie en affichant la fenêtre de selection du Nb de personnage
		 * par joueur
		 */
		public void demarre(){
			this.dispose();
			new FenetreSelectionNb(jeu);
			
		}
		/**
		 * Charge une partie préalablement sauvegardée et affiche le plateau du jeu
		 */
		public void charger(){
			try{
			jeu.charger();
			this.dispose();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Le fichier spécifié est introuvable (Pas de sauvegarde récente)", "Erreur de chargement de la sauvegarde",
		                JOptionPane.NO_OPTION);
			}
		}
}
