package com.wludio.blog.dtos;


import com.wludio.blog.entites.enums.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserDto {

    private Long id;
    private Role role;

    @NotNull(message = "Please provide a firstName")
    @Size(min = 3, max = 30, message = "The firstName should have between 3 and 30 characters")
    private String firstName;

    @NotNull(message = "Please provide a lastName")
    @Size(min = 3, max = 30, message = "The lastName should have between 3 and 30 characters")
    private String lastName;

    @NotNull(message = "Please provide a mobile")
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobile;

    @NotNull(message = "Please provide a username")
    @Size(min = 3, max = 12, message = "The username should have between 1 and 12 characters")
    private String username;

    @Email(regexp = ".@.\\..*", message = "Email should respect the standard format")
    private String email;

    private Date createdOn;
    private String createdBy;
    private Date lastLogin;
}