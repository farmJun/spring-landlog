package com.landvibe.landlog.controller;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("blogs")
public class BlogController {

    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping(value = "")
    public String blog(@RequestParam(name = "creatorId", required = false) Long creatorId, Model model) {

        Member member = memberService.findOne(creatorId);
        String name = member.getName();

        model.addAttribute("name", name);
        model.addAttribute("creatorId", creatorId);

        List<Blog> blogs = blogService.findAllBlogsByCreatorId(creatorId);
        model.addAttribute("blogs", blogs);

        return "blogs/blogList";
    }


    @GetMapping(value = "/new")
    public String createBlogForm(@RequestParam(name = "creatorId") Long creatorId, Model model) {

        Member member = memberService.findOne(creatorId);
        String name = member.getName();

        model.addAttribute("name", name);
        model.addAttribute("creatorId", creatorId);

        return "blogs/createBlogForm";
    }

    @PostMapping(value = "/new")
    public String createBlog(@RequestParam(name = "creatorId") Long creatorId, BlogForm form, RedirectAttributes redirect) {

        blogService.register(creatorId, form);

        redirect.addAttribute("creatorId", creatorId);

        return "redirect:/blogs";
    }

    @GetMapping(value = "/update")
    public String updateBlogForm(@RequestParam(name = "creatorId") Long creatorId, @RequestParam(name = "blogId", required = false) Long blogId, Model model) {

        Member member = memberService.findOne(creatorId);
        String name = member.getName();

        Blog blog = blogService.findBlogByCreatorIdAndBlogId(creatorId, blogId);

        model.addAttribute("name", name);
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blog", blog);

        return "blogs/updateBlogForm";
    }

    @PatchMapping(value = "/update")
    public String updateBlog(@RequestParam(name = "creatorId") Long creatorId, @RequestParam(name = "blogId", required = false) Long blogId, BlogForm form, RedirectAttributes redirect) {

        blogService.update(creatorId, blogId, form);

        redirect.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @DeleteMapping(value = "/delete")
    public String deleteBlog(@RequestParam(name = "creatorId") Long creatorId, @RequestParam(name = "blogId", required = false) Long blogId, RedirectAttributes redirect) {

        blogService.delete(creatorId, blogId);

        redirect.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }
}
