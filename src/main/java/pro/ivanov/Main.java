package pro.ivanov;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.StoredFields;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, TikaException, SAXException, URISyntaxException {
        Path vesnikRoot = Paths.get("C:\\Users\\csynt\\Desktop\\vesnik");
        List<Path> vesnikBroeve = Files.walk(vesnikRoot).filter(Files::isRegularFile).toList();

        Analyzer analyzer = new StandardAnalyzer();

        Path indexPath = Files.createTempDirectory("tempIndex");
        Directory directory = FSDirectory.open(indexPath);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);

        for (Path vesnikBroi : vesnikBroeve) {
            String vesnikId = vesnikBroi.getFileName().toString();
            String vesnikContent = new Parser(vesnikBroi.toString()).toText();

            String content = new Tokenizer(vesnikContent).getContent();

            Document document = new Document();
            document.add(new Field("id", vesnikId, TextField.TYPE_STORED));
            document.add(new Field("content", content, TextField.TYPE_STORED));

            iwriter.addDocument(document);
        }

        iwriter.close();

        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);

        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse("нато");
        ScoreDoc[] hits = isearcher.search(query, 15).scoreDocs;

        StoredFields storedFields = isearcher.storedFields();

        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = storedFields.document(hits[i].doc);
            System.out.println(hitDoc.get("id"));
        }

        ireader.close();
        directory.close();
        //IOUtils.rm(indexPath);
    }
}