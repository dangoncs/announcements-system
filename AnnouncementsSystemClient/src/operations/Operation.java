package operations;

import com.google.gson.Gson;

public class Operation {

	protected String op;
	
	public Operation(String op) {
		this.op = op;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}
