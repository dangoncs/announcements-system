package operations.announcements;

public record UpdateAnnouncementOp(String token, String id, String title, String text, String categoryId) {
}
