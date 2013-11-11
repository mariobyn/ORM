import java.util.HashSet;
import java.util.Set;

import Database.Configuration;
import Database.DbConnection;


public class App {
	
	public static void main (String args[]){
		
		Configuration conf = new Configuration("jdbc:mysql://localhost:3306/test","root","1234","com.mysql.jdbc.Driver","mysql");
		DbConnection<?, ?> db = new DbConnection<Integer, Long>(conf);
		Set<Job> set = new HashSet<Job>();
		//set.add(Job.class);
		Operations<Employee, Long,Job> op = new CRUD<Employee,Long,Job>(db,Employee.class, set);
		op.setTable(Employee.class);
		op.setColumns(Employee.class);
		op.createTable();
		//System.out.println("Table created");
		Job j = new Job("Programmer","Bucharest");
		set.add(j);
		Employee e = new Employee("Ionel","Strada 1", "Ionel@i.com",set);
		/*Operations<Employee, Long, Job> e3 = createProxy(op);
		e3.select(e);
		System.out.println(e3);*/
		if(op.save(e) == true){
			System.out.println("Row inserted");
		}
		/*ArrayList<Employee> arr = op.select(e);
		Iterator<Employee> it = arr.iterator();
		while(it.hasNext()){
			Employee e2 = (Employee) it.next();
			System.out.println(e2.getJob());
		}
		//System.out.println(e.getJob().size());
		/*while(e.getJob().iterator().hasNext()){
			System.out.println(e.getJob().iterator().next().getAddress());
		}
		/*ArrayList<Employee> em = op.select(e);
		for(Employee ee : em){
			System.out.println(ee.getId());
		}
		Operations<Job,Long,Object> op2 = new Operations<Job,Long,Object>(db,Job.class,null);
		op2.setTable(Job.class);
		op2.setColumns(Job.class);
		op2.createTable();
		Job j = new Job("Programmer","Bucharest");
		set.add(j);
		e.setJob(set);
		System.out.println(e.getJob().size());
		op.save(e);
		//set.add(j);
		//System.out.println(set);
		/*if(op.deleteTable() == true)
			System.out.println("Table deleted");*/
		if(db.close())
			System.out.println("Connection closed");
	}
	/*public static <T> T createProxy(T obj) {
        // this is the main cglib api entry-point
        // this object will 'enhance' (in terms of CGLIB) with new capabilities
        // one can treat this class as a 'Builder' for the dynamic proxy
        Enhancer e = new Enhancer();

        // the class will extend from the real class
        e.setSuperclass(obj.getClass());
        // we have to declare the interceptor  - the class whose 'intercept'
        // will be called when any method of the proxified object is called.
        e.setCallback(new ProxyListener(obj));
        // now the enhancer is configured and we'll create the proxified object
        T proxifiedObj = (T) e.create();
        // the object is ready to be used - return it
        return proxifiedObj;
    }*/

}
