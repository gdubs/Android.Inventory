package simpleinventory.simpleinventory.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import simpleinventory.simpleinventory.R;
import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.fragments.InventoryFragment;
import simpleinventory.simpleinventory.repositories.IRepository;
import simpleinventory.simpleinventory.repositories.InventoryRepository;
import simpleinventory.simpleinventory.utilities.Constants;

/**
 * Created by gdubs on 6/20/2016.
 */
public class MainActivity extends AppCompatActivity {

    FragmentManager mFM;
    IRepository _inventoryRepository;
    List<Inventory> mInventory = Collections.EMPTY_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(_inventoryRepository == null)
            _inventoryRepository = new InventoryRepository(this);

        mInventory = _inventoryRepository.getAll();
        mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        InventoryFragment inventoryFragment = InventoryFragment.newInstance(this, (InventoryRepository) _inventoryRepository, mInventory);
        ft.replace(R.id.frame_layout_activity_main, inventoryFragment, Constants.INVENTORYFRAGMENT);
        ft.commit();
    }
}