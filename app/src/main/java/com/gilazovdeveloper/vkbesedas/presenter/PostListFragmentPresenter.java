package com.gilazovdeveloper.vkbesedas.presenter;

import com.gilazovdeveloper.vkbesedas.model.BesedaRepository;
import com.gilazovdeveloper.vkbesedas.view.BesedaListFragmentView;

/**
 * Created by ruslan on 13.03.16.
 */
public interface PostListFragmentPresenter {

    void attachView(BesedaListFragmentView view);

    void attachRepository(BesedaRepository repository);

    void loadData();//загрузка того что есть в кеше если он не пуст

    void loadMore();// infinite scroll

    void refreshData(); // обновление кеша новыми данными с сети

    void onDestroy();



}
