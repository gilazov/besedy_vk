package com.gilazovdeveloper.vkbesedas.model;

import android.util.Log;

import com.gilazovdeveloper.vkbesedas.callback.GetBesedasResultListener;
import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;
import com.gilazovdeveloper.vkbesedas.utils.Utils;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKUsersArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 * Диалоги получаются из вк, с помощью своей хранимой процедуры
 */

public class VkService {
    public static final int CHATS_COUNT = 20;//по сколько данных грузить
    private static final String COUNT = "count";
    private static final String OFFSET = "offset";

    public static void getBesedasWithListener(int offset, final GetBesedasResultListener listener){

        final List<Beseda> resultList = new ArrayList<>();

        VKRequest getChatsRequest = new VKRequest("execute.getChats", VKParameters.from(COUNT, CHATS_COUNT, OFFSET, offset));
        getChatsRequest.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onError(VKError error) {
                super.onError(error);
                listener.onError(error);
                Log.d("TAG", "onError: " + error.toString());
            }

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                JSONObject responseJson = response.json;
                Log.d("TAG", "onComplete: "+response.json.toString());

                try {
                    //int count = responseJson.getInt("count");
                    JSONObject responseTmp = responseJson.getJSONObject("response");
                    JSONArray chatsJsonArray = responseTmp.getJSONArray("result");
                    for (int i = 0; i < chatsJsonArray.length(); i++) {
                        Beseda currentChat = new Beseda();
                        JSONObject currentChatJson = chatsJsonArray.getJSONObject(i);
                        currentChat.id = currentChatJson.getString("beseda_id");
                        currentChat.title = currentChatJson.getString("beseda_title");
                        currentChat.body = currentChatJson.getString("beseda_body");
                        currentChat.date = Utils.getHumanReadTime(currentChatJson.getLong("beseda_date"));
                        currentChat.UsersPhotoSrcList = new ArrayList<String>();

                        if (!currentChatJson.isNull("beseda_photos")) {
                            JSONObject chatPhotosJson = currentChatJson.getJSONObject("beseda_photos");

                            if (chatPhotosJson != null) {
                                JSONArray photosSrcJson = chatPhotosJson.getJSONArray("photos");
                                for (int j = 0; j < photosSrcJson.length(); j++) {
                                    currentChat.UsersPhotoSrcList.add(photosSrcJson.getString(j));
                                }
                            }
                        }

                        resultList.add(currentChat);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.onComplete(resultList);
            }
        });







    }


}
