package edu.br.ifsp.bank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.br.ifsp.bank.modelo.Investimento;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.TipoUsuario;

public class PessoaDao {
	TransferenciaDao transferenciaDao = new TransferenciaDao();
    InvestimentoDao investimentoDao = new InvestimentoDao(); 
	
	public ArrayList<Pessoa> findByAll(){
		ArrayList<Pessoa> pessoas = new ArrayList<>();

		 try {
	            Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement ps = conn.prepareStatement("SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role FROM pessoa");
	            
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Pessoa pessoa = new Pessoa();
	                pessoa.setId(rs.getInt("id"));
	                pessoa.setCpf(rs.getString("cpf"));
	                pessoa.setNome(rs.getString("nome"));
	                pessoa.setSenha(rs.getString("senha"));
	                pessoa.setEmail(rs.getString("email"));
	                pessoa.setTelefone(rs.getString("telefone"));
	                pessoa.setEndereco(rs.getString("endereco"));
	                pessoa.setSaldo(rs.getFloat("saldo"));
	                pessoa.setRole(TipoUsuario.valueOf(rs.getString("role")));
	                pessoas.add(pessoa);
	            }

	            rs.close();
	            ps.close();
	            conn.close();
	        } catch (SQLException e) {
	            throw new DataAccessException(e);
	        }

	        return pessoas;
	}
	
	
	
	public Pessoa findByNome(String nome) {
	    Pessoa pessoa = null;
	    try {
	        Connection conn = ConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role FROM pessoa WHERE nome LIKE ?");
	        ps.setString(1, "%" + nome + "%");
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            pessoa = new Pessoa();
	            pessoa.setId(rs.getInt("id"));
	            pessoa.setCpf(rs.getString("cpf"));
	            pessoa.setNome(rs.getString("nome"));
	            pessoa.setSenha(rs.getString("senha"));
	            pessoa.setEmail(rs.getString("email"));
	            pessoa.setTelefone(rs.getString("telefone"));
	            pessoa.setEndereco(rs.getString("endereco"));
	            pessoa.setSaldo(rs.getFloat("saldo"));
                pessoa.setRole(TipoUsuario.valueOf(rs.getString("role")));

	        }

	        rs.close();
	        ps.close();
	        conn.close();
	    } catch (SQLException e) {
	        throw new DataAccessException(e);
	    }

	    return pessoa;
	}

	
	public Pessoa findByEmail(String email) {
	    Pessoa pessoa = null;
	    try {
	        Connection conn = ConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role FROM pessoa WHERE email LIKE ?");
	        ps.setString(1, "%" + email + "%");
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            pessoa = new Pessoa();
	            pessoa.setId(rs.getInt("id"));
	            pessoa.setCpf(rs.getString("cpf"));
	            pessoa.setNome(rs.getString("nome"));
	            pessoa.setSenha(rs.getString("senha"));
	            pessoa.setEmail(rs.getString("email"));
	            pessoa.setTelefone(rs.getString("telefone"));
	            pessoa.setEndereco(rs.getString("endereco"));
	            pessoa.setSaldo(rs.getFloat("saldo"));
                pessoa.setRole(TipoUsuario.valueOf(rs.getString("role")));

	        }

	        rs.close();
	        ps.close();
	        conn.close();
	    } catch (SQLException e) {
	        throw new DataAccessException(e);
	    }

	    return pessoa;
	}


    public Pessoa findById(int id) {
        Pessoa pessoa = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role FROM pessoa WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setSaldo(rs.getFloat("saldo"));
                pessoa.setRole(TipoUsuario.valueOf(rs.getString("role")));

            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return pessoa;
    }
    
    public Pessoa findByCPF(String cpf) {
        Pessoa pessoa = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role FROM pessoa WHERE cpf = ?");
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setSaldo(rs.getFloat("saldo"));
                pessoa.setRole(TipoUsuario.valueOf(rs.getString("role")));

               
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return pessoa;
    }

    
    public Pessoa login(String nome, String senha) throws Exception {
	    Connection conn = ConnectionFactory.getConnection();
	    PreparedStatement ps = conn.prepareStatement(
	        "SELECT * FROM pessoa WHERE nome = ? AND senha = ?");
	    ps.setString(1, nome);
	    ps.setString(2, senha);
	    
	    ResultSet rs = ps.executeQuery();
	    Pessoa pessoa = null;
	    
	    if (rs.next()) {
	        pessoa = new Pessoa();
	        pessoa.setId(rs.getInt("id"));
	        pessoa.setCpf(rs.getString("cpf"));
	        pessoa.setNome(rs.getString("nome"));
	        pessoa.setSenha(rs.getString("senha"));
	        pessoa.setEmail(rs.getString("email"));
	        pessoa.setTelefone(rs.getString("telefone"));
	        pessoa.setEndereco(rs.getString("endereco"));
	        pessoa.setSaldo(rs.getFloat("saldo"));
            pessoa.setRole(TipoUsuario.valueOf(rs.getString("role")));

	        rs.close();
	    }
	    
	    ps.close();
	    conn.close();
	    return pessoa;

	}

    public void deletar(int id) throws Exception {
	    Connection conn = ConnectionFactory.getConnection();
	    PreparedStatement ps = conn.prepareStatement(
	        "delete from pessoa where id = ?");
	    ps.setInt(1, id);;
	    ps.executeUpdate();

  	    
	    
	    ps.close();
	    conn.close();
	    
	}
    
    
    public Pessoa add(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pessoa.getCpf());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getSenha());
            ps.setString(4, pessoa.getEmail());
            ps.setString(5, pessoa.getTelefone());
            ps.setString(6, pessoa.getEndereco());
            ps.setDouble(7, pessoa.getSaldo());
            ps.setString(8, pessoa.getRole().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pessoa.setId(rs.getInt(1));
                } else {
                    throw new DataAccessException("Erro ao pegar o ID gerado da pessoa.");
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return pessoa;
    }

    public Pessoa editar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET cpf = ?, nome = ?, senha = ?, email = ?, telefone = ?, endereco = ?, saldo = ?, role = ? "
                   + "WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pessoa.getCpf());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getSenha());
            ps.setString(4, pessoa.getEmail());
            ps.setString(5, pessoa.getTelefone());
            ps.setString(6, pessoa.getEndereco());
            ps.setDouble(7, pessoa.getSaldo());
            ps.setString(8, pessoa.getRole().name());
            ps.setInt(9, pessoa.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return pessoa;
    }

    public List<Pessoa> buscarUsuarios(String texto) throws SQLException {
	    List<Pessoa> lista = new ArrayList<>();

	    String sql = "SELECT * FROM pessoa WHERE cpf LIKE ? OR email LIKE ? OR nome LIKE ?";
	    Connection conn = ConnectionFactory.getConnection();
	    PreparedStatement ps = conn.prepareStatement(sql);

	    String busca = "%" + texto + "%";

	    ps.setString(1, busca);
	    ps.setString(2, busca);
	    ps.setString(3, busca);

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        Pessoa p = new Pessoa();
	        p.setCpf(rs.getString("cpf"));
	        p.setNome(rs.getString("nome"));
	        p.setEmail(rs.getString("email"));
	        p.setSaldo(rs.getFloat("saldo"));
	        lista.add(p);
	    }

	    return lista;
	}
    
    public void investir(String cpf, float valor, float taxaMensal, int prazoMeses) throws SQLException {
        Pessoa p = findByCPF(cpf);
        if (p == null) {
            throw new DataAccessException("Pessoa não encontrada para o CPF: " + cpf);
        }

        if (p.getSaldo() < valor) {
            throw new DataAccessException("Saldo insuficiente para aplicar em investimento.");
        }

        String sqlAtualizaSaldo = "UPDATE pessoa SET saldo = saldo - ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement ps = conn.prepareStatement(sqlAtualizaSaldo)) {
                ps.setFloat(1, valor);
                ps.setInt(2, p.getId());
                ps.executeUpdate();
            }

            Investimento inv = new Investimento();
            inv.setIdPessoa(p.getId());
            inv.setValor(valor);
            inv.setTaxaMensal(taxaMensal);
            inv.setPrazoMeses(prazoMeses);
            inv.setDataAplicacao(LocalDateTime.now());

            investimentoDao.save(conn, inv);

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { }
            }
            throw e;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { /* ignora */ }
            }
        }
    }

}
