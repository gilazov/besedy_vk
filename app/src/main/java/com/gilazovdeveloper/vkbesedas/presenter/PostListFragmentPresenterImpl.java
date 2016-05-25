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
                view.setItems(viewPosts, false);
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
                    viewPosts.clear();
                    viewPosts.addAll(items);
                    if (view!=null) {
                        view.setItems(items, false);
                        view.setInfiniteEnable(true);
                        view.showEmptyView(false);
                    }
                }else {
                    if (view!=null) {
                        view.showEmptyView(true);
                        view.setInfiniteEnable(false);
                    }
                }
                break;
            case BesedaRepository.MORE_DATA_RESPONSE:
                viewPosts.addAll(items);
                if (view!=null) {
                    view.setItems(items, true);
                    view.setInfiniteEnable(true);
                }
                break;
            case BesedaRepository.MORE_DATA_RESPONSE_END_OF_LIST:
                if (view!=null) {
                    view.setInfiniteEnable(false);
                }
                break;
        }
        if (view!=null) {
            view.showInfiniteProgress(false);
            view.showProgress(false);
        }
    }

    @Override
    public void onError(String errorMessage) {
        if (view!=null) {
            view.showProgress(false);
            view.showInfiniteProgress(false);
            view.showErrorMessage(errorMessage);
        }
   }

}
