package servico;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Contrato;
import modelo.Veiculo;

public class Locadora {

    private ArrayList<Contrato> contratos;
    private ArrayList<String> historico;

    public Locadora() {
        contratos = new ArrayList<>();
        historico = new ArrayList<>();

        carregarHistorico();
    }

    public boolean estaDisponivel(
            Veiculo veiculo,
            LocalDate dataInicio,
            LocalDate dataFim) {

        for (Contrato contrato : contratos) {

            if (contrato.getVeiculo() == veiculo) {

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

    public ArrayList<Contrato> getContratos() {
        return contratos;
    }

    public ArrayList<String> getHistorico() {
        return historico;
    }
}