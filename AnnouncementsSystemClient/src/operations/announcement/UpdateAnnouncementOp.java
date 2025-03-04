package operations.announcement;

public record UpdateAnnouncementOp(String op, String token, String id, String title, String text, String categoryId) {

    public UpdateAnnouncementOp(String token, String id, String title, String text, String categoryId) {
        this("13", token, id, title, text, categoryId);
    }
}
