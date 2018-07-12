package rinocitologia;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.itextpdf.text.DocumentException;

import net.sf.clipsrules.jni.*;
import utility.ClipsManager;
import utility.Utility;
/**
 * Launch CLIPS (Expert System) and get preliminary informations such as templates for facts and rules (sources located in src/clips).
 * Through the object handler (InputHandler) assert facts from the informations retrieved by neural network.
 */
public class Diagnosis {

	private Environment clips = null;
	private InputHandler handler;
	private Patient dict;
	private ClipsManager cm;

	Diagnosis() throws FileNotFoundException, DocumentException {
		clips = new Environment(); 
		//clips.clear();
		clips.loadFromResource("/clips/fatticopia.clp");
		clips.loadFromResource("/clips/diagnosi.clp");
		
		clips.reset();
		
		final File folder = new File(System.getProperty("user.home"), "data");
		if(!folder.exists() && !folder.mkdir()) {
		   //failed to create the folder, probably exit
		   throw new RuntimeException("Failed to create save directory.");
		}
		
		//DIAGNOSI NARESMA
		dict = new Patient();
		dict.addElement("Eosinofili", 6);
		dict.addElement("Mastociti", 6);
		dict.addElement("Neutrofili", 6);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		Utility util = new Utility(dict);
		util.writeReports();
		
	
		/*
		//EXAMPLE OF ADDING ELEMENTS MANUALLY TO DICTIONARY NO NAME
		dict = new Patient();
		dict.addElement("Neutrofili", 6);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		Utility util = new Utility(dict);
		util.writeJson();
		util.writePdfReport();
		*/
		
		/*
		//EXAMPLE OF ADDING ELEMENTS MANUALLY TO DICTIONARY NAME
		dict = new Patient("gian", "sekko");
		dict.addElement("Neutrofili", 6);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		Utility util = new Utility(dict);
		util.writeJson();
		util.writePdfReport();
		 */
		
		
		/*
		//EXAMPLE OF LOAD INFORMATIONS FROM JSON NO NAME
		Utility util = new Utility(new Patient());
		dict = util.readJson();
		System.out.println(dict);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		*/
		
		/*
		//EXAMPLE OF LOAD INFORMATIONS FROM JSON NAME
		Utility util = new Utility(new Patient());
		dict = util.readJson("Lac", "Gua");
		System.out.println(dict);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		*/
		
		
		/*
		//PROVA LETTURA DA FILE SERGIO
		dict = new Patient("Lac", "Gua");
		handler = new InputHandler(clips, dict);
		handler.readFile();
		System.out.println(dict);
		*/
		
		clips.run();
		
		cm = new ClipsManager(clips);
		cm.getDiagnosis();
	}

	public static void main(String args[]) throws FileNotFoundException, DocumentException {
	// void repl() {
		Diagnosis elimina = new Diagnosis();
		boolean endInteraction = false;
		Scanner in = new Scanner(System.in);
		while (!endInteraction) {
			System.out.print(" CLIPS >");
			String userInput = in.nextLine(); /* Read */
			try {
				String response = elimina.clips.eval(userInput).toString(); /* E v a l */
				System.out.println(response); /* P r i n t */
				if (response.equals("(exit)")) { /* Loop */
					endInteraction = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}