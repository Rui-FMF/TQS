package tqs.homework.airquality;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class CacheData {
    public long lastAccessed = System.currentTimeMillis();
    public AirData value;

    public CacheData(AirData value) {
        this.value = value;
    }
}

public class Cache{

    private long timeToLive;
    private long timer;
    private Map<String, CacheData> cacheMap;
    private int requests;
    private int hits;
    private int misses;
    private long lastRefresh = System.currentTimeMillis();

    public Cache(long timeToLive, final long timer) {

        if(timeToLive <= 0 || timer <= 0)
            throw new IllegalArgumentException("TTL and timer must be positive.");

        this.timeToLive = timeToLive * 1000;
        this.timer = timer * 1000;
        this.cacheMap = new HashMap<>();

        var t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(timer * 1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    refresh();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void put(String location, AirData value) {
        synchronized (cacheMap) {
            cacheMap.put(location, new CacheData(value));
        }
    }

    public AirData get(String location) {
        synchronized (cacheMap) {
            this.requests++;

            var cacheData = cacheMap.get(location);
            if(cacheData == null) {
                this.misses++;
                return null;
            }
            this.hits++;
            cacheData.lastAccessed = System.currentTimeMillis();
            return cacheData.value;
        }
    }

    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public void remove(String location) {
        synchronized (cacheMap) {
            cacheMap.remove(location);
        }
    }

    public void refresh() {

        long now = System.currentTimeMillis();
        this.lastRefresh = now;

        List<String> expiredData = new ArrayList<>();

        synchronized (cacheMap) {

            for(String location : cacheMap.keySet()) {

                var cacheData = cacheMap.get(location);

                if(cacheData != null && now > (timeToLive + cacheData.lastAccessed)) {
                    expiredData.add(location);
                }
            }
        }

        for(String location: expiredData) {
            synchronized (cacheMap) {
                cacheMap.remove(location);
            }
            Thread.yield();
        }
    }

    public void clean() {
        synchronized (cacheMap) {
            cacheMap = new HashMap<>();
            requests = 0;
            hits = 0;
            misses = 0;
            lastRefresh = System.currentTimeMillis();
        }
    }


    public int getRequests() {
        return requests;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public long getTimer() {
        return timer;
    }

    public long getLastRefresh() {
        return lastRefresh;
    }

    public Map<String, CacheData> getCacheMap() {
        return cacheMap;
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("hits", getHits());
        stats.put("misses", getMisses());
        stats.put("requests", getRequests());
        stats.put("timeToLive", getTimeToLive() / 1000);
        stats.put("timer", getTimer() / 1000);
        stats.put("lastRefresh", getLastRefresh());
        stats.put("cache_map", getCacheMap());

        return stats;
    }

}
