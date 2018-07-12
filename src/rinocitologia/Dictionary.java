package rinocitologia;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Contains informations meaningful for the diagnosis such as an HashMap in the format:
 * <br>
 * <String, Cellule> => <Cell Name, Cell Informations>
 * <br>
 * Cell Informations contains the count of the cells (done by neural network) and the grade transposition.
 * <br>
 * Contains methods useful to manipulate those informations.
 */
public class Dictionary {
	private String firstName; //????????
	private String surname; //??????
	private Map<String, Cell> dictionary;
	
	

	public Dictionary() {
		dictionary = new HashMap<String, Cell>();
		final File folder = new File(System.getProperty("user.home") + File.separator + "data" , "Pinco_Pallino");
		if(!folder.exists() && !folder.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}
	}
	
	public Dictionary(String name, String surname) {
		dictionary = new HashMap<String, Cell>();
		this.firstName = name;
		this.surname = surname;
		
		final File folder = new File(System.getProperty("user.home") + File.separator + "data" , name + "_" + surname);
		if(!folder.exists() && !folder.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Map<String, Cell> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<String, Cell> dictionary) {
		this.dictionary = dictionary;
	}
	
	/**
	 * Put an element in the HashMap (dictionary).
	 * Since there could be some missunderstanding in the format of the name, it will be lower cased and then capitalized just the first letter.
	 * @param nome String that contains name of the cell.
	 * @param numeroCellule Integer that contains the number of the cells (for each type) retrieved by neural network.
	 */
	public void addElement(String nome, int numeroCellule) {
		String actualName = nome.toLowerCase().substring(0,1).toUpperCase() + nome.toLowerCase().substring(1);
		dictionary.put(actualName, new Cell(nome, numeroCellule));
		
	}
	
	/**
	 * Autofills the dictionary with the missing informations from neural network.
	 * It is filled with missing cells with a count of 0.
	 */
	public void completeDictionary() {
		List<String> cellsList = Arrays.asList("Ciliate", "Mucipare", "Metaplastiche", "Neutrofili", "Eosinofili", "Mastociti", "Linfociti", "Batteri", "Spore", "Macchia");
		String cellName;
		Iterator<String> i = cellsList.iterator();
		while(i.hasNext()) {
			cellName = i.next();
			Cell cell = dictionary.get(cellName);
			if(cell == null) {
				dictionary.put(cellName, new Cell(0,0));
			}
		}
	}
	
	@Override
	public String toString() {
		String name = "First Name: " + firstName + "\nSurname: " + surname + "\n\n";
		StringBuilder cells = new StringBuilder("Cells List:\n");
		for (Map.Entry<String, Cell> entry : dictionary.entrySet()) {
			cells.append("Name: " + entry.getKey() + " - Count: " + entry.getValue().getcellCount() + " - Grade: " + entry.getValue().getgrade() + ";\n");
		}
		return name + cells.toString();
	}
}


