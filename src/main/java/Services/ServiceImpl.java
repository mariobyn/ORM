package Services;


import Annotations.Transactional;
import DAO.GenericDAO;

import java.util.Iterator;
import java.util.Set;

@Transactional
public class ServiceImpl<T> implements Service<T>{

    private GenericDAO dao;

    public ServiceImpl(GenericDAO dao){
        this.dao = dao;
    }

    public void commit(){
        dao.commit();
    }

    public void rollback(){
        dao.rollback();
    }

    public void beginTransaction(){
        dao.beginTransaction();
    }

    public void endTransaction(){
        dao.endTransaction();
    }
    public void save(Set<T> object){
        Iterator<T> it = object.iterator();
        while(it.hasNext()){
        dao.save(it.next());
        }
    }

    public void delete(T object){
        dao.delete(object);
    }

    public void update(T object){
        dao.update(object);
    }

    public void select(T object){
        dao.select(object);
    }
}
