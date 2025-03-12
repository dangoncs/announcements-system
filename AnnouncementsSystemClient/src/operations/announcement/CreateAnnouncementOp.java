package operations.announcement;

import entities.Announcement;

public record CreateAnnouncementOp(String op, String token, Announcement announcement) {
}
