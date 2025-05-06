package com.moetez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moetez.entities.Article;
import com.moetez.repos.ArticleRepository;
@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articlerepos;
	public List<Article> getAllArticles()
	{
		return articlerepos.findAll();
	}
	public void deleteArticle(Long id)
	{
		articlerepos.deleteById(id);
	}
	public Article createArticle(Article a)
	{
		return articlerepos.save(a);
	}
	public Article updateArticle(Long id, Article a) {
	    if (articlerepos.existsById(id)) {
	        Article existingArticle = articlerepos.findById(id).orElse(null);
	        
	        if (existingArticle != null) {
	            existingArticle.setNom(a.getNom());
	            existingArticle.setPrix(a.getPrix());

	            return articlerepos.save(existingArticle);
	        }
	    }
	    return null;
	}
	public Article saveArticle(Article article) {
	    return articlerepos.save(article);
	}
	   public Optional<Article> getArticleById(Long id) { 
	        return articlerepos.findById(id);
	    }

	   public List<Article> findByNom(String nom) {
		    return articlerepos.findByNomContaining(nom);
		}

		public List<Article> findByPrixBetween(Long prixMin, Long prixMax) {
		    return articlerepos.findByPrixBetween(prixMin, prixMax);
		}

		public List<Article> findByNomAndPrixBetween(String nom, Long prixMin, Long prixMax) {
		    return articlerepos.findByNomAndPrixBetween(nom, prixMin, prixMax);
		}


}
