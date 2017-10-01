import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Probabilidades {
	private TreeMap<String,Double> mapaVocabulario;
	private TreeMap<String,Double> mapaPositivo;
	private TreeMap<String,Double> mapaNegativo;
	/*Estos 2 mapas contienen las probabilidades calculadas de cada palabra*/
	private TreeMap<String,Double> mapaFinalPositivo = new TreeMap<String,Double>();
	private TreeMap<String,Double> mapaFinalNegativo = new TreeMap<String,Double>();
	
	private int numeroDocumentosPos;
	private int numeroPalabrasPos;
	
	private int numeroDocumentosNeg;
	private int numeroPalabrasNeg;
	
	public Probabilidades(TreeMap<String,Double> aux) {
		mapaVocabulario = aux;
		numeroDocumentosPos = 0;
		numeroPalabrasPos = 0;
		numeroDocumentosNeg = 0;
		numeroPalabrasNeg = 0;
	}
	public void probabilidadPos() {
		try {
		      //PARA CORPUSPOS
			  BufferedReader br = new BufferedReader(new FileReader("corpuspos.txt"));
		      StringBuffer linea = new StringBuffer();
		      
		      while (br.ready()) { //Leemos el fichero en un buffer
		    	  numeroDocumentosPos++;
		          linea.append(br.readLine());
		          linea.append(" ");
		      }
		      br.close();
	
		      HashMap<String, Double> mapa = new HashMap<String, Double>();
		      String[] aux = new String(linea).split(" ");
		      
		      mapa.put(aux[0].toLowerCase(), (double) 1);
		      for (int i = 1; i < aux.length ; i++) {
	 			 String key = aux[i].toLowerCase();
				 if(mapa.containsKey(key)){ //si coincide con algo del hash
					 mapa.put(key, mapa.get(key)+1);
				 }
				 else if (!"".equals(key)) {
					 mapa.put(key, (double) 1);
				 }
	 	 	  }
		      this.mapaPositivo = new TreeMap<String,Double>(mapa);
		      for (String key : mapaPositivo.keySet()) {
		    	  numeroPalabrasPos ++;
		      }
		      double numeroPalabrasVocabulario = 0.0;
		      for (String key : mapaVocabulario.keySet()) {
		    	  numeroPalabrasVocabulario++;
		      }
		      //ORDENANDO
		     
		      //Volcamos los datos al fichero
		      mapaPositivo.remove("");
		      double prob;
		      for (String key : mapaPositivo.keySet()) {
		    	  prob = (mapaPositivo.get(key)+1)/(numeroPalabrasPos + numeroPalabrasVocabulario);
		    	  prob = Math.log(prob);
		    	  mapaFinalPositivo.put(key, prob);
		      }
		      
		    PrintWriter writer = new PrintWriter("aprendizajepos.txt", "UTF-8");
		    writer.println("Numero de documentos del corpus: " + numeroDocumentosPos);
		    writer.println("Numero de palabras del corpus: " + numeroPalabrasPos);
		    for (String key : mapaPositivo.keySet()) {
		    		writer.println("Palabra: "+ key + " Frec: " +  mapaPositivo.get(key) + " LogProb: " + mapaFinalPositivo.get(key));
		    }		    
		    writer.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void probabilidadNeg() {
		try {
		      //PARA CORPUSPOS
			  BufferedReader br = new BufferedReader(new FileReader("corpusneg.txt"));
		      StringBuffer linea = new StringBuffer();
		      
		      while (br.ready()) { //Leemos el fichero en un buffer
		    	  numeroDocumentosNeg++;
		          linea.append(br.readLine());
		          linea.append(" ");
		      }
		      br.close();
	
		      HashMap<String, Double> mapa = new HashMap<String, Double>();
		      String[] aux = new String(linea).split(" ");
		      
		      mapa.put(aux[0].toLowerCase(), (double) 1);
		      for (int i = 1; i < aux.length ; i++) {
	 			 String key = aux[i].toLowerCase();
				 if(mapa.containsKey(key)){ //si coincide con algo del hash
					 mapa.put(key, mapa.get(key)+1);
				 }
				 else if (!"".equals(key)) {
					 mapa.put(key, (double) 1);
				 }
	 	 	  }
		      this.mapaNegativo = new TreeMap<String,Double>(mapa);
		      for (String key : mapaNegativo.keySet()) {
		    	  numeroPalabrasNeg ++;
		      }
		      double numeroPalabrasVocabulario = 0.0;
		      for (String key : mapaVocabulario.keySet()) {
		    	  numeroPalabrasVocabulario++;
		      }
		      //ORDENANDO
		     
		      //Volcamos los datos al fichero
		      mapaNegativo.remove("");
		      double prob;
		      for (String key : mapaNegativo.keySet()) {
		    	  prob = (mapaNegativo.get(key)+1)/(numeroPalabrasNeg + numeroPalabrasVocabulario);
		    	  prob = Math.log(prob);
		    	  mapaFinalNegativo.put(key, prob);
		      }
		      
		    PrintWriter writer = new PrintWriter("aprendizajeneg.txt", "UTF-8");
		    writer.println("Numero de documentos del corpus neg: " + numeroDocumentosNeg);
		    writer.println("Numero de palabras del corpus neg: " + numeroPalabrasNeg);
		    for (String key : mapaNegativo.keySet()) {
		    		writer.println("Palabra: "+ key + " Frec: " +  mapaNegativo.get(key) + " LogProb: " + mapaFinalNegativo.get(key));
		    }		    
		    writer.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	boolean probabilidadOpinion(String opinion) {
		double sumaprobpos = 0.0;
		double sumaprobneg = 0.0;
		String[] aux = new String(opinion).split(" ");
		for (int i = 0; i < aux.length ; i++) {
			if (mapaFinalPositivo.get(aux[i]) != null)
				sumaprobpos += mapaFinalPositivo.get(aux[i]);
			if (mapaFinalNegativo.get(aux[i]) != null)
				sumaprobneg += mapaFinalNegativo.get(aux[i]);
		}
		if (sumaprobpos > sumaprobneg) { //OPINION POSITIVA
			return true;
		} else { //OPINION NEGATIVA
			return false;
		}
	}
	
	public void clasificacion() {
		BufferedReader br;
		try {
		  br = new BufferedReader(new FileReader("corpustodo.txt"));
	      StringBuffer linea = new StringBuffer();
	      
	      while (br.ready()) { //Leemos el fichero en un buffer
	          linea.append(br.readLine());
	          linea.append("\n");
	      }
	      br.close();

	      String[] aux = new String(linea).split("\n");
	      PrintWriter writerp = new PrintWriter("clasificacion.txt", "UTF-8");
		  for (int i = 0 ; i < aux.length ; ++i) {
			  String cadena = aux[i].toLowerCase().substring(11, aux[i].length() - 3);
			  if (probabilidadOpinion(cadena) == true) {
				  writerp.println("Clase:<pos> Texto:<" + cadena + ">");
			  } else {
				  writerp.println("Clase:<neg> Texto:<" + cadena + ">");
			  }
		  }
		  writerp.close();
	      
		} catch(Exception e){
		      e.printStackTrace();
		}
	}
	
	public void generarClasificacion_ok() {
		BufferedReader lectorPos;
		BufferedReader lectorNeg;
		
		try {
		  //POSITIVO
		  lectorPos = new BufferedReader(new FileReader("corpuspos.txt"));
	      StringBuffer lineaPos = new StringBuffer();
	      
	      while (lectorPos.ready()) { //Leemos el fichero en un buffer
	          lineaPos.append(lectorPos.readLine());
	          lineaPos.append("\n");
	      }
	      lectorPos.close();

	      String[] auxPos = new String(lineaPos).split("\n");
	      PrintWriter writer = new PrintWriter("clasificacion_ok.txt", "UTF-8");
		  for (int i = 0 ; i < auxPos.length ; ++i) {
			  String cadena = auxPos[i].toLowerCase().substring(11, auxPos[i].length() - 3);
			  writer.println("Clase:<pos> Texto:<" + cadena + ">");
		  }
		  
		  //NEGATIVO
		  lectorNeg = new BufferedReader(new FileReader("corpusneg.txt"));
	      StringBuffer lineaNeg = new StringBuffer();
	      
	      while (lectorNeg.ready()) { //Leemos el fichero en un buffer
	          lineaNeg.append(lectorNeg.readLine());
	          lineaNeg.append("\n");
	      }
	      lectorNeg.close();

	      String[] auxNeg = new String(lineaNeg).split("\n");
		  for (int i = 0 ; i < auxNeg.length ; ++i) {
			  String cadena = auxNeg[i].toLowerCase().substring(11, auxNeg[i].length() - 3);
			  writer.println("Clase:<neg> Texto:<" + cadena + ">");
		  }
		  writer.close();
	      
		} catch(Exception e){
		      e.printStackTrace();
		}
	}
}
