package pl.com.kantoch.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOperationServiceImplementation implements FileOperationService {

    private DecompressionService decompressionService;

    @Override
    public Collection<Path> loadLogFiles() throws IOException {
        File rootDirectory = new File("./");
        return listFiles(rootDirectory.getPath())
                .stream()
                .filter(e->e.contains("log"))
                .map(Path::of).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) throw new IllegalStateException("File "+fileName+" does not exists");
        StringBuilder content = new StringBuilder();
        try (BufferedReader r  =new BufferedReader(new FileReader(file))) {
            String line;
            while((line=r.readLine())!=null)
            {
                content.append(line);
                content.append("\n");
            }
        }
        return content.toString();
    }

    @Override
    public String readArchivedFile(String fileName) throws IOException {
        //unzip file
        String tempTargetFileName = "temp_"+fileName.substring(0,fileName.lastIndexOf(".")-1);
        if(decompressionService==null) decompressionService = new DecompressionService();
        decompressionService.decompressGzip(Path.of(fileName),Path.of(tempTargetFileName));
        //pass as param to readFile method temp file name
        String content = readFile(tempTargetFileName);
        removeFile(tempTargetFileName);
        //remove temp file
        return content;
    }

    public Set<String> listFiles(String directory) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(directory))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    private void removeFile(String fileName){
        File file = new File(fileName);
        if(file.exists()) file.delete();
    }
}
