package com.ds.devsuuser.infraestructure.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScopeUtilsTest {

    @Test
    void getScopeValueReturnsLocalWhenEnvIsMissing() {
        String scope = ScopeUtils.getScopeValue();

        assertEquals("local", scope);
        assertTrue(ScopeUtils.isLocalScope());
    }

    @Test
    void calculateScopeSuffixSetsSystemProperty() {
        ScopeUtils.calculateScopeSuffix();

        assertEquals(ScopeUtils.getScopeValue(), System.getProperty(ScopeUtils.SCOPE_SUFFIX));
    }

    @Test
    void getValidScopeReturnsExpectedFallbacks() throws Exception {
        Method method = ScopeUtils.class.getDeclaredMethod("getValidScope", String.class);
        method.setAccessible(true);

        assertEquals("local", method.invoke(null, "LOCAL"));
        assertEquals("test", method.invoke(null, "test"));
        assertEquals("dev", method.invoke(null, "dev"));
        assertEquals("prod", method.invoke(null, "prod"));
    }
}
