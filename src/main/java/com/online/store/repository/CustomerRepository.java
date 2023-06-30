package com.online.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.online.store.model.Customer;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAll();

    Customer findById(String id);

    Customer findUserByEmail(String email);
    
    
    @Modifying
    @Query("update Customer c set c.firstName = :firstname, c.lastName = :lastname where c.id = :id")
    void updateUserInfoById(String firstname, String lastname, Integer id);
    
    @Modifying
    @Query("update Customer c set c.password =:password where c.email =:email")
    void changePassword(String email, String password);
    
    
}
