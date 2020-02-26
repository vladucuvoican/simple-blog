package com.wludio.blog.entites;


import com.wludio.blog.entites.BaseEntity;
import com.wludio.blog.entites.constants.Schema;
import com.wludio.blog.entites.enums.Role;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Entity
@Audited
@EntityListeners({AuditingEntityListener.class})
@Table(schema = Schema.USER_MANAGEMENT, name = "USER")
public class User extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @NotNull(message = "First Name cannot be null")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "Last Name cannot be null")
    private String lastName;

    @Column(nullable = false)
    @Pattern(regexp="(^$|[0-9]{10})")
    private String mobile;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Email(regexp=".@.\\..*", message = "Email should respect the standard format")
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @CreationTimestamp
    private Date createdOn;

    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;

    private Date lastLogin;
}