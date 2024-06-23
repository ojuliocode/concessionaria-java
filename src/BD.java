import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BD {

    public static void abrirArquivo(String arquivo){
        FileReader fr;
        try{
            fr = new FileReader(arquivo);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println("Criando arquivo do BD (não existe)");
            File file = new File(arquivo);

        }

    }

    public static void salvarNoBD(String arquivo, String info){
        abrirArquivo(arquivo);
        try {
            Path path = Path.of(arquivo);
            Files.write(path, info.getBytes(), StandardOpenOption.APPEND);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void alterarNoBD(){

    }

    public void deletarNoBD(){

    }

    public void pegarNoBD(){

    }
    public static Cliente buscarClientePorCPF(String cpf) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/dbClientes.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                // Utilizando expressão regular para extrair o CPF
                Pattern pattern = Pattern.compile("cpf:(.*?),");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String cpfEncontrado = matcher.group(1).trim();
                    if (cpfEncontrado.equals(cpf)) {
                        System.out.println(cpfEncontrado);
                        System.out.println(cpf);
                        // Encontrou o cliente com o CPF especificado
                        // Extrair os dados do cliente da linha encontrada
                        String[] campos = line.split("[{},]");
                        String nome = null;
                        boolean carteiraDeMotorista = false;
                        String NIV = null;
                        double saldo = 0.0;
                        for (String campo : campos) {
                            System.out.println(">>>");
                            System.out.println(campo);
                            if (campo.startsWith("nome:")) {
                                nome = campo.substring(5);
                            } else if (campo.startsWith("carteiraDeMotorista:")) {
                                carteiraDeMotorista = Boolean.parseBoolean(campo.substring(20));
                            } else if (campo.startsWith("NIV:")) {
                                NIV = campo.substring(4);
                            } else if (campo.startsWith("saldo:")) {
                                saldo = Double.parseDouble(campo.substring(6));
                            }
                        }
                        // Retornar uma nova instância de Cliente
                        return new Cliente(cpf, nome, carteiraDeMotorista, saldo, NIV);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Se não encontrou o cliente, retorna null
        return null;
    }


    public static String formatarPraSalvarClienteNoBD(Cliente cliente) {
        String cpfFormatado = "{cpf:" + cliente.getCpf() + ",";
        String niv = "NIV:" + cliente.getNIV() + ",";
        String nome = "nome:" + cliente.getNome() + ",";
        String carteiraDeMotorista = "carteiraDeMotorista:" + cliente.temCarteira() + ",";
        String saldo = "saldo:" + cliente.getSaldo() + "}\n";

        return cpfFormatado + niv + nome + carteiraDeMotorista + saldo;
    }

    public static String formatarPraSalvarClienteNoBD(Cliente cliente, String NIV) {
        String cpfFormatado = "{cpf:" + cliente.getCpf() + ",";
        String niv = "NIV:" + NIV + ",";
        String nome = "nome:" + cliente.getNome() + ",";
        String carteiraDeMotorista = "carteiraDeMotorista:" + cliente.temCarteira() + ",";
        String saldo = "saldo:" + cliente.getSaldo() + "}\n";

        return cpfFormatado + niv + nome + carteiraDeMotorista + saldo;
    }


    public static void lerEAtualizarCliente(String cpfSearch, String newNIV) {
        // File path
        String filePath = "src/dbClientes.txt";
        // List to hold Cliente objects
        List<Cliente> clientes = new ArrayList<>();

        // Read the file and populate the list
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line to extract Cliente information
                String cpf = extrairValor(line, "cpf:");
                String nome = extrairValor(line, "nome:");
                boolean carteiraDeMotorista = Boolean.parseBoolean(extrairValor(line, "carteiraDeMotorista:"));
                String NIV = extrairValor(line, "NIV:");
                double saldo = Double.parseDouble(extrairValor(line, "saldo:"));

                // Create Cliente object and add to list
                Cliente cliente = new Cliente(cpf, nome, carteiraDeMotorista, saldo, NIV);
                clientes.add(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Search for the CPF in the list
        boolean found = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpfSearch)) {
                // Update the NIV value
                cliente.setNIV(newNIV);
                found = true;
                break;
            }
        }

        // If CPF not found, add a new entry
        if (!found) {
            // Assuming other fields are null or default values
            Cliente newCliente = new Cliente(cpfSearch, null, false, 0.0, newNIV);
            clientes.add(newCliente);
        }

        // Write the updated list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to extract value between key and next comma
    private static String extrairValor(String line, String key) {
        int startIndex = line.indexOf(key) + key.length();
        int endIndex = line.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = line.length() - 1;
        }
        System.out.println("-----");
        System.out.println(line);
        System.out.println(key);
        System.out.println(startIndex);
        System.out.println(endIndex);
        System.out.println("-----");
        return line.substring(startIndex, endIndex).trim();
    }

}
