package pl.com.kantoch.mavn_tools;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VersionReceiverService {

    private Model model;
    private String mavenFilePath;
    public VersionReceiverService(String mavenFilePath) throws XmlPullParserException, IOException {
        this.mavenFilePath = mavenFilePath;
        this.model = initializeModel();
    }

    private Model initializeModel() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        if ((new File("pom.xml")).exists())
            model = reader.read(new FileReader("pom.xml"));
        else
            model = reader.read(new InputStreamReader(VersionReceiverService.class.getResourceAsStream(mavenFilePath)));
        return model;
    }

    public String getVersion() {
        if(model!=null) return model.getVersion();
        else return null;
    }

}
