package tqs.homework.airquality;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    private Cache cache;
    private long ttl = 4;
    private long refreshTimer = 2;

    @BeforeAll
    static void setUp() {
        System.out.println("Starting CacheTest");
    }

    @Test
    void badInitializationTest() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                cache = new Cache(-2, 0);
            }
        });
    }

    @Test
    void put() {
        cache = new Cache(ttl, refreshTimer);

        String location = "lisbon";
        AirData ad = new AirData();

        cache.put(location, ad);
        assertEquals(ad, cache.get(location));
    }

    @Test
    void get() {
        cache = new Cache(ttl, refreshTimer);

        String location = "lisbon";
        AirData ad = new AirData();
        ad.setName("LISBOA");

        String location2 = "beijing";
        AirData ad2 = new AirData();
        ad2.setName("BEIJING");

        cache.put(location, ad);
        cache.put(location2, ad2);
        assertEquals(ad2, cache.get(location2));
        assertNotEquals(ad2, cache.get(location));
        assertEquals("LISBOA", cache.get(location).getName());
    }

    @Test
    void size() {
        cache = new Cache(ttl, refreshTimer);

        for(int i=1; i<=3; i++) {
            cache.put("location"+i, new AirData());
        }
        assertEquals(3, cache.size());
    }

    @Test
    void remove() {
        cache = new Cache(ttl, refreshTimer);

        String location = "lisbon";
        AirData ad = new AirData();

        String location2 = "beijing";
        AirData ad2 = new AirData();

        cache.put(location, ad);
        cache.put(location2, ad2);
        cache.remove(location);

        assertNull(cache.get(location));
        assertNotNull(cache.get(location2));
        assertEquals(1, cache.size());
    }

    @Test
    void clean() {
        cache = new Cache(ttl, refreshTimer);

        int requests = 0, hits = 0, misses = 0;
        for(int i = 1; i <= 3; i++) {
            cache.get("location"+i);
            requests++;
            misses ++;
            cache.put("location"+i, new AirData());
            cache.get("location"+i);
            requests++;
            hits++;
        }
        assertEquals(3, cache.size());
        assertEquals(requests, cache.getRequests());
        assertEquals(hits, cache.getHits());
        assertEquals(misses, cache.getMisses());

        cache.clean();
        assertEquals(0, cache.size());
        assertEquals(0, cache.getRequests());
        assertEquals(0, cache.getHits());
        assertEquals(0, cache.getMisses());
    }

    @Test
    void getRequests() {
        cache = new Cache(ttl, refreshTimer);

        cache.put("lisbon", new AirData());
        cache.get("lisbon");
        cache.get("lisbon");
        assertEquals(2, cache.getRequests());

        cache.get("lisbon");
        assertEquals(3, cache.getRequests());
    }

    @Test
    void getHits() {
        cache = new Cache(ttl, refreshTimer);

        cache.get("lisbon");
        cache.put("lisbon", new AirData());
        cache.get("lisbon");
        cache.get("porto");
        assertEquals(1, cache.getHits());

        cache.get("lisbon");
        assertEquals(2, cache.getHits());
    }

    @Test
    void getMisses() {
        cache = new Cache(ttl, refreshTimer);

        cache.get("lisbon");
        cache.put("lisbon", new AirData());
        cache.get("lisbon");
        cache.get("porto");
        assertEquals(2, cache.getMisses());

        cache.get("lisbon");
        assertEquals(2, cache.getMisses());
    }

    @Test
    void getCache_map() {
        cache = new Cache(ttl, refreshTimer);

        String location = "lisbon";
        AirData ad = new AirData();
        cache.put(location, ad);

        Map<String, CacheData> map = cache.getCacheMap();
        assertEquals(ad, map.get(location).value);
        assertEquals(1, map.size());
    }

    @Test
    void getStats() {
        cache = new Cache(ttl, refreshTimer);

        int requests = 0, hits = 0, misses = 0;
        for(int i = 1; i <= 3; i++) {
            cache.get("location"+i);
            requests++;
            misses ++;
            cache.put("location"+i, new AirData());
            cache.get("location"+i);
            requests++;
            hits++;
        }

        Map<String, Object> stats = cache.getStats();

        assertEquals(7, stats.size());
        assertEquals( (long) 4, stats.get("timeToLive"));
        assertEquals((long) 2, stats.get("timer"));
        assertEquals(requests, stats.get("requests"));
        assertEquals(hits, stats.get("hits"));
        assertEquals(misses, stats.get("misses"));
    }
}