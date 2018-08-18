package com.ray.monitor.core.repository;

import com.ray.monitor.model.CommentInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rui on 2018/8/18.
 */
public interface CommentRepository extends CrudRepository<CommentInfo, Long> {
}
