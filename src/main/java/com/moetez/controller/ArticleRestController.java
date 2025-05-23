package com.moetez.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moetez.entities.Article;
import com.moetez.entities.User;
import com.moetez.service.ArticleService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
	@Autowired 
	ArticleService articleservice;
	
	
	
	@GetMapping
	public List<Article> getAllArticles()
	{
		return articleservice.getAllArticles();
	}
	
	@DeleteMapping("/{id}")
	public void deleteArticle(@PathVariable Long id)
	{
		articleservice.deleteArticle(id);
	}
	@PutMapping("/{id}")
	public Article updateArticle(@PathVariable Long id, @RequestBody Article a)
	{
		return articleservice.updateArticle(id, a);
	}
	@PostMapping
	public Article createArticle(@RequestBody Article article) {
	    return articleservice.saveArticle(article);
	}
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleservice.getArticleById(id);
        return article.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public List<Article> searchArticles(
        @RequestParam(required = false) String nom,
        @RequestParam(required = false) Long prixMin,
        @RequestParam(required = false) Long prixMax) {
        
        if (nom != null && prixMin != null && prixMax != null) {
            return articleservice.findByNomAndPrixBetween(nom, prixMin, prixMax);
        } else if (nom != null) {
            return articleservice.findByNom(nom);
        } else if (prixMin != null && prixMax != null) {
            return articleservice.findByPrixBetween(prixMin, prixMax);
        } else {
            return articleservice.getAllArticles();
        }
    }

	

}
