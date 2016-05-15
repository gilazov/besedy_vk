package com.gilazovdeveloper.vkbesedas.utils;

import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;

import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 */
public interface BesedaCache {
    void clearCache();
    List<Beseda> getAll();
    void addAll(List<Beseda> list);
    int getSize();
}
