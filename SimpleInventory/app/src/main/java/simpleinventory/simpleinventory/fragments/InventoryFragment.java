package simpleinventory.simpleinventory.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import simpleinventory.simpleinventory.R;
import simpleinventory.simpleinventory.adapters.InventoryAdapter;
import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.interfaces.SimpleInventoryClicklistener;
import simpleinventory.simpleinventory.repositories.InventoryRepository;
import simpleinventory.simpleinventory.utilities.TableMarginDecoration;

/**
 * Created by gdubs on 6/21/2016.
 */
public class InventoryFragment extends Fragment {

    List<Inventory> mInventories = Collections.emptyList();

    //@BindView(R.id.fab_fragment_inventory)FloatingActionButton mFab;

    private Unbinder unbinder;

    RecyclerView mRecyclerViewInventory;
    InventoryAdapter mInventoryAdapter;
    SimpleInventoryClicklistener mClickListener;

    InventoryRepository _inventoryRepository;
    Context mContext;

    public static InventoryFragment newInstance(Context context, InventoryRepository _inventoryRepository, List<Inventory> items){
        InventoryFragment fragment = new InventoryFragment();
        fragment.mContext = context;
        fragment._inventoryRepository = _inventoryRepository;
        fragment.mInventories = items;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        unbinder = ButterKnife.bind(this, view);

        mClickListener = (SimpleInventoryClicklistener) getContext();

        //mInventories = _inventoryRepository.getAll();
        // get mInventories
        /*Inventory i = new Inventory();
        i.Id = 1;
        i.Quantity = 4;
        i.Description = "Archer";
        i.Remark = new Remark();
        i.TagId = "12jkdjkfh";
        i.UnitValue = 22.00;
        i.TotalValue = 88.00;

        mInventories = new ArrayList<>();
        mInventories.add(i);*/

        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (3 - position % 3);
            }
        });
*/

        mRecyclerViewInventory = (RecyclerView) view.findViewById(R.id.recyclerview_fragment_inventory);
        mRecyclerViewInventory.addItemDecoration(new TableMarginDecoration(mContext));
        mRecyclerViewInventory.setHasFixedSize(true);
        mRecyclerViewInventory.setLayoutManager(new GridLayoutManager(mContext, 1));

        View header = LayoutInflater.from(mContext).inflate(R.layout.recycler_inventory_header, mRecyclerViewInventory, false);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Grid layout header", Toast.LENGTH_SHORT).show();
            }
        });

        mInventoryAdapter = new InventoryAdapter(mContext, mInventories, header);
        mInventoryAdapter.setClickListener(mClickListener);
        //mRecyclerViewInventory.setAdapter(new NumberedAdapter(40));
        mRecyclerViewInventory.setAdapter(mInventoryAdapter);

        return view;
    }

//    @OnClick(R.id.fab_fragment_inventory)
//    public void newItemDialog(){
//        mClickListener.onFragmentToActivityGetDialogItemClicked();
//    }

    public void refreshFighterList(Inventory inventory){
        if(inventory != null){
            mInventories.add(inventory);
        }
        mInventoryAdapter.notifyDataSetChanged();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
