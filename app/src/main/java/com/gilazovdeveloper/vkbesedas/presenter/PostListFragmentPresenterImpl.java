package com.gilazovdeveloper.vkbesedas.presenter;

import com.gilazovdeveloper.vkbesedas.callback.OnFinishedListener;
import com.gilazovdeveloper.vkbesedas.model.BesedaRepository;
import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;
import com.gilazovdeveloper.vkbesedas.view.BesedaListFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public class PostListFragmentPresenterImpl implements PostListFragmentPresenter, OnFinishedListener {

    BesedaListFragmentView view;
    BesedaRepository postRepository;
    List<Beseda> viewPosts;

    public PostListFragmentPresenterImpl(BesedaListFragmentView view) {
        attachView(view);
        attachRepository(new BesedaRepository());

        if (viewPosts==null) {
            viewPosts = new ArrayList<>();
        }

    }

    @Override
    public void attachView(BesedaListFragmentView view) {
            this.view = view;
    }


    @Override
    public void attachRepository(BesedaRepository repository) {
        postRepository = repository;
    }

    @Override
    public void loadData() {
        if (view!=null) {
            view.showProgress(true);
        }

        if (viewPosts.size() == 0) {
            postRepository.getBesedas(this);
        }else {
            if (view!=null) {
                view.setItems(viewPosts, 0);
                view.showProgress(false);
            }
        }
    }

    @Override
    public void loadMore() {
        if (view!=null) {
            view.showInfiniteProgress(true);
        }
        postRepository.getMoreData(viewPosts.size(), this);
    }

    @Override
    public void refreshData() {
        if (view!=null) {
            view.showProgress(true);
        }
        postRepository.refreshData(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }


    @Override
    public void onFinished(List<Beseda> items, int responseCode) {

        switch (responseCode) {
            case BesedaRepository.FULL_DATA_RESPONSE :
                if (items.size()!=0) {
                    view.setItems(items, 0);
                    viewPosts.clear();
                    viewPosts.addAll(items);
                    view.setInfiniteEnable(true);
                    view.showEmptyView(false);
                }else {
                    view.showEmptyView(true);
                    view.setInfiniteEnable(false);
                }
                break;
            case BesedaRepository.MORE_DATA_RESPONSE:
                int offset = viewPosts.size();
                viewPosts.addAll(items);
                view.setItems(viewPosts, offset);
                view.showInfiniteProgress(false);
                view.setInfiniteEnable(true);
                break;
            case BesedaRepository.MORE_DATA_RESPONSE_END_OF_LIST:
                view.setInfiniteEnable(false);
                break;
        }
        view.showProgress(false);
    }

    @Override
    public void onError(String errorMessage) {
        view.showProgress(false);
        view.showInfiniteProgress(false);
        view.showErrorMessage(errorMessage);
   }

}
