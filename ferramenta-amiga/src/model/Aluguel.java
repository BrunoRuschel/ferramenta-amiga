package model;

public class Aluguel {

    private int coda;
    private String data;
    private int codf;
    private String cpf_locat;

    public Aluguel(int coda, String data, int codf, String cpf_locat) {
        this.coda = coda;
        this.data = data;
        this.codf = codf;
        this.cpf_locat = cpf_locat;
    }

    public Aluguel() {}

    public int getCod() {
        return coda;
    }

    public void setCod(int coda) {
        this.coda = coda;
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
        return "Aluguel{" +
                "coda=" + coda +
                ", data='" + data + '\'' +
                ", codf=" + codf +
                ", cpf_locat='" + cpf_locat + '\'' +
                '}';
    }

    public boolean isCpfValido() {
        return cpf_locat != null && cpf_locat.matches("\\d{11}");
    }
}
