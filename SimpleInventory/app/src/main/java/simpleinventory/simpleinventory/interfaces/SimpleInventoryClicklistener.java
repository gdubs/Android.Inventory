package simpleinventory.simpleinventory.interfaces;

import android.view.View;

import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.domain.Remark;

/**
 * Created by gdubs on 6/24/2016.
 */
public interface SimpleInventoryClicklistener {
    public void onSimpleInventoryItemClicked(View view, int position);
    public void onFragmentToActivityGetDialogItemClicked();
    public void onFragmentToActivityAddItemClicked(Inventory inventory);
    public void onFragmentToActivityUpdateItemClicked(Inventory inventory);
    public int onFragmentToActivityAddNewRemarkClicked(Remark remark);
}
