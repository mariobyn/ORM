package DAO;


import java.util.ArrayList;

public interface GenericDAO<T,FK> {

    public abstract Boolean save(T object);

    public abstract ArrayList<T> select (T object);

    public abstract Boolean update(T object);

    public abstract Boolean delete(T object);

    public abstract void beginTransaction();

    public abstract void endTransaction();

    public abstract void commit();

    public abstract void rollback();

}
