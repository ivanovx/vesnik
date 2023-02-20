package pro.ivanov;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenizer {
    private final String text;

    public Tokenizer(String text) {
        this.text = text;
    }

    public String getContent() throws URISyntaxException, IOException {
        final StringTokenizer tokens = new StringTokenizer(this.text.toLowerCase());
        final String content = this.removeStopWords(tokens);

        return content;
    }

    private String removeStopWords(StringTokenizer tokens) throws URISyntaxException, IOException {
        StringBuffer stringBuffer = new StringBuffer();

        URI path = this.getClass().getClassLoader().getResource("stop_words_bulgarian.txt").toURI();

        List<String> stopWords = Files.readAllLines(Paths.get(path));

        while (tokens.hasMoreTokens()) {
            String word = tokens.nextToken();

            if (!stopWords.contains(word)) {
                stringBuffer.append(word);
                stringBuffer.append(" ");
            }
        }

        return stringBuffer.toString();
    }
}
