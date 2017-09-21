package simpleinventory.simpleinventory.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import simpleinventory.simpleinventory.R;

/**
 * Created by gdubs on 6/27/2016.
 */
public class InventoryItemFragment extends Fragment {
    public InventoryItemFragment newInstance(){
        InventoryItemFragment fragment = new InventoryItemFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_inventory_item, container, false);

        return view;
    }
}
