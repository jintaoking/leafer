package org.ziwenxie.leafer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.ziwenxie.leafer.model.Tag;
import org.ziwenxie.leafer.service.ITagService;

import java.security.Principal;
import java.util.List;

@Controller
public class TagController {

    private ITagService tagService;

    @Autowired
    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tag")
    public String index(ModelMap modelMap, Principal principal) {
        List<Tag> tags = tagService.getAllTags(principal.getName());

        modelMap.addAttribute("tags", tags);

        return "tag/index";
    }

    @GetMapping("/tag/{tagId}")
    public String getOneTag(@PathVariable("tagId") long tagId, ModelMap modelMap, Principal principal) {
        Tag tag = tagService.getOneTagById(tagId);
        List<Tag> tags = tagService.getAllTags(principal.getName());

        modelMap.addAttribute("tag", tag);
        modelMap.addAttribute("tags", tags);

        return "tag/display";
    }

    @ResponseBody
    @PostMapping("/tag/{tagId}")
    public Tag getOneTag(@PathVariable("tagId") long tagId) {
        return tagService.getOneTagById(tagId);
    }

    @ResponseBody
    @PostMapping("/tag/n")
    public Tag newTag(@RequestParam("value") String tagName, Principal principal) {
        Tag isTag = tagService.getOneTagByName(tagName, principal.getName());
        if (isTag != null ) {
            return isTag;
        } else {
            Tag tag = new Tag();
            tag.setName(tagName);
            tag.setUsername(principal.getName());
            tagService.insertOneTag(tag);
            return tag;
        }
    }

    @ResponseBody
    @PostMapping("/tag/all")
    public List<Tag> getAllTags(Principal principal) {
        return tagService.getAllTags(principal.getName());
    }

    @PostMapping("/tag/delete/{tagId}")
    public String deleteOneTag(@PathVariable("tagId") long tagId) {
        tagService.deleteOneTagById(tagId);

        return "redirect:/";
    }

}
