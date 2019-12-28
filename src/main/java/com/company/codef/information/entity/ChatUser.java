package com.company.codef.information.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@EntityScan
@Table(name = "chat_users")
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "general_chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ConnUser connUser;

    public ChatUser(Chat chat, ConnUser connUser){
        this.chat = chat;
        this.connUser = connUser;
    }

    public ChatUser(){}
}
