package operations.announcement;

import operations.Operation;

public class ReadAnnouncementOp extends Operation {
    private final String token;

    public ReadAnnouncementOp(String token) {
        super("12");
        this.token = token;
    }

    @Override
    public String toString() {
        return "ReadAnnouncementOp{" +
                "op='" + op + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
