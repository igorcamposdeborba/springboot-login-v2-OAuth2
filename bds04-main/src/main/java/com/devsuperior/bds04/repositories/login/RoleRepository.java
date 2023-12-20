package com.devsuperior.bds04.repositories.login;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.bds04.entities.login.User;

public interface RoleRepository extends JpaRepository<User, Long> {

}
