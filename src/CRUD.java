import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import Annotations.GetValue;
import Annotations.SetValue;
import Annotations.Table;
import Database.DbConnection;



public class CRUD<T,PK, FK> extends Operations<T,PK, FK>{

	public CRUD(DbConnection<?, ?> db, Class<T> classToCreate, Set<FK> set) {
		super(db, classToCreate, set);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean save(T object){
Method[] methods = classToCreate.getDeclaredMethods();		//luam fiecare metoda si construiesc 2 array-uri cu numele campurilor si valorile lor
		
		String[] fieldsQuery = new String[methods.length];
		String[] valuesQuery = new String[methods.length];
		Set<FK> setForUpdate = null;
		Boolean makeUpdateOnForeign = Boolean.FALSE;
		Annotation annotationForUpdate = classToCreate.getAnnotation(Table.class);
		Table infoAnnotation = (Table) annotationForUpdate;
		makeUpdateOnForeign = infoAnnotation.mainClass();
		int i = 0;
		Method setId = null;
		for(Method m : methods){
			Annotation annotationForGetId = m.getAnnotation(GetValue.class);
			Annotation annotationForSetId = m.getAnnotation(SetValue.class);
			if(annotationForSetId != null){
				SetValue infoForSetId = (SetValue) annotationForSetId;
				if(infoForSetId.id())
					setId = m;
			}
			if(annotationForGetId != null){
				GetValue info = (GetValue) annotationForGetId;
				if(!info.foreign())
				try {		
					i++;
					fieldsQuery[i] = info.name();
					valuesQuery[i] = m.invoke(object).toString();	//iau valoarea campului prin metoda getNume_Camp
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new RuntimeException("Error when extracting values to insert new row");
				}
				else{
					try {
						setForUpdate = (Set<FK>) m.invoke(object);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
					
			}
			
			
		
		}
		
		
		return executeQueryInsert(fieldsQuery,valuesQuery,setId,object,makeUpdateOnForeign,setForUpdate);
	}

public ArrayList<T> select(T o){
		
		query = createQuerySelect(o);
		ArrayList<T> objects = new ArrayList<T>();
		try {
			ResultSet rs = conn.select(query);
			Integer nr = rs.getMetaData().getColumnCount();

			while(rs.next()){
				T objectNew = (T) classToCreate.newInstance();
				for(int i = 1; i <= nr; i++){
					Method method = getMethod(rs.getMetaData().getColumnName(i),(Class<T>) classToCreate,SetValue.class);
					Annotation annotation = method.getAnnotation(SetValue.class);
					if(annotation != null){
						method.invoke(objectNew,rs.getObject(i));
					}
				}
				objects.add(objectNew);
			}
		} catch (SQLException | NullPointerException e) {
			throw new RuntimeException("ERROR can't execute query in operation, error at ResultSet");
		} catch (SecurityException e) {
			throw new RuntimeException("Can't set value of a field, no method defined");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Can't create object");
		} catch (InstantiationException e) {
			throw new RuntimeException("Can't create new instance");
		}
		return objects;
		 
	}


}
