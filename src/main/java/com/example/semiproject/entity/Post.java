package com.example.semiproject.entity;

import com.example.semiproject.dao.form.PostForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private Boolean remove_Yn = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void postMapping(PostForm form){
        this.title = form.getTitle();
        this.content = form.getContent();
        this.createDate = LocalDateTime.now();
    }

}
