package com.gilazovdeveloper.vkbesedas.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gilazovdeveloper.vkbesedas.R;
import com.gilazovdeveloper.vkbesedas.adapter.BesedaRecyclerViewAdapter;
import com.gilazovdeveloper.vkbesedas.callback.InfiniteManager;
import com.gilazovdeveloper.vkbesedas.callback.InfiniteScrollListener;
import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;
import com.gilazovdeveloper.vkbesedas.presenter.PostListFragmentPresenterImpl;

import java.util.List;

public class BesedaListFragment extends Fragment implements BesedaListFragmentView{

    PostListFragmentPresenterImpl presenter;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    ProgressBar infiniteProgressBar;
    LinearLayoutManager layoutManager;
    InfiniteManager infiniteManager = new InfiniteManager();
    View view;
    TextView emptyView;
    public BesedaListFragment() {
    }

    @SuppressWarnings("unused")
    public static BesedaListFragment newInstance() {
        BesedaListFragment fragment = new BesedaListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PostListFragmentPresenterImpl(this);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_beseda_list, container, false);
        infiniteProgressBar = (ProgressBar) view.findViewById(R.id.infiniteProgress);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        emptyView = (TextView) view.findViewById(R.id.emptyView);
        recyclerView.setOnScrollListener(new InfiniteScrollListener(layoutManager, infiniteManager) {
            @Override
            public void onLoadMore() {
                loadMoreItems();
            }
        });
        return view;
    }

    @Override
    public void refreshData() {
        presenter.refreshData();
    }

    @Override
    public void showProgress(boolean isProgress) {
        if (isProgress) {
           refreshLayout.setRefreshing(true);
        }else {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showInfiniteProgress(boolean isProgress) {
        if (isProgress) {
            infiniteProgressBar.setVisibility(View.VISIBLE);
        }else {
            infiniteProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setItems(List<Beseda> result , int scrollPosition) {
        recyclerView.setAdapter(new BesedaRecyclerViewAdapter(getContext(), result));
        recyclerView.getAdapter().notifyDataSetChanged();
        if (scrollPosition < recyclerView.getAdapter().getItemCount())
        recyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void loadMoreItems() {
            presenter.loadMore();
    }

    @Override
    public void setInfiniteEnable(boolean b) {
        infiniteManager.setCanInfinite(b);
    }

    @Override
    public void showEmptyView(boolean b) {
        if (b){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(view,errorMessage, Snackbar.LENGTH_LONG).show();
    }

}
