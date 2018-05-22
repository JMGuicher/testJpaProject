package co.simplon.patrimoine.dao.jpa;

import javax.persistence.EntityManager;

import co.simplon.patrimoine.dao.MonumentDao;
import co.simplon.patrimoine.model.Monument;

public class MonumentDaoJpa implements MonumentDao {
	EntityManager em;
	
	public MonumentDaoJpa(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Monument createMonument(Monument monument) {
		em.persist(monument);
		return monument;
	}

	@Override
	public Monument getMonumentById(Long id) {
		return em.find(Monument.class, id);
		
	}

	@Override
	public Monument updateMonument(Monument monument) {
		// TODO Auto-generated method stub
		return em.merge(monument);
	}

	@Override
	public void deleteMonumentById(Long id) {
		em.remove(getMonumentById(id));
		
		
	}

}
