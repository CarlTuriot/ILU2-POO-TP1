package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois gaulois = new Gaulois("Abraracourcix", 10);
		Gaulois vendeur = new Gaulois("Bonemine", 10);
		
		//liberer etal
		etal.libererEtal();
			
		
		//la quantité doit être positive
		try {
			etal.occuperEtal(vendeur, "Fleure", 10);
			etal.acheterProduit(10, null);
			
			//IllegalArgumentException
			etal.acheterProduit(0, gaulois);
			
			//IllegalStateException
			etal.libererEtal();
			etal.acheterProduit(10, gaulois);
			
			
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("quantité inférieure a 1");
		} catch(IllegalStateException e) {
			e.printStackTrace();
			System.out.println("l'étal n'est pas occupé");
		}
	
		
		
		
		System.out.println("Fin du test");

	}

}
