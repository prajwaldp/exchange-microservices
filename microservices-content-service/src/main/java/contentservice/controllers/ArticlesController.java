package contentservice.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import contentservice.models.Article;
import contentservice.repositories.ArticleRepository;

@RestController
public class ArticlesController {

  @Autowired
  private ArticleRepository repository;

  @GetMapping("/articles")
  public List<Article> index() {
    List<Article> articles = new ArrayList<>();
    repository.findAll().forEach(articles::add);
    return articles;
  }

  @GetMapping("/articles/{id}")
  public Optional<Article> get(@PathVariable String id) {
    Optional<Article> article = repository.findById(id);
    return article;
  }

  @PostMapping("/articles")
  public Article create(@RequestBody Article newArticle) {
    newArticle.setId(String.valueOf(new Random().nextInt()));
    return repository.save(newArticle);
  }

  @PutMapping("/articles/{id}")
  public Optional<Article> update(@RequestParam String title, @RequestParam String content, @PathVariable String id) {
    Optional<Article> article = repository.findById(id);
    if (article.isPresent()) {
      article.get().setTitle(title);
      article.get().setContent(content);
      repository.save(article.get());
    }
    return article;
  }

  @DeleteMapping("/articles/{id}")
  public void delete(@PathVariable String id) {
    Optional<Article> article = repository.findById(id);
    if (article.isPresent()) {
      repository.deleteById(article.get().getId());
    }
  }
}
