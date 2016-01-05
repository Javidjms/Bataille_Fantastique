package Fenetre;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Partie.Joueur;
import Personnage.Personnage;

/**
 * 
 * Classe contenant un bouton affichant une fenêtre IHMListePersonnage
 * et contenant un objet IHMPersonnageInfo
 *
 */
public class IHMBandeauJoueur extends JPanel {
	
	public IHMJeu ihmjeu;
	private IHMPersonnageInfo info;
	private JLabel label;
	private JButton listePerso;
	private Joueur joueur;
	
	/**
	 * Constructeur qui affiche la fenêtre IHMBandeauJoueur
	 * @param ie la fenêtre de jeu 
	 * @param _joueur un joueur 
	 * @param p un personnage présent sur le plateau
	 */
	public IHMBandeauJoueur(IHMJeu ie, Joueur _joueur, Personnage p){
		ihmjeu=ie;
		joueur=_joueur;
		listePerso = new JButton("Liste des personnages");
		info = new IHMPersonnageInfo(p);
		info.setPreferredSize(new Dimension(200,500));
		label = new JLabel("Joueur " +joueur.getNom());
		label.setFont(new Font("Serif", Font.BOLD, 20));
		info.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
		this.add(label);
		this.add(info);
		this.add(listePerso);
		this.setPreferredSize(new Dimension(250,550));
		this.setBackground(Color.GRAY);
		
		listePerso.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){new IHMListePersonnages(joueur);}
				}
				);
		
	}
	
	/**
	 * Retourne la fenêtre IHMPersonnageInfo du personnage
	 * 
	 */
	public IHMPersonnageInfo getInfo() {
		return info;
	}
	
	
}
