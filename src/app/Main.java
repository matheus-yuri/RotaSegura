package app;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import modelo.Cliente;
import modelo.Contrato;
import modelo.Veiculo;
import modelo.Popular;
import modelo.Sedan;
import modelo.SUV;
import servico.Locadora;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // A referência é Veiculo, mas cada objeto possui seu próprio comportamento.
        Veiculo popular = new Popular();
        Veiculo sedan = new Sedan();
        Veiculo suv = new SUV();

        popular.setValorDiaria(100);
        sedan.setValorDiaria(100);
        suv.setValorDiaria(100);

        System.out.printf("Popular: R$ %.2f%n", popular.calcularDiaria());
        System.out.printf("Sedan: R$ %.2f%n", sedan.calcularDiaria());
        System.out.printf("SUV: R$ %.2f%n", suv.calcularDiaria());

        Cliente cliente = new Cliente();

        try {

            System.out.print("Digite o nome do cliente: ");
            cliente.setNome(scanner.nextLine());

            System.out.print("Digite o CPF do cliente: ");
            cliente.setCpf(scanner.nextLine());

            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());

            System.out.println("\nEscolha o tipo de veiculo:");
            System.out.println("1 - Popular");
            System.out.println("2 - Sedan");
            System.out.println("3 - SUV");
            System.out.print("Opcao: ");

            int opcao;

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Erro: a opcao deve ser um numero.");
                scanner.close();
                return;
            }

            Veiculo veiculoEscolhido;

            if (opcao == 1) {
                veiculoEscolhido = popular;
            } else if (opcao == 2) {
                veiculoEscolhido = sedan;
            } else if (opcao == 3) {
                veiculoEscolhido = suv;
            } else {
                System.out.println("Tipo de veiculo invalido.");
                scanner.close();
                return;
            }

            scanner.nextLine();

            Contrato contrato = new Contrato();

            contrato.setCliente(cliente);
            contrato.setVeiculo(veiculoEscolhido);

            System.out.print("Digite a data de inicio (AAAA-MM-DD): ");
            LocalDate dataInicio =
                    LocalDate.parse(scanner.nextLine());

            System.out.print("Digite a data de fim (AAAA-MM-DD): ");
            LocalDate dataFim =
                    LocalDate.parse(scanner.nextLine());

            contrato.setDataInicio(dataInicio);
            contrato.setDataFim(dataFim);

            Locadora locadora = new Locadora();

            locadora.adicionarContrato(contrato);

            System.out.printf(
                "Valor do contrato: R$ %.2f%n",
                contrato.calcularValor()
            );

            System.out.println(
                "Contratos cadastrados: "
                + locadora.getContratos().size()
            );

            System.out.println();
            System.out.println(contrato.gerarRelatorio());

            System.out.println();
            locadora.exibirHistorico();

        } catch (DateTimeParseException e) {

            System.out.println(
                "Erro: data invalida. Use o formato AAAA-MM-DD."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Erro: " + e.getMessage()
            );
        }

        scanner.close();
    }
}