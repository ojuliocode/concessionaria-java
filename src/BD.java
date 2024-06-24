import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BD {
    public static void abrirArquivo(String arquivo){
        File file = new File(arquivo);
        if(!file.exists()){
            try {
                file.createNewFile(); // Cria o arquivo se ele não existir
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            FileReader fr = new FileReader(arquivo);
            fr.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Criando arquivo do BD (não existe)");
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

    public static void modificarNoDB(String caminhoArquivo, String cpfProcurado, String novoNIV, double novoSaldo) throws IOException {
        List<String> linhas = lerLinhasDoArquivo(caminhoArquivo);
        StringBuilder novoConteudo = new StringBuilder();

        for (String linha : linhas) {
            if (linha.contains("cpf:") && linha.substring(linha.indexOf("cpf:") + 4, linha.length()).startsWith(cpfProcurado)) {
                // Encontrou o CPF, modifica apenas o NIV
                 String nivAntigo = linha.substring(linha.indexOf("NIV:") + 4, linha.indexOf(",", linha.indexOf("NIV:")));
                String saldoAntigo = linha.substring(linha.indexOf("saldo:") + 6, linha.indexOf("}", linha.indexOf("saldo:")));

                // Constrói a nova linha com os valores atualizados
                String novaLinha = linha.replaceFirst(nivAntigo, novoNIV)
                        .replaceFirst(saldoAntigo, String.valueOf(novoSaldo));

                novoConteudo.append(novaLinha).append("\n");

            } else {
                novoConteudo.append(linha).append("\n");
            }
        }

        // Salva o novo conteúdo no arquivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            writer.print(novoConteudo.toString());
        }
    }

    private static List<String> lerLinhasDoArquivo(String caminhoArquivo) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine())!= null) {
                linhas.add(linha);
            }
        }
        return linhas;
    }


    public static void removerLinhaPorCPF(String caminhoArquivo, String cpfProcurado) throws IOException {
        List<String> linhas = lerLinhasDoArquivo(caminhoArquivo);
        List<String> novasLinhas = new ArrayList<>();

        for (String linha : linhas) {
            if (!linha.contains("cpf:") ||!linha.substring(linha.indexOf("cpf:") + 4, linha.length()).startsWith(cpfProcurado)) {
                novasLinhas.add(linha);
            }
        }

        // Escreve as novas linhas no arquivo temporário
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/temp.dbClientes.txt"))) {
            for (String novaLinha : novasLinhas) {
                writer.println(novaLinha);
            }
        }

        // Renomeia o arquivo temporário para o nome original
        File tempFile = new File("src/temp.dbClientes.txt");
        File originalFile = new File(caminhoArquivo);
        if (deleteLatest("src", "dbClientes.txt")) {
            tempFile.renameTo(originalFile);
            System.out.println("Arquivo original substituído com sucesso.");
        } else {
            System.err.println("Falha ao deletar o arquivo original.");
        }



    }

    public static boolean deleteLatest(String directoryPath, String fileName) {
        try {
            Path dir = Paths.get(directoryPath);
            Optional<File[]> optionalFiles = Optional.ofNullable(dir.toFile().listFiles((dir1, name) -> name.equals(fileName)));

            if (optionalFiles.isPresent()) {
                File[] files = optionalFiles.get();
                Arrays.sort(files, Comparator.comparingLong(File::lastModified));

                if (files.length > 0) {
                    Path latestVersion = files[files.length - 1].toPath();
                    System.out.println("Última versão encontrada: " + latestVersion);

                    // Exclui o arquivo mais recente
                    Files.deleteIfExists(latestVersion);
                    System.out.println("Última versão excluída com sucesso.");
                } else {
                    System.out.println("Arquivo não encontrado no diretório especificado.");
                }
            } else {
                System.out.println("Diretório vazio ou não encontrado.");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao processar o diretório: " + e.getMessage());
            return false;
        }
    }



    public static boolean existeNIVNoDB(String arquivo, String niv) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine())!= null) {
                if (linha.contains("NIV:" + niv)) {
                    return true; // NIV encontrado
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return false; // NIV não encontrado
    }

}
