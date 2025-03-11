package operations.announcement;

public record UpdateAnnouncementOp(String op, String token, String id, String title, String text, String categoryId) {
}
