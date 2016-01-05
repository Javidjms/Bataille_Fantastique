package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe représente l'action  "Nuage Malefique"
 */
public class NuageMalefique extends Action {
	/**
	 * Elle inflige 3 point de dégat au personnage attaqué
	 */
	private static int degat=3;
	/**
	 * La portée maximale de cette action est de 2 cases
	 */
	private static int porteeMax=2;
	/**
	 * La portée minimale de cette action est de 1 case
	 */
	private static int porteeMin=1;
	/**
	 * Le type de cette action est Normale
	 */
	private static typeAction typeaction=typeAction.NORMAL;
	/**
	 * La type de portée de cette action est Normale
	 */
	private static typePortee typeportee=typePortee.NORMAL;

	/**Retourne le nombre de dégats qu'inflige cette action
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
		return "<HTML><BODY>le nuage maléfique  inflige 3 dommages à une cible à une "
				+ "distance de moins de <BR> 2 cases.</BODY></HTML>";
	}
	/**
	 * Retourne la portée maximum de l'action
	 * @return variable portéeMax
	 */
	public static int getPorteeMax() {
		return porteeMax;
	}

	/**
	 * Retourne la portée minimum de l'action
	 * @return variable portéeMin
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
	 * Retourne le type de portée de l'action
	 * @return variable typeportée
	 */
	public static typePortee getTypePortee() {
		return typeportee;
	}
	
	/**
	 * Routine de l'action de Nuage Malefique sur le personnage attaqué
	 * (instancié uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqué
	 * @param jeu le controleur du jeu
	 */
	public NuageMalefique(Personnage p,Jeu jeu){
		p.reduirePV(degat);
	}
}
