package com.bookstore.Bookstore.domains.models;

import java.util.GregorianCalendar;
import java.util.List;

import com.bookstore.Bookstore.domains.dto.UserRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="users")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    
    @Column(name = "phone", nullable = true, length = 50)
    private String phone;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "role", nullable = false)
    private String Role;

    @Column(name = "created_at", nullable = false, length = 20)
    @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar createdAt;
    
    @Column(name = "modified_at", nullable = false, length = 20)
    @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar modifiedAt;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    public UserEntity() {
    }

    public UserEntity(String name, String lastName, String phone, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.Role = "Customer";
        this.orders = List.of();
        this.createdAt = new GregorianCalendar();
        this.modifiedAt = new GregorianCalendar();
    }

    public UserEntity(UserRequestDto userRequest){
        this.name = userRequest.getName();
        this.lastName = userRequest.getLastName();
        this.phone = userRequest.getPhone();
        this.email = userRequest.getEmail();
        this.password = userRequest.getPassword();
        this.Role = "Customer";
        this.orders = List.of();
        this.createdAt = new GregorianCalendar();
        this.modifiedAt = new GregorianCalendar();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public GregorianCalendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(GregorianCalendar createdAt) {
        this.createdAt = createdAt;
    }

    public GregorianCalendar getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(GregorianCalendar modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return String.format("""
                {"id":"%s", "name": "%s", "lastName": "%s", "phone": "%s", "email": "%s"}
                """,String.valueOf(this.getId()), this.name,this.lastName, this.phone, this.email);
    }

    
    
}
