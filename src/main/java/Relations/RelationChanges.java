package Relations;
import java.util.Set;

import DBComponents.DbConnection;


public interface RelationChanges<FK,T> {

	public void change(Set<FK> objects, DbConnection conn,T object,Object id);
}
