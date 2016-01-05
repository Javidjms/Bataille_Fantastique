package Fenetre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Personnage.*;

/**
 * 
 * Classe permettant de voir les informations d'un personnage
 *
 */
public class IHMPersonnageInfo extends JPanel{
	private JButton attaque = new JButton("Attaques du personnage");
	private JLabel labelType,labelNom, labelVie, labelMouvement,labelMouvementD, labelEtat;
	private Personnage perso;
	private JPanel jpanel;
	private JButton boutonCarte=new JButton();
	
	/**
	 * Constructeur qui affiche les informations du personnage
	 * @param p le personnage dont on veut voir les informations
	 */
	public IHMPersonnageInfo(Personnage p){
		this.perso = p;
		jpanel= new JPanel();
		jpanel.setLayout(new GridLayout(7,1));
		labelType= new JLabel("Type :");
		labelNom = new JLabel("Nom :");
		labelVie = new JLabel("PV :");
		labelMouvement = new JLabel("PM :");
		labelMouvementD = new JLabel("PM Diag :");
		labelEtat = new JLabel("Etat :");
		attaque.setPreferredSize(new Dimension(350,100));
		this.add(boutonCarte);
		this.add(jpanel);
		boutonCarte.setBackground(Color.WHITE);
		this.setLayout(new GridLayout(2,1));
		jpanel.add(labelType);
		jpanel.add(labelNom);
		jpanel.add(labelVie);
		jpanel.add(labelMouvement);
		jpanel.add(labelMouvementD);
		jpanel.add(labelEtat);
		jpanel.add(attaque);
		attaque.setEnabled(false);		
		attaque.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){voirAttaque();}
				}
				);

	}
	
	/**
	 * Permet de mettre à jour les informations d'un personnage 
	 * @param p le personnage sélectionné
	 */
	public void miseAJour(Personnage p){
		if(p != null){
			perso=p;
			labelType.setText("Type : "+p.getClass().getSimpleName());
			labelNom.setText("Nom :" + p.getNom());
			labelVie.setText("PV :" + p.getPointVie());
			labelMouvement.setText("PM :" + p.getPointMouvement());
			labelMouvementD.setText("PM Diag :" + p.getPointMouvementDiagonale());
			labelEtat.setText("Etat : " + p.getEtat());
			attaque.setEnabled(true);
			boutonCarte.setIcon(p.getCarteIcon());
		} else {
			labelType.setText("Type :");
			labelNom.setText("Nom :");
			labelVie.setText("PV :");
			labelMouvement.setText("PM :");
			labelMouvementD.setText("PM Diag :");
			labelEtat.setText("Etat :");
			attaque.setEnabled(false);
			boutonCarte.setIcon(null);
		}
	}
	
	/**
	 * Affiche une fenêtre IHMListeAttaque
	 */
	public void voirAttaque(){
		new IHMListeAttaque(this.perso);
	}

}
