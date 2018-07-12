package utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import rinocitologia.Cell;
import rinocitologia.Patient;


/**
 * Provides some utilities such as transformation of data stored in Patient in XML file, JSON and PDF.
 * <br>
 * Refer to https://stackoverflow.com/questions/5971964/file-separator-or-file-pathseparator for File.separator.
 */
public class Utility {
	
	private Patient dict;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private String PATH = String.format("%s", System.getProperty("user.home"));
	
	/**
	 * Instantiates dict by a parameter and creates inputs and reports folders.
	 * @param dict
	 */
	public Utility(Patient dict) {
		this.dict = dict;
		createDirs();
				
	}
	
	
	public void writeReports() {
		writeJson();
		try {
			writePdfReport();
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates inputs and reports folders.
	 * If name is missing, default name is Pinco Pallino.
	 */
	public void createDirs() {
		if(dict.getFirstName()==null && dict.getSurname()==null) {
			dict.setFirstName("Pinco");
			dict.setSurname("Pallino");
		}
		
		String usrPATH = PATH + File.separator + "data" + File.separator + dict.getFirstName() + "_" + dict.getSurname();
		final File folderReports = new File(usrPATH , "reports");
		if(!folderReports.exists() && !folderReports.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}
		
		final File folderInputs = new File(usrPATH , "inputs");
		if(!folderInputs.exists() && !folderInputs.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}
	}
	
	
	/**
	 * Writes informations contained in Patient in a JSON File located in data/<FistName>_<Surname>/.
	 * If the directory does not exist, creates it.
	 */
	public void writeJson() {
		String json = gson.toJson(dict);	
		String directoryPath = PATH + File.separator + "data" + File.separator + dict.getFirstName() + "_" + dict.getSurname() + File.separator + "reports";
		String fullPath = directoryPath + File.separator + dict.getFirstName() + "_" + dict.getSurname() + ".json";
		 try {
			   FileWriter writer = new FileWriter(fullPath);
			   writer.write(json);
			   writer.close();
			  
		 	  } catch (IOException e) {
			   e.printStackTrace();
			  }
		 System.out.println("Writing JSON into file:\n" + fullPath + "\n----------------------------");
			  
	}
	
	
	/**
	 * Reads informations contained in a JSON format file (retrieved by firstName and surname) and populates an instance of Patient.
	 * @param firstName String that contains user's first name.
	 * @param surname String that contains user's surname.
	 * @return dictionary instance of the class Patient populated with the informations obtained from JSON file.
	 */
	public Patient readJson(String firstName, String surname) {
		String directoryPath = PATH + File.separator + "data" + File.separator + firstName + "_" + surname + File.separator + "reports";
		String fullPath = directoryPath + File.separator + firstName + "_" + surname + ".json";
		Patient patient = null;
		
		try {
			  
			   System.out.println("Reading JSON from a file");
			   System.out.println(PATH);
			   System.out.println(fullPath);
			   System.out.println("----------------------------");
			   
			   BufferedReader br = new BufferedReader(
			     new FileReader(fullPath));
			   
			    //convert the json string back to object
			   patient = gson.fromJson(br, Patient.class);

			  
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
			 
		return patient;
	}
	
	
	/**
	 * Reads informations contained in a JSON format file (retrieved by firstName = Pinco and surname = Pallino) and populates an instance of Patient.
	 * @return dictionary instance of the class Patient populated with the informations obtained from JSON file.
	 */
	public Patient readJson() {
		String directoryPath = PATH + File.separator + "data" + File.separator + "Pinco_Pallino" + File.separator + "reports";
		String fullPath = directoryPath + File.separator + "Pinco_Pallino" + ".json";
		Patient patient = null;
		
		try {
			  
			   System.out.println("Reading JSON from a file");
			   System.out.println(PATH);
			   System.out.println(fullPath);
			   System.out.println("----------------------------");
			   
			   BufferedReader br = new BufferedReader(
			     new FileReader(fullPath));
			   
			    //convert the json string back to object
			   patient = gson.fromJson(br, Patient.class);

			  
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
			 
		return patient;
	}
	
	/**
	 * Writes a report in Pdf format.
	 * Refer to http://www.baeldung.com/java-pdf-creation
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void writePdfReport() throws FileNotFoundException, DocumentException {
		String directoryPath = PATH + File.separator + "data" + File.separator + dict.getFirstName() + "_" + dict.getSurname() + File.separator + "reports";
		String fullPath = directoryPath + File.separator + dict.getFirstName() + "_" + dict.getSurname() + ".pdf";
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(fullPath));
		 
		document.open();
		Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Report clinico per il paziente: " + dict.getSurname() + " " + dict.getFirstName() + ".\n", fontHeader);
		
		
		Font fontInformativa = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
		
		Paragraph info = new Paragraph("Nome: " + dict.getFirstName() + "\nCognome: " + dict.getSurname() + "\nNato/a a: " + null + "\nIl: " + null, fontInformativa);

		Paragraph informativa = new Paragraph("Questo è un report generato automaticamente dal sistema di supporto medico per la Rinocitologia Nasale. Per maggiori informazioni rivolgersi a un dottore specializzato in Rinocitologia.", fontInformativa);
		informativa.setIndentationLeft(20);
		PdfPTable table = new PdfPTable(3); //3 is the number of columns for the table: Name, Cell Count and Grade
		table.setSpacingBefore(12);   
		table.setSpacingAfter(12);

		addTableHeader(table);
		for (Map.Entry<String, Cell> entry : dict.getDictionary().entrySet()) {
			addRows(table, entry.getKey(), Integer.toString(entry.getValue().getcellCount()), Integer.toString(entry.getValue().getgrade()));
		}
		 
	
		document.add(chunk);
		document.add( Chunk.NEWLINE );
		document.add(info);
		
		document.add(table);
		document.add(informativa);
		document.close();
		
		System.out.println("Writing PDF into file:\n" + fullPath + "\n----------------------------");
	}
	
	private void addTableHeader(PdfPTable table) {
	    Stream.of("Nome", "Conta cellule", "Grado")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void addRows(PdfPTable table, String name, String count, String grade) {
	    table.addCell(name);
	    table.addCell(count);
	    table.addCell(grade);
		
	}
}
