package org.timo;

import java.sql.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            println("Please specify <HOST> <PORT> <DATABASE> <USER>, password will be asked");
        } else {
            String host = args[0];
            String port = args[1];
            String db = args[2];
            String user = args[3];
            println("Please enter your password:");
            String pwd = new String(System.console().readPassword());
            //TODO right now is for postgres, extend to mysql, oracle and others
            connectToDb(host, port, db, user, pwd);
        }
    }

    private static void connectToDb(String host, String port, String db, String user, String pwd) {
        try {
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":" + port + "/" + db, user,
                    pwd)) {
                try (Statement st = connection.createStatement()) {
                    ResultSet rs = st.executeQuery("SELECT now()");
                    while (rs.next())
                        println(rs.getString(1));

                    println("SUCCESS!!");
                } catch (Exception e) {
                    println("Error on connection: " + e.getMessage());
                }
            } catch (Exception e) {
                println("Error on connection: " + e.getMessage());
            }
        } catch (Exception e) {
            println("Error on connection: " + e.getMessage());
        }
    }

    private static void println(String s) {
        System.out.println(s);
    }
}
