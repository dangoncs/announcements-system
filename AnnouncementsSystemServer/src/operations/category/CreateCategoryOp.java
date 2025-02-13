package operations.category;

import entities.Category;

import java.util.ArrayList;

public record CreateCategoryOp(String token, ArrayList<Category> categories) {
}
