package com.my_project.LightNovel_web_backend.entity;


import com.my_project.LightNovel_web_backend.Enum.LigthNovelStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "light_novel")
public class LigthNovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "display_name")
    private String displayName;

    private String image;

    private String description;

    private String author;

    @Enumerated(EnumType.STRING)
    private LigthNovelStatus status;

    @Column(name = "translation_groups")
    private String translationGroups;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "like_total")
    private String likeTotal;

    @OneToMany(mappedBy = "ligthNovel", cascade = CascadeType.REMOVE)
    private List<Reveiw> reveiws;

    @ManyToMany(mappedBy = "ligthNovels")
    private List<User> users;

    @OneToMany(mappedBy = "ligthNovel", cascade = CascadeType.REMOVE)
    private List<Chapter> chapters;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            joinColumns = @JoinColumn(name = "ligth_novel_id"),
            inverseJoinColumns = @JoinColumn(name = "genres_id")
    )
    private List<Genre> genres;
}
