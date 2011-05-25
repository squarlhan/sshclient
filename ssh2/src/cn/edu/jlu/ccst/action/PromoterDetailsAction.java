package cn.edu.jlu.ccst.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.service.GetHomology;
import cn.edu.jlu.ccst.service.GetKeyword;
import cn.edu.jlu.ccst.service.GetReference;
import cn.edu.jlu.ccst.service.GetResource;

import com.opensymphony.xwork2.ActionSupport;

@Component("promoterDetailsAction")
public class PromoterDetailsAction extends ActionSupport {
	private String Promoter;
	private String tip;
	private List<String> resultlist_Reference;
	private List<String> resultlist_Resource;
	private List<String> resultlist_Homology;
	private List<String> resultlist_Keyword;
	private GetReference gr;
	private GetResource gres;
	private GetHomology gh;
	private GetKeyword gk;
	private List<String> Refresult;
	private List<String> Resresult;
	private List<String> Hresult;
	private List<String> Kresult;

	public String PromoterDetails() throws ClassNotFoundException {
		List<String> Reference = gr.Query_Reference(Promoter);
		if(Reference.size() == 0){
			tip = "There is no Reference for this Promoter!";
		}else{
			Refresult = new ArrayList();
			setResultlist_Reference(Reference);
			int size = Reference.size();
			int i = 0;
			while (i < size) {
				String ref = Reference.get(i);
				Refresult.add(ref);
				List<String> Ref_id = gr.Query_Reference_id(ref);
				Refresult.addAll(Ref_id);
				List<String> Ref_title = gr.Query_Reference_title(ref);
				Refresult.addAll(Ref_title);
				List<String> Ref_author = gr.Query_Reference_author(ref);
				Refresult.addAll(Ref_author);
				List<String> Ref_location = gr.Query_Reference_location(ref);
				Refresult.addAll(Ref_location);
				i++;
			}
		}
		List<String> Resource = gres.Query_Resource(Promoter);
		if(Resource.size() == 0){
			tip = "There is no Resource for this Promoter!";
		}else{
			Resresult = new ArrayList();
			setResultlist_Resource(Resource);
			int size = Resource.size();
			int i = 0;
			while (i < size) {
				String resource = Resource.get(i);
				Resresult.add(resource);
				List<String> resource_name = gres.Query_Resource_name(resource);
				Resresult.addAll(resource_name);
				List<String> resource_id = gres.Query_Resource_id(resource);
				Resresult.addAll(resource_id);
				List<String> resource_link = gres.Query_Resource_link(resource);
				Resresult.addAll(resource_link);
				i++;
			}
		}
		List<String> Homology = gh.Query_Homology(Promoter);
		if(Homology.size() == 0){
			tip = "There is no Homology for this Promoter!";
		}else{
			Hresult = new ArrayList();
			setResultlist_Homology(Homology);
			int size = Homology.size();
			int i = 0;
			while (i < size) {
				String homology = Homology.get(i);
				Hresult.add(homology);
				List<String> homology_name = gh.Query_Homology_name(homology);
				Hresult.addAll(homology_name);
				
				i++;
			}
		}
		List<String> Keyword = gh.Query_Homology(Promoter);
		if(Keyword.size() == 0){
			tip = "There is no Keyword for this Promoter!";
		}else{
			Kresult = new ArrayList();
			setResultlist_Keyword(Keyword);
			int size = Keyword.size();
			int i = 0;
			while (i < size) {
				String key = Keyword.get(i);
				Kresult.add(key);
				List<String> key_keys = gk.Query_Keyword_Keywords(key);
				Kresult.addAll(key_keys);				
				i++;
			}
		}
		return SUCCESS;
	}

	public GetReference getGr() {
		return gr;
	}

	@Resource
	public void setGr(GetReference gr) {
		this.gr = gr;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

	public String getPromoter() {
		return Promoter;
	}

	public void setPromoter(String promoter) {
		Promoter = promoter;
	}

	public void setResultlist_Reference(List<String> resultlist_Reference) {
		this.resultlist_Reference = resultlist_Reference;
	}

	public List<String> getResultlist_Reference() {
		return resultlist_Reference;
	}

	public void setRefresult(List<String> refresult) {
		Refresult = refresult;
	}

	public List<String> getRefresult() {
		return Refresult;
	}

	public void setResultlist_Resource(List<String> resultlist_Resource) {
		this.resultlist_Resource = resultlist_Resource;
	}

	public List<String> getResultlist_Resource() {
		return resultlist_Resource;
	}
@Resource
	public void setGres(GetResource gres) {
		this.gres = gres;
	}

	public GetResource getGres() {
		return gres;
	}

	public void setResresult(List<String> resresult) {
		Resresult = resresult;
	}

	public List<String> getResresult() {
		return Resresult;
	}

	public void setResultlist_Homology(List<String> resultlist_Homology) {
		this.resultlist_Homology = resultlist_Homology;
	}

	public List<String> getResultlist_Homology() {
		return resultlist_Homology;
	}
@Resource
	public void setGh(GetHomology gh) {
		this.gh = gh;
	}

	public GetHomology getGh() {
		return gh;
	}

	public void setHresult(List<String> hresult) {
		Hresult = hresult;
	}

	public List<String> getHresult() {
		return Hresult;
	}

	public void setResultlist_Keyword(List<String> resultlist_Keyword) {
		this.resultlist_Keyword = resultlist_Keyword;
	}

	public List<String> getResultlist_Keyword() {
		return resultlist_Keyword;
	}
	@Resource
	public void setGk(GetKeyword gk) {
		this.gk = gk;
	}

	public GetKeyword getGk() {
		return gk;
	}

	public void setKresult(List<String> kresult) {
		Kresult = kresult;
	}

	public List<String> getKresult() {
		return Kresult;
	}

}
