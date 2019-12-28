package com.company.codef.information.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.List;

@Data
@EntityScan
@Entity
@Table(name = "valid_options")
public class ValidRatingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long config_id;

    @Column
    private Long lastContestStart;

    @Column
    private Long currentContestStart;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Chat chat;

    public ValidRatingConfig(Chat chat, Long currentContestStart){
        this.chat = chat;
        this.currentContestStart = currentContestStart;
    }

    public ValidRatingConfig(){};
}
