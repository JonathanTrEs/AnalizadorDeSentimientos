import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;


public class Validacion {
	private double contAcierto;
	private double contTotal;
	
	public Validacion() {
		contAcierto = 0;
		contTotal = 0;
	}
	
	public void calcularError() {
		BufferedReader br;
		BufferedReader brOk;
		try {
		  br = new BufferedReader(new FileReader("clasificacion.txt"));
		  brOk = new BufferedReader(new FileReader("clasificacion_ok.txt"));
	      String lineaBr = new String();
	      String lineaBrOk = new String();
	      
	      while (br.ready()) { //Leemos el fichero en un buffer
	          lineaBr = br.readLine();
	          if(brOk.ready()) {
	        	  lineaBrOk = brOk.readLine();
	        	  if(lineaBr.equals(lineaBrOk))
	        		  contAcierto++;
	          }
	          contTotal++;
	      }
	      br.close();
	      brOk.close();
	      
	      double porcentajeError;
	      porcentajeError = (contAcierto*100)/contTotal;

	      PrintWriter writerp = new PrintWriter("error.txt", "UTF-8");
	      writerp.println("Porcentaje de error:< " + porcentajeError + " >");
		  writerp.close();
	      
		} catch(Exception e){
		      e.printStackTrace();
		}
	}
}
