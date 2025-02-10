package controle;
import modelo.dao.DAOLivro;
import modelo.dto.DTOLivro;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CTRLLivro {

    private DAOLivro daoLivro;

    public CTRLLivro() {
        this.daoLivro = new DAOLivro(); // Inicializa o DAO
    }

    // Método para listar todos os livros
    public List<String[]> listarLivros() {
        List<DTOLivro> livros = daoLivro.listarTodos();
        List<String[]> dados = new ArrayList<>();
        for (DTOLivro livro : livros) {
            dados.add(new String[]{
                String.valueOf(livro.getIdLivro()),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getGenero(),
                livro.getDataPublicacao(),
                livro.getEditora()
            });
        }
        return dados;
    }

    // Método para salvar um livro
    public void salvarLivro(String titulo, String autor, String genero, String dataPublicacao, String editora) {
        DTOLivro livro = new DTOLivro(0, titulo, autor, genero, dataPublicacao, editora);
        daoLivro.salvar(livro);
    }

    // Método para excluir um livro
    public void excluirLivro(int idLivro) {
        daoLivro.excluir(idLivro);
    }
    
    public String[] buscarLivroPorId(int idLivro) {
        DTOLivro livro = daoLivro.buscarPorId(idLivro);
        if (livro != null) {
            // Retorna os dados do livro em um array de String
            return new String[]{
                livro.getTitulo(),
                livro.getAutor(),
                livro.getGenero(),
                livro.getDataPublicacao(),
                livro.getEditora()
            };
        } else {
            return null; // Retorna null se o livro não for encontrado
        }
}
    
    public void atualizarLivro(int idLivro, String titulo, String autor, String genero, String dataPublicacao, String editora) {
        DTOLivro livro = new DTOLivro(idLivro, titulo, autor, genero, dataPublicacao, editora);
        daoLivro.atualizar(livro);
}
    
}
