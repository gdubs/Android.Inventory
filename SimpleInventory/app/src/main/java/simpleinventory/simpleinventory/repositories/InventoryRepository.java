package simpleinventory.simpleinventory.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import simpleinventory.simpleinventory.database.DbConstants;
import simpleinventory.simpleinventory.database.DbHelper;
import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.domain.Remark;

/**
 * Created by gdubs on 6/28/2016.
 */
public class InventoryRepository implements IRepository<Inventory> {

    DbHelper db;
    private static final String selectAllQuery = "i." + DbConstants.INVENTORY_COLUMN_ID + " AS i" + DbConstants.INVENTORY_COLUMN_ID +
            ",i." + DbConstants.INVENTORY_COLUMN_DESCRIPTION + " AS i" + DbConstants.INVENTORY_COLUMN_DESCRIPTION +
            ",i." + DbConstants.INVENTORY_COLUMN_TAG_NUMBER + " AS i" + DbConstants.INVENTORY_COLUMN_TAG_NUMBER +
            ",i." + DbConstants.INVENTORY_COLUMN_UNIT_VALUE + " AS i" + DbConstants.INVENTORY_COLUMN_UNIT_VALUE +
            ",i." + DbConstants.INVENTORY_COLUMN_QUANTITY + " AS i" + DbConstants.INVENTORY_COLUMN_QUANTITY +
            ",i." + DbConstants.INVENTORY_COLUMN_TOTAL_VALUE + " AS i" + DbConstants.INVENTORY_COLUMN_TOTAL_VALUE +
            ",i." + DbConstants.INVENTORY_COLUMN_IMAGE_FILE_NAME + " AS i" + DbConstants.INVENTORY_COLUMN_IMAGE_FILE_NAME +
            ",i." + DbConstants.INVENTORY_COLUMN_REMARK_ID + " AS i" + DbConstants.INVENTORY_COLUMN_REMARK_ID +
            ",r." + DbConstants.REMARK_COLUMN_NAME + " AS r" + DbConstants.REMARK_COLUMN_NAME;



    public InventoryRepository(Context context){
        if(db == null)
            db = new DbHelper(context);
    }

    @Override
    public Integer add(Inventory item) {
        ContentValues contentValues = db.setContentValuesInventory(item);

        int id = db.add(DbConstants.INVENTORY_TABLE_NAME, contentValues);
        db.getWritableDatabase().close();

        return id;
    }

    @Override
    public boolean update(Inventory item) {
        ContentValues contentValues = db.setContentValuesInventory(item);

        StringBuilder whereClause = new StringBuilder();
        whereClause.append(DbConstants.INVENTORY_COLUMN_ID + " = ? ");

        ArrayList<String> args = new ArrayList<String>();
        args.add(item.Id.toString());

        //http://stackoverflow.com/a/8082757
        int rowsUpdated = db.update(DbConstants.INVENTORY_TABLE_NAME, contentValues, whereClause.toString(), args.toArray(new String[args.size()]));

        return (rowsUpdated > 0) ? true : false;
    }

    @Override
    public boolean remove(Inventory item) {

        return true;
    }

    @Override
    public List<Inventory> getAll() {
        List<Inventory> inventories = new ArrayList<>();

        String whereClause = "";
        String[] whereVal = new String [0];

        Cursor res = db.getReadableDatabase().rawQuery("Select " + selectAllQuery + " FROM " +
                DbConstants.INVENTORY_TABLE_NAME + " i LEFT JOIN " +
                DbConstants.REMARK_TABLE_NAME + " r on i." + DbConstants.INVENTORY_COLUMN_REMARK_ID + "= r." + DbConstants.REMARK_COLUMN_ID +
                whereClause, whereVal);

        if(res.moveToFirst()){
            do{
                Inventory item = getInventoryValue(res);
                inventories.add(item);
            }while (res.moveToNext());
        }


        return inventories;
    }

    @Override
    public List<Inventory> getById(Object Id) {
        List<Inventory> inventories = new ArrayList<>();
        int id = (Integer) Id;

        Cursor res = db.getById(DbConstants.INVENTORY_TABLE_NAME, DbConstants.INVENTORY_COLUMN_ID, id);

        if(res.moveToFirst()){
            do{
                /*Inventory item = new Inventory();
                item.Id = res.getInt(res.getColumnIndex(DbConstants.INVENTORY_COLUMN_ID));
                item.TagId = res.getString(res.getColumnIndex(DbConstants.INVENTORY_COLUMN_TAG_NUMBER));
                item.Description = res.getString(res.getColumnIndex(DbConstants.INVENTORY_COLUMN_DESCRIPTION));
                item.Quantity = res.getInt(res.getColumnIndex(DbConstants.INVENTORY_COLUMN_QUANTITY));
                item.UnitValue = res.getDouble(res.getColumnIndex(DbConstants.INVENTORY_COLUMN_UNIT_VALUE));
                item.TotalValue = res.getDouble(res.getColumnIndex(DbConstants.INVENTORY_COLUMN_TOTAL_VALUE));
                item.ImageFileName = res.getString(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_IMAGE_FILE_NAME));
                item.RemarkId = res.getInt(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_REMARK_ID));

                Remark remark = new Remark();
                remark.Id = res.getInt(res.getColumnIndex("remark" + DbConstants.REMARK_COLUMN_ID));
                remark.Name = res.getString(res.getColumnIndex("remark" + DbConstants.REMARK_COLUMN_NAME));

                item.Remark = remark;*/
                Inventory item = getInventoryValue(res);
                inventories.add(item);
            }while (res.moveToNext());
        }

        res.close();
        db.getReadableDatabase().close();

        return inventories;
    }

    public Inventory getInventoryValue(Cursor res){
        Inventory item = new Inventory();
        item.Id = res.getInt(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_ID));
        item.Description = res.getString(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_DESCRIPTION));
        item.UnitValue = res.getDouble(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_UNIT_VALUE));
        item.Quantity = res.getInt(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_QUANTITY));
        item.TotalValue = res.getDouble(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_TOTAL_VALUE));
        item.TagId = res.getString(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_TAG_NUMBER));
        item.ImageFileName = res.getString(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_IMAGE_FILE_NAME));
        item.RemarkId = res.getInt(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_REMARK_ID));

        Remark remark = new Remark();
        remark.Id = res.getInt(res.getColumnIndex("i" + DbConstants.INVENTORY_COLUMN_REMARK_ID));
        remark.Name = res.getString(res.getColumnIndex("r" + DbConstants.REMARK_COLUMN_NAME));

        item.Remark = remark;

        return item;
    }
}
