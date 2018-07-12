package rinocitologia;
/**
 * Contains and populates informations such as cell count and relative grade (for more informations, please refer to "Atlante di Citologia Nasale" by Dr.Gelardi).
 */
public class Cell {
	private int cellCount;
	private int grade;
	
	/**
	 * Constructor that populates instance attributes.
	 * Mostly used whenever the report from neural network lacks of some informations.
	 * @param cellCount Integer that contains the number of the cells (for each type) retrieved by neural network.
	 * @param grade Integer that works as symbolically frequency indicator.
	 */
	public Cell(int cellCount, int grade) {
		this.cellCount = cellCount;
		this.grade = grade;
	}
	
	/**
	 * Constructor that populates instance attributes.
	 * Uses table 6.5 and figure 6.25 from "Atlante di Citologia Nasale" by Dr.Gelardi as base to populate those attributes.
	 * Since there could be some missunderstandings in the format of the name, it's evaluated in upper case.
	 * @param nome String that contains the name of the cell.
	 * @param cellCount Integer that contains the number of the cells (for each type) retrieved by neural network.
	 */
	public Cell(String nome, int cellCount) {
		this.cellCount = cellCount;
		switch(nome.toUpperCase()) {
			case "CILIATE":
			case "MUCIPARE":
			case "METAPLASTICHE":
				this.grade = count300(cellCount);
				break;
			case "NEUTROFILI":
				this.grade = count100(cellCount);
				break;
			case "EOSINOFILI":
			case "MASTOCITI":
			case "LINFOCITI":
				this.grade = count30(cellCount);
				break;
			case "BATTERI":
			case "SPORE":
				this.grade = count16(cellCount);
			case "MACCHIA":
				this.grade = 0;
				break;
		}
	}
	
	public int getcellCount() {
		return cellCount;
	}
	public void setcellCount(int cellCount) {
		this.cellCount = cellCount;
	}
	public int getgrade() {
		return grade;
	}
	public void setgrade(int grade) {
		this.grade = grade;
	}

	public int count300(int cellNumber) {
		
		int grade = 0;

		if(cellNumber == 0){
	        grade = 0;
		}
	    if(cellNumber > 0 && cellNumber < 101) {
	        grade = 1;
	    }
	    if(cellNumber > 100 && cellNumber < 201) {
	        grade = 2;
	    }
	    if(cellNumber > 200 && cellNumber < 301) {
	    	grade = 3;
	    }
	    if (cellNumber > 300) {
	        grade = 4;
	    }

	    return grade;
	}

	public int count100(int cellNumber) {

		int grade = 0;

	    if(cellNumber == 0) {
	        grade = 0;
	    }
	    if(cellNumber > 0 && cellNumber < 21) {
	    	grade = 1;
	    }
	    if(cellNumber > 20 && cellNumber < 41) {
	    	grade = 2;
	    }
	    if(cellNumber > 40 && cellNumber < 101) {
	    	grade = 3;
	    }
	    if(cellNumber > 100) {
	    	grade = 4;
	    }

	    return grade;
	}

	public int count30(int cellNumber) {

		int grade = 0;


	    if (cellNumber == 0) {
	    	grade = 0;
	    }
	    if (cellNumber > 0 && cellNumber < 6) {
	    	grade = 1;
	    }
	    if (cellNumber > 5 && cellNumber < 11) {
	    	grade = 2;
	    }
	    if (cellNumber > 10 && cellNumber < 31) {
	    	grade = 3;
	    }
	    if (cellNumber > 30) {
	    	grade = 4;
	    }

	    return grade;
	}



	public int count16(int cellNumber) {

		int grade = 0;

	    if (cellNumber == 0) {
	    	grade = 0;
	    }
	    if (cellNumber > 0 && cellNumber < 4) {
	    	grade = 1;
	    }
	    if (cellNumber > 3 && cellNumber < 8) {
	    	grade = 2;
	    }
	    if (cellNumber > 7 && cellNumber < 17) {
	    	grade = 3;
	    }
	    if (cellNumber > 16) {
	    	grade = 4;
	    }

	    return grade;
	}

	
}
