
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String arquivoJson = "src/tabela-fipe.json";
        List<Veiculo> veiculosTabelaFipe = lerCarrosDeJson(arquivoJson);
        Concessionaria concessionariaDoBraian = new Concessionaria(veiculosTabelaFipe, 2000);
        Cliente cliente = new Cliente("1234567", "Vandão", true);

        for (Veiculo veiculo : concessionariaDoBraian.getVeiculosDisponiveis()) {
            veiculo.setPreco(veiculo.getPrecoFipe() + concessionariaDoBraian.getCompensacaoPrecoVenda());
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