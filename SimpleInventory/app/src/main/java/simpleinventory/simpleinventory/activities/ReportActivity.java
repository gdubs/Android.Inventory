package simpleinventory.simpleinventory.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import simpleinventory.simpleinventory.R;
import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.domain.Remark;
import simpleinventory.simpleinventory.domain.Report;
import simpleinventory.simpleinventory.fragments.ReportFragment;
import simpleinventory.simpleinventory.interfaces.SimpleInventoryClicklistener;
import simpleinventory.simpleinventory.repositories.ReportRepository;
import simpleinventory.simpleinventory.utilities.Constants;

/**
 * Created by gwulfwud on 8/5/2016.
 * list reports that has activities.
 */
public class ReportActivity  extends AppCompatActivity {

    FragmentManager mFM;
    Context mContext;
    List<Report> mReports = Collections.EMPTY_LIST;

    ReportRepository _reportRepository;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        mContext = this;

        mReports = _reportRepository.getAll();

        mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ReportFragment reportFragment = ReportFragment.newInstance(this, mReports);
        ft.replace(R.id.frame_layout_activity_report, reportFragment, Constants.REPORTFRAGMENT);
        ft.commit();
    }

}
