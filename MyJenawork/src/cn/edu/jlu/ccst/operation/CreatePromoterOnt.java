package cn.edu.jlu.ccst.operation;

import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;

import cn.edu.jlu.ccst.example.SimpleExample;
import cn.edu.jlu.ccst.model.*;

public class CreatePromoterOnt {

	/**
	 * Create Ontology of promoters
	 * @param pros
	 */
	public void creatOnt(List<Promoter> pros){
		String nameuri = "http://miuras.inf.um.es/ontologies/OGO.owl#Name";
		String authoruri = "http://miuras.inf.um.es/ontologies/promoter.owl#Author";
		String iduri = "http://miuras.inf.um.es/ontologies/OGO.owl#Identifier";
		String linkuri = "http://miuras.inf.um.es/ontologies/promoter.owl#Link";
		String locuri = "http://miuras.inf.um.es/ontologies/OGO.owl#Location";
		String titleuri = "http://miuras.inf.um.es/ontologies/promoter.owl#Title";
		
		String activeuri = "http://miuras.inf.um.es/ontologies/promoter.owl#active";
		String fromspeciesuri = "http://miuras.inf.um.es/ontologies/OGO.owl#fromSpecies";
		String hasgouri = "http://miuras.inf.um.es/ontologies/OGO.owl#hasGO";
		String hashomologyuri = "http://miuras.inf.um.es/ontologies/promoter.owl#hasHomolgy";
		String haskeyworduri = "http://miuras.inf.um.es/ontologies/promoter.owl#hasKeywords";
		String haspromoteruri = "http://miuras.inf.um.es/ontologies/promoter.owl#hasPromoter";
		String hasreferenceuri = "http://miuras.inf.um.es/ontologies/promoter.owl#hasReference";
		String hasresourceuri = "http://miuras.inf.um.es/ontologies/OGO.owl#hasResource";
		String isbelongeduri = "http://miuras.inf.um.es/ontologies/promoter.owl#isBelongedTo";
		String isexpresseduri = "http://miuras.inf.um.es/ontologies/promoter.owl#isExpressedTo";
		String istranscribeduri = "http://miuras.inf.um.es/ontologies/promoter.owl#isTranscribedTo";
		String istranslateduri = "http://miuras.inf.um.es/ontologies/OGO.owl#isTranslatedTo";
		
		String gouri = "http://miuras.inf.um.es/ontologies/OGO.owl#GO_term";
		String geneuri = "http://miuras.inf.um.es/ontologies/OGO.owl#Gene";
		String homologyuri = "http://miuras.inf.um.es/ontologies/promoter.owl#Homology";
		String keyworduri = "http://miuras.inf.um.es/ontologies/promoter.owl#Keyword";
		String taxonomyuri = "http://um.es/ncbi.owl#NCBI_1";
		String promoteruri = "http://miuras.inf.um.es/ontologies/promoter.owl#Promoter";
		String proteinuri = "http://miuras.inf.um.es/ontologies/OGO.owl#Protein";
		String referenceuri = "http://miuras.inf.um.es/ontologies/promoter.owl#Reference";
		String resourceuri = "http://miuras.inf.um.es/ontologies/OGO.owl#Resource";
		String mrnauri = "http://miuras.inf.um.es/ontologies/promoter.owl#mRNA";
		
		SimpleExample se = new SimpleExample();
		OntModel onmo = null;
		try {
			onmo = se.loadDB2nd();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index = 1;
		for(Promoter pro: pros){
			// Create new individual of promoter;
			Individual newproidv = se.CreateandGetIdv(
					promoteruri + "_" + pro.getName(),
					promoteruri, onmo);
			// Create the relation between promoter and homology
			if(pro.getHomology().getId().trim().length()>0){
				Individual homoidv = se.CreateandGetIdv(
						homologyuri + "_" + pro.getHomology().getId(),
						homologyuri, onmo);
				se.addObjProperty(onmo, newproidv.getURI(), hashomologyuri, homoidv.getURI());
			}
			//create the reference individual and the relation to promoter
			for(Reference ref : pro.getReferences()){
				Individual refidv = se.CreateandGetIdv(
						referenceuri + "_" + ref.getPubmed(),
						referenceuri, onmo);
				se.addDataProperty(onmo, refidv.getURI(), authoruri, ref.getAuther());
				se.addDataProperty(onmo, refidv.getURI(), titleuri, ref.getTitle());
				se.addDataProperty(onmo, refidv.getURI(), iduri, ref.getPubmed());
				se.addDataProperty(onmo, refidv.getURI(), locuri, ref.getLocation());
				se.addObjProperty(onmo, newproidv.getURI(), hasreferenceuri, refidv.getURI());
			}
			//create the resource individual and the relation to promoter
			for(Resource res : pro.getResources()){
				Individual residv = se.CreateandGetIdv(
						resourceuri + "_" + res.getId(),
						resourceuri, onmo);
				se.addDataProperty(onmo, residv.getURI(), nameuri, res.getDataset());
				se.addDataProperty(onmo, residv.getURI(), linkuri, res.getLink());
				se.addDataProperty(onmo, residv.getURI(), iduri, res.getId());
				se.addObjProperty(onmo, newproidv.getURI(), hasresourceuri, residv.getURI());
			}
			//create the keyword individual and the relation to promoter
			for(Keyword kwd : pro.getKeywords()){
				Individual kwdidv = se.CreateandGetIdv(
						keyworduri + "_" + kwd.getKeyword(),
						keyworduri, onmo);
				se.addDataProperty(onmo, kwdidv.getURI(), nameuri, kwd.getKeyword());
				se.addObjProperty(onmo, newproidv.getURI(), haskeyworduri, kwdidv.getURI());
			}
			//create the gene individual and the relative individuals to the gene
			//create the relation between the gene individual and the relative individuals to the gene and the promoter
			for(Gene gen: pro.getGenes()){
				//create the gene individual and the relation to promoter
				Individual genidv = se.CreateandGetIdv(
						geneuri + "_" + gen.getId(),
						geneuri, onmo);
				se.addDataProperty(onmo, genidv.getURI(), iduri, gen.getId());
				se.addObjProperty(onmo, genidv.getURI(), haspromoteruri, newproidv.getURI());
				se.addObjProperty(onmo, newproidv.getURI(), isbelongeduri, genidv.getURI());
				//create or get the go individual and the relation to gene
				for(GO ngo: gen.getGos()){
					Individual goidv = se.CreateandGetIdv(
							se.OGO_PREFIX + "#GO_" + ngo.getId(),
							gouri, onmo);
					se.addObjProperty(onmo, genidv.getURI(), hasgouri, goidv.getURI());
				}
				//create or get the taxonomy individual and the relation to gene
				Individual taxidv = se.CreateandGetIdv(
						se.NCBI_PREFIX + "#NCBI_" + gen.getTaxonomy().getId(),
						taxonomyuri, onmo);
				se.addObjProperty(onmo, genidv.getURI(), fromspeciesuri, taxidv.getURI());
				//create the mRNA and protein individual and the relation to gene
				for(int i = 0; i<=gen.getMrnas().size()-1; i++){
					String rnaid = gen.getMrnas().get(i).getId();
					if(!rnaid.equals("-")){
						Individual rnaidv = se.CreateandGetIdv(
							mrnauri + "_" + rnaid,
							mrnauri, onmo);
						se.addDataProperty(onmo, rnaidv.getURI(), iduri, rnaid);
					    se.addObjProperty(onmo, genidv.getURI(), istranscribeduri, rnaidv.getURI());
					    se.addObjProperty(onmo, newproidv.getURI(), activeuri, rnaidv.getURI());
					    String prnid = gen.getProteins().get(i).getId();
					    if(!prnid.equals("-")){
					    	Individual prnidv = se.CreateandGetIdv(
									proteinuri + "_" + rnaid,
									proteinuri, onmo);
							se.addDataProperty(onmo, prnidv.getURI(), iduri, prnid);
							se.addObjProperty(onmo, genidv.getURI(), isexpresseduri, prnidv.getURI());
							se.addObjProperty(onmo, rnaidv.getURI(), istranslateduri, prnidv.getURI());
					    }
					}
					
				}
			}
			
			System.out.println(index+++".	"+"Promoter: "+pro.getName()+" finished!");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		FilterDB myobj = new FilterDB();
//		myobj.generateepdgene("epd104.dat");
		
		ReadEPD re = new ReadEPD();
		List<Promoter> pros= re.getPromoters("epd104.dat");
//		List<Promoter> pros= re.getPromoters("test.txt");
		CreatePromoterOnt cpo = new CreatePromoterOnt();
		cpo.creatOnt(pros);

	}

}
