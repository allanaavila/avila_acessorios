package com.avila.acessorios.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection {
    public static void main(String[] args) {
        // Informações de conexão para o banco "avila_acessorios"
        String url = "jdbc:postgresql://localhost:5432/avila_acessorios";
        String user = "allanaavila";
        String password = "2810";

        // Conectar ao banco de dados
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexão estabelecida com sucesso ao banco avila_acessorios!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
