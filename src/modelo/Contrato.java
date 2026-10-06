package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato implements Relatorio {

    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {

        if (dataInicio != null && !dataFim.isAfter(dataInicio)) {
            throw new IllegalArgumentException(
                "A data de fim deve ser posterior à data de início."
            );
        }

        this.dataFim = dataFim;
    }

    // O valor final considera a diária, o seguro e a manutenção do veículo.
    public double calcularValor() {

        long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);

        double diaria = veiculo.calcularDiaria();
        double seguro = veiculo.calcularSeguro();
        double manutencao = veiculo.calcularManutencao();

        double valorPorDia = diaria + seguro + manutencao;

        return dias * valorPorDia;
    }

    @Override
    public String gerarRelatorio() {

        return "===== CONTRATO DE LOCACAO =====\n"
                + "Cliente: " + cliente.getNome() + "\n"
                + "CPF: " + cliente.getCpf() + "\n"
                + "Veiculo: " + veiculo.getClass().getSimpleName() + "\n"
                + "Data de inicio: " + dataInicio + "\n"
                + "Data de fim: " + dataFim + "\n"
                + String.format("Valor total: R$ %.2f", calcularValor());
    }
}