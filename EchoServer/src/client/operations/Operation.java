package client.operations;

public class Operation {

	protected String op;
	
	public Operation(String op) {
		this.op = op;
	}

	public Operation(String op, String username, String password) {
		this.op = op;
	}
}
