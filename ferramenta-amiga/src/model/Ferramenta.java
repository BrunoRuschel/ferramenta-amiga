package model;

public class Ferramenta {

    private int codf;
    private String tipo;
    private String marca;
    private float preco;
    private String estado;
    private String status;
    private String cpf_locad;

    public Ferramenta(int codf, String tipo, String marca, float preco, String estado, String status, String cpf_locad) {
        this.codf = codf;
        this.tipo = tipo;
        this.marca = marca;
        this.preco = preco;
        this.estado = estado;
        this.status = status;
        this.cpf_locad = cpf_locad;
    }

    public int getCodf() {
        return codf;
    }

    public void setCodf(int codf) {
        this.codf = codf;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpf_locad() {
        return cpf_locad;
    }

    public void setCpf_locad(String cpf_locad) {
        this.cpf_locad = cpf_locad;
    }

    @Override
    public String toString() {
        return "Ferramenta{" +
                "codf=" + codf +
                ", tipo='" + tipo + '\'' +
                ", marca='" + marca + '\'' +
                ", preco=" + preco +
                ", estado='" + estado + '\'' +
                ", status='" + status + '\'' +
                ", cpf_locad='" + cpf_locad + '\'' +
                '}';
    }
}
