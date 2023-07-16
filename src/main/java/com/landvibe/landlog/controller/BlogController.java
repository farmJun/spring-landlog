package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BlogController {

    private final MemberService memberService;

    public BlogController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/blogs/{creatorId}")
    public String blog(@PathVariable Long creatorId, Model model) {
        Optional<Member> blogMember = memberService.findOne(creatorId);

        if (blogMember.isEmpty()) {
            return "redirect:/";
        }

        Member member = blogMember.orElse(null);
        model.addAttribute("member", member);

        return "members/blog";
    }
}
