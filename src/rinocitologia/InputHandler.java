package rinocitologia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import net.sf.clipsrules.jni.Environment;

/**
 * Handles the inputs from neural network.
 * If some informations are missing, automatically fills them with default values.
 * Assert facts for the Expert System from the informations previously retrieved.
 */
public class InputHandler {
	private Environment clips;
	private Patient patient;
	private String PATH = System.getProperty("user.home");
	
	/**
	 * @param clips An instance of the clips engine.
	 * @param patient An instance of the object containing all the information useful to fill generate facts.
	 */
	public InputHandler(Environment clips, Patient patient) {
		this.clips = clips;
		this.patient = patient;
	}
	
	/**
	 * Assert facts from informations retrieved by neural network.
	 * Those informations are stored in a class which contains an HashMap having the following structure:
	 * <br>
	 * <String, Cellule> => <Cell Name, Cell Informations>
	 * <br>
	 * Cell Informations contains the count of the cells (done by neural network) and the grade transposition.
	 * For more informations, please refer to "Atlante di Citologia Nasale" by Dr. Gelardi
	 */
	public void assertFacts() {
		String assertion;
		patient.completeDictionary();
		
		for (Map.Entry<String, Cell> entry : patient.getDictionary().entrySet()) {
			assertion = "(cellule (nome " + entry.getKey() + ") (grado " + entry.getValue().getgrade() + "))";
			//System.out.println(assertion);
			clips.assertString(assertion);
		}	
	}
	
	
	
	/**
	 * Read and parse a file (locating it by patient's name) and populates an instance of patient.
	 * @return patient An instance of Patient containing informations retrieved from file and subsequently filled with default values for missing entries.
	 */
	public Patient readFile() {
		
		if(patient.getFirstName()==null && patient.getSurname()==null) {
			patient.setFirstName("Pinco");
			patient.setSurname("Pallino");
		}
		
		String directoryPath = PATH + File.separator + "data" + File.separator + patient.getFirstName() + "_" + patient.getSurname() + File.separator + "inputs";
		String fullPath = directoryPath + File.separator + patient.getFirstName() + "_" + patient.getSurname() + ".txt";
		
		try {
			File file = new File(fullPath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				String[] array = parseInput(line);
				patient.addElement(array[0], Integer.parseInt(array[1]));
			}
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		patient.completeDictionary();
		
		return patient;
	}
	
	public String[] parseInput(String line) {
		String[] array = line.trim().split("\\s*-\\s*");		
		return array;
	}
}
