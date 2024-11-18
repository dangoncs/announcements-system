package server.services;

import server.dao.AccountDAO;
import server.dao.Database;
import server.entities.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountService {
    public void create(Account account) {
        try {
            Connection conn = Database.connect();

            //TODO: Validate input in GUI

            new AccountDAO(conn).create(account);
            System.out.println("INFO: Conta criada.");
        } catch (SQLException e) {
            System.err.println("ERRO ao criar conta: " + e.getLocalizedMessage());
        } finally {
            Database.disconnect();
        }
    }

    public Account read(String user) {
        try {
            Connection conn = Database.connect();

            //TODO: Validate input in GUI

            return new AccountDAO(conn).searchByUser(user);
        } catch (SQLException e) {
            System.err.println("ERRO ao ler conta: " + e.getLocalizedMessage());
        } finally {
            Database.disconnect();
        }

        return null;
    }

    public void update(Account account) {
        try {
            Connection conn = Database.connect();

            //TODO: Validate input in GUI

            new AccountDAO(conn).create(account);
            System.out.println("INFO: Conta atualizada.");
        } catch (SQLException e) {
            System.err.println("ERRO ao atualizar conta: " + e.getLocalizedMessage());
        } finally {
            Database.disconnect();
        }
    }

    public void delete(String username) {
        try {
            Connection conn = Database.connect();

            //TODO: Validate input in GUI

            int manipulatedLines = new AccountDAO(conn).delete(username);
            if(manipulatedLines < 1) {
                System.out.println("INFO: Conta não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("ERRO ao excluir conta: " + e.getLocalizedMessage());
        } finally {
            Database.disconnect();
        }
    }
}
