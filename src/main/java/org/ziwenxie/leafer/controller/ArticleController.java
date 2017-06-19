package org.ziwenxie.leafer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.ziwenxie.leafer.model.Article;
import org.ziwenxie.leafer.model.Tag;
import org.ziwenxie.leafer.service.IArticleService;

import java.security.Principal;
import java.util.List;


@Controller
public class ArticleController {

    private IArticleService articleService;

    @Autowired
    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap, Principal principal) {
        long articlesCount = articleService.getArticlesCount(principal.getName());

        List<Article> articles = articleService.getArticlesOfOnePage(principal.getName(), 1);

        modelMap.addAttribute("articles", articles);
        modelMap.addAttribute("articlesCount", articlesCount);
        modelMap.addAttribute("isIndex", true);

        return "article/index";
    }

    @GetMapping("/article/new")
    public Article newArticle(ModelMap modelMap) {
        Article article = new Article();

        modelMap.addAttribute("article", article);

        return article;
    }

    @GetMapping(value = "/article/{articleId}")
    public String getArticle(@PathVariable long articleId, ModelMap modelMap, Principal principal) {
        Article article = articleService.getOneArticleById(articleId, principal.getName());

        modelMap.addAttribute("article", article);

        return "article/display";
    }

    @GetMapping(value = "/article/modify/{articleId}")
    public String updateArticle(@PathVariable long articleId, ModelMap modelMap, Principal principal) {
        Article article = articleService.getOneArticleById(articleId, principal.getName());
        List<Tag> tags = articleService.getAllTagsOfOneArticle(articleId);

        modelMap.addAttribute("article", article);
        modelMap.addAttribute("tags", tags);

        return "article/modify";
    }

    @PostMapping(value = "/page/{page}")
    @ResponseBody
    public List<Article> pagination(@PathVariable int page, Principal principal) {
        return articleService.getArticlesOfOnePage(principal.getName(), page);
    }

    @PostMapping(value = "/article/new")
    @ResponseBody
    public long insertArticle(Principal principal) {
        Article article = new Article();
        article.setUsername(principal.getName());

        return articleService.insertOneArticle(article);
    }

    @PostMapping(value = "/article/delete/{articleId}")
    public String deleteArticle(@PathVariable long articleId, Principal principal) {
        articleService.deleteOneArticleById(articleId, principal.getName());

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping(value = "/article/update")
    public Article updateArticle(@ModelAttribute Article article, Principal principal) {
        int page = articleService.getArticlePage(principal.getName(), article.getId());
        articleService.updateOneArticle(article, principal.getName(), page);

        return article;
    }

}
