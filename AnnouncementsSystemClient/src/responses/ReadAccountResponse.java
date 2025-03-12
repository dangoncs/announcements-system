package responses;

import entities.Account;

public record ReadAccountResponse(String response, String message, Account account) {
}
