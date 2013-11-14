package ORM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import Relations.OneToMany;
import Relations.OneToOne;
import Relations.RelationChanges;
import Annotations.Column;
import Annotations.Foreign;
import Annotations.References;
import Annotations.Select;
import Annotations.Table;
import DBComponents.DbConnection;


public abstract class OperationHelper<T, FK>{
	
	protected String query; 
	protected DbConnection conn = null;
	protected String tableName;
	protected Class<T> classToCreate = null;
	protected Hashtable<String,String[]> columns = new Hashtable<>();

	public OperationHelper(){}
	public OperationHelper(DbConnection db, Class<T> classToCreate, Set<FK> set){
		this.conn = db;
		this.classToCreate = classToCreate;
		setTable();		//setam numele tabelului
		setColumns();		//setam numele coloanelor
        createTable();
	}
	
	public void setColumns(){
		columns.clear();
		for(Field field: classToCreate.getDeclaredFields()){		//pentru fiecare camp al clasei iau toate anotatiile si le parsez	
			Annotation[] annotations = field.getDeclaredAnnotations();
			for(Annotation annotation : annotations)
                if (annotation instanceof Column) {
                    Column col = (Column) annotation;
                    String[] s = new String[6];
                    if (!col.Auto_Increment().equals("")) {
                        s[3] = col.Auto_Increment();
                    }
                    if (!col.ConstraintP().equals("")) {
                        s[2] = col.ConstraintP();
                    }
                    if (!col.NULL().equals("")) {
                        s[1] = col.NULL();
                    }
                    if (!col.Kind().equals("")) {
                        s[0] = col.Kind();
                    }
                    if (!col.Default().equals("")) {
                        s[4] = col.Default();
                    }
                    columns.put(col.name(), s);
                }
		}
	}
	
	public void setTable(){
		Annotation ann = classToCreate.getAnnotation(Table.class);		//iau annotatiile de la clasa sa aflu numele tabelului
		if(ann != null){
			tableName = ((Table) ann).name();
		}
	}

    public void beginTransaction(){
        conn.beginTransaction();
    }

    public void endTransaction(){
        conn.endTransaction();
    }

    public void commit(){
        conn.commit();
    }

    public void rollback(){
        conn.rollback();
    }
	public synchronized Boolean createTable(){
		
		query = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
		StringBuffer sb = new StringBuffer();
		Iterator<String> itCol = columns.keySet().iterator();		//iterez printre coloane si construiesc array-ul
		while(itCol.hasNext()){
			String val = itCol.next();
            if(val != null){
			    sb.append(val + " ");
                for(String st:columns.get(val)){
                    if(st != null)
                        sb.append(st).append(" ");
                }
                sb.append(",");
            }
		}
		
		query = query + sb.deleteCharAt(sb.length() - 1).toString() + ")";
			try {
				conn.executeQuery(query);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Can't execute query " + query);
			}
		setFK();
		return Boolean.TRUE;
	}
	
	protected void setFK(){
		for(Field field: classToCreate.getDeclaredFields()){
			Annotation annotation = field.getAnnotation(References.class);
			if(annotation != null){
				References col = (References) annotation;
				//System.out.println(col.column());
				if(col.set()){
					String query = "ALTER TABLE " + tableName + " ADD CONSTRAINT fk_" + new Random().nextInt(100) + " FOREIGN KEY ("+field.getName()+") REFERENCES "+col.table() + " ("+col.column()+") ON DELETE CASCADE ON UPDATE CASCADE";
					//System.out.println(query);
					conn.executeQuery(query);
				}
				
			}
		}
	}
	
	public synchronized Boolean deleteTable(){
		query = "DROP TABLE " + tableName;
		try {
			conn.executeQuery(query);
		} catch (Exception e) {
			throw new RuntimeException("Table can't be dropped");
		}
		return Boolean.TRUE;
	}
	
	public abstract Boolean save(T object);

	/**Implementeaza FactoryMethod
	 * Parametrii:relation - relatia pe care trebuie sa o facem
	 * objects: set-ul de obiecte pe care dorim sa le updatam in tabela straina
	 * object: obiectul care tine minte obiectul care contine set-ul
	 * id: id-ul obiectului care contine set-ul
	 */
	 
	protected void relationChange(RelationChanges<FK,T> relation, Set<FK> objects,T object, Object id){
		relation.change(objects,conn,object,id);
	}

    protected Boolean executeQueryInsert(String[] fieldsQuery, String[] valuesQuery,Method setId, T object,Boolean makeUpdateOnForeign,Set<FK> setForUpdate){
        String queryInsert = "INSERT INTO " + tableName + " (";
        Long id = null;
        for(String f : fieldsQuery){
            if(f != null){
                queryInsert = queryInsert + f + ",";
            }
        }
        queryInsert = queryInsert.substring(0, queryInsert.length()-1) + ") VALUES (";
        for(String f : valuesQuery){
            if(f != null){
                queryInsert = queryInsert + "'" + f + "'" + ",";
            }
        }
        queryInsert = queryInsert.substring(0, queryInsert.length()-1) + ")";

        try {
            id = (Long) conn.executeInsert(queryInsert);
            setId.invoke(object,new Object[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error can't execute query in save");
        }
        if(makeUpdateOnForeign){
            for(Field field : object.getClass().getDeclaredFields()){
                if(field.getAnnotation(Foreign.class) != null){
                    String relation = field.getAnnotation(Foreign.class).relation();
                    if(relation.toLowerCase().compareTo("one-to-many") == 0){
                        RelationChanges<FK,T> oneToMany = new OneToMany<>();
                        relationChange(oneToMany,setForUpdate, object, id);

                    }
                    if(relation.toLowerCase().compareTo("one-to-one") == 0){
                        RelationChanges<FK,T> oneToOne =  new OneToOne<>();
                        relationChange(oneToOne, setForUpdate, object, id);
                    }
                }
            }
        }

        return Boolean.TRUE;
    }
	
	
	protected String createQuerySelect(T o){
		String query = "SELECT  * FROM " + tableName + " WHERE ";
		Field[] fields = classToCreate.getDeclaredFields();
		for(Field field : fields){
			Annotation annotation = field.getAnnotation(Select.class);
			if(annotation != null){
					Select select = (Select) annotation;
					if(select.selected()){
						Method method;
						try {
							method = classToCreate.getMethod("get" + field.getName().substring(0, 1).toUpperCase()+ field.getName().substring(1, field.getName().length()), new Class[] {});
							//System.out.println(method.getName());
						} catch (NoSuchMethodException | SecurityException e1) {
							throw new RuntimeException("Can't select getter method");
						}
						
						try {
							query += " " + field.getName() + " = '" + method.invoke(o) + "' AND";
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							throw new RuntimeException("Error can't create select statement");
						}
					}
			}
		}
		query = query.substring(0,query.length() - 4);
		return query;
	}

	
	public static Method getMethod(String methodName, Class classToCreate){
		Method[] methods = classToCreate.getDeclaredMethods();
		for(Method method : methods){
			if(method.getName().toLowerCase().compareTo("set" + methodName) != 0)
				continue;
			return method;
		}
		return null;
	}
	
	public abstract ArrayList<T> select(T o);

    public abstract Boolean update(T o);

    public abstract Boolean delete(T o);

}
