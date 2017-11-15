package br.ufjf.dcc171;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Connection conexao;
    private PreparedStatement operacaoInsere;
    private PreparedStatement operacaoListar;

    public ProdutoDAO() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        String driverUrl = "jdbc:derby://localhost:1527/dcc171-2013-3";
        conexao = DriverManager.getConnection(driverUrl, "usuario", "senha");
        operacaoInsere = conexao.prepareStatement("INSERT INTO produto(nome, qtd, atualizado) VALUES(?,?,CURRENT_TIMESTAMP)");
        operacaoListar = conexao.prepareStatement("SELECT nome, qtd FROM produto WHERE qtd > ? ORDER BY qtd DESC");

    }

    public void criar(Produto prod) throws Exception {
        operacaoInsere.clearParameters();
        operacaoInsere.setString(1, prod.getNome());
        operacaoInsere.setInt(2, prod.getQtd());
        operacaoInsere.executeUpdate();
    }

    public List<Produto> listarTodos() throws Exception {
        List<Produto> produtos = new ArrayList<>();
        operacaoListar.clearParameters();
        operacaoListar.setInt(1, 0);
        ResultSet resultado = operacaoListar.executeQuery();
        while (resultado.next()) {
            Produto p = new Produto();
            p.setNome(resultado.getString(1));
            p.setQtd(resultado.getInt(2));
            produtos.add(p);
        }
        return produtos;
    }
}
