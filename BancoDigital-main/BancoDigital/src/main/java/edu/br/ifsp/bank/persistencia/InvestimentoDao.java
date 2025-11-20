package edu.br.ifsp.bank.persistencia;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.br.ifsp.bank.modelo.Investimento;

public class InvestimentoDao {

    // Usa sua ConnectionFactory
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

    // 1) Método simples para abrir a conexão própria
    public Investimento save(Investimento inv) {
        try (Connection conn = getConnection()) {
            return save(conn, inv);              
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    // 2) Sobrecarga usada pelo PessoaDao.investir 
    public Investimento save(Connection conn, Investimento inv) throws SQLException {
        String sql = "INSERT INTO investimento "
                   + "(id_pessoa, valor, taxa_mensal, prazo_meses, data_aplicacao) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inv.getIdPessoa());
            ps.setFloat(2, inv.getValor());
            ps.setFloat(3, inv.getTaxaMensal());
            ps.setInt(4, inv.getPrazoMeses());
            ps.setTimestamp(5, Timestamp.valueOf(inv.getDataAplicacao()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    inv.setId(rs.getInt(1));
                }
            }
        }

        return inv;
    }

    // 3) Listar investimentos de uma pessoa
    public List<Investimento> listarPorPessoa(int idPessoa) throws Exception {
        String sql = 
            "SELECT i.*, p.nome AS nomePessoa " +
            "FROM investimento i " +
            "JOIN pessoa p ON p.id = i.id_pessoa " +
            "WHERE i.id_pessoa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPessoa);
            ResultSet rs = ps.executeQuery();

            List<Investimento> lista = new ArrayList<>();

            while (rs.next()) {
                Investimento inv = new Investimento();
                inv.setId(rs.getInt("id"));
                inv.setValor(rs.getFloat("valor"));
                inv.setTaxaMensal(rs.getFloat("taxa_mensal"));
                inv.setPrazoMeses(rs.getInt("prazo_meses"));
                inv.setDataAplicacao(rs.getTimestamp("data_aplicacao").toLocalDateTime());

                inv.setNomePessoa(rs.getString("nomePessoa"));

                lista.add(inv);
            }

            return lista;
        }
    }
}
