
import Annotations.Column;
import Annotations.Foreign;
import Annotations.GetValue;
import Annotations.Id;
import Annotations.References;
import Annotations.SetValue;
import Annotations.Table;


@Table(name = "Jobs")
public class Job {
	
	
	@Id
	@Column(name = "id", Kind = "BigInt(10)", ConstraintP = "PRIMARY KEY", 
			Auto_Increment = "AUTO_INCREMENT", NULL = "NOT NULL")
	private Long id = new Long(0);
	
	@Column(name = "name", Kind = "varchar(128)", NULL = "NOT NULL")
	private String name;
	
	@Column(name = "city", Kind = "varchar(128)", NULL = "NOT NULL")
	private String city;
	
	@Column(name="employee_id", Kind = "BigInt(10)")
	@Foreign(relation = "one-to-many")
	@References(column = "id", table="employees")
	private Long employee_id;

	public Job(){}
	
	public Job(String name, String city, Long employee_id){
		this.name = name;
		this.city = city;
		this.employee_id = employee_id;
		
	}
	public Job(String name, String city){
		this.name = name;
		this.city = city;
		
	}
	
	@SetValue(name = "id",id=true)
	public void setId( Long id){
		this.id = id;
	}
	
	@GetValue(name = "id",id = true)
	public Long getId(){
		return id;
	}
	
	@SetValue(name = "name")
	public void setName(String name){
		this.name = name;
	}
	
	@GetValue(name = "name")
	public String getName(){
		return name;
	}
	
	@SetValue(name = "city")
	public void setAddress(String city){
		this.city = city;
	}
	
	@GetValue(name = "city")
	public String getAddress(){
		return city;
	}
	
	@SetValue(name = "employee_id",foreign = true)
	public void setEmployeeId(Long employee_id){
		this.employee_id = employee_id;
	}
	
	@GetValue(name = "employee_id",foreign = true)
	public Long getEmployeeId(){
		return employee_id;
	}
	

}
