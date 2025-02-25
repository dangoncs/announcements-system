package operations.category;

import operations.Operation;

public class ReadCategoryOp extends Operation {
    private final String token;

    public ReadCategoryOp(String token) {
        super("8");
        this.token = token;
    }

    @Override
    public String toString() {
        return "ReadCategoryOperation{" +
                "token='" + token + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
