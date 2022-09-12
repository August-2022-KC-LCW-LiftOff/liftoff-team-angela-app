package com.ark.demo.models.data;


import com.ark.demo.models.Thread;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Integer> {


}