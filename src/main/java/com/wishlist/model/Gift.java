package com.wishlist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "gift")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    @NotBlank
    private String name;

    @Column(length = 500)
    @NotBlank
    private String link;

    @Column(length = 5000)
    @NotBlank
    private String description;

    private Byte[] picture;

    @OneToMany(mappedBy = "gift")
    private Set<GiftPicture> giftPictureList;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private List list;

    private boolean booked;

    public boolean isBooked() {
        return booked;
    }
}
