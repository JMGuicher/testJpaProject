package co.simplon.patrimoine.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import co.simplon.patrimoine.dao.UserDao;
import co.simplon.patrimoine.dao.jpa.CityDaoJpa;
import co.simplon.patrimoine.dao.jpa.MonumentDaoJpa;
import co.simplon.patrimoine.dao.jpa.UserDaoJpa;


public class Main implements AutoCloseable {
	private static final String PERSISTENCE_UNIT_NAME = "jpa";
	private EntityManagerFactory factory;
	
	public Main(){
		String persistenceUnitName= "demo-jpa-1"; // defined in persistence.xml
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		for (String envName : env.keySet()) {
		  if (envName.contains("DB_USER")) {
		    configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
		  }
		  if (envName.contains("DB_PASS")) {
		    configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
		  }
		  if (envName.contains("DB_URL")) {
		    configOverrides.put("javax.persistence.jdbc.url", env.get(envName));    
		  }
		}
		 factory = Persistence.createEntityManagerFactory(persistenceUnitName
		                                            ,configOverrides);
					
	}
	public void close() {
		factory.close();
	}

	
	public static void main(String[] args) {
		try(Main app= new Main()){
//				City city = new City();
//				
//				//System.out.println(app.createCity());
//				Monument monument = new Monument();
//				//User user = new User();
//				//app.findAllUser(0, 0);
//				//app.createUser();
//				
//				app.deleteMonumentById();
			
			//création d'une nouvelle ville DaoJpa
			EntityManager em= app.factory.createEntityManager();
			CityDaoJpa cityDaoJpa= new CityDaoJpa(em);
			City city = new City("Paris", 12., 32.);
			System.out.println("création de la cité : " + cityDaoJpa.createCity(city));
			
			//mise à jour latitude et longitude DaoJpa
			city.setLatitude(19.);
			city.setLongitude(23.);
			City cityUpdated= cityDaoJpa.updateCity(city);
			System.out.println("mise à jour de la cité : " + cityUpdated);
			
			//lecture de la cité par id DaoJpa
			City cityFound = cityDaoJpa.getCityById(city.getId());
			System.out.println("Lecture de la cité par son id : " + cityFound);
			
			//suppression d'une cité via l'id DaoJpa
			cityDaoJpa.deleteCityById(cityFound.getId());
			
			//création d'un nouveau monument
			MonumentDaoJpa monumentDaojpa= new MonumentDaoJpa(em);
			Monument monument= new Monument("tour eiffel", city);
			System.out.println("création du monument: " + monument);
			
			
			//lecture d'un monument DaoJpa
			Monument monumentFound= monumentDaojpa.getMonumentById(monument.getId());
			System.out.println("Lecture d'un monument par l'id: " + monumentFound);
			
			//suppression d'un monument par l'id en DaoJpa
			monumentDaojpa.deleteMonumentById(monumentFound.getId());
			
			//création d'un user
			UserDaoJpa userDaoJpa= new UserDaoJpa(em);
			User user= new User("bidule");
			System.out.println("Création du user: " + user);
			
			em.close();
		}
		
		
		}
	
	public List<Monument> findAllMonument(int first, int size) {
		EntityManager em= factory.createEntityManager();
		List<Monument> list= em.createNamedQuery("Monument.findAll", Monument.class)
		  .setFirstResult(first).setMaxResults(size).getResultList();
		em.close();
		return list;
		
	}
	
	public List<User> findAllUser(int first, int size) {
		EntityManager em= factory.createEntityManager();
		List<User> list= em.createNamedQuery("User.findAll", User.class)
		  .setFirstResult(first).setMaxResults(size).getResultList();
		em.close();
		return list;
		
	}
	
	public void deleteMonumentById () {
		EntityManager em= factory.createEntityManager();
		em.getTransaction().begin();
		int nbObjectsDeleted = em.createNamedQuery("Monument.deleteById").setParameter("id", 1L).executeUpdate();
		System.out.println("Nb objets supprimés : " + nbObjectsDeleted);
		em.getTransaction().commit();
		em.close();
	}
	
	


	public void SelectCityWithJpql () {
		EntityManager em= factory.createEntityManager();
		TypedQuery<City> query = em.createQuery("SELECT c FROM City AS c WHERE c.name=: nameParam", City.class);
		query.setParameter("nameParam", "Paris");
		for (City c : query.getResultList()) {
		System.out.println(c);
		}
		em.close();	
	}
		
	public void SelectMonumentWithJpql () {
		EntityManager em= factory.createEntityManager();
		TypedQuery<Monument> query = em.createQuery("SELECT c FROM Monument AS c WHERE c.name=: nameParam", Monument.class);
		query.setParameter("nameParam", "Montparnasse");
		for (Monument c : query.getResultList()) {
		System.out.println(c);
		}
		em.close();	
	}
	
	public void SelectUserWithJpql () {
		EntityManager em= factory.createEntityManager();
		TypedQuery<User> query = em.createQuery("SELECT c FROM User AS c WHERE c.name=: nameParam", User.class);
		query.setParameter("nameParam", "Pierre");
		for (User c : query.getResultList()) {
		System.out.println(c);
		}
		em.close();	
	}
	
	public City createCity() {
	    EntityManager em= factory.createEntityManager();
	    City city= new City("Atlantis", 0, 0.5);
	    city= create(em, city);
	    em.close();
	    return city;
	}
	public City create(EntityManager em, City city) {
	    em.getTransaction().begin();
	    em.persist(city);
	    em.getTransaction().commit();
	    return city;
	
}

	public City createCityAndUpdate() {
		  EntityManager em= factory.createEntityManager();
		  City city= new City("Vannes", 100, 10);
		  em.getTransaction().begin();
		  em.persist(city);
		  city.setLongitude(500.);
		  em.getTransaction().commit();// MAGIC HAPPENS HERE !
		  em.close();
		  return city;
	}

	public City readCity() {
	    EntityManager em= factory.createEntityManager();
	    City city= readCity(em, 4L);
	    em.close();
	    return city;
	}
	public City readCity(EntityManager em, Long id) {
	    return em.find(City.class, id);
	}
	
	public City updateCity() {
	    return update(new City(2L,"PaRiS", -1., -2.));
	}
	public City update(City city) {
	    EntityManager em= factory.createEntityManager();
	    em.getTransaction().begin();
	    city= em.merge(city);
	    em.getTransaction().commit();
	    return city;
	}
	
	public City deleteCity() {
		EntityManager em= factory.createEntityManager();
		 em.getTransaction().begin();
		 City city= em.find(City.class, 2L);
		em.remove(city);		 
		em.getTransaction().commit();
		return city;
	}
	
	public City deleteCity1() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		City city = new City(1L, "autre", 10, 2);
		City citie2= em.merge(city);
		em.remove(citie2);
		em.getTransaction().commit();
		em.close();
		return city;

	}
	
	public Monument createMonument() {
	    EntityManager em= factory.createEntityManager();
	    Monument monument= new Monument("Saint Anne", createCity());
	    monument= create(em, monument);
	    em.close();
	    return monument;
	}
	
	public Monument create(EntityManager em, Monument monument) {
	    em.getTransaction().begin();
	    em.persist(monument);
	    em.getTransaction().commit();
	    return monument;
	    
	}
	
	public Monument readMonument() {
	    EntityManager em= factory.createEntityManager();
	    Monument monument= readMonument(em, 1L);
	    em.close();
	    return monument;    
	}
	
	public Monument readMonument(EntityManager em, Long id) {
	    return em.find(Monument.class, id);
	}
	
	public Monument deleteMonument() {
		EntityManager em= factory.createEntityManager();
		 em.getTransaction().begin();
		 Monument monument= em.find(Monument.class, 702L);
		em.remove(monument);		 
		em.getTransaction().commit();
		return monument;
	}
	/*
	public Monument updateMonument() {
	    return update(new Monument("ToUr MonTparNasse", "paris"));
	}
	public Monument update(Monument monument, City city) {
	    EntityManager em= factory.createEntityManager();
	    em.getTransaction().begin();
	    monument= em.merge(monument);
	    em.getTransaction().commit();
	    return monument;
	}
	*/
	public User createUser() {
	    EntityManager em= factory.createEntityManager();
	    User user= new User("Relou");
	    City city= create(em, new City("Paris", 32, 21));
	    Monument monument= create (em, new Monument("tour de pise", city));
	    user.addMonument(monument);
	    
	    user= create(em, user);
	    em.close();
	    return user;
	}
	public User create(EntityManager em, User user) {
	    em.getTransaction().begin();
	    em.persist(user);
	    em.getTransaction().commit();
	    return user;
	    
	}
}
