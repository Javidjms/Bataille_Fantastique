package Fenetre;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import Personnage.*;

/**
 * 
 * Classe affichant une fenêtre permettant de voir la liste des attaques d'un personnage
 *
 */
public class IHMListeAttaque extends JFrame {
	private JLabel[] nomAttaque;
	private Personnage perso;
	
	/**
	 * Constructeur qui affiche la fenêtre avec la liste des attaques d'un personnage
	 * @param p un personnage présent sur le plateau
	 */
	public IHMListeAttaque(Personnage p){
		super("Liste des attaques");
		this.perso=p;
		this.setSize(500,500);
		this.setLayout(new GridLayout(10,1));
		ArrayList<Class> s;
		try {
			s = (ArrayList<Class>) perso.getClass().getMethod("getActions").invoke(perso);
			nomAttaque = new JLabel[2*s.size()];
			for(int i=0; i<s.size(); i++){
				Class tempAction=s.get(i);
				String tempString;
				nomAttaque[i] = new JLabel(""+s.get(i).getSimpleName()+"  :");
				tempString = (String) tempAction.getMethod("getDescription").invoke(tempAction);
				nomAttaque[i+1] = new JLabel(""+tempString);
				this.getContentPane().add(nomAttaque[i]);
				this.getContentPane().add(nomAttaque[i+1]);
				}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
