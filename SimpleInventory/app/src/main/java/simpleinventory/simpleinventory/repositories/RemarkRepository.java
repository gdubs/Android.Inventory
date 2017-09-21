package simpleinventory.simpleinventory.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import simpleinventory.simpleinventory.database.DbConstants;
import simpleinventory.simpleinventory.database.DbHelper;
import simpleinventory.simpleinventory.domain.Remark;

/**
 * Created by gwulfwud on 8/3/2016.
 */
public class RemarkRepository implements IRepository<Remark> {

    DbHelper db;

    public static final String selectAllQuery = "r." + DbConstants.REMARK_COLUMN_ID + " AS r" + DbConstants.REMARK_COLUMN_ID +
            ",r." + DbConstants.REMARK_COLUMN_NAME + " AS r" + DbConstants.REMARK_COLUMN_NAME;

    public RemarkRepository(Context context){
        if(db == null)
            db = new DbHelper(context);
    }

    @Override
    public Integer add(Remark item) {
        ContentValues contentValues = db.setContentValuesRemarks(item);

        int id = db.add(DbConstants.REMARK_TABLE_NAME, contentValues);
        db.getWritableDatabase().close();

        return id;
    }

    @Override
    public boolean update(Remark item) {
        return false;
    }

    @Override
    public boolean remove(Remark item) {
        return false;
    }

    @Override
    public List<Remark> getAll() {
        List<Remark> remarks = new ArrayList<>();

        String whereClause = "";
        String[] whereVal = new String [0];

        Cursor res = db.getReadableDatabase().rawQuery("Select " + selectAllQuery + " from " +
                DbConstants.REMARK_TABLE_NAME + " r " +
            whereClause, whereVal);

        if(res.moveToFirst()){
            do {
                Remark item = getRemarksValue(res);
                remarks.add(item);
            }while (res.moveToNext());
        }

        return remarks;
    }

    @Override
    public List<Remark> getById(Object Id) {
        return null;
    }

    public Remark getRemarksValue(Cursor res){
        Remark item = new Remark();
        item.Id = res.getInt(res.getColumnIndex("r" + DbConstants.REMARK_COLUMN_ID));
        item.Name = res.getString(res.getColumnIndex("r" + DbConstants.REMARK_COLUMN_NAME));
        return item;
    }
}
