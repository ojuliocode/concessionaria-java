public class Cliente {
    private String cpf;
    private String nome;
    private boolean carteiraDeMotorista;
    private String NIV = null; // Numero de identificacao do veiculo

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

    public boolean isCarteiraDeMotorista() {
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

    public Cliente(String cpf, String nome, boolean carteiraDeMotorista) {
        this.cpf = cpf;
        this.nome = nome;
        this.carteiraDeMotorista = carteiraDeMotorista;
    }
}
