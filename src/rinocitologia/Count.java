package rinocitologia;
import net.sf.clipsrules.jni.Environment;

public class Count {

	private int ciliate;
	private int mucipare;
	private int metaplastiche;
	private int neutrofili;
	private int eosinofili;
	private int mastociti;
	private int linfociti;
	private int batteri;
	private int spore;
	private boolean macchia;

	
	public Count(Environment clips, int ciliate, int mucipare, int metaplastiche, int neutrofili, int eosinofili, int mastociti,
			int linfociti, int batteri, int spore, boolean macchia) {
		this.ciliate = count300(ciliate);
		this.mucipare = count300(mucipare);
		this.metaplastiche = count300(metaplastiche);
		this.neutrofili = count100(neutrofili);
		this.eosinofili = count30(eosinofili);
		this.mastociti = count30(mastociti);
		this.linfociti = count30(linfociti);
		this.batteri = count16(batteri);
		this.spore = count16(spore);
		this.macchia = macchia;
	}
	
	public Count(int ciliate, int mucipare, int metaplastiche, int neutrofili, int eosinofili, int mastociti,
			int linfociti, int batteri, int spore, boolean macchia) {
		this.ciliate = count300(ciliate);
		this.mucipare = count300(mucipare);
		this.metaplastiche = count300(metaplastiche);
		this.neutrofili = count100(neutrofili);
		this.eosinofili = count30(eosinofili);
		this.mastociti = count30(mastociti);
		this.linfociti = count30(linfociti);
		this.batteri = count16(batteri);
		this.spore = count16(spore);
		this.macchia = macchia;
	}


	public int count300(int number_cells) {

		int grade = 0;

		if(number_cells == 0){
	        grade = 0;
		}
	    if(number_cells > 0 && number_cells < 101) {
	        grade = 1;
	    }
	    if(number_cells > 100 && number_cells < 201) {
	        grade = 2;
	    }
	    if(number_cells > 200 && number_cells < 301) {
	    	grade = 3;
	    }
	    if (number_cells > 300) {
	        grade = 4;
	    }

	    return grade;
	}

	public int count100(int number_cells) {

		int grade = 0;

	    if(number_cells == 0) {
	        grade = 0;
	    }
	    if(number_cells > 0 && number_cells < 21) {
	    	grade = 1;
	    }
	    if(number_cells > 20 && number_cells < 41) {
	    	grade = 2;
	    }
	    if(number_cells > 40 && number_cells < 101) {
	    	grade = 3;
	    }
	    if(number_cells > 100) {
	    	grade = 4;
	    }

	    return grade;
	}

	public int count30(int number_cells) {

		int grade = 0;


	    if (number_cells == 0) {
	    	grade = 0;
	    }
	    if (number_cells > 0 && number_cells < 6) {
	    	grade = 1;
	    }
	    if (number_cells > 5 && number_cells < 11) {
	    	grade = 2;
	    }
	    if (number_cells > 10 && number_cells < 31) {
	    	grade = 3;
	    }
	    if (number_cells > 30) {
	    	grade = 4;
	    }

	    return grade;
	}



	public int count16(int number_cells) {

		int grade = 0;

	    if (number_cells == 0) {
	    	grade = 0;
	    }
	    if (number_cells > 0 && number_cells < 4) {
	    	grade = 1;
	    }
	    if (number_cells > 3 && number_cells < 8) {
	    	grade = 2;
	    }
	    if (number_cells > 7 && number_cells < 17) {
	    	grade = 3;
	    }
	    if (number_cells > 16) {
	    	grade = 4;
	    }

	    return grade;
	}
}
