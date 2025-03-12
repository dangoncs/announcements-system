package responses;

import java.util.List;

import entities.Category;

public record ReadCategoriesResponse(String response, String message, List<Category> categories) {
}
