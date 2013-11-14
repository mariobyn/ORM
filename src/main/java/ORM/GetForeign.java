package ORM;

import Annotations.Id;
import Annotations.References;
import Annotations.SetValue;
import DBComponents.DbConnection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: marius.baisan
 * Date: 7/15/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
class GetForeign<T>{

    public static <T> Set<T> getForeignValues(Object realObj,Object[] objects) throws IllegalAccessException, SQLException, InvocationTargetException, InstantiationException {
        Object id = null;
        String tableName = "";
        String column = "";
        Class classToCreate = null;
        for(Field field : realObj.getClass().getDeclaredFields()){                  //extrag id-ul pentru a face select dupa id in tabela straina
            if(field.isAnnotationPresent(Id.class)){
                field.setAccessible(true);
                id = field.get(realObj);
            }
            if(field.isAnnotationPresent(References.class)){
                tableName = ((References) field.getAnnotation(References.class)).table();      //numele tabelului din care se face select
                column = ((References) field.getAnnotation(References.class)).column();          //numele coloanei straine
                classToCreate = ((References) field.getAnnotation(References.class)).foreignClass();   //numele clasei pentru a face obiecte
            }
        }
        Statement stmt = DbConnection.conn.createStatement();
        Set<T> set = new HashSet<T>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE " + column + "='" + id + "'");
        Integer nr = rs.getMetaData().getColumnCount();

        while(rs.next()){         //parsez rs si creez obiecte pentru fiecare rezultat si le adaug intr-un set.
            assert classToCreate != null;
            T objectNew = (T) classToCreate.newInstance();
            for(int i = 1; i <= nr; i++){
                Method method = OperationHelper.getMethod(rs.getMetaData().getColumnName(i), classToCreate);
                if(method != null){
                    Annotation annotation = method.getAnnotation(SetValue.class);
                    if(annotation != null){
                        method.invoke(objectNew,rs.getObject(i));
                    }
                }
            }
            set.add(objectNew);
        }
        return set;
    }
}
