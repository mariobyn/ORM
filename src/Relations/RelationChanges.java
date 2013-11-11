package Relations;
import java.util.Set;

import Database.DbConnection;


public interface RelationChanges<FK,T> {

	public void change(Set<FK> objects, DbConnection<?, ?> conn,T object,Object id);
}
