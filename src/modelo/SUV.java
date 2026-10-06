package modelo;

public class SUV extends Veiculo {

    @Override
    public double calcularDiaria() {
        return getValorDiaria() * 1.20;
    }

    @Override
    public double calcularSeguro() {
        return getValorDiaria() * 0.12;
    }

    @Override
    public double calcularManutencao() {
        return getValorDiaria() * 0.07;
    }
}