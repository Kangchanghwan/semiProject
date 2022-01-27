package com.example.semiproject.controller;

import com.example.semiproject.dao.MemberCustomDetails;
import com.example.semiproject.dao.form.PostForm;
import com.example.semiproject.entity.Member;
import com.example.semiproject.entity.Post;
import com.example.semiproject.entity.Role;
import com.example.semiproject.repository.MemberRepository;
import com.example.semiproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;

    @GetMapping(value = {"","/admin","/mypost"})
    public String list(Model model , @PageableDefault(size = 10) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText
                       , HttpServletRequest request) {
        Page<Post> posts;
        Authentication authentic = SecurityContextHolder.getContext().getAuthentication();
        String name = authentic.getName();
        switch (request.getRequestURI()){

            case "/post/mypost" :{
                posts = postService.myPostSelectALL(name,searchText,pageable);
                extracted(model, posts);
                return "/post/myPost";
            }
            case "/post/admin" :{
                posts = postService.adminselectAll(searchText,pageable);
                extracted(model, posts);
                return "/admin/adminPost";
            }
            default: {
                posts = postService.selectAll(searchText, pageable);
                extracted(model, posts);
                return "/index";
            }
        }
    }

    private void extracted(Model model, Page<Post> posts) {
        model.addAttribute("list", posts);
        int startPage = Math.max(1, posts.getPageable().getPageNumber()-2);
        int endPage = Math.min((posts.getTotalPages()==0 ? 1 : posts.getTotalPages()),
                posts.getPageable().getPageNumber()+2);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
    }

    @GetMapping("/newp")
    public String newPost(Model model) {
        model.addAttribute("form", new PostForm());
        return "/post/postForm";
    }

    @PostMapping("/newp")
    public String createPost(@ModelAttribute PostForm form, @AuthenticationPrincipal MemberCustomDetails user) {
        Post post = new Post();
        String name = user.getEmail();
        Optional<Member> member = memberRepository.findByEmail(name);
        post.setMember(member.get());
        post.postMapping(form);
        postService.save(post);
        return "redirect:/post";
    }
    @GetMapping("/form{id}")
    public String postPage(
            @RequestParam Long id,Model model){
        Post post = postService.selectOne(id);
        PostForm form = new PostForm();
        form.setId(post.getId());
        form.setTitle(post.getTitle());
        form.setContent(post.getContent());
        form.setRemoveYn(post.getRemoveYn());
        form.setEmail(post.getMember().getEmail());
        model.addAttribute("form",form);
        return "post/form";
    }

    @GetMapping("/delete{id}")
    public String deletePost(@PathVariable("id") Long id,@AuthenticationPrincipal MemberCustomDetails user){
        boolean role_admin = user.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        if(role_admin){
            postService.deleteFromAdmin(id);
        }else{
            postService.delete(id,user.getEmail());
        }
        return "redirect:/post";
    }

    @GetMapping("/edit{id}")
    public String editPostPage(@RequestParam Long id, Model model){
        Post post = postService.selectOne(id);
        PostForm form = new PostForm();
        form.setId(post.getId());
        form.setTitle(post.getTitle());
        form.setContent(post.getContent());
        form.setEmail(post.getMember().getEmail());
        model.addAttribute("form",form);
        return "post/editForm";
    }

    @PostMapping("/edit")
    public String editPostPage(PostForm form,@AuthenticationPrincipal MemberCustomDetails user){
        postService.update(form,user.getEmail());
        return "redirect:/post/form?id="+form.getId();
    }


}
