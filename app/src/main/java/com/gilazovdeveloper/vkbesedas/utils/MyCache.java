package com.gilazovdeveloper.vkbesedas.utils;

/**
 * Created by ruslan on 31.03.16.
 */
public class MyCache {
    static BesedaLruCacheImpl mCache =  new BesedaLruCacheImpl();;
    public static BesedaLruCacheImpl getCacheInstance(){
        return mCache;
    }
}
