package com.company.codef.information.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EntityScan
@Entity
@Data
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long generalChatId;

    @Column(length = 25, nullable = false, unique = true)
    private String chatName;

    @Column(nullable = false, unique = true)
    private Long chatId;

    @OneToMany(mappedBy = "connUser")
    private List<ChatUser> chatUsers;

    @OneToOne(mappedBy = "chat", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private ValidRatingConfig validRatingConfig;

    public Chat(String chatName, Long chatId){
        this.chatId = chatId;
        this.chatName = chatName;
    }

    public Chat(){};
}
