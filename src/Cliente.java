import java.util.List;
import java.util.Scanner;

public class Cliente {
    private String cpf;
    private String nome;
    private boolean carteiraDeMotorista;
    private String NIV = null; // Numero de identificacao do veiculo
    private double saldo;

    public void verOpcoes(Concessionaria concessionaria, Scanner scanner){
        System.out.println("Os veículos disponiveis são: ");
        for(int i = 0; i < concessionaria.getVeiculosDisponiveis().size(); i++){
            System.out.println(i + ": " + concessionaria.getVeiculosDisponiveis().get(i).toString());
        }
        System.out.println("Qual veiculo deseja comprar?");
        int indiceVeiculo = scanner.nextInt();

        try {
            comprarVeiculo(indiceVeiculo, concessionaria);
        }catch (VeiculoIndisponivelException | SaldoInsuficienteException | NaoTemCNHException e){
            System.out.println(e.getMessage());
        }
    }


    public void comprarVeiculo(int indiceDoVeiculo, Concessionaria concessionaria) throws NaoTemCNHException {
        List<Veiculo> lista = concessionaria.getVeiculosDisponiveis();


        if(lista.size() < indiceDoVeiculo){
            throw  new VeiculoIndisponivelException("Não há veículos com esse índice");
        }
        if(this.getSaldo() < lista.get(indiceDoVeiculo).getPreco()){
            throw new SaldoInsuficienteException("Saldo insuficiente pra operação");
        }

        if(this.temCarteira() == false){
            throw new NaoTemCNHException("Não tem carteira de habilitação");
        }

        lista.get(indiceDoVeiculo).setCpfDoDono(this.getCpf());
        this.setSaldo(this.getSaldo() - lista.get(indiceDoVeiculo).getPreco());
        this.setNIV(lista.get(indiceDoVeiculo).getNIV());

        String dbDeClientes = "src/dbClientes.txt";
        String dbDeCarros = "src/dbCarros.txt";


        BD.salvarNoBD(dbDeClientes,BD.formatarPraSalvarClienteNoBD(this, lista.get(indiceDoVeiculo).getNIV()));
        System.out.println("Carro comprado com sucesso! ");
    }


    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
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

    public boolean temCarteira() {
        return carteiraDeMotorista;
    }

    public void setCarteiraDeMotorista(boolean carteiraDeMotorista) {
        this.carteiraDeMotorista = carteiraDeMotorista;
    }

    public String getNIV() {
        return NIV;
    }

    public void setNIV(String NIV) {
        this.NIV = NIV;
    }

    public Cliente(String cpf, String nome, boolean carteiraDeMotorista, double saldo) {
        this.cpf = cpf;
        this.nome = nome;
        this.carteiraDeMotorista = carteiraDeMotorista;
        this.saldo = saldo;
    }

    public Cliente(String cpf, String nome, boolean carteiraDeMotorista, double saldo, String NIV) {
        this.cpf = cpf;
        this.nome = nome;
        this.carteiraDeMotorista = carteiraDeMotorista;
        this.saldo = saldo;
        this.NIV = NIV;
    }

    public Cliente(){

    }
}
