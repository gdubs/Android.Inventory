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
import simpleinventory.simpleinventory.domain.Report;
import simpleinventory.simpleinventory.interfaces.SimpleInventoryClicklistener;

/**
 * Created by gwulfwud on 8/13/2016.
 */
public class ReportAdapter  extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    List<Report> aReports = Collections.emptyList();
    Context aContext;
    LayoutInflater aInflater;
    SimpleInventoryClicklistener clicklistener;

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.recycler_inventory_row, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        Report currentReport = aReports.get(position);

        holder.vhReportName.setText(currentReport.Name);
    }

    @Override
    public int getItemCount() {
        return aReports.size();
    }

    public void setClickListener(SimpleInventoryClicklistener clickListener){
        this.clicklistener = clickListener;
    }

    class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recycler_report_name) TextView vhReportName;

        public ReportViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
