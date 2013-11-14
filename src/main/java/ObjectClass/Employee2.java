package ObjectClass;

import Annotations.*;

import java.util.HashSet;
import java.util.Set;


@Table(name = "Employees2",mainClass = true)
public class Employee2 {


	@Id
	@Column(name = "id", Kind = "BigInt(10)", ConstraintP = "PRIMARY KEY",
			Auto_Increment = "AUTO_INCREMENT", NULL = "NOT NULL")
	private Long id = (long) 0;

	@Column(name = "name", Kind = "varchar(128)", NULL = "NOT NULL")
	@Select(selected = true)
	private String name;

	@Column(name = "address", Kind = "varchar(128)", NULL = "NOT NULL")
	@Select(selected = true)
	private String address;

	@Column(name = "email", Kind = "varchar(128)", NULL = "NOT NULL")
	private String email;



	public Employee2(){}

	public Employee2(String name, String address, String email){
		this.name = name;
		this.address = address;
		this.email = email;

		
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



	

}
