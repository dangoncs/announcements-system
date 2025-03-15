package responses;

import java.util.List;

import entities.Announcement;

public class AnnouncementResponse extends Response {
    private final List<Announcement> announcements;

    public AnnouncementResponse(String responseCode, String message, List<Announcement> announcements) {
        super(responseCode, message);
        this.announcements = announcements;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }
}
