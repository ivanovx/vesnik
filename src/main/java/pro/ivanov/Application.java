package pro.ivanov;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        Path vesnikRoot = Paths.get("C:\\Users\\csynt\\Desktop\\vesnik");
        List<Path> vesnikBroeve = Files.walk(vesnikRoot).filter(Files::isRegularFile).toList();

        Indexer indexer = new Indexer();

        for (Path vesnikBroi : vesnikBroeve) {
            String vesnikId = vesnikBroi.getFileName().toString();
            String vesnikContent = new Parser(vesnikBroi.toString()).toText();

            String content = new Tokenizer(vesnikContent).getContent();

            Document document = new Document();
            document.add(new Field("id", vesnikId, TextField.TYPE_STORED));
            document.add(new Field("content", content, TextField.TYPE_STORED));

            indexer.indexDoc(document);
        }

        List<Document> documents = indexer.searchDoc("content", "Вежди");

        documents.forEach(document -> System.out.println(document.get("id")));
    }
}