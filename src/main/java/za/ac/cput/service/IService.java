package za.ac.cput.service;

import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {
    T create(T entity);
    Optional<T> read(ID id);
    T update(T entity);
    boolean delete(ID id);
    List<T> getAll();
}