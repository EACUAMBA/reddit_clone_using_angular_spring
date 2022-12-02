package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.file_manager_helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class FileManagerHelper {

    public InputStream getFileAsInputStream(String prefix, String fileName) throws FileNotFoundException {
        File file = this.getFile(prefix, fileName);
        return new FileInputStream(file);
    }

    public InputStream getFileAsInputStreamFromClasspath(String fileName) throws FileNotFoundException {
        File file = this.getFileFromClasspath(fileName);
        return new FileInputStream(file);
    }

    public File getFileFromClasspath(String fileName) throws FileNotFoundException {
        return this.getFile(ResourceUtils.CLASSPATH_URL_PREFIX, fileName);
    }

    public File getFile(String prefix, String fileName) throws FileNotFoundException {
        return ResourceUtils.getFile(String.format("%s%s", prefix, fileName));
    }

    /**
     * Utilize este método para criar um ficheiro (file) no sistema de ficheiros único, caso o ficheiro que queres gravar já existe ele retorna null.
     * @param path o caminho do ficheiro que deseja criar.
     * @param newFileBytes bytes do ficheiro que deseja armazenar, são utilizados para verificar se o ficheiro existente nesse caminho é mesmo ao que desejas guardar.
     * @return path o caminho do ficheiro criado ou null se o ficheiro que queres criar é igual ao que está no sistema de ficheiros
     * @throws IOException caso aja algum problema na verificação ou criação do ficheiro.
     * @author Edilson Alexandre Cuamba
     */
    public Path createNewFile(Path path, byte[] newFileBytes) throws IOException {
        log.debug(String.format("Tentando criar o ficheiro (%s)", path.toAbsolutePath().toString()));
        String fileName = path.getFileName().toString();
        while (Files.exists(path)) {
            if(Arrays.equals(newFileBytes, Files.readAllBytes(path))){
                log.debug(String.format("O ficheiro (%s) já existe no sistema de ficheiros, não será gravado por cima nem recriado.", path.toAbsolutePath().toString()));
                return null;
            }
            path = Paths.get(path.toString().replace(fileName, generateNewFileName(fileName)));
            fileName = path.getFileName().toString();
        }
        Files.createFile(path);
        return path;
    }

    /**
     * Gera um novo nome de ficheiro caso já existe um ficheiro com o mesmo nome, adiciona aqueles parentes com números dentro (#).
     * @param fileName O nome do ficheiro.
     * @return String o novo nome de ficheiro gerado.
     * @author Edilson Alexandre Cuamba
     */
    private String generateNewFileName(String fileName) {
        long index = 1L;
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String name = fileName.replace(extension, "").trim();
        Pattern compile = Pattern.compile("[\\s]*[^\\s+$]*\\(\\d\\)$");
        Matcher matcher = compile.matcher(name);
        if (matcher.matches()) {
            String temp = Pattern.compile("\\(\\d+\\)").matcher(name).replaceAll("");
            long oldIndex = Long.parseLong(name.replace(temp, "").replace("(", "").replace(")", "").trim());
            name = temp;
            index = oldIndex + 1;
        }
        fileName = name.concat("(").concat(Objects.toString(index)).concat(")");
        return fileName.concat(extension);
    }

    /**
     * Enum com o objectivo de converter unidades de medida de dados como bit para seus multíplos como MB, KB e GB.
     * @author Edilson Alexandre Cuamba
     */
    public enum BitUnits{
        BYTE{
            public double toKILOBYTE(int size){return (double)size / 1024;}
            public double toMEGABYTE(int size){return (double)size / 1024 / 1024;}
            public double toGIGABYTE(int size){return (double)size / 1024 / 1024 / 1024;}
        };

        public double toKILOBYTE(int size){throw new AbstractMethodError();}
        public double toMEGABYTE(int size){throw new AbstractMethodError();}
        public double toGIGABYTE(int size){throw new AbstractMethodError();}
    }

}
