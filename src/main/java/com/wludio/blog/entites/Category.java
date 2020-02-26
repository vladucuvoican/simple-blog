package com.wludio.blog.entites;

import com.wludio.blog.entites.constants.Schema;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Audited
@Table(schema = Schema.BLOG, name = "CATEGORY")
public class Category extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> children = new LinkedList<>();

    private String title;
    private String description;
}