package utility;

import java.util.ArrayList;
import java.util.Iterator;

import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;

public class ClipsManager {
	private Environment clips;
	private ArrayList<String> diagnosis = new ArrayList<String>();
	
	public ClipsManager(Environment clips){
		this.clips = clips;
	}
	
	


	/**
	 * Returns a list of possible diagnosis from cell count.
	 * @return diagnosis ArrayList containing name of possible diagnosis.
	 */
	public ArrayList<String> getDiagnosis(){
		MultifieldValue mv = (MultifieldValue) clips.eval("(find-all-facts((?f diagnosi)) TRUE)");
		for(int i=0;i<mv.size();i++){
		    FactAddressValue fv = (FactAddressValue) mv.get(i);
		    try {
				diagnosis.add(fv.getFactSlot("nome").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("LIST OF DIAGNOSIS:");
		Iterator<String> i = diagnosis.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		
		return diagnosis;
	}
}
