package br.ufjf.dcc171;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Aula16Exem01 {

    public static void main(String[] args) {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String driverUrl = "jdbc:derby://localhost:1527/dcc171-2013-3";
            Connection conexao = DriverManager.getConnection(driverUrl, "usuario", "senha");
            PreparedStatement operacaoInsere = conexao.prepareStatement("INSERT INTO produto(nome, qtd, atualizado) VALUES(?,?,CURRENT_TIMESTAMP)");
            PreparedStatement operacaoListar = conexao.prepareStatement("SELECT nome, qtd FROM produto WHERE qtd > ? ORDER BY qtd DESC");
            Random rnd = new Random();
            for (int i = 0; i < 10; i++) {
                operacaoInsere.clearParameters();
                operacaoInsere.setString(1, "Produto " + rnd.nextInt(100));
                operacaoInsere.setInt(2, rnd.nextInt(10) + 1);
                operacaoInsere.executeUpdate();
            }
            operacaoListar.clearParameters();
            operacaoListar.setInt(1, 0);
            ResultSet resultado = operacaoListar.executeQuery();
            while (resultado.next()) {
                System.out.println(
                        resultado.getString(1)
                        + "\t\t"
                        + resultado.getInt(2)
                );
            }

        } catch (Exception ex) {
            Logger.getLogger(Aula16Exem01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
