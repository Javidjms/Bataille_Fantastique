package Personnage;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;


abstract public class Personnage implements Serializable {
	private enum etatPossible {Normal,Ralenti}
	private ArrayList<Class<?>> action=new ArrayList<Class<?>>();
	private int defense=0;
	private boolean estKO=false;
	private etatPossible etat;
	private String nom;;
	private int numTourDefense;
	private int numTourRalenti;
	private int PointMouvement;
	private int PointMouvementDiagonale;
	private int PointVie;
	private int[] position= new int[2];
	private boolean saitVoler;
	public Personnage(int _PointVie,String _nom,int _PointMouvement,int _PointMouvementDiagonale,
			boolean _saitVoler,ArrayList<Class<?>> _action){
		PointVie = _PointVie;
		nom=_nom;
		PointMouvement=_PointMouvement;
		PointMouvementDiagonale=_PointMouvementDiagonale;
		etat=etatPossible.Normal;
		saitVoler=_saitVoler;
		action=_action;
		
	}
	
	abstract public void deplacer(int[] _position);
	public void finDefense() {
		defense=0;
	}

	public ArrayList<Class<?>> getAction() {
		return action;
	}

	abstract public Class<?> getAction(int idAction);

	abstract public ArrayList<Class<?>> getActions();

	public boolean isKO(){
		return estKO;
	}

	public etatPossible getEtat() {
		return etat;
	}

	abstract public Icon getMiniIcon();

	public String getNom() {
		return nom;
	}

	public int getnumTourDefense() {
		return numTourDefense;
	}

	public int getnumTourRalenti(){
		return numTourRalenti;
	}

	public int getPointMouvement() {
		return PointMouvement;
	}
	
	public int getPointMouvementDiagonale() {
		return PointMouvementDiagonale;
	}
	
	public int getPointVie() {
		return PointVie;
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public boolean getSaitVoler() {
		return saitVoler;
	}
	
	public boolean hasDefense() {
		return(defense>=0);	
	}
	
	public void hasNormal(){
		etat=etatPossible.Normal;
		numTourRalenti=0;
		PointMouvement++;
		PointMouvementDiagonale++;
	}
	
	
	public void hasRalenti(int _numTourRalenti){
		etat=etatPossible.Ralenti;
		numTourRalenti=_numTourRalenti+1;
		PointMouvement--;
		PointMouvementDiagonale--;
	}
	
	public boolean isPointVieEgal0(){
		return (PointVie<=0);
	}
	
	public boolean isRalenti(){
		return (etat==etatPossible.Ralenti);
	}
	
	public void reduirePV(int degat){
		if(degat-defense>=0)
			PointVie=PointVie-(degat-defense);
	}
		
	public void setDefense(int i,int numTour) {
		numTourDefense=numTour+1;
		defense=i;	
	}
		
	public void setKO(){
		PointVie=0;
		estKO=true;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPosition(int[] _position){
		position=_position;
	}
	abstract public String toString();
	abstract public  ImageIcon getCarteIcon();
	
	
}
