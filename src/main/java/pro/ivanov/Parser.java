package pro.ivanov;

import lombok.SneakyThrows;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;
import java.nio.file.Path;

public class Parser {

    @SneakyThrows
    public static String toText(Path path) {
        final BodyContentHandler handler = new BodyContentHandler(-1);
        final AutoDetectParser parser = new AutoDetectParser();
        final Metadata metadata = new Metadata();

        try (InputStream stream = new FileInputStream(path.toFile())) {
            parser.parse(stream, handler, metadata);

            return handler.toString();
        }
    }
}