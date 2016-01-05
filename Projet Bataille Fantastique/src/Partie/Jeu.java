package Partie;
import Action.Action;
import Action.Action.typePortee;
import Exception.*;
import Fenetre.*;
import Personnage.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Cette classe représente le controleur Jeu
 */
public class Jeu implements Serializable {
	/**
	 * Le type enumere pour indiquer la phase actuelle du jeu 
	 */
	public enum Phase {ACTION_PERSONNAGE,DEPLACEMENT_PERSONNAGE,NORMAL,
						PLACEMENT_PERSONNAGE,SELECTION_PERSONNAGE}
	private int _countjoueur=0;
	private int idActionSelectionné=-1;
	private int IdPersonnageSelectionné=0;
	private IHMJeu ihmjeu;
	private Joueur joueur1,joueur2; 
	private int nbPersonnagesEquipe;
	private ArrayList<Class> personnages=new ArrayList<Class>();
	
	private Personnage personnageSelectionné;
	private Phase phase=Phase.NORMAL;
	private Plateau plateau;
	private int[] positionCaseSelectionné;
	private int TaillePlateauX;
	private int TaillePlateauY;;
	private Tour tour;
	
	/**
	 * Constructeur qui initialise les joueurs et affiche la fenetre principale
	 */
	public Jeu(){
		this.listePersonnages();
		joueur1=new Joueur(Color.CYAN);
		joueur2=new Joueur(Color.MAGENTA);
		new FenetrePrincipale(this);
	}

	/**
	 * Constructeur non officielle mais utile pour le TestAvancé
	 * @param s String Test
	 */
	public Jeu(String s){
		//Test Avancé
		this.listePersonnages();
		joueur1=new Joueur(Color.CYAN);
		joueur2=new Joueur(Color.MAGENTA);
	}
	
	/**
	 * Afficher la fenetre de Creation du joueur 1
	 */
	public void afficherFenetreCreationJoueurJ1(){
		new FenetreCreerJoueur(this,joueur1);
	}
	
	/**
	 * Afficher la fenetre de Creation du joueur 2
	 */
	public void afficherFenetreCreationJoueurJ2(){
		if(_countjoueur!=1){
		new FenetreCreerJoueur(this,joueur2);
		_countjoueur++;
		}
		else{
			this.init();
			_countjoueur=0;
		}
	}
	
	/**
	 * Ajoute un personnage dans l'équipe du joueur sélectionné
	 * @param j joueur qui sélectionne
	 * @param p Classe du personnage selectionné
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void ajouterPersonnage(Joueur j,Class<?> p) throws InstantiationException, IllegalAccessException{
		j.ajouterPersonnage((Personnage)p.newInstance());
	}
	
	/**
	 * Retourne le personnage présent sur cette case
	 * @param positionCaseSelectionné position de la case du personnage à recuperer 
	 * @return le personnage de cette case
	 */
	public Personnage caseGetPersonnage(int[] positionCaseSelectionné){
		return plateau.caseGetPersonnage(positionCaseSelectionné);
	}
	
	/**
	 * Charge une sauvegarde préalablement enregistrée
	 * @throws java.io.IOException Exception
	 * @throws ClassNotFoundException Exception
	 */
	public  void charger() throws java.io.IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			final FileInputStream fichier = new FileInputStream("jeu.ser");
			ois = new ObjectInputStream(fichier);
			final Jeu jeu = (Jeu) ois.readObject();
			jeu.getIHMJeu().setEnabled(true);
			jeu.getIHMJeu().setVisible(true);
		} catch (final java.io.IOException e) {
			throw new java.io.IOException();
		} catch (final ClassNotFoundException e) {
			throw new ClassNotFoundException();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				throw new java.io.IOException();
			}
					}
	}
	
	/**
	 * Choisit un personnage
	 * @param positionCaseSelectionné position de la case du personnage selectionné
	 * @return le personnage selectionné
	 * @throws CaseVide Exception
	 * @throws PersonnageAdverse Exception
	 */
	public Personnage choisirPersonnage(int[] positionCaseSelectionné) throws CaseVide,PersonnageAdverse{
		Personnage p =this.caseGetPersonnage(positionCaseSelectionné);
		if(p==null){
			throw new CaseVide();
		}
		else if(this.getJoueurCourant().possedePersonnage(p)){
			return p;
		}
		else{
			throw new PersonnageAdverse();
		}
	}
	
	/**
	 * Retourne un booléen si les deux positions sont égales
	 * @param positionCaseSelectionné1 position à comparer
	 * @param positionCaseSelectionné2 position à comparer
	 * @return <code>true</code> si les positions sont égales; <code>false</code> sinon
	 */
	public boolean comparepositionCaseSelectionné(int[] positionCaseSelectionné1,int[] positionCaseSelectionné2){
		int x1=positionCaseSelectionné1[0];int x2=positionCaseSelectionné2[0];
		int y1=positionCaseSelectionné1[1];int y2=positionCaseSelectionné2[1];
		return(x1==x2 &&y1==y2);
	}
	
	/**
	 * Deplace un personnage
	 * @param p personnage à déplacer
	 * @param positionCaseSelectionné nouvelle position du personnage à déplacer
	 */
	public void deplacerPersonnage(Personnage p,int[] positionCaseSelectionné){
		plateau.libererCase(p.getPosition());
		p.deplacer(positionCaseSelectionné);
		plateau.poser(p, positionCaseSelectionné);
		this.hasDéplacé();
		
	}
	
	/**
	 * Effacer les informations contenues dans les bandeaux (IHMBandeau)
	 */
	public void finInfoPersonnage(){
		ihmjeu.getBandeau1().getInfo().miseAJour(null);
		ihmjeu.getBandeau2().getInfo().miseAJour(null);
		
	}
	
	/**
	 * Retourne un booléen si l'attaque multiple est finie
	 * @return <code>true</code> si l'attaque multiple est finie; <code>false</code> sinon
	 */
	public boolean finMultipleAction(){
		return tour.finMultipleAction();
	}
	
	/**
	 * Finit le tour actuelle, met à jour les flags des personnages et Passe la main
	 */
	public void finTour() {
		this.majEtatPersonnage();
		this.finInfoPersonnage();
		this.joueurSuivant();
		tour.passerlaMain();
		
	}
	
	/**
	 * Fixe la taille du plateau
	 * @param X taille X du plateau (largeur)
	 * @param Y taille Y du plateau (longueur)
	 */
	public void fixerTaillePlateau(int X, int Y){
		TaillePlateauX=X;
		TaillePlateauY=Y;
	}
	
	/**
	 * Routine principale en fonction de la phase et activable lors de la selection d'une case
	 */
	public void fonction(){
		Phase phase=getPhase();
		switch(phase){
		case PLACEMENT_PERSONNAGE:
			this.IhmPlacementPersonnage();
			break;
		case SELECTION_PERSONNAGE:
			this.IhmSelectionPersonnage();
			break;
		case DEPLACEMENT_PERSONNAGE:
			this.IhmDeplacementPersonnage();
			break;
		case ACTION_PERSONNAGE:
			this.IhmActionPersonnage();
			break;
		default:
			this.IhmDeselectionPersonnage();
		}
	}
	
	/**
	 * Retourne une case
	 * @param positionCaseSelectionné position de la case voulue
	 * @return case à la position voulue
	 */
	public Case getCase(int[] positionCaseSelectionné) {
		return this.plateau.getCase(positionCaseSelectionné);
	}

	/**
	 * Retourne le numero de l'action selectionné
	 * @return le numero de l'action selectionné
	 */
	public int getidActionSelectionné() {
		return idActionSelectionné;
	}
	
	/**
	 * Retourne l'interface graphique du jeu
	 * @return un IHMJeu
	 */
	public IHMJeu getIHMJeu(){
		return ihmjeu;
	}
	
	/**
	 * Retourne le joueur 1
	 * @return joueur 1
	 */
	public Joueur getJoueur1() {
		return joueur1;
	}
	/**
	 * Retourne le joueur 2
	 * @return joueur 2
	 */
	public Joueur getJoueur2() {
		return joueur2;
	}

	/**
	 * Retourne le joueur courant
	 * @return le joueur courant
	 */
	public Joueur getJoueurCourant() {
		return tour.getJoueurCourant();
	}
	
	/**
	 * Retourne le nombre de personnages par équipe
	 * @return le nombre de personnages par équipe
	 */
	public int getNbPersonnage(){
		return nbPersonnagesEquipe;
	}
	
	/**
	 * Retourne la liste des classes de personnages
	 * @return la liste des classes de personnages
	 */
	public ArrayList<Class> getPersonnages(){
		return this.personnages;
	}
	
	/**
	 * Retourne la phase courante
	 * @return la phase courante
	 */
	public Phase getPhase(){
		return phase;
	}

	/**
	 * Retourne la Taille X du plateau (largeur)
	 * @return la Taille X
	 */
	public int getTaillePlateauX(){
		return TaillePlateauX;
	}
	
	/**
	 * Retourne la Taille Y du plateau (longueur)
	 * @return la Taille Y
	 */
	public int getTaillePlateauY(){
		return TaillePlateauY;
	}
	
	/**
	 * Retourne le tour courant
	 * @return le tour courant
	 */
	public Tour getTour(){
		return tour;
	}
	
	/**
	 * Notifie au tour que l'action est finie
	 */
	public void hasAction() {
		tour.hasAction();
	}
	/**
	 * Notifie au tour que le Deplacement est finie
	 */
	public void hasDéplacé() {
		tour.hasDéplacé();
	}
	/**
	 * Incrémente le compteur d'action multiple
	 * @param nbMultipleAction nb de repetition possible par tour
	 */
	public void hasMultipleAction(int nbMultipleAction) {
		tour.hasMultipleAction(nbMultipleAction);
		
	}
	/**
	 * Notifie au tour que le placement est finie
	 */
	public void hasPlacé() {
		tour.hasPlacé();
	}
	
	/**
	 * Routine graphique lorsque la commande Action est appuyé
	 */
	public void IhmActionAppuyé() {
		ihmjeu.getCommande2().setVisible(false);
		ihmjeu.remove(ihmjeu.getCommande2());
		ihmjeu.setCommande3(new IHMCommandeVue3(personnageSelectionné,ihmjeu,this,getJoueurCourant()));
		ihmjeu.getContentPane().add(ihmjeu.getCommande3(), BorderLayout.SOUTH);
		ihmjeu.afficherTerminal("Selectionner une Action");
		this.setPhase(Phase.ACTION_PERSONNAGE);
	}

	/**
	 * Routine graphique de l'action selectionné d'un personnage
	 */
	public void IhmActionPersonnage(){
		try{
			if(this.verifierCaseAction(idActionSelectionné, positionCaseSelectionné)){
				this.personnageAction(personnageSelectionné,idActionSelectionné,positionCaseSelectionné);
				ihmjeu.getCommande2().actionFinie();
				this.IhmFinAction();
				this.hasAction();
				this.IhmFinVoirCasePossibleAction(idActionSelectionné);
				this.MAJPVPersonnage();
				this.verifiegagner();
			}
		}
		catch(ActionNonSelectionne e){
			ihmjeu.afficherTerminal("Il faut d'abord selectionner une attaque");
		}
		catch(PorteeActionInsuffisante e){
			ihmjeu.afficherTerminal("La portée de l'attaque est insuffisante pour atteindre cette case");
		}
		
	}
	/**
	 * Routine graphique du deplacement d'un personnage
	 */
	public void IhmDeplacementPersonnage(){
		try{
			if(this.verifierCaseDeplacement(personnageSelectionné,positionCaseSelectionné)){
				this.IhmFinVoirCasePossibleDeplacement();
				this.deplacerPersonnage(personnageSelectionné, positionCaseSelectionné);
				ihmjeu.getCommande2().deplacementFinie();
			}
			ihmjeu.getGuiCase(positionCaseSelectionné).setBackground(Color.YELLOW);
			ihmjeu.afficherTerminal("Selectionner une Commande");
			this.setPhase(Phase.NORMAL);
		}
		catch(DeplacementImpossible e){
			ihmjeu.afficherTerminal("Vos points de mouvements ne permettent pas de se deplacer sur cette case");
		}
		
	}
	
	/**
	 * Routine graphique de la désélection d'un personnage
	 */
	public void IhmDeselectionPersonnage(){
		if((!isDéplacé() &&!isAction()&&isPlacé())&&personnageSelectionné==caseGetPersonnage(positionCaseSelectionné)){
			ihmjeu.getGuiCase(positionCaseSelectionné).setBackground(getJoueurCourant().getColor());
			this.setPhase(Phase.SELECTION_PERSONNAGE);
			ihmjeu.getCommande2().boutonDesactive();
			ihmjeu.afficherTerminal("Selectionner un Personnage");
		}
		
	}

	/**
	 * Routine graphique de la fin d'une Action
	 */
	public void IhmFinAction() {
		ihmjeu.getCommande3().setVisible(false);
		ihmjeu.remove(ihmjeu.getCommande3());
		ihmjeu.getCommande2().setVisible(true);
		ihmjeu.getContentPane().add(ihmjeu.getCommande2(), BorderLayout.SOUTH);
		if(tour.isMultipleAction() && !tour.finMultipleAction())
			ihmjeu.getCommande2().actionMultiple();
		ihmjeu.afficherTerminal("Selectionner une Commande");
		this.setPhase(Phase.NORMAL);
	}
	
	/**
	* Routine graphique de la fin du Tour 
	 */
	public void IhmFinTour(){
		ihmjeu.getGuiCase(personnageSelectionné.getPosition()).setBackground(getJoueurCourant().getColor());
		this.finTour();
		ihmjeu.getCommande2().setVisible(false);
		ihmjeu.remove(ihmjeu.getCommande2());
		ihmjeu.setCommande2(new IHMCommandeVue2(ihmjeu,this));
		ihmjeu.getContentPane().add(ihmjeu.getCommande2(),BorderLayout.SOUTH);
		ihmjeu.afficherTerminal("Selectionner un Personnage");
		this.setPhase(Phase.SELECTION_PERSONNAGE);
	}
	
	/**
	 * Routine graphique permettant de ne plus voir les cases où l'on peut effectuer l'action
	 * @param idActionSelectionné action à effectuer
	 */
	public void IhmFinVoirCasePossibleAction(int idActionSelectionné) {
		for(int[] pos:this.VoisinageCaseAction(idActionSelectionné)){
			if(this.getCase(pos).estVide())
				ihmjeu.getGuiCase(pos).setBackground(Color.WHITE);
			else if(this.comparepositionCaseSelectionné(personnageSelectionné.getPosition(), pos))
				ihmjeu.getGuiCase(pos).setBackground(Color.YELLOW);
			else if(this.joueur1.possedePersonnage(this.getCase(pos).getPersonnage()))
				ihmjeu.getGuiCase(pos).setBackground(this.joueur1.getColor());
			else if(this.joueur2.possedePersonnage(this.getCase(pos).getPersonnage()))
				ihmjeu.getGuiCase(pos).setBackground(this.joueur2.getColor());
		}
	}

	/**
	 * Routine graphique pour ne plus voir les cases où l'on peut se déplacer le personnage selectionné
	 */
	public void IhmFinVoirCasePossibleDeplacement() {
		for(int[] pos : this.VoisinageCaseDeplacement(personnageSelectionné)){
			ihmjeu.getGuiCase(pos).setBackground(Color.WHITE);
		}
	}
	
	/**
	 * Routine graphique pour ne plus voir les cases où l'on peut se placer le personnage selectionné
	 */
	public void IhmFinVoirCasePossiblePlacement() {
		for(int i=0;i<TaillePlateauX;i++){
			for(int j=0;j<TaillePlateauY;j++){
				int[] positionCaseSelectionné = {i,j};
				if(getJoueurCourant()==joueur1 && plateau.caseEstVide(positionCaseSelectionné) && i<TaillePlateauX/2)
					ihmjeu.getGuiCase(positionCaseSelectionné).setBackground(Color.WHITE);
				else if(getJoueurCourant()==joueur2 && plateau.caseEstVide(positionCaseSelectionné) && i>=(TaillePlateauX-TaillePlateauX/2))
					ihmjeu.getGuiCase(positionCaseSelectionné).setBackground(Color.WHITE);
			}
		}
	}
	/**
	 * Routine graphique pour le placement des personnages
	 */
	public void IhmPlacementPersonnage(){
		try {
			this.placerPersonnage(getJoueurCourant(), IdPersonnageSelectionné,this.positionCaseSelectionné);
			this.IhmFinVoirCasePossiblePlacement();
			ihmjeu.getCommande1().placementReussi(IdPersonnageSelectionné);
			ihmjeu.getCommande1().boutonActive(IdPersonnageSelectionné);
		} catch (CaseNonVide e) {
			JOptionPane.showMessageDialog(null, "La case n'est pas vide", e.toString(),
                    JOptionPane.ERROR_MESSAGE);
			
		}
		catch (CaseAdverse e) {
			JOptionPane.showMessageDialog(null, "La case selectionné est dans le champs adverse", e.toString(),
                    JOptionPane.ERROR_MESSAGE);
		}
		
		if(_countjoueur<1 && ihmjeu.getCommande1().finPlacement()){
			_countjoueur++;
			ihmjeu.remove(ihmjeu.getCommande1());
			this.joueurSuivant();
			ihmjeu.setCommande1(new IHMCommandeVue1(this,getJoueurCourant()));
			ihmjeu.getContentPane().add(ihmjeu.getCommande1(),BorderLayout.SOUTH);
			ihmjeu.afficherTerminal("Joueur "+this.getJoueurCourant().getNom()+" : Placer vos personnages sur le plateau");
	        
		}
		else if(_countjoueur==1 && ihmjeu.getCommande1().finPlacement()){
			this.hasPlacé();
			this.joueurSuivant();
			this.setPhase(Phase.SELECTION_PERSONNAGE);
			ihmjeu.remove(ihmjeu.getCommande1());
			ihmjeu.setCommande2(new IHMCommandeVue2(ihmjeu,this));
			ihmjeu.getContentPane().add(ihmjeu.getCommande2(),BorderLayout.SOUTH);
			ihmjeu.afficherTerminal("Selectionner un Personnage");
		}
	}
	
	/**
	 * Routine graphique pour la selection des personnages
	 */
	public void IhmSelectionPersonnage(){
			try{
				personnageSelectionné=this.choisirPersonnage(positionCaseSelectionné);
				ihmjeu.getGuiCase(positionCaseSelectionné).setBackground(Color.YELLOW);
				this.setPhase(Phase.NORMAL);
				ihmjeu.getCommande2().boutonActive();
				ihmjeu.afficherTerminal("Selectionner une Commande");
			}
			catch(CaseVide e){
				ihmjeu.afficherTerminal("Erreur, la case selectionné est vide. Selectionner un Personnage");
			}
			catch(PersonnageAdverse e){
				ihmjeu.afficherTerminal("Erreur, la personnage selectionné n'est pas le votre. Selectionner un Personnage");
			}
		}
	/**
	 * Routine graphique permettant de  voir les cases où l'on peut effectuer l'action
	 * @param idActionSelectionné action à effectuer
	 */
	public void IhmVoirCasePossibleAction(int idActionSelectionné) {
		for(int[] pos:this.VoisinageCaseAction(idActionSelectionné))
			ihmjeu.getGuiCase(pos).setBackground(Color.RED);
	}
	/**
	 * Routine graphique pour  voir les cases où l'on peut se déplacer le personnage selectionné
	 */
	public void IhmVoirCasePossibleDeplacement() {
		for(int[] pos : this.VoisinageCaseDeplacement(personnageSelectionné)){
			ihmjeu.getGuiCase(pos).setBackground(Color.GREEN);
		}
	}
	/**
	 * Routine graphique pour  voir les cases où l'on peut se placer le personnage selectionné
	 */
	public void IhmVoirCasePossiblePlacement() {
		for(int i=0;i<TaillePlateauX;i++){
			for(int j=0;j<TaillePlateauY;j++){
				int[] positionCaseSelectionné = {i,j};
				if(getJoueurCourant()==joueur1 && plateau.caseEstVide(positionCaseSelectionné) && i<TaillePlateauX/2)
					ihmjeu.getGuiCase(positionCaseSelectionné).setBackground(Color.GREEN);
				else if(getJoueurCourant()==joueur2 && plateau.caseEstVide(positionCaseSelectionné) && i>=(TaillePlateauX-TaillePlateauX/2))
					ihmjeu.getGuiCase(positionCaseSelectionné).setBackground(Color.GREEN);
			}
		}
	}
	/**
	 * Inialise le plateau du jeu
	 */
	public void init() {
		plateau = new Plateau(TaillePlateauX,TaillePlateauY);
		this.tirageAuSort();
		JOptionPane.showMessageDialog(null, "Le joueur qui va débuté est le Joueur :"+this.getJoueurCourant().getNom(), "Tirage au Sort",
                JOptionPane.NO_OPTION);
		this.setPhase(Phase.NORMAL);
		ihmjeu=new IHMJeu(this);
		this.lierCaseInterface();
	}
	/**
	 * Retourne un booléen si l'action est finie
	 * @return <code>true</code> si l'action est finie; <code>false</code> sinon
	 */
	public boolean isAction() {
		return tour.isAction();
	}
	/**
	 * Retourne un booléen si le deplacement est finie
	 * @return <code>true</code> si le deplacement est finie; <code>false</code> sinon
	 */
	public boolean isDéplacé() {
		return tour.isDéplacé();
	}
	/**
	 * Retourne un booléen si l'action est multiple
	 * @return <code>true</code> si l'action  est multiple; <code>false</code> sinon
	 */
	public boolean isMultipleAction(){
		return tour.isMultipleAction();
	}
	/**
	 * Retourne un booléen si le placement est finie
	 * @return <code>true</code> si le placement est finie; <code>false</code> sinon
	 */
	public boolean isPlacé() {
		return tour.isPlacé();
	}
	
	/**
	 * Met à jour le joueur courant et le retourne
	 * @return joueur suivant
	 */
	public Joueur joueurSuivant(){
		Joueur j= this.getJoueurCourant();
		if(j==joueur1){
			 tour.setJoueurCourant(joueur2);
			 return joueur2;
		}
		else{
			tour.setJoueurCourant(joueur1);
			return joueur1;
		}
	}
	
	/**
	 * Lie les cases avec les IHMCase (case graphique JButton)
	 */
	public void lierCaseInterface() {
		for(int i=0;i<TaillePlateauX;i++){
			for(int j=0;j<TaillePlateauY;j++){
				int[] positionCaseSelectionné = {i,j};
				Case c = plateau.getCase(positionCaseSelectionné);
				IHMCase ie=ihmjeu.getGuiCase(positionCaseSelectionné);
				c.addObserver(ie);
			}
		}
	}
	
	/**
	 * Ajoutes la liste des personnages
	 */
	public void listePersonnages(){
		personnages.add(Magicien.class);
		personnages.add(CavalierCeleste.class);
		personnages.add(Guerrier.class);
		personnages.add(Voleur.class);
	}
	
	/**
	 * Met à jour l'état des personnages de l'équipe du joueur courant
	 */
	public void majEtatPersonnage(){
		int numTour = getTour().getNumeroTour();
		for(Personnage p:getJoueurCourant().getEquipe()){
			if(p.isRalenti() && p.getnumTourRalenti()<=numTour)
				p.hasNormal();
			if(p.hasDefense() && p.getnumTourDefense()<=numTour )
				p.finDefense();
			
		}
			
	}
	
	/**
	 * Met à jour les personnages s'ils sont KO
	 */
	public void MAJPVPersonnage(){
		for(Personnage p:joueur1.getEquipe()){
			if(p.isPointVieEgal0() &&!p.isKO()){
				p.setKO();
				Case c=plateau.getCase(p.getPosition());
				c.liberer();
			}
				
		}
		
		for(Personnage p:joueur2.getEquipe()){
			if(p.isPointVieEgal0() &&!p.isKO()){
				p.setKO();
				Case c=plateau.getCase(p.getPosition());
				c.liberer();
			}
				
		}
	}
	
	/**
	 * Utilise l'action du personnage selectionné
	 * @param p personnage selectionné
	 * @param idActionSelectionné action selectionné  
	 * @param positionCaseSelectionné position de la case où l'on veut lancer l'attaque
	 */
	public void personnageAction(Personnage p,int idActionSelectionné,int[] positionCaseSelectionné){
			Class<?> c =p.getAction(idActionSelectionné);
			typePortee tp;
			try{
				Constructor<?> constructeur = 
	                     c.getConstructor (new Class [] {Personnage.class,Jeu.class});
				tp= (typePortee) c.getMethod("getTypePortee").invoke(c);
				if(tp==typePortee.CIBLE){
					for(int[] tc:this.VoisinageCaseZoneCible(positionCaseSelectionné)){
						if(plateau.caseGetPersonnage(tc)!=null)
							constructeur.newInstance (new Object [] {plateau.caseGetPersonnage(tc),this});
					}
				}
				else if(plateau.caseGetPersonnage(positionCaseSelectionné)!=null){
					constructeur.newInstance (new Object [] {plateau.caseGetPersonnage(positionCaseSelectionné),this});
			
				}
				
			}
			catch(Exception e){}
		

	}
	/**
	 * Place un personnage sur le plateau
	 * @param j joueur qui va placer les personnages 
	 * @param idPersonnage numero du personnage dans l'équipe du joueur
	 * @param positionCaseSelectionné position de la case à placer le joueur
	 * @throws CaseNonVide Exception
	 * @throws CaseAdverse Exception
	 */
	public void placerPersonnage(Joueur j,int idPersonnage,int[] positionCaseSelectionné) throws CaseNonVide,CaseAdverse{
		if(plateau.caseEstVide(positionCaseSelectionné)) {
			if(j==joueur1 && positionCaseSelectionné[0]<TaillePlateauX/2 ||j==joueur2 && positionCaseSelectionné[0]>=(TaillePlateauX-TaillePlateauX/2)){
			Personnage p=j.getPersonnage(idPersonnage);
			p.setPosition(positionCaseSelectionné);
			plateau.poser(p,positionCaseSelectionné);
			}
			else throw new CaseAdverse();
		}
		else{
			throw new CaseNonVide();
		}
	}
	
	/**
	 * Effectue une sauvegarde de la partie actuelle 
	 */
	public  void sauvegarder() {

		final Jeu tempjeu = this;
		ObjectOutputStream oos = null;
		try {
			final FileOutputStream fichier = new FileOutputStream("jeu.ser");
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(tempjeu);
			oos.flush();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (oos != null) {
						oos.flush();
						oos.close();
					}
				} catch (final IOException ex) {
					ihmjeu.afficherTerminal("Sauvegarde Impossible");
				}
			}
	}

	/**
	 * Met l'id de l'action selectionné
	 * @param idActionSelectionné numéro de l'action selectionné
	 */
	public void setidActionSelectionné(int idActionSelectionné) {
		this.idActionSelectionné = idActionSelectionné;
	}
	
	/**
	 * Met le nombre de perso par équipe
	 * @param perso nb perso par équipe
	 */
	public  void setNbPerso(int perso){
		nbPersonnagesEquipe = perso;
	}
	/**
	 * Met le nom du joueur
	 * @param j joueur à mettre le nom
	 * @param nom futur nom du joueur
	 */
	public void setnomjoueur(Joueur j,String nom){
		j.setNom(nom);
	}
	/**
	 * Met le personnage selectionné 
	 * @param personnageId nouveau personnage selectionné
	 */
	public void setPersonnageSelectionné(int personnageId) { 
		IdPersonnageSelectionné=personnageId;
		
	}
	
	/**
	 * Met la phase du jeu
	 * @param _phase nouvelle phase du jeu
	 */
	public void setPhase(Phase _phase){
		phase =_phase;
	}
	/**
	 * Met la position de la case selectionné 
	 * @param pos position de la case selectionné
	 */
	public void setpositionCaseSelectionné(int[] pos){
		positionCaseSelectionné=pos;
	}
	
	/**
	 * Effectue un tirage au sort
	 */
	public void tirageAuSort(){
		int random = (int)(Math.random()*2);
		if(random==0){
			tour=new Tour(joueur1);
		}
		else{
			tour=new Tour(joueur2);
		}
	}
	/**
	 * Retourne un booléen si l'equipe du joueur est KO
	 * @param j joueur à verifier
	 * @return <code>true</code> si l'équipe est KO; <code>false</code> sinon
	 */
	public boolean verifieEquipeKO(Joueur j){
		for(Personnage p:j.getEquipe()){
			if(!p.isKO())
				return false;
		}
		return true;
	  }
	
	/**
	 * Verifie si un des deux joueurs gagne la partie
	 */
	public void verifiegagner(){
		Joueur j;
		if(this.verifieEquipeKO(joueur1))
			j=joueur2;
		else if(this.verifieEquipeKO(joueur2))	
			j=joueur1;
		else
			j=null;
		if(j!=null){
			JOptionPane.showMessageDialog(null, "Bravo le joueur "+j.getNom()+" a gagner la partie", "Fin de la Partie",
	                JOptionPane.NO_OPTION,new ImageIcon("image/gagner.png"));
			Object[] options = {"Recommencer une nouvelle partie"," Quitter le jeu"};
			int confirm = JOptionPane.showOptionDialog(null, "Que voulez vous faire ?", "Quitter le jeu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,options[1]);
			if(confirm ==1){
				System.exit(0);
			}
			else if(confirm ==0){
				ihmjeu.dispose();
				new Jeu();
			}
		}
		
	}
	
	/**
	 * Verifie si l'action est utilisable sur la case selectionné
	 * @param idActionSelectionné action selectionné
	 * @param positionCaseSelectionné position de la case selectionné
	 * @return @return <code>true</code> si l'action est utilisable sur la case
	 * @throws ActionNonSelectionne Exception 
	 * @throws PorteeActionInsuffisante Exception
	 */
	public boolean verifierCaseAction(int idActionSelectionné,int[] positionCaseSelectionné) throws ActionNonSelectionne,PorteeActionInsuffisante{
		if(idActionSelectionné==-1)
			throw new ActionNonSelectionne();
		for(int[] pos:this.VoisinageCaseAction(idActionSelectionné)){
			if(this.comparepositionCaseSelectionné(positionCaseSelectionné,pos)){
				return true;
			}
		}
		throw new PorteeActionInsuffisante();
	}
	
	/**
	 * Verifie si le deplacement du personnage selectionné est possible sur la case selectionné
	 * @param p personnage selectionné
	 * @param positionCaseSelectionné position de la case selectionné
	 * @return <code>true</code> si le deplacement est utilisable sur la case
	 * @throws DeplacementImpossible Exception
	 */
	public boolean verifierCaseDeplacement(Personnage p,int[] positionCaseSelectionné) throws DeplacementImpossible{
		for(int[] pos:this.VoisinageCaseDeplacement(p)){
			if(this.comparepositionCaseSelectionné(positionCaseSelectionné,pos)){
				return true;
			}
		}
		throw new DeplacementImpossible();
	}

	/**
	 * Verifie et Met à jour le nombre de personnages par équipe
	 * @param n nombre de personnage par équipe
	 * @throws NbPersonnageIncorrect Exception
	 */
	public void verifiernbPersonnagesEquipe(int n) throws NbPersonnageIncorrect {
		if (n>0 && n<=5){
			this.setNbPerso(n);
		}
		else {
			throw new NbPersonnageIncorrect();			
		}
	}
	/**
	 * Verifie et Met à jour la taille du plateau
	 * @param X taille X du plateau (largeur)
	 * @param Y taille Y du plateau (longueur)
	 * @throws TaillePlateauIncorrect Exception
	 */
	public void verifierTaille(int X, int Y)throws TaillePlateauIncorrect{
		if(X>=5 && X<=10 && Y>=10 && Y<=15 ){
			this.fixerTaillePlateau(X, Y);	
		}
		else {
			throw new TaillePlateauIncorrect();			
		}
	}
	
	/**
	 * Affiche le catalogue à un joueur
	 * @param joueur joueur voulant voir le catalogue
	 */
	public void voirCatalogue(Joueur joueur){
		new Catalogue(this,joueur);
	}
	/**
	 * Voir les informations des personnages sur les bandeaux des joueurs
	 * @param positionCaseSelectionné position de la case selectionné
	 */
	public void voirInfoPersonnage(int[] positionCaseSelectionné){
		Personnage p = plateau.caseGetPersonnage(positionCaseSelectionné);
		if(p!=null){
			if(joueur1.possedePersonnage(p))
				ihmjeu.getBandeau1().getInfo().miseAJour(p);
			else if(joueur2.possedePersonnage(p))
				ihmjeu.getBandeau2().getInfo().miseAJour(p);
		}
	}
	
	/**
	 * Retourne les cases où l'on peut utiliser l'action pour  le personnage selectionné
	 * @param idActionSelectionné numéro de l'action selectionné
	 * @return les cases où l'on peut utiliser l'action pour  le personnage selectionné
	 */
	public ArrayList<int[]> VoisinageCaseAction(int idActionSelectionné){
		boolean[] obstacle={false,false,false,false};
		ArrayList<int[]> voisinage = new ArrayList<int[]>();
		Class<?> c =personnageSelectionné.getAction(idActionSelectionné);
		int pmMin=0;
		int pmMax=0;
		Action.typePortee tp;
		try {
			tp =  (typePortee) c.getMethod("getTypePortee").invoke(c);
			pmMin = (Integer) c.getMethod("getPorteeMin").invoke(c);
			if(tp==Action.typePortee.NORMAL)
				pmMax = (Integer) c.getMethod("getPorteeMax").invoke(c);
			else if(tp==Action.typePortee.CIBLE)
				pmMax = Math.min(TaillePlateauX,TaillePlateauY)/2;
			else if(tp==Action.typePortee.SELF){
				voisinage.add(personnageSelectionné.getPosition());
				return voisinage;
			}
			int x=personnageSelectionné.getPosition()[0];
			int y=personnageSelectionné.getPosition()[1];
			for(int i=1;i<=pmMax;i++){
				if(x-i>=0){
					int[] positionCaseSelectionné ={x-i,y};
					if(!obstacle[0]&& !this.getCase(positionCaseSelectionné).estVide()){
						obstacle[0]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(!obstacle[0] && this.getCase(positionCaseSelectionné).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(personnageSelectionné.getSaitVoler() && obstacle[0]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
				}
				
				if(x+i<TaillePlateauX){
					int[] positionCaseSelectionné ={x+i,y};
					if(!obstacle[1]&& !this.getCase(positionCaseSelectionné).estVide()){
						obstacle[1]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(!obstacle[1] && this.getCase(positionCaseSelectionné).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(personnageSelectionné.getSaitVoler() && obstacle[1]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
				}
				
				if(y-i>=0){
					int[] positionCaseSelectionné ={x,y-i};
					if(!obstacle[2]&& !this.getCase(positionCaseSelectionné).estVide()){
						obstacle[2]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(!obstacle[2] && this.getCase(positionCaseSelectionné).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(personnageSelectionné.getSaitVoler() && obstacle[2]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
				}
				
				if(y+i<TaillePlateauY){
					int[] positionCaseSelectionné ={x,y+i};
					if(!obstacle[3]&& !this.getCase(positionCaseSelectionné).estVide()){
						obstacle[3]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(!obstacle[3] && this.getCase(positionCaseSelectionné).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
					else if(personnageSelectionné.getSaitVoler() && obstacle[3]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionné);}
					}
				}
				
				
			}
		} catch (Exception e) {}
		return voisinage;
	}
	
	/**
	 * Retourne les cases où l'on peut deplacer  le personnage selectionné
	 * @param p personnage selectionné
	 * @return les cases où l'on peut deplacer  le personnage selectionné
	 */
	public ArrayList<int[]> VoisinageCaseDeplacement(Personnage p){
		boolean[] obstacle={false,false,false,false,false,false,false,false};
		ArrayList<int[]> voisinage = new ArrayList<int[]>();
		int x=p.getPosition()[0];
		int y=p.getPosition()[1];
		int pm=p.getPointMouvement();
		int pmd=p.getPointMouvementDiagonale();
		
		for(int i=1;i<=pm;i++){
			if(x-i>=0){
				int[] positionCaseSelectionné ={x-i,y};
				if(!obstacle[0]&& !this.getCase(positionCaseSelectionné).estVide() )
					obstacle[0]=true;
				else if(!obstacle[0] && this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
				else if(p.getSaitVoler() && obstacle[0] &&this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
			}
			
			if(x+i<TaillePlateauX){
				int[] positionCaseSelectionné ={x+i,y};
				if(!obstacle[1]&& !this.getCase(positionCaseSelectionné).estVide() )
					obstacle[1]=true;
				else if(!obstacle[1] && this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
				else if(p.getSaitVoler() && obstacle[1] &&this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
			}
			
			if(y-i>=0){
				int[] positionCaseSelectionné ={x,y-i};
				if(!obstacle[2]&& !this.getCase(positionCaseSelectionné).estVide() )
					obstacle[2]=true;
				else if(!obstacle[2] && this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
				else if(p.getSaitVoler() && obstacle[2] &&this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
			}
			
			if(y+i<TaillePlateauY){
				int[] positionCaseSelectionné ={x,y+i};
				if(!obstacle[3]&& !this.getCase(positionCaseSelectionné).estVide() )
					obstacle[3]=true;
				else if(!obstacle[3] && this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
				else if(p.getSaitVoler() && obstacle[3] &&this.getCase(positionCaseSelectionné).estVide())
					voisinage.add(positionCaseSelectionné);
			}
			
		}
		
		if(pmd!=0){
		for(int i=1;i<=pmd;i++){
		
			int[] positionCaseSelectionné0 ={x-i,y-i};
			int[] positionCaseSelectionné1 ={x-i,y+i};
			int[] positionCaseSelectionné2 ={x+i,y+i};
			int[] positionCaseSelectionné3 ={x+i,y-i};
			if(x-i>=0 && y-i>=0){
			if(!obstacle[4] && this.getCase(positionCaseSelectionné0).estVide())
				voisinage.add(positionCaseSelectionné0);
			else if(!obstacle[4]&&!this.getCase(positionCaseSelectionné0).estVide())
				obstacle[4]=true;
			else if(obstacle[4]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionné0).estVide())
				voisinage.add(positionCaseSelectionné0);
			}
			if(x-i>=0 && y+i<TaillePlateauY){
			if(!obstacle[5] && this.getCase(positionCaseSelectionné1).estVide())
				voisinage.add(positionCaseSelectionné1);
			else if(!obstacle[5]&&!this.getCase(positionCaseSelectionné1).estVide())
				obstacle[5]=true;
			else if(obstacle[5]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionné1).estVide())
				voisinage.add(positionCaseSelectionné1);
			}
			if(x+i<TaillePlateauX && y+i<TaillePlateauY){
			if(!obstacle[6] && this.getCase(positionCaseSelectionné2).estVide())
				voisinage.add(positionCaseSelectionné2);
			else if(!obstacle[6]&&!this.getCase(positionCaseSelectionné2).estVide())
				obstacle[6]=true;
			else if(obstacle[6]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionné2).estVide())
				voisinage.add(positionCaseSelectionné2);
			}
			if(x+i<TaillePlateauX && y-i>=0){
			if(!obstacle[7] && this.getCase(positionCaseSelectionné3).estVide())
				voisinage.add(positionCaseSelectionné3);
			else if(!obstacle[7]&&!this.getCase(positionCaseSelectionné3).estVide())
				obstacle[7]=true;
			else if(obstacle[7]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionné3).estVide())
				voisinage.add(positionCaseSelectionné3);
			}
			
			}
		}
	return voisinage;
}
	/**
	 * Retourne les cases autour d'une case ciblé
	 * @param positionCaseSelectionné position de la case ciblé 
	 * @return les cases autour d'une case ciblé
	 */
	public ArrayList<int[]> VoisinageCaseZoneCible(int[] positionCaseSelectionné){
		ArrayList<int[]> voisinage =new ArrayList<int[]>();
		int x = positionCaseSelectionné[0]-1;
		int y = positionCaseSelectionné[1]-1;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				int[] pos = {x+i,y+j};
				if(x+i>=0 && x+i<TaillePlateauX && y+j>=0 && y+j<TaillePlateauY)
					voisinage.add(pos);
			}
		}
		return voisinage;
	}

}
