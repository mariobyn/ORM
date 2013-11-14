package ObjectClass;
import java.util.HashSet;
import java.util.Set;

import Annotations.Column;
import Annotations.Foreign;
import Annotations.GetValue;
import Annotations.Id;
import Annotations.ParamType;
import Annotations.References;
import Annotations.Select;
import Annotations.SetValue;
import Annotations.Table;


@Table(name = "Employees",mainClass = true)
public class Employee {
	
	
	@Id
	@Column(name = "id", Kind = "BigInt(10)", ConstraintP = "PRIMARY KEY", 
			 NULL = "NOT NULL")
	private Long id = (long) 0;
	
	@Column(name = "name", Kind = "varchar(128)", NULL = "NOT NULL")
	@Select(selected = true)
	private String name;
	
	@Column(name = "address", Kind = "varchar(128)", NULL = "NOT NULL")
	@Select(selected = true)
	private String address;
	
	@Column(name = "email", Kind = "varchar(128)", NULL = "NOT NULL")
	private String email;
	
	@References(table="jobs",column="employee_id",foreignClass = Job.class)
	@Foreign(relation="one-to-many")
	private Set<Job> job;
	
	public Employee(){}
	
	public Employee(Long id,String name, String address, String email,Set<Job> set){
		this.id = id;
        this.name = name;
		this.address = address;
		this.email = email;
		this.job = set;
		
	}
	@SetValue(name = "id", id = true)
	@ParamType(type = "BigDecimal")
	public void setId( Long id){
		this.id = id;
	}
	
	@GetValue(name = "id", id = true)
	public Long getId(){
		return id;
	}
	
	@SetValue(name = "name")
	@ParamType(type = "String")
	public void setName(String name){
		this.name = name;
	}
	
	@GetValue(name = "name")
	public String getName(){
		return name;
	}
	
	@SetValue(name = "address")
	@ParamType(type = "String")
	public void setAddress(String address){
		this.address = address;
	}
	
	@GetValue(name = "address")
	public String getAddress(){
		return address;
	}
	
	@SetValue(name = "email")
	@ParamType(type = "String")
	public void setEmail(String email){
		this.email = email;
	}
	
	@GetValue(name = "email")
	public String getEmail(){
		return email;
	}
	
	@GetValue(name = "job",foreign = true)
	public Set<Job> getJob() {
		return job;
	}
	@SetValue(name = "job",foreign = true)
	@ParamType(type = "Set<Job>")
	public void setJob(HashSet<Job> job) {
		this.job = job;
	}


	

}
