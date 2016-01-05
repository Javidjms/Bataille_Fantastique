package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe représente l'action "Terre Marécageuse"
 */
public class TerreMarecageuse extends Action {
	/**
	 * Elle n'inflige pas de dégat au personnage attaqué
	 */
	private static int degat=0;
	/**
	 * La portée minimale de cette action est de 2 cases
	 */
	private static int porteeMin=2;
	/**
	 * Le type de cette action est Normale
	 */
	private static typeAction typeaction=typeAction.NORMAL;
	/**
	 * Le type de portée de cette action est ciblée (Attaque Ciblée)
	 */
	private static typePortee typeportee=typePortee.CIBLE;
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
		return "<HTML><BODY>la terre marécageuse  ralentit toutes les cibles d’une zone, "
				+ "de 9 cases, centrée<BR> autour d’une case cible</BODY></HTML>";
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
	 * Routine de l'action de Terre Marécageuse sur le personnage attaqué
	 * (instancié uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqué
	 * @param jeu le controleur du jeu
	 */
	public TerreMarecageuse(Personnage p,Jeu jeu){
		if(!p.isRalenti())
			p.hasRalenti(jeu.getTour().getNumeroTour());
	}
}
