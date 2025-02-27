package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;
import java.util.List;

public interface GenericRepository<T> {
    T create(T entity);
    Iterator<T> findAll();
    T findById(String id);
    T update(String id, T updatedEntity);
    void delete(String id);
}