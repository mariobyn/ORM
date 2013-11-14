package DBComponents;

public interface Database {

    public abstract String getUser();
    public abstract String getLink();
    public abstract String getPassword();
    public abstract String getType();
    public abstract String getDriver();
    public abstract String getDbName();
    public abstract void setUser(String user);
    public abstract void setLink(String link);
    public abstract void setPassword(String password);
    public abstract void setType(String type);
    public abstract void setDriver(String driver);
    public abstract void setDbName(String dbName);


}
