package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            User user = new User();
            User2 user2 = new User2();
            System.out.println(user.Find(null).Fetch(true));
            System.out.println(user2.Find(null).Fetch(true));

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }
}