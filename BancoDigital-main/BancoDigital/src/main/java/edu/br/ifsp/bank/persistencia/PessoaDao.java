package edu.br.ifsp.bank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.br.ifsp.bank.modelo.EnviarEmail;
import edu.br.ifsp.bank.modelo.GeradorCodigos;
import edu.br.ifsp.bank.modelo.Investimento;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.TipoUsuario;

public class PessoaDao {
    TransferenciaDao transferenciaDao = new TransferenciaDao();
    InvestimentoDao investimentoDao = new InvestimentoDao(); 

    private Pessoa mapPessoa(ResultSet rs) throws SQLException {
        Pessoa p = new Pessoa();
        p.setId(rs.getInt("id"));
        p.setCpf(rs.getString("cpf"));
        p.setNome(rs.getString("nome"));
        p.setSenha(rs.getString("senha"));
        p.setEmail(rs.getString("email"));
        p.setTelefone(rs.getString("telefone"));
        p.setEndereco(rs.getString("endereco"));
        p.setSaldo(rs.getFloat("saldo"));
        p.setRole(TipoUsuario.valueOf(rs.getString("role")));
        p.setHabilitadoPeloCliente(rs.getBoolean("habilitadoPeloGerente"));
        return p;
    }

    public ArrayList<Pessoa> findByAll() {
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente FROM pessoa"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) pessoas.add(mapPessoa(rs));

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
                "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente FROM pessoa WHERE nome LIKE ?"
            );
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) pessoa = mapPessoa(rs);

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
                "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente FROM pessoa WHERE email LIKE ?"
            );
            ps.setString(1, "%" + email + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) pessoa = mapPessoa(rs);

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
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente FROM pessoa WHERE id = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) pessoa = mapPessoa(rs);

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
                "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente FROM pessoa WHERE cpf = ?"
            );
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) pessoa = mapPessoa(rs);

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
            "SELECT * FROM pessoa WHERE nome = ? AND senha = ?"
        );
        ps.setString(1, nome);
        ps.setString(2, senha);

        ResultSet rs = ps.executeQuery();
        Pessoa pessoa = null;

        if (rs.next()) pessoa = mapPessoa(rs);

        rs.close();
        ps.close();
        conn.close();

        return pessoa;
    }

    public void deletar(int id) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(
            "DELETE FROM pessoa WHERE id = ?"
        );
        ps.setInt(1, id);
        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public Pessoa add(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            ps.setBoolean(9, pessoa.isHabilitadoPeloCliente());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) pessoa.setId(rs.getInt(1));
                else throw new DataAccessException("Erro ao pegar o ID gerado da pessoa.");
            }

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return pessoa;
    }

    public Pessoa editar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET cpf=?, nome=?, senha=?, email=?, telefone=?, endereco=?, saldo=?, role=?, habilitadoPeloGerente=? "
                   + "WHERE id=?";

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
            ps.setBoolean(9, pessoa.isHabilitadoPeloCliente());
            ps.setInt(10, pessoa.getId());

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

        while (rs.next()) lista.add(mapPessoa(rs));

        return lista;
    }

    public void investir(String cpf, float valor, float taxaMensal, int prazoMeses) throws SQLException {
        Pessoa p = findByCPF(cpf);
        if (p == null) throw new DataAccessException("Pessoa não encontrada.");
        if (p.getSaldo() < valor) throw new DataAccessException("Saldo insuficiente.");

        String sql = "UPDATE pessoa SET saldo = saldo - ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setFloat(1, valor);
            ps.setInt(2, p.getId());
            ps.executeUpdate();

            Investimento inv = new Investimento();
            inv.setIdPessoa(p.getId());
            inv.setValor(valor);
            inv.setTaxaMensal(taxaMensal);
            inv.setPrazoMeses(prazoMeses);
            inv.setDataAplicacao(LocalDateTime.now());

            investimentoDao.save(conn, inv);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public boolean validarSenhaAntiga(int id, String senhaAntiga) {
        String sql = "SELECT senha FROM pessoa WHERE id = ?";
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            String senhaBanco = null;

            if (rs.next()) senhaBanco = rs.getString("senha");

            rs.close();
            ps.close();
            conn.close();

            return senhaBanco != null && senhaBanco.equals(senhaAntiga);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alterarSenha(int id, String senhaNova) {
        String sql = "UPDATE pessoa SET senha = ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, senhaNova);
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0)
                throw new DataAccessException("Pessoa não encontrada.");

            GeradorCodigos.getInstance().criaCodigo();
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) {}
            }
        }
    }

    public void EnviarEmailVerificacaoCodigo(String emaildestino) {
        GeradorCodigos.getInstance().criaCodigo(); 
        EnviarEmail.enviarEmailComCodigo(emaildestino);
    }

    public void EnviarEmailParaTrocarSenha(String emaildestino, int id) {
        GeradorCodigos.getInstance().criaCodigo();
        EnviarEmail.EnviarEmailParaTrocarSenha(
            emaildestino,
            id,
            GeradorCodigos.getInstance().getCodigoInt()
        );
    }

    public boolean validaCodigo(Integer codigoDigitado) {
        GeradorCodigos g = GeradorCodigos.getInstance();
        Integer codigoReal = g.getCodigoInt();
        g.criaCodigo();
        return codigoReal.equals(codigoDigitado);
    }

    public void habilitarConta(int id) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE pessoa SET habilitadoPeloGerente = 1 WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void desabilitarConta(int id) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE pessoa SET habilitadoPeloGerente = 0 WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
    public ArrayList<Pessoa> findByAllPessoasDesabilitadas(){
    	ArrayList<Pessoa> pessoas = new ArrayList<>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente FROM pessoa where habilitadoPeloGerente = 0"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) pessoas.add(mapPessoa(rs));

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return pessoas;
    }
    
    public ArrayList<Pessoa> findByAllPessoasHabilitadas(){
    	ArrayList<Pessoa> pessoas = new ArrayList<>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, cpf, nome, senha, email, telefone, endereco, saldo, role, habilitadoPeloGerente FROM pessoa where habilitadoPeloGerente = 1"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) pessoas.add(mapPessoa(rs));

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return pessoas;
    }

    public ArrayList<Pessoa> findByNomeDesabilitadas(String nome) {
    	ArrayList<Pessoa> pessoas = new ArrayList<>();
        
        
        String sql = "SELECT id, cpf, nome, senha, email, telefone, endereco, role, saldo, habilitadoPeloGerente FROM pessoa "
                   + "WHERE habilitadoPeloGerente = 0 AND nome LIKE ?";
        
        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nome + "%"); 
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    
                    Pessoa p = new Pessoa();
                    
                    p.setId(rs.getInt("id"));
                    p.setCpf(rs.getString("cpf"));
                    p.setNome(rs.getString("nome"));
                    p.setEmail(rs.getString("email"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setEndereco(rs.getString("endereco"));
                    
                    p.setHabilitadoPeloCliente(rs.getInt("habilitadoPeloGerente") == 1); 
                    
                    pessoas.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoas habilitadas por nome: " + e.getMessage());
            e.printStackTrace();
        }
        return pessoas;
    }
    
    public ArrayList<Pessoa> findByNomeHabilitadas(String nome) {
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        
        String sql = "SELECT id, cpf, nome, senha, email, telefone, endereco, role, saldo, habilitadoPeloGerente FROM pessoa "
                   + "WHERE habilitadoPeloGerente = 1 AND nome LIKE ?";
        
        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nome + "%"); 
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    
                    Pessoa p = new Pessoa();
                    
                    p.setId(rs.getInt("id"));
                    p.setCpf(rs.getString("cpf"));
                    p.setNome(rs.getString("nome"));
                    p.setEmail(rs.getString("email"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setEndereco(rs.getString("endereco"));
                    
                    p.setHabilitadoPeloCliente(rs.getInt("habilitadoPeloGerente") == 1); 
                    
                    pessoas.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoas habilitadas por nome: " + e.getMessage());
            e.printStackTrace();
        }
        return pessoas;
    }
    
}
