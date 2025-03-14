package entities;

public record Category(String id, String name, String description, boolean subscribed) {

    public Category(String id, String name, String description) {
        this(id, name, description, false);
    }
}
