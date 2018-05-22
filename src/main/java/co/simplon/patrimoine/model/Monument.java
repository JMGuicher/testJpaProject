package co.simplon.patrimoine.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name="MONUMENTS")
@NamedQueries({
	@NamedQuery(name = "Monument.findAll", query = " SELECT c FROM Monument c "),
	@NamedQuery(name = "Monument.deleteById", query = " DELETE FROM Monument c WHERE c.id = :id") })

public class Monument {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monuments_generator")
	@SequenceGenerator(name="monuments_generator", sequenceName = "monuments_seq", allocationSize=50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column (name= "name")
    private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city")
	private City city;
	
	@ManyToMany(mappedBy="monuments")
	private Set<User> users = new HashSet<User>();

    public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Monument(String name, City city) {
	  super();
	  this.name = name;
	  this.city = city;
    }
    public Monument() {
    }
    public Long getId() {
	  return this.id;
    }
    public void setId(Long id) {
	  this.id = id;
    }
    public String getName() {
	  return this.name;
    }
    public void setName(String name) {
	  this.name = name;
    }
    /*
    public City getCity() {
	  return city;
    }
  
    public void setCity(City city) {
	  this.city = city;
    }
    */
    @Override
    public String toString() {
	  return "Monument [id=" + id + ", name=" + name
	      + ", city=" /*+ city */+ "]";
    }
	public Set<User> getUsers() {
		// TODO Auto-generated method stub
		return this.users;
	}
}