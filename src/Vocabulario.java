import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeMap;

/*Esta clase crea un objeto que contiene un Treemap de todo el vocabulario, ordenado*/

public class Vocabulario {
	private TreeMap<String,Double> sorted_map;
	
	public TreeMap<String, Double> getSorted_map() {
		return sorted_map;
	}
	public void setSorted_map(TreeMap<String, Double> sorted_map) {
		this.sorted_map = sorted_map;
	}
	public Vocabulario() {
		try {
	      BufferedReader br = new BufferedReader(new FileReader("corpustodo.txt"));
	      StringBuffer linea = new StringBuffer();
	      
	      while (br.ready()) { //Leemos el fichero en un buffer
	          linea.append(br.readLine());
	          linea.append(" ");
	      }
	      br.close();

	      HashMap<String, Double> mapa = new HashMap<String, Double>();
	      String[] aux = new String(linea).split(" ");
	      
	      mapa.put(aux[0].toLowerCase(), (double) 1);
	      for(int i = 1; i < aux.length ; i++) {
 			 String key = aux[i].toLowerCase();
			 if(mapa.containsKey(key)){ //si coincide con algo del hash
				 mapa.put(key, mapa.get(key)+1);
			 }
			 else if (!"".equals(key)) {
				 mapa.put(key, (double) 1);
			 }
 	 	  }
	      //ORDENANDO
	      this.sorted_map = new TreeMap<String,Double>(mapa);
	      //Volcamos los datos al fichero
	      sorted_map.remove("");
	    PrintWriter writer = new PrintWriter("vocabulario.txt", "UTF-8");
	    for (String key : sorted_map.keySet()) {
	    		writer.println("Numero de palabras: "+ sorted_map.get(key) + "\n" + "Palabra: " + key);
	    }
	    writer.close();
		}
		catch(Exception e){
	      e.printStackTrace();
		}
	}
}