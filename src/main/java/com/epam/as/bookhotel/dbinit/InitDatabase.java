package com.epam.as.bookhotel.dbinit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * Create tables and fill database
 */
public class InitDatabase {

    private static Logger logger = LoggerFactory.getLogger("InitDatabase");

    InitDatabase

    {
        Properties props = new Properties();
        try (InputStream in = InitDatabase.class.getClassLoader().getResourceAsStream(dbPropertyFileName)) {
            props.load(in);
        } catch (IOException e) {
            logger.error("Can't open file {} for reading properties of database.", e.getMessage());
        }

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) System.setProperty("jdbc.drivers", drivers);
        url = props.getProperty("jdbc.url");
        username = props.getProperty("jdbc.username");
        password = props.getProperty("jdbc.password");
    }

    public static void main(String args[]) {

        String commandFileName = "postgres_create.sql";
        {
            try (Scanner in = new Scanner(Paths.get(commandFileName));) {

                try (Connection conn = getConnection()) {
                    logger.debug("Connection with data base established.");
                    Statement stat = conn.createStatement();
                    logger.debug("Statement with data base created.");
                    while (true) {
                        if (!in.hasNextLine()) return;

                        String line = in.nextLine();
                        if (line.equalsIgnoreCase("EXIT")) return;
                        if (line.trim().endsWith(";")) {
                            line = line.trim();
                            line = line.substring(0, line.length() - 1);
                        }
                        boolean isResult = stat.execute(line);
                        if (isResult) {
                            ResultSet rs = stat.getResultSet();
                            showResultSet(rs);
                        } else {
                            int updateCount = stat.getUpdateCount();
                            System.out.println(updateCount + " rows updated");
                        }
                    }
                }
            } catch (SQLException e) {
                for (Throwable t : e)
                    logger.error("SQLException error:", t);
            } catch (IOException e) {
                logger.error("IOException error:", e);
            }
        }
    }

    /**
     * Gets a connection from the properties specified in the file database.properties
     *
     * @return the database connection
     */

    private static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        } catch (IOException e) {
            logger.error("IOException error:", e);
        }

        //String drivers = props.getProperty("jdbc.drivers");
        //if (drivers != null) System.setProperty("jdbc.drivers", drivers);

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Prints a result set.
     *
     * @param result the result set to be printed
     */
    public static void showResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) System.out.print(", ");
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();

        while (result.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) System.out.print(", ");
                System.out.print(result.getString(i));
            }
            System.out.println();
        }
    }

}
