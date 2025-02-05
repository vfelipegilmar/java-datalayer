package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class User extends DataLayer {
    private int id;
    private String name;
    private String username;
    private String password;

    public User() {
        super("users", User.getRequired(), "id", true, User.getDatabase());
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password = password;
    }

    private static ArrayList<String> getRequired() {
        return new ArrayList<>(Arrays.asList("name", "username", "password"));
    }

    public static HashMap<String, String> getDatabase() {
        HashMap<String, String> db = new HashMap<>();
        db.put("DATABASE_NAME", "javadatabase");
        db.put("USERNAME", "postgres");
        db.put("PASSWORD", "CafeSemAcucar");
        return db;
    }

    @Override
    public String toString() {
        return "User { id=" + this.id + ", name='" + this.name + "', username=" + this.username + '}';
    }
}
