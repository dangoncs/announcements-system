package operations.category;

import entities.Category;

import java.util.ArrayList;

public record UpdateCategoryOp(String token, ArrayList<Category> categories) {
}
