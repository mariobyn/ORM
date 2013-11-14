package Services;


import java.util.Set;

public interface Service<T> {

    public abstract void save(Set<T> object);

    public abstract void delete(T object);

    public abstract void update(T object);

    public abstract void select(T object);

    public abstract void beginTransaction();

    public abstract void endTransaction();

    public abstract void commit();

    public abstract void rollback();
}
