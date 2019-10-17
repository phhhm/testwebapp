package controller;


import model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.NewsNotFoundException;
import service.NewsService;

import java.util.List;

@Controller
public class HomeController {

    NewsService newsService;

    @Autowired
    public HomeController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping({"/", "/index"})
    public String home(Model model){
        List<News> news = newsService.getAllNews();
        model.addAttribute("newsList", news);
        return "news";
    }

    @GetMapping("/news")
    public String news(@RequestParam("id") int id, Model model) throws NewsNotFoundException {
        News news = newsService.find(id);
        model.addAttribute("news", news);
        return "inews";

    }
}
