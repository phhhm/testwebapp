package controller;

import model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.NewsNotFoundException;
import service.NewsService;

import java.io.IOException;
import java.util.List;

@Controller("/admin")
@RequestMapping("/admin")
public class AdminController {

    NewsService newsService;

    @Autowired
    public AdminController(NewsService newsService){
        this.newsService = newsService;
    }


    @GetMapping("/add-news")
    public String publishNews(){
        return "add-news";
    }

    @GetMapping("/edit-delete-news")
    public String editDeleteNews(Model model){
        List<News> news = newsService.getAllNews();
        model.addAttribute("newsList", news);
        return "edit-delete-news";
    }

    //TODO get News object
    @PostMapping("/publish-news")
    public String saveNews(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestPart("image") MultipartFile image) throws IOException {
        News news = new News(title,content,image);
        newsService.save(news);
        return "redirect:/admin/add-news";
    }

    @GetMapping("/delete-news")
    public String deleteNews(@RequestParam("id") int id){
        newsService.delete(id);
        return "redirect:/admin/edit-delete-news";
    }

    @PostMapping("/edit-publish")
    public String editNews(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestPart("image") MultipartFile image,
                            @RequestParam("id") int id) throws IOException, NewsNotFoundException {
        News news = newsService.find(id);

        news.setTitle(title);
        news.setContent(content);
        news.setMultipartFile(image);

        newsService.save(news);

        return "redirect:/admin/edit-delete-news";
    }

    @GetMapping("edit-news")
    public String  editForm(@RequestParam("id") int id, Model model) throws NewsNotFoundException {
        News news = newsService.find(id);
        model.addAttribute("news", news);
        return "edit-news";
    }
}
