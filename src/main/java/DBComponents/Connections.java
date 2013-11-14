package DBComponents;

import java.sql.ResultSet;

public interface Connections {

    public abstract <PK> PK executeInsert(String query);
    public abstract ResultSet select(String query);
    public abstract Boolean executeQuery(String query);
    public abstract boolean close();

}
