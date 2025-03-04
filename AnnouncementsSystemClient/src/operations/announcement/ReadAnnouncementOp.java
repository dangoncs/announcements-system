package operations.announcement;

public record ReadAnnouncementOp(String op, String token) {

    public ReadAnnouncementOp(String token) {
        this("12", token);
    }
}
