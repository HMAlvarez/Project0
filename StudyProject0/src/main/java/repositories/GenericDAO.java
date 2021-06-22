package repositories;

import java.util.List;

public interface GenericDAO<T> {
	
	public T add(T t);
	
	public T getById(Integer i);
	
	public List<T> getAll();
	
	public boolean update(T t);
	
	public boolean delete(T t);

}
