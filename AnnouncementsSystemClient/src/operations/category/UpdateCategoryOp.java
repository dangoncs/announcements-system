package operations.category;

import java.util.List;

import entities.Category;
import operations.Operation;

public class UpdateCategoryOp extends Operation {
    private final String token;
    private final List<Category> categories;

    public UpdateCategoryOp(String token, List<Category> categories) {
        super("9");
        this.token = token;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "UpdateCategoryOperation{" +
                "token='" + token + '\'' +
                ", categories=" + categories +
                ", op='" + op + '\'' +
                '}';
    }
}
