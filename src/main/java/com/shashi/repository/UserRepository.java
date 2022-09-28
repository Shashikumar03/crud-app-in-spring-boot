package com.shashi.repository;



import org.springframework.data.repository.CrudRepository;

import com.shashi.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	

}
