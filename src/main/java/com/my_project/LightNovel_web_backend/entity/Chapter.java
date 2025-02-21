package com.my_project.LightNovel_web_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private LocalDateTime UpdateAt;

    private int viewtotal;

    @ManyToOne
    private LigthNovel ligthNovel;

    @OneToMany(mappedBy = "chapter")
    private List<Comment> comments;
}
