package com.gilazovdeveloper.vkbesedas;

import android.content.Intent;

import com.gilazovdeveloper.vkbesedas.view.LoginActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by ruslan on 13.03.16.
 */
public class Application extends android.app.Application{

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(Application.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(getApplicationContext());
        vkAccessTokenTracker.startTracking();

    }

}
