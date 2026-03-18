package com.ds.devsuuser.infraestructure.lock;

import org.springframework.stereotype.Service;

@Service
public class LockService implements ILockService{


    @Override
    public boolean acquireLock(String key) {
        return false;
    }

    @Override
    public void releaseLock(String key) {

    }
}
