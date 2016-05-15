package com.gilazovdeveloper.vkbesedas.callback;

import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;

import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public interface OnFinishedListener {
    void onFinished(List<Beseda> items, int responseCode);
    void onError(String errorMessage);
}
