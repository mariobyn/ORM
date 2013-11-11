package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;




public class DbConnection<T,PK> {


	public Connection conn = null;
	public PreparedStatement stmt = null;
	public ResultSet rs = null;
    
    public DbConnection(Configuration conf){
    	
    	if(conf.getType().toLowerCase().compareTo("mysql") == 0){
	    	try {
	    		Class.forName(conf.getDriver());
				conn = DriverManager.getConnection(conf.getLink(),conf.getUser(),conf.getPassword());
				System.out.println("Connection established");
			} catch (SQLException e) {
				throw new RuntimeException("Can't connect to database");
			} catch(ClassNotFoundException e){
				throw new RuntimeException("Driver can't be loaded");
			} 
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
	public PK executeInsert(String query, String[][] valuesToBind){
    	
    	PK returnedValue = null;
    	try {
			//stmt = conn.createStatement();
			stmt = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet rs2 = stmt.getGeneratedKeys();
			if(rs2.next())
            {
                PK last_inserted_id = (PK) rs2.getObject(1);
                returnedValue = (PK) last_inserted_id;
                return returnedValue;
            }
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't execute insert");
		}
    	return returnedValue;
    	
    	
    }
    
    public ResultSet select(String query){
    	try {
    		Statement stmt = conn.createStatement();
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			throw new RuntimeException("Can't execute select statement for " + query);
		}
    }
    
    public void executeQuery(String query){
    	try {
    		Statement stmt = conn.createStatement();
    		//System.out.println(query);
			stmt.execute(query);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't create Statement in executeQuery or Can't executeQuery");
		}
    	
    	
    }
    
    
    public boolean close(){
    	try {
    		stmt.close();
			conn.close();
		} catch (SQLException | NullPointerException e) {
			return false;
		}
    	return true;
    }
    
	
}
