package modelo;

public class Popular extends Veiculo {

    @Override
    public double calcularDiaria() {
        return getValorDiaria();
    }

    @Override
    public double calcularSeguro() {
        return getValorDiaria() * 0.05;
    }

    @Override
    public double calcularManutencao() {
        return getValorDiaria() * 0.03;
    }
}