package com.ifsp.biblioteca.BibliotecaDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateTables {
    public static void main(String[] argv) throws SQLException {
        // Connection Configuration
        Properties connConfig = new Properties();
        connConfig.setProperty("user", "root");
        connConfig.setProperty("password", "isabelly1611");

        // Create Connection to MariaDB Enterprise
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", connConfig)) {

            // Disable Auto-Commit
            conn.setAutoCommit(false);

            // user
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.user ("
                                + "usid INT PRIMARY KEY AUTO_INCREMENT,"
                                + "name VARCHAR(50))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // publisher
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.publisher ("
                                + "puid INT PRIMARY KEY AUTO_INCREMENT,"
                                + "name VARCHAR(50),"
                                + "endereco VARCHAR(50))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // author
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.author ("
                                + "auid INT PRIMARY KEY AUTO_INCREMENT,"
                                + "name VARCHAR(50))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // book
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.books ("
                                + "boid INT PRIMARY KEY AUTO_INCREMENT,"
                                + "titulo VARCHAR(50),"
                                + "author VARCHAR(50),"
                                + "email VARCHAR(100),"
                                + "fkpuid INT (8) ,"
                                + "fkauid INT (8),"
                                + "CONSTRAINT FK_puid FOREIGN KEY (fkpuid)"
                                + " REFERENCES livraria.publisher(puid),"
                                + "CONSTRAINT FK_auid FOREIGN KEY (fkauid)"
                                + " REFERENCES livraria.author(auid))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // account
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.account ("
                                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                                + "email VARCHAR(50),"
                                + "password VARCHAR(30))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // shippinginfo
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.shippinginfo ("
                                + "shipper VARCHAR(50),"
                                + "endereco VARCHAR(50))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // billinginfo
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.billinginfo ("
                                + "email VARCHAR(50),"
                                + "pagamento VARCHAR(50))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // order
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.order ("
                                + "orid INT PRIMARY KEY AUTO_INCREMENT,"
                                + "date VARCHAR(20),"
                                + "fkusid INT NOT NULL,"
                                + "endereco VARCHAR(50),"
                                + "pagamento VARCHAR(50),"
                                + "CONSTRAINT FK_usid FOREIGN KEY (fkusid)"
                                + " REFERENCES livraria.user(usid))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // order itens
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS livraria.orderitens ("
                                + "fkorid INT NOT NULL," //id da venda
                                + "fkboid INT NOT NULL," //id do livro
                                + "qtde INT,"
                                + "CONSTRAINT FK_orid FOREIGN KEY (fkorid)"
                                + " REFERENCES livraria.order(orid),"
                                + "CONSTRAINT FK_boid FOREIGN KEY (fkboid)"
                                + " REFERENCES livraria.books(boid))"
                                + "ENGINE=InnoDB;");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
