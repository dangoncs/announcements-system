package operations.category;

import java.util.List;

import operations.Operation;

public class DeleteCategoryOp extends Operation {
    private final String token;
    private final List<String> categoryIds;

    public DeleteCategoryOp(String token, List<String> categoryIds) {
        super("10");
        this.token = token;
        this.categoryIds = categoryIds;
    }

    @Override
    public String toString() {
        return "DeleteCategoryOperation{" +
                "token='" + token + '\'' +
                ", categoryIds=" + categoryIds +
                ", op='" + op + '\'' +
                '}';
    }
}
