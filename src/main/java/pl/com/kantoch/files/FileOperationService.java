package pl.com.kantoch.files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public interface FileOperationService {
    Collection<Path> loadLogFiles() throws IOException;
    String readFile(String fileName) throws IOException;
    String readArchivedFile(String fileName) throws IOException;
}
