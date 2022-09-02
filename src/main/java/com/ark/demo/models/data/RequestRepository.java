package com.ark.demo.models.data;

import com.ark.demo.models.Request;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface RequestRepository extends CrudRepository<Request, Integer> {

   List<Request> findByUserId(Integer UserId);

   @Transactional
//   @Modifying
   @Query(value = "select * from request where user_id = ?1 order by date_requested DESC limit 1" , nativeQuery = true)
   Request findLastRequestByUserId(Integer userId);



}
