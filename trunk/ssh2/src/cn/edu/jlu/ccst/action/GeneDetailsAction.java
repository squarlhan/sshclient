package cn.edu.jlu.ccst.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.service.GetGO;
import cn.edu.jlu.ccst.service.GetPromoter;
import cn.edu.jlu.ccst.service.GetProtein;
import cn.edu.jlu.ccst.service.GetTaxonomy;
import cn.edu.jlu.ccst.service.GetmRNA;

import com.opensymphony.xwork2.ActionSupport;

@Component("geneDetailsAction")
public class GeneDetailsAction extends ActionSupport {

	private String tip;
	private String Gene;

	private GetPromoter gp;
	private GetGO gG;
	private GetTaxonomy gt;
	private GetmRNA gm;
	private GetProtein gP;

	private List<String> resultlist_Promoter;
	private List<String> resultlist_Go;
	private List<String> resultlist_Tax;
	private List<String> resultlist_mRNA;
	private List<String> resultlist_Protein;

	private List<String> Presult;
	private List<String> Goresult;
	private List<String> Tresult;
	private List<String> mresult;
	private List<String> Proresult;

	public String GeneDetails() throws ClassNotFoundException {
		List<String> Promoter = gp.Query_Promoter(Gene);
		if (Promoter.size() == 0) {
			setTip("There is no Promoter for this Gene!");
		} else {
			Presult = new ArrayList();
			setResultlist_Promoter(Promoter);
			int size = Promoter.size();
			int i = 0;
			while (i < size) {
				String promoter = Promoter.get(i);
				Presult.add(promoter);
				List<String> Promoter_name = gp.Query_Promoter_name(promoter);
				Presult.addAll(Promoter_name);
				i++;
			}
		}
		List<String> GO = gG.Query_Go(Gene);
		if (GO.size() == 0) {
			setTip("There is no GO for this Gene!");
		} else {
			Goresult = new ArrayList();
			setResultlist_Go(GO);
			int size = GO.size();
			int i = 0;
			while (i < size) {
				String go = GO.get(i);
				Goresult.add(go);
				List<String> Go_Item = gG.Query_Go_item(go);
				Goresult.addAll(Go_Item);
				i++;
			}
		}
		List<String> Tax = gt.Query_TaxonomyBYGene(Gene);
		if (Tax.size() == 0) {
			setTip("There is no Taxonomy has this Gene!");
		} else {
			Tresult = new ArrayList();
			setResultlist_Tax(Tax);
			int size = Tax.size();
			int i = 0;
			while (i < size) {
				String tax = Tax.get(i);
				Tresult.add(tax);
				List<String> Tax_name = gt.Query_Taxonomy_label(tax);
				Tresult.addAll(Tax_name);
				i++;
			}
		}
		List<String> mRNA = gm.Query_mRNAByP(Promoter);
		if (mRNA.size() == 0) {
			setTip("There is no mRNA has this Gene!");
		} else {
			mresult = new ArrayList();
			setResultlist_mRNA(mRNA);
			int size = mRNA.size();
			int i = 0;
			while (i < size) {
				String mrna = mRNA.get(i);
				mresult.add(mrna);
				List<String> mRNA_name = gm.Query_mRNA_name(mrna);
				mresult.addAll(mRNA_name);
				List<String> mRNA_id = gm.Query_mRNA_id(mrna);
				mresult.addAll(mRNA_id);
				i++;
			}
		}
		List<String> Pro = gP.Query_Protein(Gene);
		if (Pro.size() == 0) {
			setTip("There is no Protein has this Gene!");
		} else {
			Proresult = new ArrayList();
			setResultlist_Protein(Pro);
			int size = Pro.size();
			int i = 0;
			while (i < size) {
				String pro = Pro.get(i);
				Proresult.add(pro);
				List<String> Pro_name = gP.Query_Protein_name(pro);
				Proresult.addAll(Pro_name);
				List<String> Pro_id = gP.Query_Protein_id(pro);
				Proresult.addAll(Pro_id);
				i++;
			}
		}
		return SUCCESS;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

	public void setResultlist_Promoter(List<String> resultlist_Promoter) {
		this.resultlist_Promoter = resultlist_Promoter;
	}

	public List<String> getResultlist_Promoter() {
		return resultlist_Promoter;
	}

	public void setResultlist_Go(List<String> resultlist_Go) {
		this.resultlist_Go = resultlist_Go;
	}

	public List<String> getResultlist_Go() {
		return resultlist_Go;
	}

	public List<String> getPresult() {
		return Presult;
	}

	public void setPresult(List<String> presult) {
		Presult = presult;
	}

	public String getGene() {
		return Gene;
	}

	public void setGene(String gene) {
		Gene = gene;
	}

	public GetPromoter getGp() {
		return gp;
	}

	@Resource
	public void setGp(GetPromoter gp) {
		this.gp = gp;
	}

	public void setGoresult(List<String> goresult) {
		Goresult = goresult;
	}

	public List<String> getGoresult() {
		return Goresult;
	}

	public GetGO getgG() {
		return gG;
	}

	@Resource
	public void setgG(GetGO gG) {
		this.gG = gG;
	}

	public void setTresult(List<String> tresult) {
		Tresult = tresult;
	}

	public List<String> getTresult() {
		return Tresult;
	}
@Resource
	public void setGm(GetmRNA gm) {
		this.gm = gm;
	}

	public GetmRNA getGm() {
		return gm;
	}

	public GetTaxonomy getGt() {
		return gt;
	}

	@Resource
	public void setGt(GetTaxonomy gt) {
		this.gt = gt;
	}

	public void setResultlist_mRNA(List<String> resultlist_mRNA) {
		this.resultlist_mRNA = resultlist_mRNA;
	}

	public List<String> getResultlist_mRNA() {
		return resultlist_mRNA;
	}
	public List<String> getResultlist_Tax() {
		return resultlist_Tax;
	}

	public void setResultlist_Tax(List<String> resultlist_Tax) {
		this.resultlist_Tax = resultlist_Tax;
	}

	public void setMresult(List<String> mresult) {
		this.mresult = mresult;
	}

	public List<String> getMresult() {
		return mresult;
	}
@Resource
	public void setgP(GetProtein gP) {
		this.gP = gP;
	}

	public GetProtein getgP() {
		return gP;
	}

	public void setResultlist_Protein(List<String> resultlist_Protein) {
		this.resultlist_Protein = resultlist_Protein;
	}

	public List<String> getResultlist_Protein() {
		return resultlist_Protein;
	}

	public void setProresult(List<String> proresult) {
		Proresult = proresult;
	}

	public List<String> getProresult() {
		return Proresult;
	}
}
