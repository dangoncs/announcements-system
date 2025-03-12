package operations.account;

import entities.Account;

public record CreateAccountOp(String op, Account account) {
}
