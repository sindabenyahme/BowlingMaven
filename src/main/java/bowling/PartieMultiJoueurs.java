package bowling;


import java.util.HashMap;
import java.util.Map;

public class PartieMultiJoueurs implements IPartieMultiJoueurs{
	private String[] nomsJoueurs;
	private int nbJoueur;
	private int tourJoueurNo;
	private Map<String, PartieMonoJoueur> Parties;
	


	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
		if (nomsDesJoueurs.length == 0) throw new IllegalArgumentException(" minimum un  joueur pour commencer");
		Parties = new HashMap<>();
		nbJoueur = nomsDesJoueurs.length;
		tourJoueurNo = 0;
		this.nomsJoueurs = nomsDesJoueurs;
		for (String nom: nomsDesJoueurs) {
			Parties.put(nom, new PartieMonoJoueur());
		}
		return tourJoueur();
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if (estTerminer()) throw  new IllegalStateException("La partie est terminée");
		PartieMonoJoueur partieJoueur = Parties.get(nomsJoueurs[tourJoueurNo]);
		partieJoueur.enregistreLancer(nombreDeQuillesAbattues);
		if (partieJoueur.numeroProchainLancer() == 1 || partieJoueur.estTerminee()) tourJoueurNo = (tourJoueurNo+1)%nbJoueur;
		if (estTerminer()) return "la Partie est  terminée";
		return tourJoueur();
	}

	@Override
	public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
		PartieMonoJoueur partieJoueur = Parties.get(nomDuJoueur);
		if (partieJoueur == null) throw new IllegalArgumentException("Joueur non trouver");
		return partieJoueur.score();
	}

	private String tourJoueur(){
		return "Prochain tir: joueur " + nomsJoueurs[tourJoueurNo] + ", tour n° " + Parties.get(nomsJoueurs[tourJoueurNo]).numeroTourCourant() + ", boule n° " + Parties.get(nomsJoueurs[tourJoueurNo]).numeroProchainLancer();
	}

	private boolean estTerminer(){
		return Parties.get(nomsJoueurs[nbJoueur-1]).estTerminee();
	}
}