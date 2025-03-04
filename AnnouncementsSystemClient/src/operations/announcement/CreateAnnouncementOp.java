package operations.announcement;

public record CreateAnnouncementOp(String op, String token, String title, String text, String categoryId) {

    public CreateAnnouncementOp(String token, String title, String text, String categoryId) {
        this("11", token, title, text, categoryId);
    }
}
