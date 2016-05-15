package com.gilazovdeveloper.vkbesedas.utils;

import android.support.v4.util.LruCache;

import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 */
public class BesedaLruCacheImpl implements BesedaCache {
    private LruCache<String, Beseda> mMemoryCache;

    public BesedaLruCacheImpl() {
        //working with cache initializing
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<>(cacheSize);

    }

    public void add(String key, Beseda beseda) {
        if (get(key) == null) {
            mMemoryCache.put(key, beseda);
        }
    }

    public Beseda get(String key) {
        return mMemoryCache.get(key);
    }

    public void addAll(List<Beseda> list) {
        for (Beseda beseda: list) {
            mMemoryCache.put(beseda.id, beseda);
        }
        }



    public void clearCache(){
        mMemoryCache.evictAll();
    }

    public int getSize(){
        return mMemoryCache.size();
    }

    public List<Beseda> getAll(){
            List<Beseda> list = new ArrayList<>();
            list.addAll(mMemoryCache.snapshot().values());
        return list;
    }

}
