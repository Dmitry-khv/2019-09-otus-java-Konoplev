package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HWCacheDemo {
  private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

  public static void main(String[] args) throws InterruptedException {
    new HWCacheDemo().demo();
  }

  private void demo() throws InterruptedException {
    HwCache<String, BigObject> cache = new MyCache<>();
    HwListener<String, BigObject> listener =
            (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
    cache.addListener(listener);
    for (int i = 0 ; i < 100 ; i++) {
      cache.put("" + i, new BigObject());
    }
    logger.info("cache size before gc {}", cache.size());
    logger.info("getValue:{}", cache.get("1"));
    cache.remove("1");

    logger.info("listener count {}", cache.listenersSize());
    cache.removeListener(listener);
    logger.info("listener count {}", cache.listenersSize());


    System.gc();
    Thread.sleep(300);
    logger.info("cache size after gc {}", cache.size());

  }
}
