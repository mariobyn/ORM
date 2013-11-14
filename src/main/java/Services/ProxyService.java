package Services;


import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import Annotations.Transactional;
import Transactions.TransactionManager;

public class ProxyService implements InvocationHandler{
    private Object serviceImpl;
    TransactionManager transaction = null;
    private Object results= null;

    public ProxyService(Object impl, Service operation) {
        this.serviceImpl = impl;
        transaction = new TransactionManager(operation);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

         if(serviceImpl.getClass().isAnnotationPresent(Transactional.class)){
            transaction.beginTransaction();

            try{
                results =  method.invoke(serviceImpl, args);
            }catch (Exception e){
                transaction.rollback();
            }

            transaction.commit();
             transaction.endTransaction();
         }
        return results;
    }
}
