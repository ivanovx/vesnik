package pro.ivanov.web;

import lombok.SneakyThrows;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import pro.ivanov.Parser;
import pro.ivanov.Tokenizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements ApplicationRunner {

    @SneakyThrows
    private static Stream<String> indexPdfContents(String path) {
        Path rootPath = Paths.get(path);

        Stream<String> contents = Files
            .walk(rootPath)
            .filter(Files::isRegularFile)
            .parallel()
            .map(file -> {
                String text = Parser.toText(file);
                String content = new Tokenizer(text).getContent();

                return content;
            });

        return contents;
    }

    public static void main(String[] args) throws IOException, ParseException {
        String path = "C:\\Users\\csynt\\Desktop\\vesnik";

         Stream<String> pdfContents = indexPdfContents(path);

         pdfContents.forEach(content -> {
             try {
                 InputStream modelIn = new FileInputStream("C:\\Users\\csynt\\Desktop\\lucene-vesnik\\src\\main\\resources\\langdetect-183.bin");

                 TokenizerModel model = new TokenizerModel(modelIn);
                 TokenizerME tokenizer = new TokenizerME(model);

                 System.out.println(Arrays.toString(tokenizer.tokenize(content)));
             } catch (FileNotFoundException e) {
                 throw new RuntimeException(e);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }


         });

        //SpringApplication.run(Application.class, args);



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