package com.ark.demo.models.data;

import com.ark.demo.models.Response;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends CrudRepository<Response, Integer> {
}
