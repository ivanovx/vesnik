package pro.ivanov;

import lombok.SneakyThrows;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;

import java.io.*;

public class Parser {
    private final String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    @SneakyThrows
    public String toText() {
        PDFParser pdfParser = new PDFParser();
        PDFParserConfig config = new PDFParserConfig();

        config.setExtractActions(true);
        config.setExtractFontNames(false);
        config.setExtractBookmarksText(true);
        config.setExtractInlineImages(false);

        pdfParser.setPDFParserConfig(config);

        ContentHandler handler = new ToXMLContentHandler();
        //BodyContentHandler handler = new BodyContentHandler(-1);
        //ContentHandler handler = new BodyContentHandler(
         //       new ToXMLContentHandler());
        //AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();

        try (InputStream stream = new FileInputStream(this.fileName)) {
            pdfParser.parse(stream, handler, metadata);

            return handler.toString();
        }
    }
}