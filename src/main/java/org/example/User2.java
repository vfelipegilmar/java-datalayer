package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class User2 extends DataLayer {
    private int id;
    private String name;
    private String username;
    private String password;

    public User2() {
        super("users", User2.getRequired(), "id", true, User2.getDatabase());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static ArrayList<String> getRequired() {
        return new ArrayList<>(Arrays.asList("name", "username", "password"));
    }

    public static HashMap<String, String> getDatabase() {
        HashMap<String, String> db = new HashMap<>();
        db.put("DATABASE_NAME", "javadatabase1");
        db.put("USERNAME", "postgres");
        db.put("PASSWORD", "CafeSemAcucar");
        return db;
    }

    @Override
    public String toString() {
        return "User { id=" + this.id + ", name='" + this.name + "', username=" + this.username + '}';
    }
}
