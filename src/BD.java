import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class BD {

    private static void abrirArquivo(String arquivo){
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
}
