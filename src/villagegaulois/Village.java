package villagegaulois;

import exceptions.VillageSansChefException;
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

	public String afficherVillageois() throws VillageSansChefException {
		if (this.chef==null) {
			throw new VillageSansChefException("le village n'a pas de chef");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- ").append(villageois[i].getNom()).append("\n");
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
	
	public class Marche {
		private int nombreEtal;
		private Etal[] etals;
		
		public Marche(int nombreEtal) {
			this.nombreEtal = nombreEtal;
			etals = new Etal[nombreEtal];
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			if (etals[indiceEtal]==null) {
				etals[indiceEtal]=new Etal();
			}
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for(int i=0;i<nombreEtal;i++) {
				if (etals[i]==null || !etals[i].isEtalOccupe())return i;
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalValid=0;
			for(int i=0;i<nombreEtal;i++) {
				if (etals[i]!=null && etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
						nbEtalValid++;
					}
			}
			
			Etal[] etalsValid= new Etal[nbEtalValid];
			int indiceEtal=0;
			
			for(int j=0;j<nombreEtal;j++) {
				if (etals[j]!=null && etals[j].isEtalOccupe() && etals[j].contientProduit(produit)) {
						etalsValid[indiceEtal]=etals[j];
						indiceEtal++;
					}
			}
			return etalsValid;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nombreEtal;i++) {
				if (gaulois.equals(etals[i].getVendeur()))return etals[i];
			}
			return null;
		}
		
		public String afficherMarche(){
			int nombreEtalVide=0;
			StringBuilder etalsOccupe= new StringBuilder("Les etals du marche son les suivants:\n");
			
			for(int i=0;i<nombreEtal;i++) {
				if (etals[i]!=null && etals[i].isEtalOccupe()) {
					etalsOccupe.append(etals[i].afficherEtal());
				} else {
					nombreEtalVide++;
				}
			}
			if(nombreEtalVide!=0) {
				etalsOccupe.append("Il reste " + nombreEtalVide + " etals non utilises dans le marche.\n");
			}
			return etalsOccupe.toString();
		}
		
	}
}

