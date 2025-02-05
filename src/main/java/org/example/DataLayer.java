package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataLayer {
    private final String primary;
    private final String entity;
    private final ArrayList<String> required;
    private final boolean timestamps;
    private final HashMap<String, String> database;
    private String group;
    private String limit;
    private Statement stmt = null;
    private String columns = "*";
    protected String statement = null;
    protected ArrayList<Object> params;
    protected SQLException fail = null;
    protected ResultSet data = null;


    public DataLayer(String entity, ArrayList<String> required) {
        this.entity = entity;
        this.required = required;
        this.primary = "id";
        this.timestamps = false;
        this.database = null;
    }

    public DataLayer(String entity, ArrayList<String> required, String primary, boolean timestamps, HashMap<String, String> database) {
        this.entity = entity;
        this.primary = primary;
        this.required = required;
        this.timestamps = timestamps;
        this.database = database;
    }

    public DataLayer Find(String columns) {
        if (columns != null) {
            this.columns = columns;
        }

        this.statement = String.format("SELECT %s FROM %s ", this.columns, entity);
        return this;
    }

    public DataLayer Find(String terms, String params, String columns) {
        this.statement = String.format("SELECT %s FROM %s WHERE %s", columns, entity, terms);
        return this;
    }

    public String Fetch(boolean all) throws SQLException {
        Connection connection = null;
        ArrayList<Object> listObject = new ArrayList<>();
        try {
            connection = ConnectDB.getConnection(this.database);
            this.stmt = connection.createStatement();
            ResultSet result = this.stmt.executeQuery(this.statement);

            while (result.next()) {
                HashMap<String, String> objectFetch = new HashMap<>();
                for (String column : this.Columns()) {
                    objectFetch.put(column, result.getString(column));
                }
                listObject.add("\n" + objectFetch);
            }

            return (!all ? listObject.get(0).toString() : listObject.toString());

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            throw e;
        } finally {
            ConnectDB.closeConnection(connection);
        }
    }

    public ArrayList<String> Columns() {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConnectDB.getConnection(this.database);
            stmt = connection.prepareStatement("SELECT column_name, data_type, is_nullable\n" +
                    "FROM information_schema.columns\n" +
                    "WHERE table_name = ?");
            stmt.setString(1, this.entity);
            ResultSet result = stmt.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (result.next()) {
                list.add(result.getString("column_name"));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            return null;
        }
    }

    public int Count() {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConnectDB.getConnection(this.database);
            String sql = "SELECT COUNT(1) FROM " + this.entity;
            stmt = connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            return 0;
        }
    }

    public DataLayer Limit(String limit) {
        this.limit = "LIMIT " + limit;
        return this;
    }

    public DataLayer Group(String group) throws SQLException {
        this.group = "GROUP BY " + group;
        return this;
    }

    public DataLayer FindById(int id, String columns) throws SQLException {
        try {
            String sql = String.format("%s = %s", this.primary, id);
            this.Find(sql, "id = " + id, columns).Fetch(false);
            return this;
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            this.fail = e;
            throw e;
        }
    }
}
