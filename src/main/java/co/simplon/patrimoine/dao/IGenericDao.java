package co.simplon.patrimoine.dao;





/*
 * Je crée une interface. Elle n'effectue aucune implémentation, elle manipule des références vers des objets et elle est PUBLIC
 * Les classes CityDAO, MonumentDAO et UserDAO implémentent cette interface
 */
public interface IGenericDao<T>{
	public T create(T instance);
	public T update(T instance);
	public T getById(long id);
	public T deleteById(long id);

}
