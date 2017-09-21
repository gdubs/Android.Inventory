package simpleinventory.simpleinventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.domain.Remark;
import simpleinventory.simpleinventory.domain.Report;

/**
 * Created by gdubs on 6/28/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static final int DATABASE_VERSION = 1;
    private static final String SET_CASCADE_DELETE = "PRAGMA foreign_keys = ON;";

    public DbHelper(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    private static final String CREATE_TABLE_REMARK = "CREATE TABLE " + DbConstants.REMARK_TABLE_NAME + "(" +
                DbConstants.REMARK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                DbConstants.REMARK_COLUMN_NAME + " TEXT," +
                DbConstants.REMARK_COLUMN_REPORT_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + DbConstants.INVENTORY_COLUMN_REPORT_ID + ") REFERENCES " + DbConstants.REPORT_TABLE_NAME + "(" + DbConstants.REPORT_COLUMN_ID + ") ON DELETE SET NULL ON UPDATE CASCADE" +
            ")"
            ;

    private static final String CREATE_TABLE_INVENTORY = "CREATE TABLE " + DbConstants.INVENTORY_TABLE_NAME + "(" +
                DbConstants.INVENTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                DbConstants.INVENTORY_COLUMN_TAG_NUMBER + " TEXT," +
                DbConstants.INVENTORY_COLUMN_DESCRIPTION + " TEXT," +
                DbConstants.INVENTORY_COLUMN_QUANTITY + " INTEGER DEFAULT 0," +
                DbConstants.INVENTORY_COLUMN_UNIT_VALUE + " REAL," +
                DbConstants.INVENTORY_COLUMN_TOTAL_VALUE + " REAL," +
                DbConstants.INVENTORY_COLUMN_REMARK_ID + " INTEGER," +
                DbConstants.INVENTORY_COLUMN_REPORT_ID + " INTEGER," +
                DbConstants.INVENTORY_COLUMN_IMAGE_FILE_NAME + " TEXT," +
                "FOREIGN KEY(" + DbConstants.INVENTORY_COLUMN_REMARK_ID + ") REFERENCES " + DbConstants.REMARK_TABLE_NAME + "(" + DbConstants.REMARK_COLUMN_ID + ") ON DELETE SET NULL ON UPDATE CASCADE" +
                "FOREIGN KEY(" + DbConstants.INVENTORY_COLUMN_REPORT_ID + ") REFERENCES " + DbConstants.REPORT_TABLE_NAME + "(" + DbConstants.REPORT_COLUMN_ID + ") ON DELETE SET NULL ON UPDATE CASCADE" +
            ")";

    private static final String CREATE_TABLE_REPORT = "CREATE TABLE " + DbConstants.REPORT_TABLE_NAME + "(" +
            DbConstants.REPORT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            DbConstants.REPORT_COLUMN_NAME + " TEXT)"
            ;

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL(SET_CASCADE_DELETE);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL(CREATE_TABLE_REMARK);
        db.execSQL(CREATE_TABLE_INVENTORY);

        Remark rem = new Remark();
        rem.Name = "Test";
        ContentValues remContentValues = setContentValuesRemarks(rem);
        long id = db.insert(DbConstants.REMARK_TABLE_NAME, null, remContentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ContentValues setContentValuesRemarks(Remark remark){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.REMARK_COLUMN_ID, remark.Id);
        contentValues.put(DbConstants.REMARK_COLUMN_NAME, remark.Name);
        contentValues.put(DbConstants.REMARK_COLUMN_REPORT_ID, remark.ReportId);

        return contentValues;
    }

    public ContentValues setContentValuesInventory(Inventory inventory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.INVENTORY_COLUMN_ID, inventory.Id);
        contentValues.put(DbConstants.INVENTORY_COLUMN_DESCRIPTION, inventory.Description);
        contentValues.put(DbConstants.INVENTORY_COLUMN_QUANTITY, inventory.Quantity);
        contentValues.put(DbConstants.INVENTORY_COLUMN_TAG_NUMBER, inventory.TagId);
        contentValues.put(DbConstants.INVENTORY_COLUMN_UNIT_VALUE, inventory.UnitValue);
        contentValues.put(DbConstants.INVENTORY_COLUMN_TOTAL_VALUE, inventory.TotalValue);
        contentValues.put(DbConstants.INVENTORY_COLUMN_REMARK_ID, inventory.RemarkId);
        contentValues.put(DbConstants.INVENTORY_COLUMN_REPORT_ID, inventory.ReportId);
        contentValues.put(DbConstants.INVENTORY_COLUMN_IMAGE_FILE_NAME, inventory.ImageFileName);

        return contentValues;
    }

    public ContentValues setContentValuesReports(Report report){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.REPORT_COLUMN_ID, report.Id);
        contentValues.put(DbConstants.REPORT_COLUMN_NAME, report.Name);

        return contentValues;
    }

    public Cursor getById(String tableName, String columnName, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " +
                        columnName + "=?", new String[]{Integer.toString(id)}
        );
        //db.close();
        return res;
    }

    public Integer add(String tableName, ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();

        long Id = db.insert(tableName, null, contentValues);
        db.close();

        return (int) Id;
    }

    public Integer update(String tableName, ContentValues contentValues, String where, String[] args){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.update(tableName, contentValues, where, args);
        db.close();
        return rows;
    }
    public Integer remove(String tableName, String where, String args){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(tableName, where + " = ? ", new String[]{args});
        db.close();
        return rows;
    }
}
