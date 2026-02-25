package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder(vendeur + "cherche un endroit pour vendre " + nbProduit + produit + ".\n");
		int indiceEtal;
		indiceEtal=marche.trouverEtalLibre();
		if(indiceEtal!=-1) {
			marche.utiliserEtal(indiceEtal, vendeur,produit, nbProduit);
			chaine.append("Le vendeur " + vendeur + "vend des fleurs à l'étal n°" + indiceEtal + ".\n");
		} else {
			chaine.append("le vendeur " + vendeur + "n'a pas trouver d'etal libre .\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal [] etalsValid=marche.trouverEtals(produit);
		int nbEtalValid=etalsValid.length;
		
		if(nbEtalValid==0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + "au marché.\n");
		} else if(nbEtalValid==1){
			chaine.append("Seul le vendeur " + etalsValid[0].getVendeur() +"propose des " + produit + "au marché.\n");
		} else {
			chaine.append("Les vendeurs qui vendent des " + produit + "au marché sont:");
			for(int i=0;i<nbEtalValid;i++) {
				chaine.append(etalsValid[i].getVendeur() + ", ");
			}
		}
		return chaine.toString();
	}
	
}