package operations.announcements;

public record CreateAnnouncementOp(String token, String title, String text, String categoryId) {
}
