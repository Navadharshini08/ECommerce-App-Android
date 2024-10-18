package com.hitesh_sahu.retailapp.domain.api;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.domain.mock.FakeWebServer;
import com.hitesh_sahu.retailapp.util.AppConstants;
import com.hitesh_sahu.retailapp.util.Utils;
import com.hitesh_sahu.retailapp.util.Utils.AnimationType;
import com.hitesh_sahu.retailapp.view.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.view.adapter.CategoryListAdapter;
import com.hitesh_sahu.retailapp.view.adapter.CategoryListAdapter.OnItemClickListener;
import com.hitesh_sahu.retailapp.view.fragment.ProductOverviewFragment;

/**
 * The Class ImageLoaderTask.
 */
public class ProductCategoryLoaderTask extends AsyncTask<String, Void, Void> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private Context context;
    private RecyclerView recyclerView;

    public ProductCategoryLoaderTask(RecyclerView listView, Context context) {

        this.recyclerView = listView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (context instanceof ECartHomeActivity) {
            ECartHomeActivity activity = (ECartHomeActivity) context;
            if (activity.getProgressBar() != null) {
                activity.getProgressBar().setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (context instanceof ECartHomeActivity) {
            ECartHomeActivity activity = (ECartHomeActivity) context;
            if (activity.getProgressBar() != null) {
                activity.getProgressBar().setVisibility(View.GONE);
            }
        }

        if (recyclerView != null) {
            CategoryListAdapter simpleRecyclerAdapter = new CategoryListAdapter(context);
            recyclerView.setAdapter(simpleRecyclerAdapter);

            simpleRecyclerAdapter.SetOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {
                    AppConstants.CURRENT_CATEGORY = position;

                    if (context instanceof ECartHomeActivity) {
                        Utils.switchFragmentWithAnimation(
                                R.id.frag_container,
                                new ProductOverviewFragment(),
                                (ECartHomeActivity) context,
                                null,
                                AnimationType.SLIDE_LEFT);
                    }
                }
            });
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            // Simulating network call or heavy operation
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FakeWebServer.getFakeWebServer().addCategory();

        return null;
    }
}
