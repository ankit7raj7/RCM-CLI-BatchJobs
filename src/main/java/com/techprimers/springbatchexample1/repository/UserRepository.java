package com.techprimers.springbatchexample1.repository;

import com.techprimers.springbatchexample1.model.Folders;
import com.techprimers.springbatchexample1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<Folders, Integer> {
}
