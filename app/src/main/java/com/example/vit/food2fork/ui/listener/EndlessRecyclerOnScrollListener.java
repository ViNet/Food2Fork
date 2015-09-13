package com.example.vit.food2fork.ui.listener;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Vit on 12.09.2015.
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener{

    private OnLoadMoreListener listener;
    private GridLayoutManager layoutManager;

    public EndlessRecyclerOnScrollListener(GridLayoutManager layoutManager, Fragment listener) {
        this.layoutManager = layoutManager;
        try {
            this.listener = (OnLoadMoreListener) listener;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(listener.toString()
                    + " must implement OnLoadMoreListener");
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();


        // if item can't be completely visible
        if(lastVisible == RecyclerView.NO_POSITION){
            lastVisible = layoutManager.findLastVisibleItemPosition();
        }

        if ( lastVisible >= (totalItemCount - 3)) {
            //loading = true;
            // load next page
            listener.onLoadMore();
        }
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}
