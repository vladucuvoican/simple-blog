package com.wludio.blog.entites;

import com.wludio.blog.entites.constants.Schema;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@Entity
@Audited
@Table(schema = Schema.BLOG, name = "KEYWORD")
public class Keyword extends BaseEntity {

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID", nullable = false)
    private Article article;
}
