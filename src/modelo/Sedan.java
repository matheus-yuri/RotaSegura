package modelo;

public class Sedan extends Veiculo {

    @Override
    public double calcularDiaria() {
        return getValorDiaria() * 1.10;
    }

    @Override
    public double calcularSeguro() {
        return getValorDiaria() * 0.08;
    }

    @Override
    public double calcularManutencao() {
        return getValorDiaria() * 0.05;
    }
}