package cn.edu.jlu.ccst.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.service.GetReference;

import com.opensymphony.xwork2.ActionSupport;

@Component("promoterDetailsAction")
public class PromoterDetailsAction extends ActionSupport {
	private String Promoter;
	private String tip;
	private List<String> resultlist_Reference;
	private GetReference gr;
	private List<String> Refresult;

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

}
