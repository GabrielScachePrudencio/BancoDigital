package edu.br.ifsp.bank.persistencia;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.TipoUsuario;
import edu.br.ifsp.bank.modelo.Transferencia;
import jakarta.servlet.http.HttpSession;

public class PessoaDao {
	TransferenciaDao transferenciaDao = new TransferenciaDao();
	
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
	
	public List<Pessoa> findByDescricao(String nome) {
        List<Pessoa> pessoas = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, cpf, nome, senha, email, telefone, endereco, saldo FROM pessoa WHERE nome LIKE ?");
            ps.setString(1, "%" + nome + "%");
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
	            "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo FROM pessoa WHERE nome LIKE ?");
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
	            "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo FROM pessoa WHERE email LIKE ?");
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
            PreparedStatement ps = conn.prepareStatement("SELECT id, cpf, nome, senha, email, telefone, endereco, saldo FROM pessoa WHERE id = ?");
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
    
    
    public Pessoa save(Pessoa pessoa) {
        try {
            if (isNew(pessoa)) {
                insert(pessoa);
            } else {
                update(pessoa);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return pessoa;
    }

    private boolean isNew(Pessoa pessoa) {
        return pessoa.getId() == 0;
    }

    private void insert(Pessoa pessoa) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, pessoa.getCpf());
        ps.setString(2, pessoa.getNome());
        ps.setString(3, pessoa.getSenha());
        ps.setString(4, pessoa.getEmail());
        ps.setString(5, pessoa.getTelefone());
        ps.setString(6, pessoa.getEndereco());
        ps.setDouble(7, pessoa.getSaldo());
        ps.setString(8, pessoa.getRole().name());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            pessoa.setId(id);
        } else {
            throw new DataAccessException("PK faltando");
        }

        rs.close();
        ps.close();
        conn.close();
    }

    private void update(Pessoa pessoa) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "UPDATE pessoa SET cpf = ?, nome = ?, senha = ?, email = ?, telefone = ?, endereco = ?, saldo = ?, role = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
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
        ps.close();
        conn.close();
    }
    
    
    public void depositar(String cpf, float valor) throws SQLException {
    	Transferencia t = new Transferencia();
        String sql = "UPDATE pessoa SET saldo = saldo + ? WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, valor);
            ps.setString(2, cpf);
            ps.executeUpdate();
            t.setHorario(LocalDateTime.now());
            t.setValor(valor);
            t.setId_usuarioQueRecebeu(findByCPF(cpf).getId());
            t.setId_usuarioQueTransferiu(findByCPF(cpf).getId());
            transferenciaDao.save(t);
        }
    }

    
    public void retirar(String cpf, float valor) throws SQLException {
    	Transferencia t = new Transferencia();

        String sql = "UPDATE pessoa SET saldo = saldo - ? WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setFloat(1, valor);
            ps.setString(2, cpf);
            ps.executeUpdate();
            t.setHorario(LocalDateTime.now());
            t.setValor(valor);
            t.setId_usuarioQueRecebeu(findByCPF(cpf).getId());
            t.setId_usuarioQueTransferiu(findByCPF(cpf).getId());
            transferenciaDao.save(t);
        }
    }
    
    
    

	

	
	public Transferencia transferirViaCpf(String cpfQueVaiSerTransferido, String cpfDoUsuarioLogado, float valor) throws SQLException{
		retirar(cpfDoUsuarioLogado, valor);
		depositar(cpfQueVaiSerTransferido, valor);
		
		Transferencia t = new Transferencia();
		
		t.setId_usuarioQueTransferiu(findByCPF(cpfDoUsuarioLogado).getId());
		t.setId_usuarioQueRecebeu(findByCPF(cpfQueVaiSerTransferido).getId());
		t.setHorario(LocalDateTime.now());
		t.setValor(valor);
		
		return transferenciaDao.save(t);
		
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

}

   
    


