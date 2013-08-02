package roadMapGnomes;

import roadMap.Graph;

public class GnomeGenerator {
	public GnomeGenerator(int numberOfGnomes,Graph g){
		for (int i=0;i<numberOfGnomes;i++){
			Gnome gno = new Gnome(g);
		}//End for making n number of gnomes
	}//End Constructor
}//End GnomeGenerator
