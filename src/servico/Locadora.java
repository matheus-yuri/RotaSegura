package servico;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Contrato;
import modelo.Veiculo;

public class Locadora {

    private ArrayList<Veiculo> frota;
    private ArrayList<Cliente> clientes;
    private ArrayList<Contrato> contratos;
    private ArrayList<String> historico;

    public Locadora() {
        frota = new ArrayList<>();
        clientes = new ArrayList<>();
        contratos = new ArrayList<>();
        historico = new ArrayList<>();

        carregarHistorico();
    }

    public void cadastrarVeiculo(Veiculo veiculo) {
        frota.add(veiculo);
    }

    public void listarVeiculos() {

        if (frota.isEmpty()) {
            System.out.println("Nenhum veiculo cadastrado.");
            return;
        }

        System.out.println("===== VEICULOS CADASTRADOS =====");

        for (Veiculo veiculo : frota) {
            System.out.println(
                "Tipo: " + veiculo.getClass().getSimpleName()
                + " | Diaria base: R$ "
                + String.format("%.2f", veiculo.getValorDiaria())
            );
        }
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void listarClientes() {

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("===== CLIENTES CADASTRADOS =====");

        for (Cliente cliente : clientes) {
            System.out.println(
                "Nome: " + cliente.getNome()
                + " | CPF: " + cliente.getCpf()
            );
        }
    }

    public boolean estaDisponivel(
            Veiculo veiculo,
            LocalDate dataInicio,
            LocalDate dataFim) {

        for (Contrato contrato : contratos) {

            if (contrato.getVeiculo() == veiculo
                    && !contrato.isDevolvido()) {

                // Verifica se o período informado entra em conflito com outra locação.
                boolean conflito =
                        dataInicio.isBefore(contrato.getDataFim())
                        && dataFim.isAfter(contrato.getDataInicio());

                if (conflito) {
                    return false;
                }
            }
        }

        return true;
    }

    public void adicionarContrato(Contrato contrato) {

        if (!estaDisponivel(
                contrato.getVeiculo(),
                contrato.getDataInicio(),
                contrato.getDataFim())) {

            throw new IllegalArgumentException(
                "O veiculo ja esta alugado nesse periodo."
            );
        }

        contratos.add(contrato);
        salvarHistorico(contrato);
    }

    public void devolverVeiculo(Contrato contrato) {

        contrato.devolver();

        salvarHistorico(contrato);

        System.out.println("Veiculo devolvido com sucesso.");
    }

    private void salvarHistorico(Contrato contrato) {

        // O modo true mantém os registros anteriores no arquivo.
        try (FileWriter arquivo =
                new FileWriter("historico_locacoes.txt", true)) {

            arquivo.write(contrato.gerarRelatorio());
            arquivo.write("\n\n");

            historico.add(contrato.gerarRelatorio());

        } catch (IOException e) {

            System.out.println(
                "Erro ao salvar o historico: " + e.getMessage()
            );
        }
    }

    private void carregarHistorico() {

        Path caminho = Path.of("historico_locacoes.txt");

        if (Files.exists(caminho)) {

            try {

                historico = new ArrayList<>(
                    Files.readAllLines(caminho)
                );

            } catch (IOException e) {

                System.out.println(
                    "Erro ao carregar o historico: "
                    + e.getMessage()
                );
            }
        }
    }

    public void exibirHistorico() {

        if (historico.isEmpty()) {
            System.out.println("Nenhuma locacao registrada.");
            return;
        }

        System.out.println("===== HISTORICO DE LOCACOES =====");

        for (String linha : historico) {
            System.out.println(linha);
        }
    }

    public ArrayList<Veiculo> getFrota() {
        return frota;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Contrato> getContratos() {
        return contratos;
    }

    public ArrayList<String> getHistorico() {
        return historico;
    }
}