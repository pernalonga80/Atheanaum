package modelo.dao;

import modelo.dto.DTOCliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCliente {

    private final String url = "jdbc:mysql://localhost:3306/sistema_bibliotecario";
    private final String usuario = "root";
    private final String senha = "";

    public void salvar(DTOCliente cliente) throws SQLException {
        String query = "INSERT INTO clientes (nome,email,endereco,telefone,nascimento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getEmail());
            pst.setString(3, cliente.getEndereco());
            pst.setString(4, cliente.getTelefone());
            pst.setDate(5, Date.valueOf(cliente.getNascimento()));
            pst.executeUpdate();
        }
    }

    public void excluir(int idCliente) throws SQLException {
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, idCliente);
            pst.executeUpdate();
        }
    }

    public List<DTOCliente> listarTodos() throws SQLException {
        List<DTOCliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                DTOCliente cliente = new DTOCliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("nascimento")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public DTOCliente buscarPorId(int idCliente) throws SQLException {
        DTOCliente cliente = null;
        String query = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, idCliente);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    cliente = new DTOCliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("endereco"),
                            rs.getString("telefone"),
                            rs.getString("nascimento")
                    );
                }
            }
        }
        return cliente;
    }

    public void atualizar(DTOCliente cliente) throws SQLException {
        String query = "UPDATE clientes SET nome = ?, email = ?, endereco = ?, telefone = ?, nascimento = ? WHERE id_cliente = ?";
        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getEmail());
            pst.setString(3, cliente.getEndereco());
            pst.setString(4, cliente.getTelefone());
            pst.setString(5, cliente.getNascimento());
            pst.setInt(6, cliente.getIdCliente());
            pst.executeUpdate();
        }
    }

    public List<DTOCliente> buscarClientes(String id, String nome, String email, String endereco, String telefone, String nascimento) throws SQLException {
        List<DTOCliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes WHERE 1=1";
        if (id != null && !id.isEmpty()) {
            query += " AND id_cliente = ?";
        }
        if (nome != null && !nome.isEmpty()) {
            query += " AND nome LIKE ?";
        }
        if (email != null && !email.isEmpty()) {
            query += " AND email LIKE ?";
        }
        if (endereco != null && !endereco.isEmpty()) {
            query += " AND endereco LIKE ?";
        }
        if (telefone != null && !telefone.isEmpty()) {
            query += " AND telefone LIKE ?";
        }
        if (nascimento != null && !nascimento.isEmpty()) {
            query += " AND nascimento LIKE ?";
        }

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement pst = conn.prepareStatement(query)) {
            int index = 1;
            if (id != null && !id.isEmpty()) {
                pst.setInt(index++, Integer.parseInt(id));
            }
            if (nome != null && !nome.isEmpty()) {
                pst.setString(index++, "%" + nome + "%");
            }
            if (email != null && !email.isEmpty()) {
                pst.setString(index++, "%" + email + "%");
            }
            if (endereco != null && !endereco.isEmpty()) {
                pst.setString(index++, "%" + endereco + "%");
            }
            if (telefone != null && !telefone.isEmpty()) {
                pst.setString(index++, "%" + telefone + "%");
            }
            if (nascimento != null && !nascimento.isEmpty()) {
                pst.setString(index++, "%" + nascimento + "%");
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DTOCliente cliente = new DTOCliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("endereco"),
                            rs.getString("telefone"),
                            rs.getString("nascimento")
                    );
                    clientes.add(cliente);
                }
            }
        }
        return clientes;
    }
}
