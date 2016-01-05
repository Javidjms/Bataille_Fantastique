package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe repr�sente l'action  "Instinct d' Esquive"
 */
public class InstinctEsquive extends Action {
	/**
	 * Elle protege de 3 point le personnage attaquant
	 */
	private static int defense=3;
	/**
	 * La port�e maximale de cette action est de 0 case
	 */
	private static int porteeMax=0;
	/**
	 * La port�e minimale de cette action est de 0 case
	 */
	private static int porteeMin=0;
	/**
	 * Le type de cette action est Normale
	 */
	private static typeAction typeaction=typeAction.NORMAL;
	/**
	 * La port�e de l'action est sur le personnage utilisant l'action (SELF)
	 */
	private static typePortee typeportee=typePortee.SELF;


	/**Retourne le nombre de pt de d�fense de cette action
	 * @return variable defense
	 */
	public static int getDefense() {
		return defense;
	}
	
	/**
	 * Retourne la description de l'attaque du personnage
	 * @return un String contenant la description
	 */
	public static String getDescription(){
		return "<HTML><BODY>l�instinct d�esquive prot�ge le personnages des <BR> 3"
				+ " points de vie qu�il pourrait perdre dans ce tour</BODY></HTML>";
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
	 * Routine de l'action de Instinct d'Esquive sur le personnage qui attaque
	 * (instanci� uniquement lors d'une attaque)
	 * @param p Le personnage qui lance l'attaque
	 * @param jeu le controleur du jeu
	 */
	public InstinctEsquive(Personnage p,Jeu jeu){
		p.setDefense(defense,jeu.getTour().getNumeroTour());
	}
}
