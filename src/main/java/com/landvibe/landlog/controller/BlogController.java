package com.landvibe.landlog.controller;

import com.landvibe.landlog.controller.form.BlogCreateForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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

        Optional<Member> blogMember = memberService.findOne(creatorId);

        Member member = blogMember.get();
        String name = member.getName();

        model.addAttribute("name", name);
        model.addAttribute("creatorId", creatorId);

        List<Blog> blogs = blogService.findAllBlogsByCreatorId(creatorId);
        model.addAttribute("blogs", blogs);

        return "blogs/blogList";
    }


    @GetMapping(value = "/new")
    public String createBlogForm(@RequestParam(name = "creatorId", required = false) Long creatorId, Model model) {

        Optional<Member> blogMember = memberService.findOne(creatorId);

        Member member = blogMember.get();
        String name = member.getName();

        model.addAttribute("name", name);
        model.addAttribute("creatorId", creatorId);

        return "blogs/createBlogForm";
    }

    @PostMapping(value = "/new")
    public String createBlog(@RequestParam(name = "creatorId", required = false) Long creatorId, BlogCreateForm form, RedirectAttributes redirect) {

        Optional<Member> blogMember = memberService.findOne(creatorId);

        Blog blog = new Blog();
        blog.setCreatorId(creatorId);
        blog.setTitle(form.getTitle());
        blog.setContents(form.getTitle());

        blogService.register(blog);

        redirect.addAttribute("creatorId", creatorId);

        return "redirect:/blogs";
    }
}
