package Fenetre;
import java.awt.GridLayout;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Personnage.Personnage;

/**
 * Classe affichant les caractéristiques d'une classe de Personnage
 *
 */

public class CarteIdentite extends JFrame {
	
	/**
	 * Constructeur qui affiche la carte d'identité
	 * @param s la classe de Personnage dont on veut voir les caractéristiques
	 */
	
	public CarteIdentite(Class<Personnage> s){
		super("Carte Identité");	
		try{
			this.add( new JLabel("Personnage : "+s.getSimpleName()));
			this.add( new JLabel("Age : "+s.getMethod("getAge").invoke(s)));
			this.add( new JLabel("Point de Vie : "+s.getMethod("getPV").invoke(s)));
			this.add( new JLabel("Point de Mouvement : "+s.getMethod("getPM").invoke(s)));
			this.add( new JLabel("Point de Mouvement Diagonale : "+s.getMethod("getPMD").invoke(s)));
			this.add( new JLabel("Liste d'actions possibles : "));
			ArrayList<String> attaques=(ArrayList<String>) s.getMethod("afficherActions").invoke(s);
			for(String nomattaque :attaques){
				this.add( new JLabel(nomattaque));
			}
		}
		catch(Exception e){}
		this.setLayout(new GridLayout(13,1));
		this.setSize(300,300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		}
}

