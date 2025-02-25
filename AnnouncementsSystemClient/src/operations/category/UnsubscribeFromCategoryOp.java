package operations.category;

import operations.Operation;

public class UnsubscribeFromCategoryOp extends Operation {
    private final String token;
    private final String categoryId;

    public UnsubscribeFromCategoryOp(String token, String categoryId) {
        super("16");
        this.token = token;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "UnsubscribeFromCategoryOp{" +
                "token='" + token + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
