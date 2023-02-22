package pro.ivanov.web.controller;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.ivanov.Indexer;
import pro.ivanov.web.inputModel.Search;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("search", new Search());

        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Document> search(@ModelAttribute Search search, Model model) throws IOException, ParseException {
        final Indexer indexer = new Indexer();

        return indexer.searchDoc("content", search.getCriteria());
    }
}
