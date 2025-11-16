/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;


import java.sql.*;

public class Banco_Do_Jogo {
    private static final String URL = "jdbc:mysql://localhost:3306/batalha_naval?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";         
    private static final String PASSWORD = "228529"; 

  
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

  
    public void criarTabelaPlacar() {
        String sql = "CREATE TABLE IF NOT EXISTS placar ("
                   + "nome VARCHAR(50) PRIMARY KEY, "
                   + "vitorias INT DEFAULT 0, "
                   + "derrotas INT DEFAULT 0);";
        try (Connection conn = conectar(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro criarTabelaPlacar: " + e.getMessage());
        }
    }

    
    public boolean jogadorExiste(String nome) {
        String sql = "SELECT 1 FROM placar WHERE nome = ?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erro jogadorExiste: " + e.getMessage());
            return false;
        }
    }


    public void registrarJogador(String nome) {
        if (!jogadorExiste(nome)) {
            String sql = "INSERT INTO placar (nome) VALUES (?)";
            try (Connection conn = conectar();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nome);
                ps.executeUpdate();
                System.out.println("Novo jogador adicionado ao placar: " + nome);
            } catch (SQLException e) {
                System.err.println("Erro registrarJogador: " + e.getMessage());
            }
        }
    }


    public void adicionarVitoria(String nome) {
        String sql = "UPDATE placar SET vitorias = vitorias + 1 WHERE nome = ?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro adicionarVitoria: " + e.getMessage());
        }
    }

 
    public void adicionarDerrota(String nome) {
        String sql = "UPDATE placar SET derrotas = derrotas + 1 WHERE nome = ?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro adicionarDerrota: " + e.getMessage());
        }
    }

    public void mostrarPlacarIndividual(String nome) {
        String sql = "SELECT nome, vitorias, derrotas FROM placar WHERE nome = ?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("\n Jogador: %s | Vitorias: %d | Derrotas: %d\n",
                            rs.getString("nome"),
                            rs.getInt("vitorias"),
                            rs.getInt("derrotas"));
                } else {
                    System.out.println("Nenhum registro encontrado para: " + nome);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro mostrarPlacarIndividual: " + e.getMessage());
        }
    }

    public void mostrarPlacarGeral() {
        String sql = "SELECT nome, vitorias, derrotas FROM placar ORDER BY vitorias DESC, nome ASC";
        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n===== PLACAR GERAL =====");
            while (rs.next()) {
                System.out.printf("%-15s | Vitorias: %-3d | Derrotas: %-3d\n",
                                  rs.getString("nome"),
                                  rs.getInt("vitorias"),
                                  rs.getInt("derrotas"));
            }
        } catch (SQLException e) {
            System.err.println("Erro mostrarPlacarGeral: " + e.getMessage());
        }
    }
}