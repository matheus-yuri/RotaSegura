package modelo;

public abstract class Veiculo {

    private double valorDiaria;

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        if (valorDiaria <= 0) {
            throw new IllegalArgumentException(
                "O valor da diaria deve ser maior que zero."
            );
        }

        this.valorDiaria = valorDiaria;
    }

    public abstract double calcularDiaria();

    public abstract double calcularSeguro();

    public abstract double calcularManutencao();
}