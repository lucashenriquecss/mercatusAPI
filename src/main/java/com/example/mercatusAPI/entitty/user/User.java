package com.example.mercatusAPI.entitty.user;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.mercatusAPI.entitty.BaseEntity;
import com.example.mercatusAPI.entitty.inventory.Inventory;
import com.example.mercatusAPI.entitty.ticket.Ticket;
import com.example.mercatusAPI.entitty.transaction.Transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private UserRole role;
    
    @Column(nullable = false)
    private String password;

    @Column(name="is_active")
    private boolean isActive;

    @Column(precision = 19, scale = 2, nullable = false, columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")    
    private BigDecimal balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="inventory_id", referencedColumnName="id")
    private Inventory inventory;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
    
    public User(String email, String password, UserRole role, String name){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
 @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_COMMON"));
        else return List.of(new SimpleGrantedAuthority("ROLE_COMMON"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
