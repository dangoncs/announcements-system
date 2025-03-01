package responses.category;

import entities.Category;

import java.util.List;

public record ReadCategoriesResponse(String response, String message, List<Category> categories) {
}
