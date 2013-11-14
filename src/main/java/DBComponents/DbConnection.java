package DBComponents;
import java.sql.*;

import com.mysql.jdbc.PreparedStatement;




public class DbConnection {

	public static Connection conn = null;
	private PreparedStatement stmt = null;
	public ResultSet rs = null;
    
    public DbConnection(Database db){
    	
    	if(db.getType().toLowerCase().compareTo("mysql") == 0){
	    	try {
	    		Class.forName(db.getDriver());
				conn = DriverManager.getConnection(db.getLink() + "/" + db.getDbName(),db.getUser(),db.getPassword());
				System.out.println("Connection established");
			} catch (SQLException e) {
				throw new RuntimeException("Can't connect to database");
			} catch(ClassNotFoundException e){
				throw new RuntimeException("Driver can't be loaded");
			} 
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
	 public <PK> PK executeInsert(String query){
    	
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
    
    public Boolean executeQuery(String query){
    	try {
    		Statement stmt = conn.createStatement();
    		//System.out.println(stmt);
			stmt.execute(query);
			return true;
			
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


    public void beginTransaction() {

        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
           throw new RuntimeException("Can't begin transactions");
        }

    }

    public void commit(){
        try {
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Can't commit");
        }
    }

    public void rollback(){
        try {

            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't rollback");
        }
    }

    public void endTransaction(){
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException("Can't end transaction");
        }
    }
}
