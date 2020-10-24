package jflat;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    private Connection connect() {
        String url = "jdbc:sqlite:dictDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
        }
        return conn;
    }

    public void listAV(ObservableList<String> words) {
        words.clear();

        String sql = "SELECT word FROM " + "av";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
            while (rs.next()) {
                favWords.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listAutoCompleteFav(ObservableList<String> words, String selectedWord) {
        words.clear();
        String sql = "SELECT word FROM fav WHERE word LIKE " + "'" + selectedWord + "%'";
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
            while (rs.next()) {
                def = rs.getString("html");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return def;
    }

    public void addFavWord(String selectedWord, String lang) {
        String sql = "INSERT INTO fav SELECT * FROM " + lang + " WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();) {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addWord(String word, String html, String type) {
        String sql = "INSERT INTO " + type + " (word,html) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.setString(2, html);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getVieDes(String selectedWord) {
        String def = "";
        String sql = "SELECT description FROM va WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                def = rs.getString("description");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return def;
    }
    public String getEngDes(String selectedWord) {
        String def = "";
        String sql = "SELECT description FROM av WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                def = rs.getString("description");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return def;
    }
    public static String addCharToString(String str, char c, int pos) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(pos, c);
        return stringBuilder.toString();
    }
    public String getViePro(String selectedWord) {
        String def = "";
        String sql = "SELECT pronounce FROM va WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                def = rs.getString("pronounce");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return def;
    }
    public String getEngPro(String selectedWord) {
        String def = "";
        String sql = "SELECT pronounce FROM av WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                def = rs.getString("pronounce");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int index = 0;
        index = def.indexOf("'", index);
        System.out.println(index);
        while( index != -1){
            def = addCharToString(def, '\\', index );
            index = def.indexOf("'", index+2);
            System.out.println(index);
        }
        System.out.println(def);
        return def;
    }
    public int getFavId() {
        int id = 0;
        String def = "";
        String sql = "select count(*) from fav";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }


    public void addFavWord(String a, boolean isAV) {
        int i;
        a = a.replace("[", "");
        a = a.replace("]", "");
        String wordDef  = new String();
        String wordDes = new String();
        String wordPro = new String();
        if( isAV){
            wordDef = getEngDef(a);
            wordDes = getEngDes(a);
            wordPro = getEngPro(a);
        } else {
            wordDef = getVieDef(a);
            wordDes = getVieDes(a);
            wordPro = getViePro(a);
        }

        int id = getFavId() ;


        try {
            Statement insert = connect().createStatement();
            String t = String.format("VALUES (%d, '%s', '%s', '%s', '%s')", id, a, wordDef, wordDes, wordPro);
            System.out.println(t);
            //insert.executeUpdate("INSERT INTO fav " + t);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }


    }



    public void deleteEngWord(String selectedWord) {
        String sql = "DELETE FROM av WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();) {
                stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteVieWord(String selectedWord) {
        String sql = "DELETE FROM va WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFavWord(String selectedWord) {
        String sql = "DELETE FROM fav WHERE word LIKE " + "'" + selectedWord + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
