package com.yewseng.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yewseng.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

}
