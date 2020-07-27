package com.chaman.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
//import java.security.Permission;
import java.security.Permissions;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
                                        inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "roles")
    private List<User> users;
}
