package server.services;

import server.dao.AccountDAO;
import server.dao.Database;
import server.entities.Account;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {

    public void create(Account account) {
        if(InputValidator.isValidAccountInfo(account)) {
            try {
                account.setName(InputValidator.truncateString(account.getName(), 40));

                Connection conn = Database.connect();
                new AccountDAO(conn).create(account);

                System.out.println("INFO: Conta criada.");
            } catch (SQLException e) {
                System.err.println("ERRO ao criar conta: " + e.getLocalizedMessage());
            } finally {
                Database.disconnect();
            }
        }
        else {
            System.out.println("ERRO ao criar conta: input inválido");
        }

    }

    public Account read(String userId) {
        if(InputValidator.isValidUserId(userId)) {
            try {
                Connection conn = Database.connect();
                return new AccountDAO(conn).searchByUser(userId);
            } catch (SQLException e) {
                System.err.println("ERRO ao ler conta: " + e.getLocalizedMessage());
                return null;
            } finally {
                Database.disconnect();
            }
        }
        else {
            System.out.println("ERRO ao ler conta: input inválido");
            return null;
        }
    }

    public void update(Account account) {
        if(InputValidator.isValidAccountInfo(account)) {
            try {
                account.setName(InputValidator.truncateString(account.getName(), 40));

                Connection conn = Database.connect();
                new AccountDAO(conn).update(account);

                System.out.println("INFO: Conta atualizada.");
            } catch (SQLException e) {
                System.err.println("ERRO ao atualizar conta: " + e.getLocalizedMessage());
            } finally {
                Database.disconnect();
            }
        }
        else {
            System.out.println("ERRO ao atualizar conta: input inválido");
        }
    }

    public void delete(String userId) {
        if(InputValidator.isValidUserId(userId)) {
            try {
                Connection conn = Database.connect();
                int manipulatedLines = new AccountDAO(conn).delete(userId);

                if(manipulatedLines < 1)
                    System.out.println("INFO: Conta não encontrada.");
                else
                    System.out.println("INFO: Conta excluída.");
            } catch (SQLException e) {
                System.err.println("ERRO ao excluir conta: " + e.getLocalizedMessage());
            } finally {
                Database.disconnect();
            }
        }
        else {
            System.out.println("ERRO ao excluir conta: input inválido");
        }
    }
}
