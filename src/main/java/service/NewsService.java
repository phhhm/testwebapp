package service;

import model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import repository.NewsRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NewsService {
    NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void save(News news) throws IOException {
        News news1 = setImage(news);
        newsRepository.save(news1);
    }


    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News find(int id) throws NewsNotFoundException {
        Optional<News> optional = newsRepository.findById(id);
//        return optional.orElseThrow(()-> new NewsNotFoundException());
        return optional.orElseThrow(NewsNotFoundException::new);
    }

    public void delete(int id){
        newsRepository.deleteById(id);
    }

    private News setImage(News news) throws IOException {
        MultipartFile file = news.getMultipartFile();
        String imageName = UUID.randomUUID().toString();
        String type = file.getContentType().substring(6);
        String address = "C:\\Users\\parham\\Desktop\\file-server\\"+imageName+"."+type;
        file.transferTo(new File(address));
        news.setImage(imageName+"."+type);
        return news;
    }
}
