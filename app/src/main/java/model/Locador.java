package model;

public class Locador {

    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private String endereco;
    private float saldo;

    public Locador(String cpf, String nome, String email, String senha, String endereco, float saldo) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.saldo = saldo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Locador{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }

    public boolean isCpfValido() {
        return cpf != null && cpf.matches("\\d{11}");
    }

    public boolean isEmailValido() {
        return email != null && email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");
    }
}
