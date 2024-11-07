package entities;

public class Operation {

	private String op;
	private String user;
	private String password;
	private String token;
	
	public Operation(String op, String user, String password, String token) {
		this.op = op;
		this.user = user;
		this.password = password;
		this.token = token;
	}
	
	public Operation(String op, String user, String password) {
		this.op = op;
		this.user = user;
		this.password = password;
		this.token = null;
	}

	@Override
	public String toString() {
		return "Operation [op=" + op + ", user=" + user + ", password=" + password + ", token=" + token + "]";
	}
}
