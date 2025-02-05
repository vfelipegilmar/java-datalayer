package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            User user = new User();
            System.out.println(user.Find(null).Fetch(true));
            System.out.println(user.Find(null).Count());

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }
}