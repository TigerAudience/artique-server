package com.artique.api.redis.repository;

import com.artique.api.entity.redis.RedisSession;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<RedisSession,String> {
}
