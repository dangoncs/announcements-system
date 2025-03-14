package responses;

import java.util.List;

import entities.Category;

public class CategoryResponse extends Response {
    private final List<Category> categories;

    public CategoryResponse(String response, String message, List<Category> categories) {
        super(response, message);
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
