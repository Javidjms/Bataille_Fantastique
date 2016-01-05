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
 * Cette classe repr�sente le controleur Jeu
 */
public class Jeu implements Serializable {
	/**
	 * Le type enumere pour indiquer la phase actuelle du jeu 
	 */
	public enum Phase {ACTION_PERSONNAGE,DEPLACEMENT_PERSONNAGE,NORMAL,
						PLACEMENT_PERSONNAGE,SELECTION_PERSONNAGE}
	private int _countjoueur=0;
	private int idActionSelectionn�=-1;
	private int IdPersonnageSelectionn�=0;
	private IHMJeu ihmjeu;
	private Joueur joueur1,joueur2; 
	private int nbPersonnagesEquipe;
	private ArrayList<Class> personnages=new ArrayList<Class>();
	
	private Personnage personnageSelectionn�;
	private Phase phase=Phase.NORMAL;
	private Plateau plateau;
	private int[] positionCaseSelectionn�;
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
	 * Constructeur non officielle mais utile pour le TestAvanc�
	 * @param s String Test
	 */
	public Jeu(String s){
		//Test Avanc�
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
	 * Ajoute un personnage dans l'�quipe du joueur s�lectionn�
	 * @param j joueur qui s�lectionne
	 * @param p Classe du personnage selectionn�
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void ajouterPersonnage(Joueur j,Class<?> p) throws InstantiationException, IllegalAccessException{
		j.ajouterPersonnage((Personnage)p.newInstance());
	}
	
	/**
	 * Retourne le personnage pr�sent sur cette case
	 * @param positionCaseSelectionn� position de la case du personnage � recuperer 
	 * @return le personnage de cette case
	 */
	public Personnage caseGetPersonnage(int[] positionCaseSelectionn�){
		return plateau.caseGetPersonnage(positionCaseSelectionn�);
	}
	
	/**
	 * Charge une sauvegarde pr�alablement enregistr�e
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
	 * @param positionCaseSelectionn� position de la case du personnage selectionn�
	 * @return le personnage selectionn�
	 * @throws CaseVide Exception
	 * @throws PersonnageAdverse Exception
	 */
	public Personnage choisirPersonnage(int[] positionCaseSelectionn�) throws CaseVide,PersonnageAdverse{
		Personnage p =this.caseGetPersonnage(positionCaseSelectionn�);
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
	 * Retourne un bool�en si les deux positions sont �gales
	 * @param positionCaseSelectionn�1 position � comparer
	 * @param positionCaseSelectionn�2 position � comparer
	 * @return <code>true</code> si les positions sont �gales; <code>false</code> sinon
	 */
	public boolean comparepositionCaseSelectionn�(int[] positionCaseSelectionn�1,int[] positionCaseSelectionn�2){
		int x1=positionCaseSelectionn�1[0];int x2=positionCaseSelectionn�2[0];
		int y1=positionCaseSelectionn�1[1];int y2=positionCaseSelectionn�2[1];
		return(x1==x2 &&y1==y2);
	}
	
	/**
	 * Deplace un personnage
	 * @param p personnage � d�placer
	 * @param positionCaseSelectionn� nouvelle position du personnage � d�placer
	 */
	public void deplacerPersonnage(Personnage p,int[] positionCaseSelectionn�){
		plateau.libererCase(p.getPosition());
		p.deplacer(positionCaseSelectionn�);
		plateau.poser(p, positionCaseSelectionn�);
		this.hasD�plac�();
		
	}
	
	/**
	 * Effacer les informations contenues dans les bandeaux (IHMBandeau)
	 */
	public void finInfoPersonnage(){
		ihmjeu.getBandeau1().getInfo().miseAJour(null);
		ihmjeu.getBandeau2().getInfo().miseAJour(null);
		
	}
	
	/**
	 * Retourne un bool�en si l'attaque multiple est finie
	 * @return <code>true</code> si l'attaque multiple est finie; <code>false</code> sinon
	 */
	public boolean finMultipleAction(){
		return tour.finMultipleAction();
	}
	
	/**
	 * Finit le tour actuelle, met � jour les flags des personnages et Passe la main
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
	 * @param positionCaseSelectionn� position de la case voulue
	 * @return case � la position voulue
	 */
	public Case getCase(int[] positionCaseSelectionn�) {
		return this.plateau.getCase(positionCaseSelectionn�);
	}

	/**
	 * Retourne le numero de l'action selectionn�
	 * @return le numero de l'action selectionn�
	 */
	public int getidActionSelectionn�() {
		return idActionSelectionn�;
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
	 * Retourne le nombre de personnages par �quipe
	 * @return le nombre de personnages par �quipe
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
	public void hasD�plac�() {
		tour.hasD�plac�();
	}
	/**
	 * Incr�mente le compteur d'action multiple
	 * @param nbMultipleAction nb de repetition possible par tour
	 */
	public void hasMultipleAction(int nbMultipleAction) {
		tour.hasMultipleAction(nbMultipleAction);
		
	}
	/**
	 * Notifie au tour que le placement est finie
	 */
	public void hasPlac�() {
		tour.hasPlac�();
	}
	
	/**
	 * Routine graphique lorsque la commande Action est appuy�
	 */
	public void IhmActionAppuy�() {
		ihmjeu.getCommande2().setVisible(false);
		ihmjeu.remove(ihmjeu.getCommande2());
		ihmjeu.setCommande3(new IHMCommandeVue3(personnageSelectionn�,ihmjeu,this,getJoueurCourant()));
		ihmjeu.getContentPane().add(ihmjeu.getCommande3(), BorderLayout.SOUTH);
		ihmjeu.afficherTerminal("Selectionner une Action");
		this.setPhase(Phase.ACTION_PERSONNAGE);
	}

	/**
	 * Routine graphique de l'action selectionn� d'un personnage
	 */
	public void IhmActionPersonnage(){
		try{
			if(this.verifierCaseAction(idActionSelectionn�, positionCaseSelectionn�)){
				this.personnageAction(personnageSelectionn�,idActionSelectionn�,positionCaseSelectionn�);
				ihmjeu.getCommande2().actionFinie();
				this.IhmFinAction();
				this.hasAction();
				this.IhmFinVoirCasePossibleAction(idActionSelectionn�);
				this.MAJPVPersonnage();
				this.verifiegagner();
			}
		}
		catch(ActionNonSelectionne e){
			ihmjeu.afficherTerminal("Il faut d'abord selectionner une attaque");
		}
		catch(PorteeActionInsuffisante e){
			ihmjeu.afficherTerminal("La port�e de l'attaque est insuffisante pour atteindre cette case");
		}
		
	}
	/**
	 * Routine graphique du deplacement d'un personnage
	 */
	public void IhmDeplacementPersonnage(){
		try{
			if(this.verifierCaseDeplacement(personnageSelectionn�,positionCaseSelectionn�)){
				this.IhmFinVoirCasePossibleDeplacement();
				this.deplacerPersonnage(personnageSelectionn�, positionCaseSelectionn�);
				ihmjeu.getCommande2().deplacementFinie();
			}
			ihmjeu.getGuiCase(positionCaseSelectionn�).setBackground(Color.YELLOW);
			ihmjeu.afficherTerminal("Selectionner une Commande");
			this.setPhase(Phase.NORMAL);
		}
		catch(DeplacementImpossible e){
			ihmjeu.afficherTerminal("Vos points de mouvements ne permettent pas de se deplacer sur cette case");
		}
		
	}
	
	/**
	 * Routine graphique de la d�s�lection d'un personnage
	 */
	public void IhmDeselectionPersonnage(){
		if((!isD�plac�() &&!isAction()&&isPlac�())&&personnageSelectionn�==caseGetPersonnage(positionCaseSelectionn�)){
			ihmjeu.getGuiCase(positionCaseSelectionn�).setBackground(getJoueurCourant().getColor());
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
		ihmjeu.getGuiCase(personnageSelectionn�.getPosition()).setBackground(getJoueurCourant().getColor());
		this.finTour();
		ihmjeu.getCommande2().setVisible(false);
		ihmjeu.remove(ihmjeu.getCommande2());
		ihmjeu.setCommande2(new IHMCommandeVue2(ihmjeu,this));
		ihmjeu.getContentPane().add(ihmjeu.getCommande2(),BorderLayout.SOUTH);
		ihmjeu.afficherTerminal("Selectionner un Personnage");
		this.setPhase(Phase.SELECTION_PERSONNAGE);
	}
	
	/**
	 * Routine graphique permettant de ne plus voir les cases o� l'on peut effectuer l'action
	 * @param idActionSelectionn� action � effectuer
	 */
	public void IhmFinVoirCasePossibleAction(int idActionSelectionn�) {
		for(int[] pos:this.VoisinageCaseAction(idActionSelectionn�)){
			if(this.getCase(pos).estVide())
				ihmjeu.getGuiCase(pos).setBackground(Color.WHITE);
			else if(this.comparepositionCaseSelectionn�(personnageSelectionn�.getPosition(), pos))
				ihmjeu.getGuiCase(pos).setBackground(Color.YELLOW);
			else if(this.joueur1.possedePersonnage(this.getCase(pos).getPersonnage()))
				ihmjeu.getGuiCase(pos).setBackground(this.joueur1.getColor());
			else if(this.joueur2.possedePersonnage(this.getCase(pos).getPersonnage()))
				ihmjeu.getGuiCase(pos).setBackground(this.joueur2.getColor());
		}
	}

	/**
	 * Routine graphique pour ne plus voir les cases o� l'on peut se d�placer le personnage selectionn�
	 */
	public void IhmFinVoirCasePossibleDeplacement() {
		for(int[] pos : this.VoisinageCaseDeplacement(personnageSelectionn�)){
			ihmjeu.getGuiCase(pos).setBackground(Color.WHITE);
		}
	}
	
	/**
	 * Routine graphique pour ne plus voir les cases o� l'on peut se placer le personnage selectionn�
	 */
	public void IhmFinVoirCasePossiblePlacement() {
		for(int i=0;i<TaillePlateauX;i++){
			for(int j=0;j<TaillePlateauY;j++){
				int[] positionCaseSelectionn� = {i,j};
				if(getJoueurCourant()==joueur1 && plateau.caseEstVide(positionCaseSelectionn�) && i<TaillePlateauX/2)
					ihmjeu.getGuiCase(positionCaseSelectionn�).setBackground(Color.WHITE);
				else if(getJoueurCourant()==joueur2 && plateau.caseEstVide(positionCaseSelectionn�) && i>=(TaillePlateauX-TaillePlateauX/2))
					ihmjeu.getGuiCase(positionCaseSelectionn�).setBackground(Color.WHITE);
			}
		}
	}
	/**
	 * Routine graphique pour le placement des personnages
	 */
	public void IhmPlacementPersonnage(){
		try {
			this.placerPersonnage(getJoueurCourant(), IdPersonnageSelectionn�,this.positionCaseSelectionn�);
			this.IhmFinVoirCasePossiblePlacement();
			ihmjeu.getCommande1().placementReussi(IdPersonnageSelectionn�);
			ihmjeu.getCommande1().boutonActive(IdPersonnageSelectionn�);
		} catch (CaseNonVide e) {
			JOptionPane.showMessageDialog(null, "La case n'est pas vide", e.toString(),
                    JOptionPane.ERROR_MESSAGE);
			
		}
		catch (CaseAdverse e) {
			JOptionPane.showMessageDialog(null, "La case selectionn� est dans le champs adverse", e.toString(),
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
			this.hasPlac�();
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
				personnageSelectionn�=this.choisirPersonnage(positionCaseSelectionn�);
				ihmjeu.getGuiCase(positionCaseSelectionn�).setBackground(Color.YELLOW);
				this.setPhase(Phase.NORMAL);
				ihmjeu.getCommande2().boutonActive();
				ihmjeu.afficherTerminal("Selectionner une Commande");
			}
			catch(CaseVide e){
				ihmjeu.afficherTerminal("Erreur, la case selectionn� est vide. Selectionner un Personnage");
			}
			catch(PersonnageAdverse e){
				ihmjeu.afficherTerminal("Erreur, la personnage selectionn� n'est pas le votre. Selectionner un Personnage");
			}
		}
	/**
	 * Routine graphique permettant de  voir les cases o� l'on peut effectuer l'action
	 * @param idActionSelectionn� action � effectuer
	 */
	public void IhmVoirCasePossibleAction(int idActionSelectionn�) {
		for(int[] pos:this.VoisinageCaseAction(idActionSelectionn�))
			ihmjeu.getGuiCase(pos).setBackground(Color.RED);
	}
	/**
	 * Routine graphique pour  voir les cases o� l'on peut se d�placer le personnage selectionn�
	 */
	public void IhmVoirCasePossibleDeplacement() {
		for(int[] pos : this.VoisinageCaseDeplacement(personnageSelectionn�)){
			ihmjeu.getGuiCase(pos).setBackground(Color.GREEN);
		}
	}
	/**
	 * Routine graphique pour  voir les cases o� l'on peut se placer le personnage selectionn�
	 */
	public void IhmVoirCasePossiblePlacement() {
		for(int i=0;i<TaillePlateauX;i++){
			for(int j=0;j<TaillePlateauY;j++){
				int[] positionCaseSelectionn� = {i,j};
				if(getJoueurCourant()==joueur1 && plateau.caseEstVide(positionCaseSelectionn�) && i<TaillePlateauX/2)
					ihmjeu.getGuiCase(positionCaseSelectionn�).setBackground(Color.GREEN);
				else if(getJoueurCourant()==joueur2 && plateau.caseEstVide(positionCaseSelectionn�) && i>=(TaillePlateauX-TaillePlateauX/2))
					ihmjeu.getGuiCase(positionCaseSelectionn�).setBackground(Color.GREEN);
			}
		}
	}
	/**
	 * Inialise le plateau du jeu
	 */
	public void init() {
		plateau = new Plateau(TaillePlateauX,TaillePlateauY);
		this.tirageAuSort();
		JOptionPane.showMessageDialog(null, "Le joueur qui va d�but� est le Joueur :"+this.getJoueurCourant().getNom(), "Tirage au Sort",
                JOptionPane.NO_OPTION);
		this.setPhase(Phase.NORMAL);
		ihmjeu=new IHMJeu(this);
		this.lierCaseInterface();
	}
	/**
	 * Retourne un bool�en si l'action est finie
	 * @return <code>true</code> si l'action est finie; <code>false</code> sinon
	 */
	public boolean isAction() {
		return tour.isAction();
	}
	/**
	 * Retourne un bool�en si le deplacement est finie
	 * @return <code>true</code> si le deplacement est finie; <code>false</code> sinon
	 */
	public boolean isD�plac�() {
		return tour.isD�plac�();
	}
	/**
	 * Retourne un bool�en si l'action est multiple
	 * @return <code>true</code> si l'action  est multiple; <code>false</code> sinon
	 */
	public boolean isMultipleAction(){
		return tour.isMultipleAction();
	}
	/**
	 * Retourne un bool�en si le placement est finie
	 * @return <code>true</code> si le placement est finie; <code>false</code> sinon
	 */
	public boolean isPlac�() {
		return tour.isPlac�();
	}
	
	/**
	 * Met � jour le joueur courant et le retourne
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
				int[] positionCaseSelectionn� = {i,j};
				Case c = plateau.getCase(positionCaseSelectionn�);
				IHMCase ie=ihmjeu.getGuiCase(positionCaseSelectionn�);
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
	 * Met � jour l'�tat des personnages de l'�quipe du joueur courant
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
	 * Met � jour les personnages s'ils sont KO
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
	 * Utilise l'action du personnage selectionn�
	 * @param p personnage selectionn�
	 * @param idActionSelectionn� action selectionn�  
	 * @param positionCaseSelectionn� position de la case o� l'on veut lancer l'attaque
	 */
	public void personnageAction(Personnage p,int idActionSelectionn�,int[] positionCaseSelectionn�){
			Class<?> c =p.getAction(idActionSelectionn�);
			typePortee tp;
			try{
				Constructor<?> constructeur = 
	                     c.getConstructor (new Class [] {Personnage.class,Jeu.class});
				tp= (typePortee) c.getMethod("getTypePortee").invoke(c);
				if(tp==typePortee.CIBLE){
					for(int[] tc:this.VoisinageCaseZoneCible(positionCaseSelectionn�)){
						if(plateau.caseGetPersonnage(tc)!=null)
							constructeur.newInstance (new Object [] {plateau.caseGetPersonnage(tc),this});
					}
				}
				else if(plateau.caseGetPersonnage(positionCaseSelectionn�)!=null){
					constructeur.newInstance (new Object [] {plateau.caseGetPersonnage(positionCaseSelectionn�),this});
			
				}
				
			}
			catch(Exception e){}
		

	}
	/**
	 * Place un personnage sur le plateau
	 * @param j joueur qui va placer les personnages 
	 * @param idPersonnage numero du personnage dans l'�quipe du joueur
	 * @param positionCaseSelectionn� position de la case � placer le joueur
	 * @throws CaseNonVide Exception
	 * @throws CaseAdverse Exception
	 */
	public void placerPersonnage(Joueur j,int idPersonnage,int[] positionCaseSelectionn�) throws CaseNonVide,CaseAdverse{
		if(plateau.caseEstVide(positionCaseSelectionn�)) {
			if(j==joueur1 && positionCaseSelectionn�[0]<TaillePlateauX/2 ||j==joueur2 && positionCaseSelectionn�[0]>=(TaillePlateauX-TaillePlateauX/2)){
			Personnage p=j.getPersonnage(idPersonnage);
			p.setPosition(positionCaseSelectionn�);
			plateau.poser(p,positionCaseSelectionn�);
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
	 * Met l'id de l'action selectionn�
	 * @param idActionSelectionn� num�ro de l'action selectionn�
	 */
	public void setidActionSelectionn�(int idActionSelectionn�) {
		this.idActionSelectionn� = idActionSelectionn�;
	}
	
	/**
	 * Met le nombre de perso par �quipe
	 * @param perso nb perso par �quipe
	 */
	public  void setNbPerso(int perso){
		nbPersonnagesEquipe = perso;
	}
	/**
	 * Met le nom du joueur
	 * @param j joueur � mettre le nom
	 * @param nom futur nom du joueur
	 */
	public void setnomjoueur(Joueur j,String nom){
		j.setNom(nom);
	}
	/**
	 * Met le personnage selectionn� 
	 * @param personnageId nouveau personnage selectionn�
	 */
	public void setPersonnageSelectionn�(int personnageId) { 
		IdPersonnageSelectionn�=personnageId;
		
	}
	
	/**
	 * Met la phase du jeu
	 * @param _phase nouvelle phase du jeu
	 */
	public void setPhase(Phase _phase){
		phase =_phase;
	}
	/**
	 * Met la position de la case selectionn� 
	 * @param pos position de la case selectionn�
	 */
	public void setpositionCaseSelectionn�(int[] pos){
		positionCaseSelectionn�=pos;
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
	 * Retourne un bool�en si l'equipe du joueur est KO
	 * @param j joueur � verifier
	 * @return <code>true</code> si l'�quipe est KO; <code>false</code> sinon
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
	 * Verifie si l'action est utilisable sur la case selectionn�
	 * @param idActionSelectionn� action selectionn�
	 * @param positionCaseSelectionn� position de la case selectionn�
	 * @return @return <code>true</code> si l'action est utilisable sur la case
	 * @throws ActionNonSelectionne Exception 
	 * @throws PorteeActionInsuffisante Exception
	 */
	public boolean verifierCaseAction(int idActionSelectionn�,int[] positionCaseSelectionn�) throws ActionNonSelectionne,PorteeActionInsuffisante{
		if(idActionSelectionn�==-1)
			throw new ActionNonSelectionne();
		for(int[] pos:this.VoisinageCaseAction(idActionSelectionn�)){
			if(this.comparepositionCaseSelectionn�(positionCaseSelectionn�,pos)){
				return true;
			}
		}
		throw new PorteeActionInsuffisante();
	}
	
	/**
	 * Verifie si le deplacement du personnage selectionn� est possible sur la case selectionn�
	 * @param p personnage selectionn�
	 * @param positionCaseSelectionn� position de la case selectionn�
	 * @return <code>true</code> si le deplacement est utilisable sur la case
	 * @throws DeplacementImpossible Exception
	 */
	public boolean verifierCaseDeplacement(Personnage p,int[] positionCaseSelectionn�) throws DeplacementImpossible{
		for(int[] pos:this.VoisinageCaseDeplacement(p)){
			if(this.comparepositionCaseSelectionn�(positionCaseSelectionn�,pos)){
				return true;
			}
		}
		throw new DeplacementImpossible();
	}

	/**
	 * Verifie et Met � jour le nombre de personnages par �quipe
	 * @param n nombre de personnage par �quipe
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
	 * Verifie et Met � jour la taille du plateau
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
	 * Affiche le catalogue � un joueur
	 * @param joueur joueur voulant voir le catalogue
	 */
	public void voirCatalogue(Joueur joueur){
		new Catalogue(this,joueur);
	}
	/**
	 * Voir les informations des personnages sur les bandeaux des joueurs
	 * @param positionCaseSelectionn� position de la case selectionn�
	 */
	public void voirInfoPersonnage(int[] positionCaseSelectionn�){
		Personnage p = plateau.caseGetPersonnage(positionCaseSelectionn�);
		if(p!=null){
			if(joueur1.possedePersonnage(p))
				ihmjeu.getBandeau1().getInfo().miseAJour(p);
			else if(joueur2.possedePersonnage(p))
				ihmjeu.getBandeau2().getInfo().miseAJour(p);
		}
	}
	
	/**
	 * Retourne les cases o� l'on peut utiliser l'action pour  le personnage selectionn�
	 * @param idActionSelectionn� num�ro de l'action selectionn�
	 * @return les cases o� l'on peut utiliser l'action pour  le personnage selectionn�
	 */
	public ArrayList<int[]> VoisinageCaseAction(int idActionSelectionn�){
		boolean[] obstacle={false,false,false,false};
		ArrayList<int[]> voisinage = new ArrayList<int[]>();
		Class<?> c =personnageSelectionn�.getAction(idActionSelectionn�);
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
				voisinage.add(personnageSelectionn�.getPosition());
				return voisinage;
			}
			int x=personnageSelectionn�.getPosition()[0];
			int y=personnageSelectionn�.getPosition()[1];
			for(int i=1;i<=pmMax;i++){
				if(x-i>=0){
					int[] positionCaseSelectionn� ={x-i,y};
					if(!obstacle[0]&& !this.getCase(positionCaseSelectionn�).estVide()){
						obstacle[0]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(!obstacle[0] && this.getCase(positionCaseSelectionn�).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(personnageSelectionn�.getSaitVoler() && obstacle[0]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
				}
				
				if(x+i<TaillePlateauX){
					int[] positionCaseSelectionn� ={x+i,y};
					if(!obstacle[1]&& !this.getCase(positionCaseSelectionn�).estVide()){
						obstacle[1]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(!obstacle[1] && this.getCase(positionCaseSelectionn�).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(personnageSelectionn�.getSaitVoler() && obstacle[1]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
				}
				
				if(y-i>=0){
					int[] positionCaseSelectionn� ={x,y-i};
					if(!obstacle[2]&& !this.getCase(positionCaseSelectionn�).estVide()){
						obstacle[2]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(!obstacle[2] && this.getCase(positionCaseSelectionn�).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(personnageSelectionn�.getSaitVoler() && obstacle[2]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
				}
				
				if(y+i<TaillePlateauY){
					int[] positionCaseSelectionn� ={x,y+i};
					if(!obstacle[3]&& !this.getCase(positionCaseSelectionn�).estVide()){
						obstacle[3]=true;
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(!obstacle[3] && this.getCase(positionCaseSelectionn�).estVide()){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
					else if(personnageSelectionn�.getSaitVoler() && obstacle[3]){
						if(i>=pmMin){voisinage.add(positionCaseSelectionn�);}
					}
				}
				
				
			}
		} catch (Exception e) {}
		return voisinage;
	}
	
	/**
	 * Retourne les cases o� l'on peut deplacer  le personnage selectionn�
	 * @param p personnage selectionn�
	 * @return les cases o� l'on peut deplacer  le personnage selectionn�
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
				int[] positionCaseSelectionn� ={x-i,y};
				if(!obstacle[0]&& !this.getCase(positionCaseSelectionn�).estVide() )
					obstacle[0]=true;
				else if(!obstacle[0] && this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
				else if(p.getSaitVoler() && obstacle[0] &&this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
			}
			
			if(x+i<TaillePlateauX){
				int[] positionCaseSelectionn� ={x+i,y};
				if(!obstacle[1]&& !this.getCase(positionCaseSelectionn�).estVide() )
					obstacle[1]=true;
				else if(!obstacle[1] && this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
				else if(p.getSaitVoler() && obstacle[1] &&this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
			}
			
			if(y-i>=0){
				int[] positionCaseSelectionn� ={x,y-i};
				if(!obstacle[2]&& !this.getCase(positionCaseSelectionn�).estVide() )
					obstacle[2]=true;
				else if(!obstacle[2] && this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
				else if(p.getSaitVoler() && obstacle[2] &&this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
			}
			
			if(y+i<TaillePlateauY){
				int[] positionCaseSelectionn� ={x,y+i};
				if(!obstacle[3]&& !this.getCase(positionCaseSelectionn�).estVide() )
					obstacle[3]=true;
				else if(!obstacle[3] && this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
				else if(p.getSaitVoler() && obstacle[3] &&this.getCase(positionCaseSelectionn�).estVide())
					voisinage.add(positionCaseSelectionn�);
			}
			
		}
		
		if(pmd!=0){
		for(int i=1;i<=pmd;i++){
		
			int[] positionCaseSelectionn�0 ={x-i,y-i};
			int[] positionCaseSelectionn�1 ={x-i,y+i};
			int[] positionCaseSelectionn�2 ={x+i,y+i};
			int[] positionCaseSelectionn�3 ={x+i,y-i};
			if(x-i>=0 && y-i>=0){
			if(!obstacle[4] && this.getCase(positionCaseSelectionn�0).estVide())
				voisinage.add(positionCaseSelectionn�0);
			else if(!obstacle[4]&&!this.getCase(positionCaseSelectionn�0).estVide())
				obstacle[4]=true;
			else if(obstacle[4]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionn�0).estVide())
				voisinage.add(positionCaseSelectionn�0);
			}
			if(x-i>=0 && y+i<TaillePlateauY){
			if(!obstacle[5] && this.getCase(positionCaseSelectionn�1).estVide())
				voisinage.add(positionCaseSelectionn�1);
			else if(!obstacle[5]&&!this.getCase(positionCaseSelectionn�1).estVide())
				obstacle[5]=true;
			else if(obstacle[5]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionn�1).estVide())
				voisinage.add(positionCaseSelectionn�1);
			}
			if(x+i<TaillePlateauX && y+i<TaillePlateauY){
			if(!obstacle[6] && this.getCase(positionCaseSelectionn�2).estVide())
				voisinage.add(positionCaseSelectionn�2);
			else if(!obstacle[6]&&!this.getCase(positionCaseSelectionn�2).estVide())
				obstacle[6]=true;
			else if(obstacle[6]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionn�2).estVide())
				voisinage.add(positionCaseSelectionn�2);
			}
			if(x+i<TaillePlateauX && y-i>=0){
			if(!obstacle[7] && this.getCase(positionCaseSelectionn�3).estVide())
				voisinage.add(positionCaseSelectionn�3);
			else if(!obstacle[7]&&!this.getCase(positionCaseSelectionn�3).estVide())
				obstacle[7]=true;
			else if(obstacle[7]&&p.getSaitVoler()&&this.getCase(positionCaseSelectionn�3).estVide())
				voisinage.add(positionCaseSelectionn�3);
			}
			
			}
		}
	return voisinage;
}
	/**
	 * Retourne les cases autour d'une case cibl�
	 * @param positionCaseSelectionn� position de la case cibl� 
	 * @return les cases autour d'une case cibl�
	 */
	public ArrayList<int[]> VoisinageCaseZoneCible(int[] positionCaseSelectionn�){
		ArrayList<int[]> voisinage =new ArrayList<int[]>();
		int x = positionCaseSelectionn�[0]-1;
		int y = positionCaseSelectionn�[1]-1;
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
