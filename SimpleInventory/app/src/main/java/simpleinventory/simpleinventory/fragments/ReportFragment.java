package simpleinventory.simpleinventory.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import simpleinventory.simpleinventory.R;
import simpleinventory.simpleinventory.domain.Report;
import simpleinventory.simpleinventory.interfaces.SimpleInventoryClicklistener;

/**
 * Created by gwulfwud on 8/5/2016.
 */
public class ReportFragment extends Fragment {

    private Unbinder unbinder;

    List<Report> mReports;
    SimpleInventoryClicklistener mClickListener;
    Context mContext;

    public static ReportFragment newInstance(Context context, List<Report> reports){
        ReportFragment fragment = new ReportFragment();
        fragment.mReports = reports;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }
}
