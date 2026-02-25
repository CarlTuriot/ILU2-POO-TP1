package villagegaulois;

import personnages.Gaulois;

public class Marche {
	private int nombreEtal;
	private Etal[] etals;
	
	public Marche(int nombreEtal) {
		this.nombreEtal = nombreEtal;
		etals = new Etal[nombreEtal];
	}
	
	public void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
		etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
	}
	
	public int trouverEtalLibre() {
		for(int i=0;i<nombreEtal;i++) {
			if (!etals[i].isEtalOccupe())return i;
		}
		return -1;
	}
	
	//modification certaines
	public Etal[] trouverEtals(String produit) {
		int nbEtalValid=0;
		for(int i=0;i<nombreEtal;i++) {
			if (etals[i].contientProduit(produit)) {
				nbEtalValid++;
			}
		}
		Etal[] etalsValid= new Etal[nbEtalValid];
		int indiceEtal=0;
		
		for(int j=0;j<nombreEtal;j++) {
			if (etals[j].contientProduit(produit)) {
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
			if (etals[i].isEtalOccupe()) {
				etalsOccupe.append(etals[i].afficherEtal());
			} else {
				nombreEtalVide++;
			}
		}
		if(nombreEtalVide!=0) {
			etalsOccupe.append("Il reste " + nombreEtalVide + "etals non utilises dans le marche.\n");
		}
		return etalsOccupe.toString();
	}
	
}
