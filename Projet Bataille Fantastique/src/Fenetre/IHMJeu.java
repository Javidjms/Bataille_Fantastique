package Fenetre;
import javax.swing.*;
import Partie.Jeu;
import Partie.Joueur;
import Personnage.Personnage;
import java.awt.*; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

/**
 * Classe qui affiche l'interface graphique du jeu
 */
public class IHMJeu extends JFrame implements Serializable {
	
	private IHMBandeauJoueur bandeau1,bandeau2;
	private IHMCommandeVue1 commande1;	
	private IHMCommandeVue2 commande2;
	private IHMCommandeVue3 commande3;
	private IHMPlateau Guiplateau;
	private Jeu jeu;
	private Joueur joueur;
	private Personnage p;
	private JLabel terminal;
	
	/**
	 * Constructeur qui initialise l'interface graphique du jeu avec les bandeaux et les commandes
	 * @param _jeu controleur du jeu
	 */
	public IHMJeu(Jeu _jeu){
		super("Plateau du jeu");
		jeu=_jeu;
		joueur = jeu.getJoueurCourant();
		this.setSize(1500,1500); 
		this.setLayout(new BorderLayout());
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.getContentPane().add(terminal = new JLabel("Bienvenue dans le Jeu Bataille Fantastique",JLabel.CENTER), BorderLayout.NORTH);
		this.getContentPane().add(Guiplateau = new IHMPlateau(this,jeu,jeu.getTaillePlateauX(),jeu.getTaillePlateauY()), BorderLayout.CENTER);
		this.getContentPane().add(bandeau1 = new IHMBandeauJoueur(this,jeu.getJoueur1(), this.p), BorderLayout.WEST);
		this.getContentPane().add(bandeau2 = new IHMBandeauJoueur(this,jeu.getJoueur2(), this.p), BorderLayout.EAST);
		this.getContentPane().add(commande1 = new IHMCommandeVue1(jeu,joueur),BorderLayout.SOUTH);
		terminal.setFont(new Font("Serif", Font.BOLD, 20));
		terminal.setForeground(Color.WHITE);
		terminal.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
		this.getContentPane().setBackground(Color.BLACK);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.afficherTerminal("Joueur "+jeu.getJoueurCourant().getNom()+" : Placer vos personnages sur le plateau");
        this.addWindowListener(new WindowAdapterSerializable() {
            public void windowClosing(WindowEvent e) {
            	Object[] options = {"Sauvegarder et Quitter"," Quitter sans sauvegarder",
                        "Annuler"};
                int confirm = JOptionPane.showOptionDialog(null, "Voulez vous sauvegardez avant de quitter le jeu", "Quitter le jeu", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,options[2]);
                if(confirm ==1){
                	System.exit(0);
                	}
                else if(confirm ==0){
                	jeu.sauvegarder();
                	System.exit(0);
                	}
            	}
        		}
        	);
		}	
	
		/**
		 * Affiche une chaine de caractère sur le terminal (en haut du plateau)
		 * @param s String à afficher sur le terminal
		 */
		public void afficherTerminal(String s){
			if(!jeu.isPlacé())
				terminal.setText(s);
			else{
				String nomjoueur= jeu.getJoueurCourant().getNom();
				int numeroTour =jeu.getTour().getNumeroTour();
				terminal.setText("Tour "+numeroTour+"- Joueur "+nomjoueur+" : "+s);
			}
		}
		
		/**
		 * Retourne le bandeau du Joueur1
		 * @return le bandeau du joueur1
		 */
		public IHMBandeauJoueur getBandeau1(){
			return bandeau1;
		}
		/**
		 * Retourne le bandeau du Joueur2
		 * @return le bandeau du joueur2
		 */
		public IHMBandeauJoueur getBandeau2(){
			return bandeau2;
		}
		/**
		 * Retourne les commandes Vue1
		 * @return commande1
		 */
		public IHMCommandeVue1 getCommande1() {
			return commande1;
		}
		/**
		 * Retourne les commandes Vue2
		 * @return commande2
		 */
		public IHMCommandeVue2 getCommande2() {
			return commande2;
		}

		/**
		 * Retourne les commandes Vue3
		 * @return commande3
		 */
		public IHMCommandeVue3 getCommande3() {
			return commande3;
		}
		
		/**
		 * Retourne la case graphique
		 * @param position position de la case graphique demandé
		 * @return la case graphique demandé
		 */
		public IHMCase getGuiCase(int[] position) {
			return Guiplateau.getGuiCase(position);
		}
		
		/**
		 * Setter commande Vue 1
		 * @param interfaceGraphiqueCommandeVue1  commande pour poser les personnages
		 */
		public void setCommande1( IHMCommandeVue1 interfaceGraphiqueCommandeVue1) {
			commande1=interfaceGraphiqueCommandeVue1;
			
		}
		
		/**
		 * Setter commande Vue 2
		 * @param interfaceGraphiqueCommandeVue2  commande pour poser le Deplacement/Action
		 */
		public void setCommande2( IHMCommandeVue2 interfaceGraphiqueCommandeVue2) {
			commande2=interfaceGraphiqueCommandeVue2;
			
		}
		
		/**
		 * Setter commande Vue 3
		 * @param interfaceGraphiqueCommandeVue3  commande pour poser les actions du personnage selectionné
		 */
		public void setCommande3( IHMCommandeVue3 interfaceGraphiqueCommandeVue3) {
			commande3=interfaceGraphiqueCommandeVue3;
			
		}
	

}
