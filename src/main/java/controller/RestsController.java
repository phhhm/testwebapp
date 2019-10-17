package controller;

import model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.NewsNotFoundException;
import service.NewsService;

import java.io.IOException;
import java.util.List;

@RestController
public class RestsController {

    NewsService newsService;

    @Autowired
    public RestsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/api/news")
    public List<News> getAll(){
        return newsService.getAllNews();
    }

    @GetMapping("/api/news/{id}")
    public News getById(@PathVariable int id) throws NewsNotFoundException {
        return newsService.find(id);
    }

    @PostMapping("/api/news")
    public void save(@RequestBody News news){
        try {
            newsService.save(news);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/api/news/{id}")
    public void delete(@PathVariable int id){
        newsService.delete(id);
    }

    @PutMapping("/api/news")
    public void update(@RequestBody News news){
        try {
            newsService.save(news);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
