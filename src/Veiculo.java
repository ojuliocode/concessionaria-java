public abstract class Veiculo {
    private String marca;
    private String modelo;
    private int ano;
    private int precoTabelaFipe; // Preco na tabela Fipe não tem centavos
    private boolean ipvaPago;

    public Veiculo(String marca, String modelo, int ano, int precoTabelaFipe, boolean ipvaPago) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.precoTabelaFipe = precoTabelaFipe;
        this.ipvaPago = ipvaPago;
    }
}
