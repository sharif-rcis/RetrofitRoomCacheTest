package sharif.roomretrofitcachetest.com.repository;

import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;

import java.util.concurrent.TimeUnit;


/**
 * Utility class that decides whether we should fetch some data or not.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class CacheValidityLimiter<KEY> {

    private ArrayMap<KEY, Long> timestamps = new ArrayMap<>();
    private final long timeout;

    public CacheValidityLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch(KEY key) {
        Long lastFetched = timestamps.get(key);
        long now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    private long now() {
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(KEY key) {
        timestamps.remove(key);
    }

}
