package com.ark.demo.models.data;


import com.ark.demo.models.Request;
import com.ark.demo.models.Thread;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Integer> {

    List<Thread> findAllByRequestId(Integer requestId);



}