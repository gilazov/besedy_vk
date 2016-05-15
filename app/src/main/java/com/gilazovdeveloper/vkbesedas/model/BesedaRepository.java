package com.gilazovdeveloper.vkbesedas.model;

import com.gilazovdeveloper.vkbesedas.callback.GetBesedasResultListener;
import com.gilazovdeveloper.vkbesedas.callback.OnFinishedListener;
import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;
import com.gilazovdeveloper.vkbesedas.utils.BesedaCache;
import com.gilazovdeveloper.vkbesedas.utils.MyCache;
import com.vk.sdk.api.VKError;

import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public class BesedaRepository {
    public static final int FULL_DATA_RESPONSE = 0;
    public static final int MORE_DATA_RESPONSE = 1;
    public static final int MORE_DATA_RESPONSE_END_OF_LIST = 2;

    BesedaCache mCache;

    public BesedaRepository() {
        setCache(MyCache.getCacheInstance());
    }

    public void setCache(BesedaCache cache){
        this.mCache = cache;
    }

    public void getBesedas(final OnFinishedListener onFinishedListener){

        if (mCache.getSize()!=0){
            onFinishedListener.onFinished(mCache.getAll(), FULL_DATA_RESPONSE);
        }else{
            VkService.getBesedasWithListener(0, new GetBesedasResultListener() {
                @Override
                public void onComplete(List<Beseda> list) {
                    mCache.addAll(list);
                    onFinishedListener.onFinished(list, FULL_DATA_RESPONSE);
                }

                @Override
                public void onError(VKError error) {
                    onFinishedListener.onError(error.errorMessage);
                }
            });
        }

    }

    public void refreshData(final OnFinishedListener onFinishedListener) {

        VkService.getBesedasWithListener(0, new GetBesedasResultListener() {
            @Override
            public void onComplete(List<Beseda> list) {
                mCache.clearCache();
                mCache.addAll(list);
                onFinishedListener.onFinished(list, FULL_DATA_RESPONSE);
            }

            @Override
            public void onError(VKError error) {
                onFinishedListener.onFinished(mCache.getAll(), FULL_DATA_RESPONSE);
            }
        });

    }

    public void getMoreData (int offset, final OnFinishedListener onFinishedListener) {
        VkService.getBesedasWithListener(offset, new GetBesedasResultListener() {
            @Override
            public void onComplete(List<Beseda> list) {
                if (list.size() == 0) {
                    onFinishedListener.onFinished(list, MORE_DATA_RESPONSE_END_OF_LIST);
                }else {
                    mCache.addAll(list);
                    onFinishedListener.onFinished(list, MORE_DATA_RESPONSE);
                }
            }

            @Override
            public void onError(VKError error) {
                onFinishedListener.onError(error.errorMessage);

            }
        });
    }



}
