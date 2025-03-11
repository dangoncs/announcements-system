package operations.announcement;

public record CreateAnnouncementOp(String op, String token, String title, String text, String categoryId) {
}
