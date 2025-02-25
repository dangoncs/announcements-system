package operations.announcement;

import operations.Operation;

public class CreateAnnouncementOp extends Operation {
    private final String token;
    private final String title;
    private final String text;
    private final String categoryId;

    public CreateAnnouncementOp(String token, String title, String text, String categoryId) {
        super("11");
        this.token = token;
        this.title = title;
        this.text = text;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CreateAnnouncementOp{" +
                "token='" + token + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
