package responses;

import entities.Account;

public class AccountResponse extends Response {
    public static final String DUPLICATE_USERNAME = "An account with the same username already exists";
    private final Account account;

    public AccountResponse(String responseCode, String message, Account account) {
        super(responseCode, message);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
