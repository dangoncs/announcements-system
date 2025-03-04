package operations.announcement;

public record DeleteAnnouncementOp(String op, String token, String id) {

    public DeleteAnnouncementOp(String token, String id) {
        this("14", token, id);
    }
}
