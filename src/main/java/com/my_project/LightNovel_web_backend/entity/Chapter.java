package com.my_project.LightNovel_web_backend.entity;

import com.my_project.LightNovel_web_backend.enums.ChapterStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private float chapter_number;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    private int viewtotal;

    @Enumerated(EnumType.STRING)
    private ChapterStatus chapterStatus;

    @ManyToOne
    private LightNovel lightNovel;

    @OneToMany(mappedBy = "chapter")
    private List<Comment> comments;

}
