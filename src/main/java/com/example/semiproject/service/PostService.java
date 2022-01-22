package com.example.semiproject.service;

import com.example.semiproject.dao.form.PostForm;
import com.example.semiproject.entity.Post;
import com.example.semiproject.repository.PostCustomRepository;
import com.example.semiproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostCustomRepository postCustomRepository;


    public Page<Post> selectAll(String searchText, Pageable pageable){

        return postRepository.findByTitleContainingOrContentContainingOrderByCreateDateDesc(searchText,searchText, pageable);
    }
    public Page<Post> myPostSelectALL(String email, Pageable pageable){
        return postCustomRepository.findByEmail(email , pageable);
    }
    public Post selectOne(Long id){
        return postRepository.findById(id).get();
    }
    public Post save(Post post){
        return postRepository.save(post);
    }
    public void delete(Long id){
        Post post = postRepository.getById(id);
        post.setRemove_Yn(!post.getRemove_Yn());
    }

    public void update(PostForm form) {
        Post post = postRepository.findById(form.getId()).orElse(null);
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
    }
}
