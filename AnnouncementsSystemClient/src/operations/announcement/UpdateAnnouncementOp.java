package operations.announcement;

import operations.Operation;

public class UpdateAnnouncementOp extends Operation {
    private final String id;
    private final String token;
    private final String title;
    private final String text;
    private final String categoryId;

    public UpdateAnnouncementOp(String token, String id, String title, String text, String categoryId) {
        super("13");
        this.token = token;
        this.id = id;
        this.title = title;
        this.text = text;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "UpdateAnnouncementOp{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
