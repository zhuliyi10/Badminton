package com.leory.badminton.news.mvp.ui.widget.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leory.commonlib.utils.ScreenUtils;

/**
 * Describe : 为了解决最后一项显示不全
 * Author : leory
 * Date : 2019-06-06
 */
public class MatchDateItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;

    public MatchDateItemDecoration(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        int childCount = parent.getAdapter().getItemCount();
        if (pos == childCount - 1) {
            outRect.set(0, 0, 0, ScreenUtils.dp2px(context, 20));
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}
