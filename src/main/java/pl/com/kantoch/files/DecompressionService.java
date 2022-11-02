package pl.com.kantoch.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

public class DecompressionService {
    public void decompressGzip(Path sourcePath, Path targetPath) throws IOException {
        try (GZIPInputStream gis = new GZIPInputStream(
                new FileInputStream(sourcePath.toFile()));
             FileOutputStream fos = new FileOutputStream(targetPath.toFile())) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }
}
