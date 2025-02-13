package controle;

import modelo.dao.DAOEmprestimo;
import modelo.dto.DTOEmprestimo;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CTRLEmprestimo {

    private DAOEmprestimo daoEmprestimo;

    public CTRLEmprestimo() {
        this.daoEmprestimo = new DAOEmprestimo(); // Inicializa o DAO
    }

    // Método para listar todos os empréstimos
    public List<String[]> listarEmprestimos() {
        List<DTOEmprestimo> emprestimos = daoEmprestimo.listarTodos();
        List<String[]> dados = new ArrayList<>();
        for (DTOEmprestimo emprestimo : emprestimos) {
            dados.add(new String[]{
                String.valueOf(emprestimo.getIdEmprestimo()),
                String.valueOf(emprestimo.getIdCliente()),
                String.valueOf(emprestimo.getIdLivro()),
                emprestimo.getDataEmprestimo().toString(),
                emprestimo.getPrazoEmprestimo().toString()
            });
        }
        return dados;
    }

    // Método para salvar um empréstimo
    public void salvarEmprestimo(int idCliente, int idLivro, String dataEmprestimo, String prazoEmprestimo) {
        try {
            // Converte as strings das datas para java.util.Date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataEmprestimo);
            Date prazo = format.parse(prazoEmprestimo);

            // Cria o objeto DTOEmprestimo
            DTOEmprestimo emprestimo = new DTOEmprestimo(0, idCliente, idLivro, data, prazo);

            // Chama o método do DAO para salvar o empréstimo
            daoEmprestimo.salvar(emprestimo);
        } catch (ParseException e) {
            throw new RuntimeException("Formato de data inválido. Use o formato yyyy-MM-dd.");
        }
    }

    // Método para excluir um empréstimo
    public void excluirEmprestimo(int idEmprestimo) {
        daoEmprestimo.excluir(idEmprestimo);
    }

    // Método para buscar empréstimos por ID do cliente
    public List<String[]> buscarEmprestimosPorClienteId(String clienteId) {
        List<DTOEmprestimo> emprestimos = daoEmprestimo.buscarEmprestimosPorClienteId(clienteId);
        List<String[]> dados = new ArrayList<>();
        for (DTOEmprestimo emprestimo : emprestimos) {
            dados.add(new String[]{
                String.valueOf(emprestimo.getIdEmprestimo()),
                String.valueOf(emprestimo.getIdCliente()),
                String.valueOf(emprestimo.getIdLivro()),
                emprestimo.getDataEmprestimo().toString(),
                emprestimo.getPrazoEmprestimo().toString()
            });
        }
        return dados;
    }
}
