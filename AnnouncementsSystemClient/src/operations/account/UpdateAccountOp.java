package operations.account;

import entities.Account;

public record UpdateAccountOp(String op, String token, Account account) {
}
