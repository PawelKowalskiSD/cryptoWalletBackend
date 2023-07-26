package com.wallet.cryptocurrency.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Column(name = "ROLE_NAME")
    private String name;

    @OneToMany(
            targetEntity = User.class,
            mappedBy = "role",
            fetch = FetchType.LAZY
    )
    private List<User> userList = new ArrayList<>();

    public Role(String name) {

        this.name = name;
    }
}
