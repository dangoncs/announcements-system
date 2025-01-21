package responses;

import entities.Category;

import java.util.List;

public class CategoryResponse extends Response {
    private final List<Category> categories;

    public CategoryResponse(String response, String message, List<Category> categories) {
        super(response, message);
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "categories=" + categories +
                ", response='" + response + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
