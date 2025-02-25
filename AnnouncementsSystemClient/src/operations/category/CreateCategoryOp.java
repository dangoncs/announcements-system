package operations.category;

import java.util.List;

import entities.Category;
import operations.Operation;

public class CreateCategoryOp extends Operation {
    private final String token;
    private final List<Category> categories;

    public CreateCategoryOp(String token, List<Category> categories) {
        super("7");
        this.token = token;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CreateCategoryOperation{" +
                "token='" + token + '\'' +
                ", categories=" + categories +
                ", op='" + op + '\'' +
                '}';
    }
}
