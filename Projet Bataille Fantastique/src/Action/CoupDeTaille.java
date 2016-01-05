package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe représente l'action  "Coup de Taille"
 */
public class CoupDeTaille extends Action {
	/**
	 * Elle inflige 4 point de dégat au personnage attaqué
	 */
	private static int degat=4;
	/**
	 * La portée maximale de cette action est de 1 case
	 */
	private static int porteeMax=1;
	/**
	 * La portée minimale de cette action est de 1 case
	 */
	private static int porteeMin=1;
	/**
	 * Le type de cette action est une action Terreste (elle ne vise que les personnage terrestres)
	 */
	private static typeAction typeaction=typeAction.SOL;
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
		return "<HTML><BODY>le coup de taille  inflige 4 dommages à une cible au sol sur une "
				+ "case adjacente <BR>au guerrier </BODY></HTML>";
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
	 * Routine de l'action de Coup de Taille sur le personnage attaqué
	 * (instancié uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqué
	 * @param jeu le controleur du jeu
	 */
	public CoupDeTaille(Personnage p,Jeu jeu){
		if(!p.getSaitVoler())
			p.reduirePV(degat);
	}
}
