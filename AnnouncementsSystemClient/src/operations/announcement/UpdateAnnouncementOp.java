package operations.announcement;

import entities.Announcement;

public record UpdateAnnouncementOp(String op, String token, Announcement announcement) {
}
