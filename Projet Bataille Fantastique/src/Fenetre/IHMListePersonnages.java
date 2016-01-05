package Fenetre;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import javax.swing.*;
import Partie.Joueur;
import Partie.Equipe;
import Personnage.*;

/**
 * 
 * Classe affichant une fenêtre permettant de voir la liste des personnages d'un joueur
 *
 */
public class IHMListePersonnages extends JFrame  {
	private Equipe equipe;
	private JButton[] bouton;
	private GridBagConstraints c = new GridBagConstraints();
	
	/**
	 * Constructeur qui affiche la fenêtre avec les personnages d'un joueur
	 * @param joueur le joueur dont on veut voir les personnages
	 */
	public IHMListePersonnages(Joueur joueur){
		super("Liste des personnages de l'équipe");
		equipe=joueur.getEquipe();
		this.setSize(300*equipe.size(),900);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridBagLayout());
		bouton = new JButton[equipe.size()];
		for(int i=0;i<equipe.size();i++){
			Personnage perso = equipe.get(i);
			bouton[i]=new JButton();
			bouton[i].setSize(new Dimension(200,330));
			bouton[i].setBackground(Color.WHITE);
			bouton[i].setIcon((perso.getCarteIcon()));
			c.gridx=i;
			c.gridy=0;
			this.add(bouton[i],c);
			if(!perso.isKO()){
				c.gridx=i;
				c.gridy=1;
				this.add(new Label("Personnage Vivant"),c);
				c.gridy=2;
				this.add(new Label("Type : "+perso.getClass().getSimpleName()),c);
				c.gridy=3;
				this.add(new Label("Nom : "+perso.getNom()),c);
				c.gridy=4;
				this.add(new Label("PV : "+perso.getPointVie()),c);
				c.gridy=5;
				this.add(new Label("PM : "+perso.getPointMouvement()),c);
				c.gridy=6;
				this.add(new Label("PMD : "+perso.getPointMouvementDiagonale()),c);
				c.gridy=7;
				this.add(new Label("Etat : "+perso.getEtat()),c);
				c.gridy=8;
				if(perso.getSaitVoler())
					this.add(new Label("Personnage Volant"),c);
				else
					this.add(new Label("Personnage Terrestre"),c);
				
			}
			else{
				c.gridx=i;
				c.gridy=1;
				this.add(new Label("Personnage KO"),c);
			}
			
			
		}
			
	}
}
