package com.wishlist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wishlist.social.Provider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = {"listSet", "authorities"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "password_hash", length = 100)
    @JsonIgnore
    private String password;

    @Column(length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private boolean activated;

    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Column(name = "about")
    @Size(max = 4000)
    private String about;

    @Column(name = "nickname")
    @Size(max = 20)
    private String nickName;

    @Column(name = "picture")
    private String photoUrl;

    @ManyToMany
    @JoinTable(name = "users_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_name", referencedColumnName = "name")
    )
    @BatchSize(size = 20)
    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Set<List> listSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Gift> bookedGifts = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public boolean isActivated() {
        return activated;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addList(List list) {
        listSet.add(list);
    }

    public void bookGift(Gift gift) {
        bookedGifts.add(gift);
    }
}
