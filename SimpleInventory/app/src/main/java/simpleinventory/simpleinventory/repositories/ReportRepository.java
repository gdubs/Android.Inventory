package simpleinventory.simpleinventory.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import simpleinventory.simpleinventory.database.DbConstants;
import simpleinventory.simpleinventory.database.DbHelper;
import simpleinventory.simpleinventory.domain.Remark;
import simpleinventory.simpleinventory.domain.Report;

/**
 * Created by gwulfwud on 8/9/2016.
 */
public class ReportRepository implements IRepository<Report> {

    DbHelper db;

    public static final String selectAllQuery = "r." + DbConstants.REPORT_COLUMN_ID + " AS r" + DbConstants.REPORT_COLUMN_ID +
            ",r." + DbConstants.REPORT_COLUMN_NAME + " AS r" + DbConstants.REPORT_COLUMN_NAME;

            //",r." + DbConstants.REPORT_COLUMN_CREATEDAT + " AS r" + DbConstants.REPORT_COLUMN_CREATEDAT;

    public ReportRepository(Context context){
        if(db == null)
            db = new DbHelper(context);
    }

    @Override
    public Integer add(Report item) {
        ContentValues contentValues = db.setContentValuesReports(item);

        int id = db.add(DbConstants.REPORT_TABLE_NAME, contentValues);
        db.getWritableDatabase().close();

        return id;
    }

    @Override
    public boolean update(Report item) {
        return false;
    }

    @Override
    public boolean remove(Report item) {
        return false;
    }

    @Override
    public List<Report> getAll() {
        List<Report> reports = new ArrayList<>();

        String whereClause = "";
        String[] whereVal = new String [0];

        Cursor res = db.getReadableDatabase().rawQuery("Select " + selectAllQuery + " from " +
                DbConstants.REMARK_TABLE_NAME + " r " +
                whereClause, whereVal);

        if(res.moveToFirst()){
            do {
                Report item = getReportValue(res);
                reports.add(item);
            }while (res.moveToNext());
        }

        return reports;
    }

    @Override
    public List<Report> getById(Object Id) {
        return null;
    }

    public Report getReportValue(Cursor res){
        Report item = new Report();
        item.Id = res.getInt(res.getColumnIndex("r" + DbConstants.REPORT_COLUMN_ID));
        item.Name = res.getString(res.getColumnIndex("r" + DbConstants.REPORT_COLUMN_NAME));

        return item;
    }
}
