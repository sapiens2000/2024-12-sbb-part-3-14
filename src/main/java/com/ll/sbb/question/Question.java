package com.ll.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.ll.sbb.answer.Answer;
import com.ll.sbb.category.Category;
import com.ll.sbb.user.SiteUser;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;

    @ManyToOne
    private Category category;

    private Long views;

    // 엔티티 생성 시 조회수 0으로 초기화
    @PrePersist
    protected void onCreate() {
        this.views = (this.views == null) ? 0L : this.views;
    }
}
