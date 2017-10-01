import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ControlFicheros {
	//-Xmx1024m

	String directoriopos, directorioneg;
	
	public void leerFicheros(ArrayList<String> listadirectorios) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writerp = new PrintWriter("corpuspos.txt", "UTF-8");
		PrintWriter writern = new PrintWriter("corpusneg.txt", "UTF-8");
		PrintWriter writer = new PrintWriter("corpustodo.txt", "UTF-8");
		for (int i = 0 ; i < 2 ; ++i) {
			File directory = new File(listadirectorios.get(i));
			File files[] = directory.listFiles();
			try {
				for (File f : files) {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line;
					while ((line = br.readLine()) != null) {
						line = line.replaceAll("[^a-zA-Z0-9 ]+", " ");
						line = line.replaceAll("br", "");
						line = " Texto : < " + line + " > ";
						if (i == 0) { //escribimos en corpus negativo
							writerp.println(line);
						} else {
							writern.println(line);
						}
						writer.println(line);
					}
					br.close();
				}
			} catch(IOException ex) {
		        System.out.println (ex.toString());
		    }
			if (i == 0) { //cerramos fichero del corpus negativo
				writerp.close();
			} else {
				writern.close();
			}
		}
		writer.close();
	}
	
	public ControlFicheros() {}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<String> listadirectorios = new ArrayList<String>();
		listadirectorios.add("C:/Users/Jhon/Desktop/Archivos/pos");
		listadirectorios.add("C:/Users/Jhon/Desktop/Archivos/neg");
		ControlFicheros control = new ControlFicheros();
		control.leerFicheros(listadirectorios);
		Vocabulario ficheroVocabulario = new Vocabulario();
		Probabilidades calcularProbabilidad = new Probabilidades(ficheroVocabulario.getSorted_map());
		calcularProbabilidad.probabilidadPos();
		calcularProbabilidad.probabilidadNeg();
		calcularProbabilidad.clasificacion();
		calcularProbabilidad.generarClasificacion_ok();
		Validacion validarDatos = new Validacion();
		validarDatos.calcularError();
		System.out.println("Finalizado");
	}
	
}