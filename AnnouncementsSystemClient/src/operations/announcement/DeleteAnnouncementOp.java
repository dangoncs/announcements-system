package operations.announcement;

import operations.Operation;

public class DeleteAnnouncementOp extends Operation {
    private final String token;
    private final String id;

    public DeleteAnnouncementOp(String token, String id) {
        super("14");
        this.token = token;
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteAnnouncementOp{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
