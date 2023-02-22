package pro.ivanov;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Indexer {
    private final Analyzer analyzer = new StandardAnalyzer();

    private final Path indexPath = Paths.get("C:\\Temp");


    private Directory getIndexDirectory() throws IOException {
        return FSDirectory.open(indexPath);
    }

    public void indexDoc(Document document) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(this.analyzer);

        try (IndexWriter indexWriter = new IndexWriter(this.getIndexDirectory(), config)) {
            indexWriter.addDocument(document);
        }
    }

    public List<Document> searchDoc(String field, String value) throws IOException, ParseException {
        try (DirectoryReader indexReader = DirectoryReader.open(this.getIndexDirectory())) {
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            QueryParser parser = new QueryParser(field, analyzer);
            Query query = parser.parse(value);

            ScoreDoc[] hits = indexSearcher.search(query, 10).scoreDocs;

            StoredFields storedFields = indexSearcher.storedFields();

            List<Document> hitDocs = Arrays.stream(hits).map(hit -> {
                Document hitDoc = null;

                try {
                    hitDoc = storedFields.document(hit.doc);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return hitDoc;

            }).toList();

            return hitDocs;
        }
    }
}
