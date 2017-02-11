package com.infopulse.mvc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by vedmant on 2/11/17.
 */
@Entity(name = "black_list")
@Getter
@Setter
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
