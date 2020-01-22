package ru.otus.cachehw.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
  private static Logger logger = LoggerFactory.getLogger(MyCache.class);
  private static final String PUT_TO_CACHE = "put";
  private static final String REMOVE_FROM_CACHE = "remove";
  private static final String GET_FROM_CACHE = "get";

  private final Map<K, V> cache = new WeakHashMap<>();
  private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

  @Override
  public void put(K key, V value) {
    cache.put(key, value);
    notify(key, value, PUT_TO_CACHE);
  }

  @Override
  public void remove(K key) {
    var value = cache.get(key);
    cache.remove(key);
    notify(key, value, REMOVE_FROM_CACHE);
  }

  @Override
  public V get(K key) {
    if (!cache.containsKey(key))
      return null;
    var value = cache.get(key);
    notify(key, value, GET_FROM_CACHE);
    return cache.get(key);
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    listeners.add(new WeakReference<>(listener));
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    if (!listeners.isEmpty())
      listeners.removeIf(refListener -> refListener.get() == listener);
  }

  private void notify(K key, V value, String msg) {
    for (WeakReference<HwListener<K, V>> weakReference : listeners) {
      try {
        Objects.requireNonNull(weakReference.get()).notify(key, value, msg);
      } catch (Exception e) {
        logger.info("There is something wrong with listener");
      }
    }
  }

  //добавил методы, ради любопытства для проверки размера кэша и списка листенеров
  public int size() {
    return cache.size();
  }
  public int listenersSize() {
    return  listeners.size();
  }
}
