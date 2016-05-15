package com.gilazovdeveloper.vkbesedas.callback;

import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;
import com.vk.sdk.api.VKError;

import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 */

public interface GetBesedasResultListener {

    void onComplete(List<Beseda> list);

    void onError(VKError error);
}

