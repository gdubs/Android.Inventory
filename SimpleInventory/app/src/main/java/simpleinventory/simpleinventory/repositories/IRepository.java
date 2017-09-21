package simpleinventory.simpleinventory.repositories;

import java.util.List;

/**
 * Created by gdubs on 6/28/2016.
 */
public interface IRepository<T> {
    Integer add(T item);
    boolean update(T item);
    boolean remove(T item);
    List<T> getAll();
    List<T> getById(Object Id);
}
