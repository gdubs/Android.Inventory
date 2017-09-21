package simpleinventory.simpleinventory.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import simpleinventory.simpleinventory.R;
import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.interfaces.SimpleInventoryClicklistener;

/**
 * Created by gdubs on 6/21/2016.
 */
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>{

    List<Inventory> aInventories = Collections.emptyList();
    Context aContext;
    LayoutInflater aInflater;
    SimpleInventoryClicklistener clicklistener;

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private final View header;

    public InventoryAdapter(Context context, List<Inventory> data, View header){
        this.aInflater = LayoutInflater.from(context);
        this.aInventories = data;
        this.aContext = context;
        this.header = header;
    }

    public void setClickListener(SimpleInventoryClicklistener clickListener){
        this.clicklistener = clickListener;
    }

    @Override
    public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_VIEW_TYPE_HEADER){
            return new InventoryViewHolder(header);
        }
        View view = aInflater.inflate(R.layout.recycler_inventory_row, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {
        if(isHeader(position))
            return;

        Inventory currInventory = aInventories.get(position - 1);

        holder.vhTagNumber.setText(currInventory.TagId);
        holder.vhQuantity.setText(String.valueOf(currInventory.Quantity));
        holder.vhDescription.setText(currInventory.Description);
        holder.vhUnitValue.setText(String.valueOf(currInventory.UnitValue));
        holder.vhTotalValue.setText(String.valueOf(currInventory.TotalValue));
        /* remove if you decide to click the row instead of cell
        holder.vhTotalValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aContext, "pak sa loob", Toast.LENGTH_LONG);
            }
        });**/
        if(currInventory.Remark != null) {
            holder.vhRemarks.setText(currInventory.Remark.Name);
        }

        if(currInventory.ImageFileName != null)
            holder.vhFileName.setText(currInventory.ImageFileName);
    }

    public boolean isHeader (int position){
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {

        return aInventories.size() + 1;
    }

    class InventoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recycler_inventory_row_tag_number) TextView vhTagNumber;
        @BindView(R.id.recycler_inventory_row_quantity)TextView vhQuantity;
        @BindView(R.id.recycler_inventory_row_description)TextView vhDescription;
        @BindView(R.id.recycler_inventory_row_unit_value)TextView vhUnitValue;
        @BindView(R.id.recycler_inventory_row_total_value)TextView vhTotalValue;
        @BindView(R.id.recycler_inventory_row_remarks)TextView vhRemarks;
        @BindView(R.id.recycler_inventory_row_filename) TextView vhFileName;

        public InventoryViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            //vhDescription = (TextView) itemView.findViewById();
        }
        @Override
        public void onClick(View v) {
            if(clicklistener != null){
                clicklistener.onSimpleInventoryItemClicked(v, getAdapterPosition() - 1);    // because header
            }
        }
    }
}
