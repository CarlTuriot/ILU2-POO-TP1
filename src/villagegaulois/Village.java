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
		this.marche= new Marche(nbEtal);
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
		StringBuilder chaine = new StringBuilder(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal;
		indiceEtal=marche.trouverEtalLibre();
		if(indiceEtal!=-1) {
			marche.utiliserEtal(indiceEtal, vendeur,produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des fleurs à l'étal n°" + (indiceEtal+1) + ".\n");
		} else {
			chaine.append("le vendeur " + vendeur.getNom() + " n'a pas trouver d'etal libre .\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal [] etalsValid=marche.trouverEtals(produit);
		int nbEtalValid=etalsValid.length;
		
		if(nbEtalValid==0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
		} else if(nbEtalValid==1){
			chaine.append("Seul le vendeur " + etalsValid[0].getVendeur().getNom() +" propose des " + produit + " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui vendent des " + produit + " au marché sont: \n");
			for(int i=0;i<nbEtalValid;i++) {
				chaine.append("- ").append(etalsValid[i].getVendeur().getNom()).append("\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = rechercherEtal(vendeur);
		return etal.libererEtal();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}

	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nombreEtal) {
			etals = new Etal[nombreEtal];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			if (etals[indiceEtal]==null) {
				etals[indiceEtal]=new Etal();
			}
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if(etals[i]==null || !etals[i].isEtalOccupe()) return i;
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbValid =0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i]!=null && !etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbValid++;
				}
			}
			Etal[] etalProd = new Etal[nbValid];
			int indiceValid=0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i]!=null && !etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalProd[indiceValid]=etals[i];
					indiceValid++;
				}
			}
			return etalProd;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i]!=null && etals[i].isEtalOccupe() && gaulois.equals(etals[i].getVendeur())) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder affichage = new StringBuilder("Les etals du marche son les suivants:\n");
			int etalLibre=0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i]!=null && etals[i].isEtalOccupe()) {
					affichage.append(etals[i].afficherEtal());
				} else {
					etalLibre++;
				}
			}
			affichage.append("Il reste " + etalLibre + " etals non utilises dans le marche.\n");
			return affichage.toString();
		}
		
		
	}
	
}