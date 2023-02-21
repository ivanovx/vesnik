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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Indexer {
    public final static String NAME = "tempIndex";

    public final Analyzer analyzer = new StandardAnalyzer();

    private Path indexPath;

    public Indexer() throws IOException {
        String dir = String.valueOf(Paths.get(NAME));
        this.indexPath = Files.createTempDirectory(dir);
    }

    private Directory getIndexDirectory() throws IOException {
        return FSDirectory.open(indexPath);
    }

    public void indexDoc(Document document) {
        IndexWriterConfig config = new IndexWriterConfig(this.analyzer);

        try (IndexWriter iwriter = new IndexWriter(this.getIndexDirectory(), config)) {
            iwriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchDoc(String field, String value) {
        try (DirectoryReader ireader = DirectoryReader.open(this.getIndexDirectory())) {
            IndexSearcher isearcher = new IndexSearcher(ireader);

            QueryParser parser = new QueryParser(field, analyzer);
            Query query = parser.parse(value);

            ScoreDoc[] hits = isearcher.search(query, 15).scoreDocs;

            StoredFields storedFields = isearcher.storedFields();

            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = storedFields.document(hits[i].doc);
                System.out.println(hitDoc.get("id"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
