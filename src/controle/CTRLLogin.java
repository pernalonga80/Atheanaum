package controle;

import modelo.dao.DAOUsuario;
import modelo.dto.DTOUsuario;
import org.mindrot.jbcrypt.BCrypt;

public class CTRLLogin {

    private final DAOUsuario daoUsuario;

    public CTRLLogin() {
        this.daoUsuario = new DAOUsuario();
    }

    // Método para cadastrar um novo usuário
    public boolean cadastrarUsuario(String usuario, String email, String senha) {
        // Criptografar a senha
        String senhaCriptografada = BCrypt.hashpw(senha, BCrypt.gensalt());

        // Criar um objeto Usuario
        DTOUsuario novoUsuario = new DTOUsuario();
        novoUsuario.setUsuario(usuario);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senhaCriptografada);

        // Chamar o DAO para salvar no banco de dados
        return daoUsuario.cadastrarUsuario(novoUsuario);
    }

    // Método para autenticar (já existente)
    public boolean autenticar(String email, String senha) {

        DTOUsuario usuario = daoUsuario.buscarPorEmail(email);
        if (usuario != null) {
            return daoUsuario.verificarSenha(senha, usuario.getSenha());
        }
        return false;
    }
}
