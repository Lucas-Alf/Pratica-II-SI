package com.setrem.pratica2api.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class SessionFactory {
    public Connection OpenConnection() {
        try {
            String url = "jdbc:postgresql://ec2-174-129-33-147.compute-1.amazonaws.com:5432/d7k37oahur5ovg";
            String usuario = "gidmcpeqjmjkfo";
            String senha = "8a34bc37e8bd2458c6ee70af09a074ca1f571f740d8394790de98eda83127a29";
            return DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void CloseConnection(Connection conexao) {
        try {
            conexao.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}