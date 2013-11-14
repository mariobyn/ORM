package DBComponents;


public class MySQL implements Database {

    private String user;
    private String link;
    private String password;
    private String driver;
    private String type;
    private String dbName;

    public MySQL(){};
    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public String getDbName(){
        return dbName;
    }

    @Override
    public void setUser(String user) {
       this.user = user;
    }

    @Override
    public void setLink(String link) {
       this.link = link;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setDriver(String driver) {
        this.driver  = driver;
    }

    @Override
    public void setDbName(String dbName){
        this.dbName = dbName;
    }
}
