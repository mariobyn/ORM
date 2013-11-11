package Database;

public class Configuration {

	private String link;
	private String user;
	private String password;
	private String driver;
	private String type;
	
	public Configuration(String link, String user, String password, String driver, String type){
		this.setLink(link);
		this.setUser(user);
		this.setPassword(password);
		this.setDriver(driver);
		this.setType(type);
		
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
