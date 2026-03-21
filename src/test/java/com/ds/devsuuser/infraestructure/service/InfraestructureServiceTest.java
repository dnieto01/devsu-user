package com.ds.devsuuser.infraestructure.service;

import com.ds.devsuuser.infraestructure.exceptions.ApiException;
import com.ds.devsuuser.infraestructure.lock.LockService;
import com.ds.devsuuser.infraestructure.lock.LockServiceLocal;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InfraestructureServiceTest {

    @Test
    void lockServiceLocalShouldAcquireReleaseAndFailWhenLocked() {
        LockServiceLocal service = new LockServiceLocal();
        assertTrue(service.acquireLock("k1"));
        assertThrows(ApiException.class, () -> service.isLocked("k1"));
        service.releaseLock("k1");
        service.isLocked("k1");
    }

    @Test
    void lockServiceLocalThrowsWhenDoubleAcquire() {
        LockServiceLocal service = new LockServiceLocal();
        service.acquireLock("k1");

        assertThrows(ApiException.class, () -> service.acquireLock("k1"));
    }

    @Test
    void lockServiceLocalAcquireAndReleaseWorks() {
        LockServiceLocal service = new LockServiceLocal();

        boolean locked = service.acquireLock("k1");
        service.releaseLock("k1");

        assertTrue(locked);
        assertDoesNotThrow(() -> service.isLocked("k1"));
    }

    @Test
    void lockServiceShouldAcquireReleaseAndThrowWhenAlreadyLocked() {
        Jedis jedis = mock(Jedis.class);
        LockService service = new LockService(jedis, 3000L);

        when(jedis.exists("k2")).thenReturn(false);
        when(jedis.set(eq("k2"), any(), any())).thenReturn("OK");
        assertTrue(service.acquireLock("k2"));

        when(jedis.eval(any(), eq(1), eq("k2"), any())).thenReturn(1L);
        service.releaseLock("k2");

        when(jedis.exists("k3")).thenReturn(true);
        assertThrows(ApiException.class, () -> service.isLocked("k3"));
    }

    @Test
    void lockServiceIsLockedThrowsWhenKeyAlreadyHeldLocally() throws Exception {
        Jedis jedis = mock(Jedis.class);
        when(jedis.exists("k1")).thenReturn(false);
        LockService service = new LockService(jedis, 1000);
        ConcurrentMap<String, String> localLocks = getLocalLocks(service);
        localLocks.put("k1", "uuid");

        assertThrows(ApiException.class, () -> service.isLocked("k1"));
    }

    @Test
    void lockServiceAcquireLockMapsInfrastructureErrorsToApiException() {
        Jedis jedis = mock(Jedis.class);
        when(jedis.exists("k1")).thenReturn(false);
        when(jedis.set(eq("k1"), any(), any())).thenThrow(new RuntimeException("redis down"));
        LockService service = new LockService(jedis, 1000);

        assertThrows(ApiException.class, () -> service.acquireLock("k1"));
    }

    @SuppressWarnings("unchecked")
    private static ConcurrentMap<String, String> getLocalLocks(LockService target) throws Exception {
        Field field = LockService.class.getDeclaredField("localLocks");
        field.setAccessible(true);
        return (ConcurrentMap<String, String>) field.get(target);
    }
}
