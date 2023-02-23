package pro.ivanov.web;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import pro.ivanov.Indexer;
import pro.ivanov.Parser;
import pro.ivanov.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements ApplicationRunner {

    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(Application.class, args);
     //Path vesnikRoot = Paths.get("C:\\Users\\csynt\\Desktop\\vesnik");
       //List<Path> vesnikBroeve = Files.walk(vesnikRoot).filter(Files::isRegularFile).toList();

      // Indexer indexer = new Indexer();

       // String vesnikContent = new Parser(vesnikBroeve.get(0).toString()).toText();

      //  System.out.println(vesnikContent);

       /* for (Path vesnikBroi : vesnikBroeve) {
            String vesnikId = vesnikBroi.getFileName().toString();
            String vesnikContent = new Parser(vesnikBroi.toString()).toText();

            System.out.println(vesnikContent);

            //String content = new Tokenizer(vesnikContent).getContent();

            //Document document = new Document();
            //document.add(new Field("id", vesnikId, TextField.TYPE_STORED));
            //document.add(new Field("content", content, TextField.TYPE_STORED));

            //indexer.indexDoc(document);
        }*/

        //List<Document> documents = indexer.searchDoc("content", "Вежди");

        //documents.forEach(document -> System.out.println(document.get("id")));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}