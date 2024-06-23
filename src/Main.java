
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

        while(opcao != 4){

            System.out.println("Bem vindo à concessionaria " + concessionariaDoBraian.getNome() + " !!!");
            System.out.println("O que deseja hoje?");
            System.out.println("1: Comprar um veiculo");
            System.out.println("2: Alugar um veiculo");
            System.out.println("3: So dar uma olhadinha");
            System.out.println("4: Sair");

            opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    comprarVeiculo(scanner, cliente, concessionariaDoBraian);
                    break;

            }
        }
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
                cliente.comprarVeiculo(indiceDoVeiculo, concessionaria);
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