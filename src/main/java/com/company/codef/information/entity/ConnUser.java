package com.company.codef.information.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EntityScan
@Entity
@Data
@Table(name = "connUser")
public class ConnUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(length = 25, nullable = false, unique = true)
    private String userName;

    @Column(length = 25, nullable = false, unique = true)
    private String handle;

    @OneToMany(mappedBy = "chat")
    private List<ChatUser> chatUsers;

    public ConnUser(String userName, String handle){
        this.userName = userName;
        this.handle = handle;
    }

    public ConnUser(){};
}
