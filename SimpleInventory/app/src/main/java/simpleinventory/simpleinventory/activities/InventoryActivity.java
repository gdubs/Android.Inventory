package simpleinventory.simpleinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import simpleinventory.simpleinventory.R;
import simpleinventory.simpleinventory.database.DbHelper;
import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.domain.Remark;
import simpleinventory.simpleinventory.domain.Report;
import simpleinventory.simpleinventory.fragments.InventoryFragment;
import simpleinventory.simpleinventory.fragments.NewInventoryItemFragment;
import simpleinventory.simpleinventory.interfaces.FragmentActivityCommunicator;
import simpleinventory.simpleinventory.interfaces.SimpleInventoryClicklistener;
import simpleinventory.simpleinventory.repositories.IRepository;
import simpleinventory.simpleinventory.repositories.InventoryRepository;
import simpleinventory.simpleinventory.repositories.RemarkRepository;
import simpleinventory.simpleinventory.utilities.Constants;
import simpleinventory.simpleinventory.utilities.LayoutUtil;

/**
 * Created by gdubs on 6/24/2016.
 */
public class InventoryActivity extends AppCompatActivity implements SimpleInventoryClicklistener {

    @BindView(R.id.tool_bar_inventory_activity) Toolbar mToolbar;
    //@BindView(R.id.tool_bar_btn_menu_more) ImageButton mBtnMore;
    @BindView(R.id.fab_inventory_activity)FloatingActionButton mFab;
    @BindView(R.id.coordinator_inventory_activity) CoordinatorLayout mCoordinatorLayout;

    FragmentManager mFM;
    Context mContext;
    IRepository inventoryRepository;
    IRepository remarkRepository;
    List<Inventory> mInventory = Collections.EMPTY_LIST;
    List<Remark> mRemarks = Collections.EMPTY_LIST;
    Report mCurrentReport;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showFloatingButton();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ButterKnife.bind(this);

        mContext = this;

        if(inventoryRepository == null)
            inventoryRepository = new InventoryRepository(mContext);

        if(remarkRepository == null)
            remarkRepository = new RemarkRepository(mContext);


        // SHOW HOME/BACK BUTTON
        if(mFM == null) {
            mFM = getSupportFragmentManager();
            mFM.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    } else {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    }
                }
            });
        }
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mInventory = inventoryRepository.getAll();
        mRemarks = remarkRepository.getAll();
        // temp, pass bundle
        mCurrentReport = new Report();
        mCurrentReport.Name = "Test Report Not Saved To Database";
        mCurrentReport.Id = 1;

        mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        InventoryFragment inventoryFragment = InventoryFragment.newInstance(this, (InventoryRepository) inventoryRepository, mInventory);
        ft.replace(R.id.frame_layout_activity_inventory, inventoryFragment, Constants.INVENTORYFRAGMENT);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_inventory_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_export_excel:
                showSnackBarMessage("Export Excel");
                generateCsvFile(mCurrentReport);
                break;
            case R.id.menu_report_settings:
                showSnackBarMessage("Report Settings");
                break;
        }
        return true;
    }

    @OnClick(R.id.fab_inventory_activity)
    public void newItemDialog(){
        onFragmentToActivityGetDialogItemClicked();
        hideFloatingButton();
    }

    @Override
    public void onSimpleInventoryItemClicked(View view, int position) {
        //Toast.makeText(this, "Clicked from adapter???? ", Toast.LENGTH_SHORT).show();
        // re open the inventory fragment

        if (position == -1)
            return;

        Inventory item = mInventory.get(position);
        NewInventoryItemFragment fragment = NewInventoryItemFragment.newInstance(mContext, item, mRemarks, mCurrentReport);
        FragmentTransaction ft = mFM.beginTransaction();
        ft.add(R.id.frame_layout_activity_inventory, fragment, Constants.NEWINVENTORYITEMFRAGMENT);
        ft.addToBackStack(null);    // para san to??
        ft.commit();
        hideFloatingButton();
    }

    @Override
    public void onFragmentToActivityGetDialogItemClicked() {
        if(mFM == null) {
            mFM = getSupportFragmentManager();
        }

        NewInventoryItemFragment fragment = NewInventoryItemFragment.newInstance(mContext, null, mRemarks, mCurrentReport);
        FragmentTransaction ft = mFM.beginTransaction();
        ft.add(R.id.frame_layout_activity_inventory, fragment, Constants.NEWINVENTORYITEMFRAGMENT);
        ft.addToBackStack(null);    // para san to??
        ft.commit();
        hideFloatingButton();
    }

    @Override
    public void onFragmentToActivityAddItemClicked(Inventory inventory) {

        int id = inventoryRepository.add(inventory);

        if(id == -1){
            showSnackBarMessage("Error Adding new item");
        }else {
            InventoryFragment fragment = (InventoryFragment) mFM.findFragmentByTag(Constants.INVENTORYFRAGMENT);
            fragment.refreshFighterList(inventory);
            mInventory.add(inventory);  // hacky.. needs to update only one and update the rest.
            this.onBackPressed();
        }
    }

    @Override
    public void onFragmentToActivityUpdateItemClicked(Inventory inventory) {
        boolean success = inventoryRepository.update(inventory);
        if(success){
            InventoryFragment fragment = (InventoryFragment) mFM.findFragmentByTag(Constants.INVENTORYFRAGMENT);
            fragment.refreshFighterList(inventory);
            this.onBackPressed();
        }
        else
            // show snackbar instead ... this is temp
            Toast.makeText(mContext, "Error on update", Toast.LENGTH_SHORT);
    }

    @Override
    public int onFragmentToActivityAddNewRemarkClicked(Remark remark) {
        int id = remarkRepository.add(remark);
        return id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showSnackBarMessage(String message) {
        if(mCoordinatorLayout != null)
            LayoutUtil.displayWarningClientMessage(mContext, mCoordinatorLayout, message).show();
        else
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public void showFloatingButton(){
        mFab.show();
    }

    public void hideFloatingButton(){
        mFab.hide();
    }

    // move this to a util file
    public void generateCsvFile(Report report){
        String directoryReportName = report.Name.replaceAll(" ", "").trim();
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/SimpleInventory/" + directoryReportName;
        File exportDir = new File(directory);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, directoryReportName + ".csv");

        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            List<String[]> header = new ArrayList<String[]>();
            header.add(new String[]{"Tag Number", "Quantity", "Description", "Unit Value", "Total Value", "Remarks" , "File Name"});
            csvWrite.writeAll(header);

            List<String[]> data = new ArrayList<String[]>();

            for(int i = 0; i < mInventory.size(); i++) {
                Inventory item = mInventory.get(i);
                data.add(new String[]{
                        item.TagId.toString(),
                        item.Quantity.toString(),
                        item.Description,
                        item.UnitValue.toString(),
                        item.TotalValue.toString(),
                        item.Remark.Name, item.ImageFileName
                });
            }

            csvWrite.writeAll(data);
            csvWrite.close();
        }
        catch(Exception ex)
        {
            showSnackBarMessage("Error");
        }
    }
}
