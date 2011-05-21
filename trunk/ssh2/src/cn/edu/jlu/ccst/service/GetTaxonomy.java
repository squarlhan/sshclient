package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jlu.ccst.model.Taxonomy;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
@Component("gt")
@Transactional
public class GetTaxonomy {
	private SimpleExample se;
	private EntityManager em;
	private Taxonomy taxonomy;
	
	// keyword----Taxonomy 返回Taxonomy列表
	@SuppressWarnings("unchecked")
	public List<String> Query_Taxonomy(String keyword)
			throws ClassNotFoundException {
		List<String> Taxonomy_list = new ArrayList();
		String querystatement = 
			"select t.ID from Taxonomy t where t.Name like '%"+keyword.trim()+"%'";	
		javax.persistence.Query query = em.createQuery(querystatement);
		System.out.println(query.getResultList());
		Taxonomy_list = query.getResultList();
		return Taxonomy_list;
	}
	
	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}
	@PersistenceContext
	@Resource
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public EntityManager getEm() {
		return em;
	}
@Resource
	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}

	public Taxonomy getTaxonomy() {
		return taxonomy;
	}

}
