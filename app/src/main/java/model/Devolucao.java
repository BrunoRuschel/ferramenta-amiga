package model;

public class Devolucao {

    private int codd;
    private String data;
    private int codf;
    private String cpf_locat;

    public Devolucao(int codd, String data, int codf, String cpf_locat) {
        this.codd = codd;
        this.data = data;
        this.codf = codf;
        this.cpf_locat = cpf_locat;
    }

    public Devolucao(){};

    public int getCodd() {
        return codd;
    }

    public void setCodd(int codd) {
        this.codd = codd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCodf() {
        return codf;
    }

    public void setCodf(int codf) {
        this.codf = codf;
    }

    public String getCpf_locat() {
        return cpf_locat;
    }

    public void setCpf_locat(String cpf_locat) {
        this.cpf_locat = cpf_locat;
    }

    @Override
    public String toString() {
        return "Devolucao{" +
                "codd=" + codd +
                ", data='" + data + '\'' +
                ", codf=" + codf +
                ", cpf_locat='" + cpf_locat + '\'' +
                '}';
    }

    public boolean isCpfValido() {
        return cpf_locat != null && cpf_locat.matches("\\d{11}");
    }
}
