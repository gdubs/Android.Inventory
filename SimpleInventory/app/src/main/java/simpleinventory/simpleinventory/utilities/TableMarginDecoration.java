package simpleinventory.simpleinventory.utilities;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import simpleinventory.simpleinventory.R;

/**
 * Created by gdubs on 6/27/2016.
 */
public class TableMarginDecoration extends RecyclerView.ItemDecoration{
    private int margin;

    public TableMarginDecoration(Context context){
        margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(margin, margin, margin, margin);
    }
}
