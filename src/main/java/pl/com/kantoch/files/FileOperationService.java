package pl.com.kantoch.files;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface FileOperationService {
    Collection<Path> loadLogFiles() throws IOException;
    String readFile(String fileName) throws IOException;
    String readArchivedFile(String fileName) throws IOException;
    CSVParser readCSVFile(File file, CSVFormat csvFormat) throws IOException;
    CSVParser readCSVFile(File file) throws IOException;
    void writeCSVFile(File file,List<List<String>> value, String... headers) throws IOException;
}
