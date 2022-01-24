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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostCustomRepository postCustomRepository;


    public Page<Post> selectAll(String searchText, Pageable pageable){

        return postRepository.findByRemoveYnAndTitleContainingOrderByCreatedDateDesc(false,searchText, pageable);
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
    public void delete(Long id,String email){
        Optional<Post> opPost = postCustomRepository.findByIdAndEmail(id,email);
        if(opPost.isPresent()){
            Post post = opPost.get();
            post.setRemoveYn(!post.getRemoveYn());
        }else{
            throw new IllegalArgumentException("잘못된 요청");
        }

    }

    public void update(PostForm form,String email) {
        Optional<Post> opPost = postCustomRepository.findByIdAndEmail(form.getId(),email);
        if(opPost.isPresent()){
            Post post = opPost.get();
            post.setTitle(form.getTitle());
            post.setContent(form.getContent());
        }else{
            throw new IllegalArgumentException("잘못된 요청");
        }
    }

    public void deleteFromAdmin(Long id) {
        Post post = postRepository.findById(id).get();
        post.setRemoveYn(!post.getRemoveYn());
    }
}
