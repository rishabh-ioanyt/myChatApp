package com.example.mychatapp.buffermessage;

import com.example.mychatapp.auth.UserRegistration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class BufferMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @OneToOne
    private UserRegistration sendBy;

    @OneToOne
    private UserRegistration receivedBy;

}
