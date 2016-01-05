package Fenetre;

import javax.swing.*;

import Partie.Jeu;
import Partie.Joueur;
import Personnage.Personnage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * 
 * Classe affichant les différentes classes de Personnage disponibles
 * pouvant être sélectionné dans une équipe
 */

public class Catalogue extends JFrame {
	private JCheckBox[] box = new JCheckBox[8];
	private Jeu jeu;
	private Joueur joueur;
	private int NbBoxSelectionné=0;	
	private int nbpersonnages;
	private ArrayList<Class> personnages;
	private JButton[] bouton= new JButton[5];
	private GridBagConstraints c = new GridBagConstraints();
	
	/**
	 * Constructeur qui affiche le catalogue
	 * @param _jeu le contrôleur du jeu
	 * @param j le joueur qui regarde le catalogue
	 */
	public Catalogue(Jeu _jeu,Joueur j) {
		super("Sélection des personnages");
		joueur=j;
		jeu=_jeu;
		nbpersonnages=jeu.getNbPersonnage();
		personnages=jeu.getPersonnages();
		for(int i=0;i<4;i++){
			bouton[i]=new JButton("");
			bouton[i].setSize(new Dimension(200,330));
			bouton[i].setBackground(Color.WHITE);
		}
		bouton[0].setIcon(new ImageIcon("image/cartemagicien.png"));
		bouton[1].setIcon(new ImageIcon("image/cartecavalier.png"));
		bouton[2].setIcon(new ImageIcon("image/carteguerrier.png"));
		bouton[3].setIcon(new ImageIcon("image/cartevoleur.png"));
		bouton[4]=new JButton("Suivant");
		bouton[4].setEnabled(false);
		this.setSize(1100,500); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());	
		for(int i=0;i<4;i++){
			box[2*i]=new JCheckBox();
			box[2*i+1]=new JCheckBox();
			c.gridx=i;
			c.gridy=0;
			this.add(bouton[i],c);
			box[2*i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			box[2*i+1].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			c.gridy=2;
			this.add(box[2*i],c);
			c.gridy=3;
			this.add(box[2*i+1],c);
		}
		c.gridx=0;
		c.gridy=1;
		this.add(new JLabel("Magicien"),c);
		c.gridx=1;
		this.add(new JLabel("Cavalier Celeste"),c);
		c.gridx=2;
		this.add(new JLabel("Guerrier"),c);
		c.gridx=3;
		this.add(new JLabel("Voleur"),c);
		c.gridy=4;
		c.ipadx=50;
		c.ipady=20;
		this.add(bouton[4],c);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.getRootPane().setDefaultButton(bouton[4]);
		for(int i=0;i<4;i++){
		final int t =i;
		bouton[i].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){voirCarteIdentite(personnages.get(t));}
				}
				);
		}
		bouton[4].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try {
							valider();
							} catch (Exception ee) {}
						}
					}
				);
		for(int i=0;i<8;i++){
			box[i].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){boxNotify(e);}
			}
			);
		}
	}
	
	/**
	 * Incrémente la variable "NbBoxSelectionné" si une box a été coché ou la décrémente si une box a été décoché
	 * Effectue ensuite un test de la variable
	 * @param actionEvent 
	 */
	public void boxNotify(ActionEvent actionEvent){
		AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
        if( abstractButton.getModel().isSelected()){
        	NbBoxSelectionné++;
        }
        else{
        	NbBoxSelectionné--;
        }
        testNbSelectionné();
	}
	
	/**
	 * Teste si le nombre de box coché est égale au nombre de personnage autorisé par équipe
	 * Active le bouton "Suivant" si c'est le cas, sinon le laisse désactivé 
	 */
	public void testNbSelectionné() {
		if(NbBoxSelectionné==nbpersonnages)
			bouton[4].setEnabled(true);
		else
			bouton[4].setEnabled(false);
	}
	
	/**
	 * Ajoute le personnage dont une box a été coché à l'équipe du joueur qui utilise le catalogue
	 * Affiche ensuite une fenêtre de liste de personnage 
	 * @throws InstantiationException exception
	 * @throws IllegalAccessException exception
	 */
	public void valider() throws InstantiationException, IllegalAccessException{
		for(int i=0;i<8;i++){
			if(box[i].isSelected()){
				jeu.ajouterPersonnage(joueur, personnages.get(i/2));
			}				
		}
		this.dispose();
		new FenetreListePersonnage(jeu,joueur);
	}
	
	/**
	 * Affiche la fenêtre CarteIdentite
	 * @param s la classe de Personnage dont on veut voir la carte d'identité
	 */
	public void voirCarteIdentite(Class<Personnage> s){
		new CarteIdentite(s);
	}
	
}
