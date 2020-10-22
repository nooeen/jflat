package jflat;

import javafx.collections.ObservableList;

import java.sql.*;

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

    public void listFav(ObservableList<String> favWords) {
        favWords.clear();

        String sql = "SELECT word FROM " + "fav";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                favWords.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listAutoCompleteAV(ObservableList<String> words, String selectedWord) {
        words.clear();
        String sql = "SELECT word FROM av WHERE word LIKE " + "'" + selectedWord + "%'";
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

    public void listAutoCompleteVA(ObservableList<String> words, String selectedWord) {
        words.clear();
        String sql = "SELECT word FROM va WHERE word LIKE " + "'" + selectedWord + "%'";
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

    public String getFavDef(String selectedWord) {
        String def = "";
        String sql = "SELECT html FROM fav WHERE word LIKE " + "'" + selectedWord + "'";
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
        String sql = "DELETE FROM av WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();) {
                stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteVieWord(String selectedWord) {
        String sql = "DELETE FROM va WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();) {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
