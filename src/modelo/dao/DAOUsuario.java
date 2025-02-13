package modelo.dao;

import modelo.dto.DTOUsuario;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class DAOUsuario {

    private final String url = "jdbc:mysql://localhost:3306/sistema_bibliotecario";
    private final String usuarioBanco = "root";
    private final String senhaBanco = "";

    // Método para buscar um usuário pelo email
    public DTOUsuario buscarPorEmail(String email) {
        String query = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(url, usuarioBanco, senhaBanco);
                PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                DTOUsuario usuario = new DTOUsuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para verificar a senha
    public boolean verificarSenha(String senhaDigitada, String senhaCriptografada) {
        return BCrypt.checkpw(senhaDigitada, senhaCriptografada);
    }

    // Método para cadastrar um novo usuário
    public boolean cadastrarUsuario(DTOUsuario usuario) {
        String query = "INSERT INTO usuarios (usuario, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, usuarioBanco, senhaBanco);
                PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, usuario.getUsuario());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getSenha());
            pst.executeUpdate();
            return true; // Cadastro bem-sucedido
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Erro ao cadastrar
        }
    }
}
