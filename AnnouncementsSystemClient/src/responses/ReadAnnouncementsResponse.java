package responses;

import java.util.List;

import entities.Announcement;

public record ReadAnnouncementsResponse(String response, String message, List<Announcement> announcements) {
}
