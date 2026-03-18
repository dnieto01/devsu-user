package com.ds.devsuuser.infraestructure.lock;

import com.ds.devsuuser.infraestructure.exceptions.ApiException;
import com.ds.devsuuser.infraestructure.exceptions.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
@Profile("local")
public class LockServiceLocal implements ILockService {

    private final ConcurrentMap<String, String> localLocks = new ConcurrentHashMap<>();


    @Override
    public boolean acquireLock(String key) {

        isLocked(key);

        String uuid = UUID.randomUUID().toString();
        localLocks.put(key, uuid);
        log.warn("Lock resource from local");
        return true;
    }

    @Override
    public void releaseLock(String key) {
        localLocks.remove(key);
    }

    public void isLocked(String key) {
        if (localLocks.containsKey(key)) {
            log.warn("Resources already locked.");
            throw new ApiException(ErrorCode.RESOURCE_ALREADY_LOCKED);
        }
    }
}
