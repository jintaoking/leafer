package org.ziwenxie.leafer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ziwenxie.leafer.model.ArticleTag;
import org.ziwenxie.leafer.service.IArticleTagService;

@Controller
public class ArticleTagController {

    private IArticleTagService articleTagService;

    @Autowired
    public ArticleTagController(IArticleTagService articleTagService) {
        this.articleTagService = articleTagService;
    }

    @ResponseBody
    @PostMapping("/articleTag/new")
    public ArticleTag newArticleTag(@RequestBody ArticleTag articleTag) {
        articleTagService.insertOneArticleTag(articleTag);

        return articleTag;

    }

    @ResponseBody
    @PostMapping("/articleTag/delete")
    public ArticleTag deleteArticleTag(@RequestBody ArticleTag articleTag) {
        articleTagService.deleteOneArticleTag(articleTag);

        return articleTag;
    }

}
