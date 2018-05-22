package co.simplon.patrimoine.dao.jpa;

import javax.persistence.EntityManager;

import co.simplon.patrimoine.dao.CityDao;
import co.simplon.patrimoine.model.City;

public class CityDaoJpa implements CityDao {
	EntityManager em;

	
	public CityDaoJpa(EntityManager em) {
			this.em = em;
	}

	@Override
	public City createCity(City city) {
		em.persist(city);
		return city;
	}

	@Override
	public City getCityById(Long id) {
		return em.find(City.class, id);
	}

	@Override
	public City updateCity(City city) {
		return em.merge(city);
	}

	@Override
	public void deleteCityById(Long id) {
		em.remove(getCityById(id));
	}

}
