package modelo;

public class Veiculo {

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

    public double calcularDiaria() {
        return valorDiaria;
    }

    public double calcularSeguro() {
        return valorDiaria * 0.05;
    }

    public double calcularManutencao() {
        return valorDiaria * 0.03;
    }
}