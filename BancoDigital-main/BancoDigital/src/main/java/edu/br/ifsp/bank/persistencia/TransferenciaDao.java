package edu.br.ifsp.bank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.br.ifsp.bank.modelo.Transferencia;

public class TransferenciaDao {

    
    public ArrayList<Transferencia> findByAll() {
        ArrayList<Transferencia> transferencias = new ArrayList<>();

        String sql = "SELECT id, id_usuarioQueTransferiu, id_usuarioQueRecebeu, horario, valor FROM transferencia";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Transferencia t = new Transferencia();
                t.setId(rs.getInt("id"));
                t.setId_usuarioQueTransferiu(rs.getInt("id_usuarioQueTransferiu"));
                t.setId_usuarioQueRecebeu(rs.getInt("id_usuarioQueRecebeu"));
                t.setHorario(rs.getTimestamp("horario").toLocalDateTime());
                t.setValor(rs.getFloat("valor"));

                transferencias.add(t);
            }

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return transferencias;
    }

    
    public Transferencia add(Transferencia transferencia) {
        String sql = "INSERT INTO transferencia "
                   + "(id_usuarioQueTransferiu, id_usuarioQueRecebeu, horario, valor) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, transferencia.getId_usuarioQueTransferiu());
            ps.setInt(2, transferencia.getId_usuarioQueRecebeu());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(transferencia.getHorario()));
            ps.setFloat(4, transferencia.getValor());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    transferencia.setId(rs.getInt(1));
                } else {
                    throw new DataAccessException("Erro ao pegar a chave gerada.");
                }
            }

            return transferencia;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
    

    public void depositar(String cpf, float valor) throws SQLException {
    	PessoaDao pdao = new PessoaDao();
    	Transferencia t = new Transferencia();
        String sql = "UPDATE pessoa SET saldo = saldo + ? WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, valor);
            ps.setString(2, cpf);
            ps.executeUpdate();
            t.setHorario(LocalDateTime.now());
            t.setValor(valor);
            t.setId_usuarioQueRecebeu(pdao.findByCPF(cpf).getId());
            t.setId_usuarioQueTransferiu(pdao.findByCPF(cpf).getId());
            add(t);
        }
    }

    
    public void retirar(String cpf, float valor) throws SQLException {
    	PessoaDao pdao = new PessoaDao();
    	Transferencia t = new Transferencia();

        String sql = "UPDATE pessoa SET saldo = saldo - ? WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setFloat(1, valor);
            ps.setString(2, cpf);
            ps.executeUpdate();
            t.setHorario(LocalDateTime.now());
            t.setValor(valor);
            t.setId_usuarioQueRecebeu(pdao.findByCPF(cpf).getId());
            t.setId_usuarioQueTransferiu(pdao.findByCPF(cpf).getId());
            add(t);
        }
    }
    
    
    

	

	
	public Transferencia transferirViaCpf(String cpfQueVaiSerTransferido, String cpfDoUsuarioLogado, float valor) throws SQLException{
		PessoaDao pdao = new PessoaDao();
		retirar(cpfDoUsuarioLogado, valor);
		depositar(cpfQueVaiSerTransferido, valor);
		
		Transferencia t = new Transferencia();
		
		t.setId_usuarioQueTransferiu(pdao.findByCPF(cpfDoUsuarioLogado).getId());
		t.setId_usuarioQueRecebeu(pdao.findByCPF(cpfQueVaiSerTransferido).getId());
		t.setHorario(LocalDateTime.now());
		t.setValor(valor);
		
		return add(t);
		
	}
	
}
