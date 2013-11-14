package DAO;

import ORM.OperationHelper;
import ORM.Operations;
import DBComponents.DbConnection;

import java.util.ArrayList;
import java.util.Set;

public class GenericDAOImpl<T,FK> implements GenericDAO<T,FK>{                        //Class GenericDAOImpl that implement Generic DAO

    private OperationHelper<T,FK> op;
    //protected Class<T> classToCreate;
    public GenericDAOImpl(DbConnection db, Set<FK> set, Class classToCreate) {

        op = new Operations<T,FK>(db,classToCreate,  set);

    }

    @Override
    public Boolean save(T object) {
        return op.save(object);
    }

    @Override
    public ArrayList<T> select(T object) {
        return op.select(object);
    }

    @Override
    public Boolean update(T object) {
        return op.update(object);
    }

    @Override
    public Boolean delete(T object) {
        return op.delete(object);
    }

    public void beginTransaction(){
        op.beginTransaction();
    }

    public void endTransaction(){
        op.endTransaction();
    }

    public void commit(){
        op.commit();
    }

    public void rollback(){
        op.rollback();
    }
}
