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
                Produto p = new Produto();
                p.setNome("Produto " + rnd.nextInt(100));
                p.setQtd(rnd.nextInt(10) + 1);
                
                operacaoInsere.clearParameters();
                operacaoInsere.setString(1, p.getNome() );
                operacaoInsere.setInt(2, p.getQtd());
                operacaoInsere.executeUpdate();
            }
            operacaoListar.clearParameters();
            operacaoListar.setInt(1, 0);
            ResultSet resultado = operacaoListar.executeQuery();
            while (resultado.next()) {
                Produto p = new Produto();
                p.setNome(resultado.getString(1));
                p.setQtd(resultado.getInt(2));
                System.out.println(
                        p.getNome()
                        + "\t\t"
                        + p.getQtd()
                );
            }

        } catch (Exception ex) {
            Logger.getLogger(Aula16Exem01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
