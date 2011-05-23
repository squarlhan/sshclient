package cn.edu.jlu.ccst.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.service.GetPromoter;

import com.opensymphony.xwork2.ActionSupport;
@Component("searchPromoterAction")
public class SearchPromoterAction extends ActionSupport{
	private GetPromoter gp;
	private List<String> resultlist_Promoter;
	private String tip;
	private String Gene;
	
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
	public String SearchPromoter() throws ClassNotFoundException{
		List<String> Promoter = gp.Query_Promoter(Gene);
		if(Promoter.size() == 0)
		{
			setTip("There is no Promoter for this Gene!");
			return ERROR;
		}else{
			setResultlist_Promoter(Promoter);
			return SUCCESS;
		}
		
	}
	public void setResultlist_Promoter(List<String> resultlist_Promoter) {
		this.resultlist_Promoter = resultlist_Promoter;
	}
	public List<String> getResultlist_Promter() {
		return resultlist_Promoter;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getTip() {
		return tip;
	}

}
