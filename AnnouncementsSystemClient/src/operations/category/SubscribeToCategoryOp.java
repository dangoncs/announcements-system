package operations.category;

import operations.Operation;

public class SubscribeToCategoryOp extends Operation {
    private final String token;
    private final String categoryId;

    public SubscribeToCategoryOp(String token, String categoryId) {
        super("15");
        this.token = token;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "SubscribeToCategoryOp{" +
                "token='" + token + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
