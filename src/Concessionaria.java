import java.util.List;

public class Concessionaria {
    private List<Veiculo> veiculosDisponiveis;
    private int compensacaoPrecoVenda; // Essa é a margem de lucro de cada concessionaria
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCompensacaoPrecoVenda() {
        return compensacaoPrecoVenda;
    }

    public Concessionaria(List<Veiculo> veiculosDisponiveis, int compensacaoPrecoVenda, String nome) {
        this.veiculosDisponiveis = veiculosDisponiveis;
        this.compensacaoPrecoVenda = compensacaoPrecoVenda;
        this.nome = nome;
    }

    public List<Veiculo> getVeiculosDisponiveis() {
        return veiculosDisponiveis;
    }

    public void setVeiculosDisponiveis(List<Veiculo> veiculosDisponiveis) {
        this.veiculosDisponiveis = veiculosDisponiveis;
    }
}
