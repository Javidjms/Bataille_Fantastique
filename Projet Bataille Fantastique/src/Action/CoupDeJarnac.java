package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe repr�sente l'action  "Coup de Jarnac"
 */
public class CoupDeJarnac extends Action {
	/**
	 * Elle inflige 1 point de d�gat au personnage attaqu�
	 */
	private static int degat=1;
	/**
	 * La port�e maximale de cette action est de 4 cases
	 */
	private static int porteeMax=4;
	/**
	 * La port�e minimale de cette action est de 1 case
	 */
	private static int porteeMin=1;
	/**
	 * Il s'agit du nombre de fois que l'on peut utiliser l'action durant le m�me tour
	 */
	private int nbMultipleAction=2;
	/**
	 * Le type de cette action est une action Terreste (elle ne vise que les personnage terrestres)
	 */
	private static typeAction typeaction=typeAction.SOL;
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
		return "<HTML><BODY> le coup de jarnac  inflige 1 dommage � une cible terrestre � <BR> "
				+ "une distance d�au maximum 4 cases (ce coup peut �tre utilis� deux fois"
				+ " dans le tour) </BODY></HTML>";
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
	 * Routine de l'action de Coup de Jarnac sur le personnage attaqu�
	 * (instanci� uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqu�
	 * @param jeu le controleur du jeu
	 */
	public CoupDeJarnac(Personnage p,Jeu jeu){ 
		if(!p.getSaitVoler()){
			p.reduirePV(degat);
		}
		jeu.hasMultipleAction(this.nbMultipleAction);
	}
	
	
}
