package pl.com.kantoch.files;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
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

    @Override
    public CSVParser readCSVFile(File file, CSVFormat csvFormat) throws IOException {
        Reader reader = Files.newBufferedReader(file.toPath());
        return new CSVParser(reader, csvFormat);
    }

    @Override
    public CSVParser readCSVFile(File file) throws IOException {
        Reader reader = Files.newBufferedReader(file.toPath());
        return new CSVParser(reader, CSVFormat.DEFAULT);
    }

    @Override
    public void writeCSVFile(File file, List<List<String>> value, String... headers) throws IOException {
        FileWriter out = new FileWriter(file);
        if(headers.length==0){
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
                value.forEach(e -> {
                    try {
                        printer.printRecord(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        } else {
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withHeader(headers))) {
                value.forEach(e -> {
                    try {
                        printer.printRecord(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }
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
