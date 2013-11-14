package ORM;

import DAO.GenericDAO;
import DAO.GenericDAOImpl;
import DBComponents.Database;
import DBComponents.DbConnection;
import DBComponents.MySQL;
import ObjectClass.Employee;
import Services.ProxyService;
import Services.Service;
import Services.ServiceImpl;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;


public class App {
	
	public static void main (String args[]) throws InstantiationException, IllegalAccessException {

        Database db = new MySQL();                                       //Set up a database
        db.setLink("jdbc:mysql://localhost:3306");
        db.setDbName("test");
        db.setUser("root");
        db.setPassword("1234");
        db.setDriver("com.mysql.jdbc.Driver");
        db.setType("mysql");

        DbConnection conn = new DbConnection(db);

		Employee employee = new Employee(new Long(1),"Marius","Adresa 1","marius@m.com",null);
        Employee employee2 = new Employee(new Long(2),"Marius2","Adresa 1","marius@m.com",null);
       // Employee employee3 = new Employee(new Long(1),"Marius","Adresa 1","marius@m.com",null);
        GenericDAO<Employee,Object> operation= new GenericDAOImpl<Employee,Object>(conn,null,Employee.class);
        Service<Employee> service = new ServiceImpl<>(operation);
        Service<Employee>   serviceProxified = (Service<Employee>) Proxy.newProxyInstance(Service.class.getClassLoader(),
                new Class<?>[]{Service.class},new ProxyService(service, (Service) new ServiceImpl<Employee>(operation)));
        Set<Employee> eh= new HashSet<Employee>();
        eh.add(employee);
        eh.add(employee2);
        //eh.add(employee3);
        serviceProxified.save(eh);
        //serviceProxified.save(employee2);
       // serviceProxified.save(employee3);
        System.out.println(employee.getAddress());
        /*Service<Employee> service = new ServiceImpl<>(operation);
        service.save(employee);
        System.out.println(employee.getAddress());

		/*Configuration conf = new Configuration("jdbc:mysql://localhost:3306/test","root","1234","com.mysql.jdbc.Driver","mysql");
		DbConnection<?, ?> DBComponents = new DbConnection<Integer, Long>(conf);
		Employee2 emp2 = new Employee2("Marius","strada 2", "marius@m.com");
        OperationHelper<Employee2,Object> opemp2 = new Operations<Employee2,Object>(conn,Employee2.class,null);
        opemp2.setTable(Employee2.class);
        opemp2.setColumns(Employee2.class);
        opemp2.createTable();
        opemp2.save(emp2);
        System.out.println(emp2.getAddress());/*
        HashSet<Job> set = new HashSet<Job>();
		//set.add(Job.class);
		OperationHelper<Employee, Long,Job> op = new Operations<Employee,Long,Job>(conn,Employee.class, set);
		op.setTable(Employee.class);
		op.setColumns(Employee.class);
		op.createTable();
        OperationHelper<Job,Long,Object> op2 = new Operations<Job,Long,Object>(conn,Job.class,null);
        op2.setTable(Job.class);
        op2.setColumns(Job.class);
        op2.createTable();
		//System.out.println("Table created");
		Job j = new Job("Programmer","Bucharest");
		set.add(j);
        //for(int i = 1;i<=1000;i++){
        j = new Job("Programmer2","Bucharest2");
        set.add(j);

        //}
		Employee e = new Employee("Ionel","Strada 1", "Ionel@i.com",set);
        if(op.save(e)){
            System.out.println("Row inserted");
        }
        //e.setAddress("Strada2");
        op.delete(e);
		/*Employee e3;
        e3 = createProxy(e);
        Set<Job> results  = e3.getJob();
        Iterator it = results.iterator();
		while(it.hasNext()){
            System.out.println(((Job)it.next()).getName());
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
		OperationHelper<Job,Long,Object> op2 = new OperationHelper<Job,Long,Object>(DBComponents,Job.class,null);
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
		/*if(DBComponents.close())
			System.out.println("Connection closed");   */
	}
	public static <T> T createProxy(T obj) {
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
        // the object is ready to be used - return it
        return (T) e.create();
    }

}
