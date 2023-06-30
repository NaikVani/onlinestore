package com.online.store.model;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    private String token;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public AuthenticationToken(Customer customer) {
        this.customer = customer;
        this.createdDate = new Date();
        this.token = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String Token) {
        this.token = Token;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AuthenticationToken(Integer id, String Token, Date createdDate, Customer customer) {
        this.id = id;
        this.token = Token;
        this.createdDate = createdDate;
        this.customer = customer;
    }

    public AuthenticationToken() {
    }
}
