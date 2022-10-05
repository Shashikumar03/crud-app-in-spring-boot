package com.shashi.repository;



import org.springframework.data.repository.CrudRepository;

import com.shashi.entities.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

public User findByName(String name);

}
