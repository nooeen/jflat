package jflat;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    /**
     * connect to the dictionary's database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:dictDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void listAV(ObservableList<String> words) {
        words.clear();
        String sql = "SELECT word FROM " + "av";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                words.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listVA(ObservableList<String> words) {
        words.clear();
        String sql = "SELECT word FROM " + "va";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                words.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listAutoCompleteAV(ObservableList<String> words, String selectedWord) {
        words.clear();
        String sql = "SELECT word FROM av WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                words.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getEngDef(String selectedWord) {
        String def = "";
        String sql = "SELECT html FROM av WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                def = rs.getString("html");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return def;
    }

    public String getVieDef(String selectedWord) {
        String def = "";
        String sql = "SELECT html FROM va WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                def = rs.getString("html");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return def;
    }

    public void addEngWord() {

    }

    public void addVieWord() {

    }

    public void deleteEngWord(String selectedWord) {

    }

    public void deleteVieWord(String selectedWord) {

    }
}
