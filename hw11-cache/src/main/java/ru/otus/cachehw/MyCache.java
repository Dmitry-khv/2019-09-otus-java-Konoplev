package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
  static Logger logger = LoggerFactory.getLogger(MyCache.class);
  private static final String PUT_TO_CACHE = "put";
  private static final String REMOVE_FROM_CACHE = "remove";
  private final Map<K, V> cache = new WeakHashMap<>();

  private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();


  @Override
  public void put(K key, V value) {
    cache.put(key, value);
    for (WeakReference<HwListener<K, V>> weakReference : listeners) {
      weakReference.get().notify(key, value, PUT_TO_CACHE);
    }
  }

  @Override
  public void remove(K key) {
    var value = cache.get(key);
    cache.remove(key);

    for (WeakReference<HwListener<K, V>> weakReference : listeners) {
      weakReference.get().notify(key, value, REMOVE_FROM_CACHE);
    }
  }

  @Override
  public V get(K key) {
    return cache.get(key);
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    listeners.add(new WeakReference<HwListener<K, V>>(listener));
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    if (!listeners.isEmpty())
      listeners.removeIf(reference -> reference.get() == listener);
  }


  public int size() {
    return cache.size();
  }
  public int listenersSize() {
    return  listeners.size();
  }
}
