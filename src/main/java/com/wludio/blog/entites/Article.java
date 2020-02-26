package com.wludio.blog.entites;

import com.wludio.blog.entites.constants.Schema;
import com.wludio.blog.entites.enums.ArticleStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Audited
@EntityListeners({AuditingEntityListener.class})
@Table(schema = Schema.BLOG, name = "ARTICLE")
public class Article extends BaseEntity {

    @Column(name = "ARTICLE_STATUS_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = Schema.BLOG,
            joinColumns = @JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    private List<User> authors;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private List<Keyword> keywords = new LinkedList<>();

    private Date publishedOn;

    @CreationTimestamp
    private Date createdOn;

    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;

    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy;

    @UpdateTimestamp
    private Date modifiedOn;

    private boolean delete;
}