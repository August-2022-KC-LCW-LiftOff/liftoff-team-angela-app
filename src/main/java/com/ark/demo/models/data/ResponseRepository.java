package com.ark.demo.models.data;

import com.ark.demo.models.Response;
import com.mysql.cj.Messages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends CrudRepository<Response, Integer> {
    List<Response> findByUserId(Integer userId);



}
