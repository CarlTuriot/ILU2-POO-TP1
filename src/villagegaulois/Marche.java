package villagegaulois;

import personnages.Gaulois;

public class Marche {
	private int nombreEtals;
	private Etal[] etals;
	
	public Marche(int nombreEtal) {
		this.nombreEtals = nombreEtals;
		etals = new Etal[nombreEtals];
	}
	
	void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
		etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
	}
	
	int trouverEtalLibre() {
		for(int i=0;i<nombreEtals;i++) {
			if (!etals[i].isEtalOccupe())return i;
		}
		return -1;
	}
	
	//modification certaines
	Etal[] trouverEtals(String produit) {
		int nbEtalValid=0;
		for(int i=0;i<nombreEtals;i++) {
			if (etals[i].contientProduit(produit)) {
				nbEtalValid++;
			}
		}
		Etal[] etalsValid= new Etal[nbEtalValid];
		int indiceEtal=0;
		
		for(int j=0;j<nombreEtals;j++) {
			if (etals[j].contientProduit(produit)) {
				etalsValid[indiceEtal]=etals[j];
				indiceEtal++;
			}
		}
		return etalsValid;
	}
	
	Etal trouverVendeur(Gaulois gaulois) {
		for(int i=0;i<nombreEtals;i++) {
			if (gaulois.equals(etals[i].getVendeur()))return etals[i];
		}
		return null;
	}
	
	
	
}
