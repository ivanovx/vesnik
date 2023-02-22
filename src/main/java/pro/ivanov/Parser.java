package pro.ivanov;

import lombok.SneakyThrows;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;

public class Parser {
    private final String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    @SneakyThrows
    public String toText(){
        BodyContentHandler handler = new BodyContentHandler(-1);
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();

        try (InputStream stream = new FileInputStream(this.fileName)) {
            parser.parse(stream, handler, metadata);

            return handler.toString();
        }
    }
}
