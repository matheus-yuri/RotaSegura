package modelo;

public class Cliente {

    private String nome;
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome do cliente nao pode ficar vazio."
            );
        }

        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O CPF do cliente nao pode ficar vazio."
            );
        }

        this.cpf = cpf;
    }
}