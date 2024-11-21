package test;

import server.entities.Account;
import server.services.AccountService;

public class AccountTest {
    public static void createAccountTest() {
        Account account = new Account();
        account.setUserId("1234567");
        account.setName("Beraldo da Silva");
        account.setPassword("0123");

        new AccountService().create(account);
    }

    public static void readAccountTest() {
        String username = "1234567";
        Account account = new AccountService().read(username);

        if(account != null) System.out.println(account);
    }

    public static void updateAccountTest() {
        Account account = new Account();
        account.setUserId("1234567");
        account.setName("Beraldo da Silva");
        account.setPassword("1234");

        new AccountService().update(account);
    }

    public static void deleteAccountTest() {
        String username = "1234567";
        new AccountService().delete(username);
    }

    public static void main(String[] args) {
        AccountTest.createAccountTest();
        AccountTest.readAccountTest();
        AccountTest.updateAccountTest();
        AccountTest.deleteAccountTest();
    }
}
