package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe repr�sente l'action  "Fiole de Poison"
 */
public class FioleDePoison extends Action {
	/**
	 * Elle inflige 2 point de d�gat au personnage attaqu�
	 */
	private static int degat=2;
	/**
	 * La port�e maximale de cette action est de 2 cases
	 */
	private static int porteeMax=2;
	/**
	 * La port�e minimale de cette action est de 1 case
	 */
	private static int porteeMin=1;
	/**
	 * Le type de cette action est Normale
	 */
	private static typeAction typeaction=typeAction.NORMAL;
	/**
	 * La type de port�e de cette action est Normale
	 */
	private static typePortee typeportee=typePortee.NORMAL;

	/**Retourne le nombre de d�gats qu'inflige cette action
	 * @return variable degat
	 */
	public static int getDegat() {
		return degat;
	}

	/**
	 * Retourne la description de l'attaque du personnage
	 * @return un String contenant la description
	 */
	public static String getDescription(){
		return "<HTML><BODY>la fiole de poison  inflige 2 dommages � une cible � une distance"
				+ "<BR> de moins de 2 cases.</BODY></HTML>";
	}
	/**
	 * Retourne la port�e maximum de l'action
	 * @return variable port�eMax
	 */
	public static int getPorteeMax() {
		return porteeMax;
	}
	/**
	 * Retourne la port�e minimum de l'action
	 * @return variable port�eMin
	 */
	public static int getPorteeMin() {
		return porteeMin;
	}
	/**
	 * Retourne le type de l'action
	 * @return variable typeaction
	 */
	public static typeAction getTypeAction() {
		return typeaction;
	}
	
	/**
	 * Retourne le type de port�e de l'action
	 * @return variable typeport�e
	 */
	public static typePortee getTypePortee() {
		return typeportee;
	}
	
	/**
	 * Routine de l'action de Fiole de Poison sur le personnage attaqu�
	 * (instanci� uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqu�
	 * @param jeu le controleur du jeu
	 */
	public FioleDePoison(Personnage p,Jeu jeu){
		p.reduirePV(degat);
	}
}
