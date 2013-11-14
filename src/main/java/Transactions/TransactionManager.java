package Transactions;


import ORM.Operations;
import Services.Service;
import Services.ServiceImpl;

public class TransactionManager {

    ThreadLocal localThread = new ThreadLocal();
    Service operation  = null;

    public TransactionManager(Service op){
        operation = op;
    }

    public void beginTransaction(){
        try{
           localThread.set(1);
            operation.beginTransaction();
        }catch (Exception e){
            throw new RuntimeException("Can't begin transaction");
        }
    }

    public void endTransaction(){
        try{
            localThread.remove();
            operation.endTransaction();
        }catch (Exception e){
            throw new RuntimeException("Can't end transaction");
        }
    }

    public void commit(){
        try{
            operation.commit();
        }catch (Exception e){
            throw new RuntimeException("Can't commit changes");
        }

    }

    public void rollback(){
        try{
            operation.rollback();
        }catch (Exception e){
             e.printStackTrace();
            throw new RuntimeException("Can't rollback changes");
        }

    }
}
