package controle;

import modelo.dao.DAOCliente;
import modelo.dto.DTOCliente;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CTRLCliente {

    private DAOCliente daoCliente;

    public CTRLCliente() {
        this.daoCliente = new DAOCliente();
    }

    public void salvarCliente(String nome, String email, String endereco, String telefone, String nascimento) {
    try {
        // Cria o objeto DTOCliente
        DTOCliente cliente = new DTOCliente(0, nome, email, endereco, telefone, nascimento);

        // Chama o método do DAO para salvar o cliente
        daoCliente.salvar(cliente);
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
    }
}

    public void excluirCliente(int idCliente) {
        try {
            daoCliente.excluir(idCliente);
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir cliente: " + e.getMessage());
        }
    }

        public List<String[]> listarClientes() {
        try {
            List<DTOCliente> clientes = daoCliente.listarTodos(); // Obtém a lista de DTOs
            List<String[]> dados = new ArrayList<>();

            // Converte cada DTOCliente em um array de String
            for (DTOCliente cliente : clientes) {
                dados.add(new String[]{
                    String.valueOf(cliente.getIdCliente()),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getEndereco(),
                    cliente.getTelefone(),
                    cliente.getNascimento()
                });
            }
            return dados; // Retorna a lista de arrays de String
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar clientes: " + e.getMessage());
            return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
        }
    }

    public String[] buscarClientePorId(int idCliente) {
    try {
        // Busca o cliente no DAO
        DTOCliente cliente = daoCliente.buscarPorId(idCliente);

        if (cliente != null) {
            // Retorna os dados do cliente em um array de String
            return new String[]{
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getEndereco(),
                cliente.getTelefone(),
                cliente.getNascimento()
            };
        } else {
            return null; // Retorna null se o cliente não for encontrado
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
    }
}

    public void atualizarCliente(String id, String nome, String email, String endereco, String telefone, String nascimento) {
        try {
            int idCliente = Integer.parseInt(id);
            DTOCliente cliente = new DTOCliente(idCliente, nome, email, endereco, telefone, nascimento);
            daoCliente.atualizar(cliente);
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: ID inválido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public List<String[]> buscarClientes(String id, String nome, String email, String endereco, String telefone, String nascimento) {
    try {
        // Busca os clientes no DAO
        List<DTOCliente> clientes = daoCliente.buscarClientes(id, nome, email, endereco, telefone, nascimento);

        // Converte a lista de DTOCliente para uma lista de String[]
        List<String[]> dados = new ArrayList<>();
        for (DTOCliente cliente : clientes) {
            dados.add(new String[]{
                String.valueOf(cliente.getIdCliente()),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getEndereco(),
                cliente.getTelefone(),
                cliente.getNascimento()
            });
        }
        return dados; // Retorna a lista de arrays de String
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar clientes: " + e.getMessage());
    }
}
}