package entities;

public record User(String userId, String token, int role) {

    @Override
    public String toString() {
        String roleString = (role == 0) ? "Comum" : "Admin";
        return '\n' + userId + " (" + roleString + ")";
    }
}
