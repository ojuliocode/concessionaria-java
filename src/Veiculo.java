public abstract class Veiculo {
    private String marca;
    private String modelo;
    private int ano;
    private int precoFipe; // Preco na tabela Fipe não tem centavos
    private boolean ipvaPago;
    private String cpfDoDono = null;
    private double preco;
    private String NIV;

    public String getNIV() {
        return NIV;
    }

    public void setNIV(String NIV) {
        this.NIV = NIV;
    }

    public int getPrecoFipe() {
        return precoFipe;
    }

    public void setPrecoFipe(int precoFipe) {
        this.precoFipe = precoFipe;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isIpvaPago() {
        return ipvaPago;
    }

    public void setIpvaPago(boolean ipvaPago) {
        this.ipvaPago = ipvaPago;
    }

    public String getCpfDoDono() {
        return cpfDoDono;
    }

    public void setCpfDoDono(String cpfDoDono) {
        this.cpfDoDono = cpfDoDono;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }


}
