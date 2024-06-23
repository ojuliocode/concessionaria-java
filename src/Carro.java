public class Carro extends Veiculo {
    public Carro() {
    }

    @Override
    public String toString() {
        return this.getMarca() + this.getAno() + this.getModelo();
    }
}
