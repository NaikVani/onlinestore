package com.online.store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.online.store.model.AuthenticationToken;
import com.online.store.model.Customer;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
	 
	@Query("select a from AuthenticationToken a where a.customer=:customer")
	AuthenticationToken findTokenByUser(Customer customer);
    
    @Query("select a from AuthenticationToken a where a.token=:token")
    AuthenticationToken findTokenByToken(String token);
}
