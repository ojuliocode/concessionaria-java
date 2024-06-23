
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        String arquivoJson = "src/tabela-fipe.json";
        List<Veiculo> veiculosTabelaFipe = lerCarrosDeJson(arquivoJson);
        Concessionaria concessionariaDoBraian = new Concessionaria(veiculosTabelaFipe, 2000, "do Braian");
        Cliente cliente = new Cliente("1234567", "Vandão", true, 98403285);

        for (Veiculo veiculo : concessionariaDoBraian.getVeiculosDisponiveis()) {
            veiculo.setPreco(veiculo.getPrecoFipe() + concessionariaDoBraian.getCompensacaoPrecoVenda());
        }

        Scanner scanner  = new Scanner(System.in);

        int opcao = 0;


            System.out.println("Bem vindo à concessionaria " + concessionariaDoBraian.getNome() + " !!!");
            System.out.println("Qual seu cpf??");
            String cpf = scanner.nextLine();
            Cliente cliente1 = BD.buscarClientePorCPF(cpf);

            if(cliente1 == null){
                cliente1 = new Cliente();
                //criando cadastro do cliente
                System.out.println("Informe seus nome plis");
                cliente1.setNome(scanner.nextLine());
                System.out.println("Informe seus dinheiro plis");
                cliente1.setSaldo(scanner.nextDouble());
                System.out.println("Tem carteira? (true ou false)");
                cliente1.setCarteiraDeMotorista(scanner.nextBoolean());

                cliente1.setCpf(cpf);
                String formatada = BD.formatarPraSalvarClienteNoBD(cliente1);
                BD.salvarNoBD("src/dbClientes.txt", formatada);
            }
        System.out.println(cliente1.getNome());
        System.out.println(cliente1.temCarteira());

        while(opcao != 2){

            System.out.println("O que deseja hoje?");
            System.out.println("1: Comprar um veiculo");
            System.out.println("2: Apagar meu cadastro na loja");
            System.out.println("3: Sair");

            opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    comprarVeiculo(scanner, cliente1, concessionariaDoBraian);
                    break;
                case 2:
                    String caminhoArquivo = "src/dbClientes.txt";
                    String cpfProcurado = cliente1.getCpf(); // Exemplo de CPF a ser removido

                    try {
                        BD.removerLinhaPorCPF(caminhoArquivo, cpfProcurado);
                        System.out.println("Cadastro removida com sucesso.");
                    } catch (IOException e) {
                        System.err.println("Erro ao remover a linha: " + e.getMessage());
                    }
            }

        }

        scanner.close();

    }

    public static void comprarVeiculo(Scanner scanner, Cliente cliente, Concessionaria concessionaria){
        System.out.println("Você já sabe qual veículo quer comprar? ");
        System.out.println("1: Sim (qual?)");
        System.out.println("2: Não");
        System.out.println("3: Sair");
        int opcao = scanner.nextInt();

        switch (opcao){
            case 1:
                int indiceDoVeiculo = scanner.nextInt();
                try{
                    cliente.comprarVeiculo(indiceDoVeiculo, concessionaria);
                }catch (SaldoInsuficienteException | VeiculoIndisponivelException | NaoTemCNHException e ){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                cliente.verOpcoes(concessionaria, scanner);
                break;
        }

    }

    public static List<Veiculo> lerCarrosDeJson(String arquivoJson) {
        List<Veiculo> veiculos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoJson))) {
            String line;
            StringBuilder jsonStr = new StringBuilder();
            while ((line = br.readLine()) != null) {
                jsonStr.append(line.trim());
            }

            if (jsonStr.length() > 0 && jsonStr.charAt(jsonStr.length() - 1) == ',') {
                jsonStr.deleteCharAt(jsonStr.length() - 1);
            }

            if (jsonStr.length() > 0 && jsonStr.charAt(0) == '[') {
                String[] objects = jsonStr.substring(1, jsonStr.length() - 1).split("\\},\\{");
                for (String obj : objects) {
                    if (obj.charAt(0) == '{') {
                        obj = obj.substring(1);
                    }
                    if (obj.charAt(obj.length() - 1) == '}') {
                        obj = obj.substring(0, obj.length() - 1);
                    }

                    String[] fields = obj.split(",");
                    Carro veiculo = new Carro();
                    for (String field : fields) {
                        String[] keyValue = field.split(":");
                        String key = keyValue[0].replaceAll("\"", "").trim();
                        String value = keyValue[1].replaceAll("\"", "").trim();
                        switch (key) {
                            case "marca":
                                veiculo.setMarca(value);
                                break;
                            case "modelo":
                                veiculo.setModelo(value);
                                break;
                            case "ano":
                                veiculo.setAno(Integer.parseInt(value));
                                break;
                            case "precoFipe":
                                veiculo.setPrecoFipe(Integer.parseInt(value));
                                break;
                            case "NIV":
                                veiculo.setNIV(value);
                                break;
                            default:
                                break;
                        }
                    }
                    veiculos.add(veiculo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return veiculos;
    }


}