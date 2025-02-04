package entities;

public class Category {
    private String categoryId;
    private final String name;
    private final String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(String categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Id: " + categoryId + ", Nome: " + name + ", Descrição: " + description + '\n';
    }
}
