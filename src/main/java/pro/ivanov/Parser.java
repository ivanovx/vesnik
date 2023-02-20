package pro.ivanov;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

public class Parser {
    private final String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    public String toText() throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler(-1);
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();

        try (InputStream stream = new FileInputStream(this.fileName)) {
            parser.parse(stream, handler, metadata);

            return handler.toString();
        }
    }
}
