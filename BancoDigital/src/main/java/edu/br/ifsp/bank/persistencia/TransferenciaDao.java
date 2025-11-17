package edu.br.ifsp.bank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.TipoUsuario;
import edu.br.ifsp.bank.modelo.Transferencia;

public class TransferenciaDao {
	
	public ArrayList<Transferencia> findByAll() {
		ArrayList<Transferencia> transferencias = new ArrayList<>();

	    try {
	        Connection conn = ConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "SELECT id, id_usuarioQueTransferiu, id_usuarioQueRecebeu, horario, valor FROM transferencia"
	        );

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Transferencia transferencia = new Transferencia();
	            transferencia.setId(rs.getInt("id"));
	            transferencia.setId_usuarioQueTransferiu(rs.getInt("id_usuarioQueTransferiu"));
	            transferencia.setId_usuarioQueRecebeu(rs.getInt("id_usuarioQueRecebeu"));
	            transferencia.setHorario(rs.getTimestamp("horario").toLocalDateTime());
	            transferencia.setValor(rs.getFloat("valor"));

	            transferencias.add(transferencia);
	        }

	        rs.close();
	        ps.close();
	        conn.close();
	    } catch (SQLException e) {
	        throw new DataAccessException(e);
	    }

	    return transferencias;
	}

	
	
	 
    public Transferencia save(Transferencia transferencia) {
        try {
            if (isNew(transferencia)) {
                insert(transferencia);
            } else {
                update(transferencia);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return transferencia;
    }

    private boolean isNew(Transferencia transferencia) {
        return transferencia.getId() == 0;
    }

    private void insert(Transferencia transferencia) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        
        String sql = "INSERT INTO transferencia (id_usuarioQueTransferiu, id_usuarioQueRecebeu, horario, valor) "
                   + "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, transferencia.getId_usuarioQueTransferiu());
        ps.setInt(2, transferencia.getId_usuarioQueRecebeu());
        ps.setTimestamp(3, java.sql.Timestamp.valueOf(transferencia.getHorario()));
        ps.setFloat(4, transferencia.getValor());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            transferencia.setId(rs.getInt(1));
        } else {
            throw new DataAccessException("Falha ao obter a PK da transferência.");
        }

        rs.close();
        ps.close();
        conn.close();
    }

    private void update(Transferencia transferencia) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "UPDATE transferencia SET "
                   + "id_usuarioQueTransferiu = ?, "
                   + "id_usuarioQueRecebeu = ?, "
                   + "horario = ?, "
                   + "valor = ? "
                   + "WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, transferencia.getId_usuarioQueTransferiu());
        ps.setInt(2, transferencia.getId_usuarioQueRecebeu());
        ps.setTimestamp(3, java.sql.Timestamp.valueOf(transferencia.getHorario()));
        ps.setFloat(4, transferencia.getValor());
        ps.setInt(5, transferencia.getId());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}
