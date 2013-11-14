package ro.teamnet.orm;

import ORM.OperationHelper;
import DBComponents.Configuration;
import DBComponents.DbConnection;
import ObjectClass.Employee;
import ObjectClass.Job;
import junit.framework.TestCase;

import java.util.HashSet;


public class TestConnection extends TestCase {
    DbConnection db = null;
    Configuration conf;
    OperationHelper<Employee, Job> op;
    HashSet<Job> set;

    @Override
    protected void setUp() throws Exception {
        //Configuration conf = new Configuration("jdbc:mysql://localhost:3306/test","root","1234","com.mysql.jdbc.Driver","mysql");
        conf = new Configuration("jdbc:mysql://localhost:3306/test","root","1234","com.mysql.jdbc.Driver","mysql");

        set = new HashSet<Job>();
        OperationHelper<Employee,  Job> op;
        /*op = new Operations<Employee, Long, Job>(DBComponents,Employee.class, set);
        op.setTable(Employee.class);
        op.setColumns(Employee.class);
        op.createTable(); */
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /*public void testConnection(){
        // Employee e = new Employee("Ionel","Strada1","Ionel@i",null);
        DBComponents = new DbConnection<Integer,Long>(conf);
        assertNotNull(DBComponents);
    }     */

    /*public void testCreateOperation(){
        op = new Operations<Employee, Job>(DBComponents,Employee.class, set);
        op.setTable();
        op.setColumns(Employee.class);
        op.createTable();
        System.out.println(op.getClass());
        assertEquals(op.getClass(), Operations.class);
    } */



}
