// GenericRepositoryImpl.java
package id.ac.ui.cs.advprog.eshop.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class GenericRepositoryImpl<T> implements GenericRepository<T> {
    private final List<T> data = new ArrayList<>();

    @Override
    public T create(T entity) {
        if (((Identifiable) entity).getId() == null) {
            UUID uuid = UUID.randomUUID();
            ((Identifiable) entity).setId(uuid.toString());
        }
        data.add(entity);
        return entity;
    }

    @Override
    public Iterator<T> findAll() {
        // Return an iterator over the data list
        return data.iterator();
    }

    @Override
    public T findById(String id) {
        return data.stream()
                .filter(entity -> ((Identifiable) entity).getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public T update(String id, T updatedEntity) {
        for (int i = 0; i < data.size(); i++) {
            T entity = data.get(i);
            if (((Identifiable) entity).getId().equals(id)) {
                data.set(i, updatedEntity);
                return updatedEntity;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        data.removeIf(entity -> ((Identifiable) entity).getId().equals(id));
    }
}