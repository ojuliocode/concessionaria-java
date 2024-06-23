import java.util.List;

public class Concessionaria {
    private List<Veiculo> veiculosDisponiveis;
    private int compensacaoPrecoVenda; // Essa é a margem de lucro de cada concessionaria

    public int getCompensacaoPrecoVenda() {
        return compensacaoPrecoVenda;
    }

    public Concessionaria(List<Veiculo> veiculosDisponiveis, int compensacaoPrecoVenda) {
        this.veiculosDisponiveis = veiculosDisponiveis;
        this.compensacaoPrecoVenda = compensacaoPrecoVenda;
    }

    public List<Veiculo> getVeiculosDisponiveis() {
        return veiculosDisponiveis;
    }

    public void setVeiculosDisponiveis(List<Veiculo> veiculosDisponiveis) {
        this.veiculosDisponiveis = veiculosDisponiveis;
    }
}
