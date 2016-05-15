package com.gilazovdeveloper.vkbesedas.view;

import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;

import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public interface BesedaListFragmentView {
     void refreshData();
     void showProgress(boolean isProgress);
     void showInfiniteProgress(boolean isProgress);
     void setItems(List<Beseda> result, int position);
     void loadMoreItems();
     void setInfiniteEnable(boolean b);
     void showEmptyView(boolean b);
     void showErrorMessage(String errorMessage);

}
