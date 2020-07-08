import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Sensor{
	
	private int volumen;
	private int led;
	private String fechaUltModif;
	private String nombreFichero;
	private final int id; 
	
	public Sensor(int id){	
		this.id=id;
	    this.nombreFichero = "./Sensor"+id+".txt";
	    File f = new File(nombreFichero);
	    
	    if(f.exists())
	    {
	      getSensor();
	    }
	    else
	    {
		  this.volumen = 30;
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	      this.fechaUltModif = dateFormat.format(new Date());
	      this.led = 4500;
	      setSensor();
	    }
	}

	public int getVolumen(){
		getSensor();

		return volumen;
	}

	public void setVolumen(int v){
		if(v < 0 || v > 100)
			this.volumen = 30;
		else
			this.volumen = v;
		
		setSensor();
	}

	public int getLed(){
		getSensor();

		return led;
	}

	public void setLed(int led){
		if(led < 0 || led > 65535)
			led = 4500;
		else
			this.led = led;
		
		setSensor();
	}

	public String getFecha() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    return dateFormat.format(new Date());
	}

	public void setFechaUltModif(String fechaUltModif){
		this.fechaUltModif = fechaUltModif;
		setSensor();
	}

	public String getFechaUltModif(){
		return this.fechaUltModif;
	}
	
	public void getSensor(){
		File archivo = new File(nombreFichero);
		try{
		FileReader fr = new FileReader(archivo);
		BufferedReader br = new BufferedReader(fr);
		String linea = br.readLine();
		for(int i = 0; i<3;i++){
			String dividir[] = linea.split("=");
			switch(i){
				case 0: setVolumen(Integer.parseInt(dividir[1]));
				break;
				case 1: setFechaUltModif(dividir[1]);
				break;
				case 2: setLed(Integer.parseInt(dividir[1]));
				break;
			}
			linea = br.readLine();
		}
		br.close();
		fr.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void setSensor(){
		OutputStream os = null;
		try{
			os = new FileOutputStream(new File(nombreFichero));
			this.fechaUltModif = getFecha(); //para actualizar la fecha de ultima modificacion cada vez que se escriba en fichero
			String texto = "Volumen="+volumen+"\n"+"UltimaFecha="+fechaUltModif+"\n"+"Led="+led;
			os.write(texto.getBytes(),0,texto.length());
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally{
			try{
				os.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}

	public int getId() {
		return id;
	}

}

