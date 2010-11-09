package cn.edu.jlu.ccst.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;

import cn.edu.jlu.ccst.example.SimpleExample;
import cn.edu.jlu.ccst.model.Homology;

public class CreateHomology {
	/**
	 * judge the string whether is number
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){
	     Pattern pattern = Pattern.compile("[0-9]*");
	     return pattern.matcher(str).matches();   
	}
	/**
	 * Get all the homologies from epd 
	 * @param addr
	 * @return
	 */
	public List<Homology> getallhomos(String addr){
		List<Homology> result = new ArrayList();
		File file = new File(addr);
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(
					file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			Homology homology = new Homology();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] lines = line.split("   ");
				// construct homology object
				if (lines[0].trim().equalsIgnoreCase("HG")) {
					homology = new Homology();
					String[] homos = lines[1].split(";");
					if (homos.length >= 2) {
						homology.setName(homos[1].trim().substring(0,homos[1].trim().length() - 1));
						String[] ids = homos[0].trim().split(" ");
						if (this.isNumeric(ids[2])) {
							homology.setId(ids[2]);
						}
					}
					if(result.size() == 0){
						result.add(homology);
					}else{
						boolean flag = true;
						for(Homology homo:result){
							if(homo.getId().equals(homology.getId())){
								flag = false;
								break;
							}
						}
						if(flag)result.add(homology);
					}
					
				}
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Create all the homology groups ontologies
	 * @param homos
	 */
	public void createhomeonts(List<Homology> homos){
		SimpleExample se = new SimpleExample();
		String nameuri = "http://miuras.inf.um.es/ontologies/OGO.owl#Name";
		String homoclsuri = se.PROMOTER_PREFIX + "#Homology";
		OntModel onmo = null;
		try {
			onmo = se.loadDB2nd();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Homology homo : homos){
			String homoidvuri = se.PROMOTER_PREFIX + "#Homology_" + homo.getId();			
			Individual itemp = onmo.createIndividual(homoidvuri, onmo.getOntClass(homoclsuri));
			se.addDataProperty(onmo, homoidvuri, nameuri, homo.getName());			
		}
		se.testListIndividual(onmo, homoclsuri);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateHomology myobj = new CreateHomology();
		myobj.createhomeonts(myobj.getallhomos("epd104.dat"));

	}

}
