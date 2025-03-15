package entities;

public record User(String id, String token, int role) {

    @Override
    public String toString() {
        String roleString = (role == 0) ? "Comum" : "Admin";
        return '\n' + id + " (" + roleString + ")";
    }
}
