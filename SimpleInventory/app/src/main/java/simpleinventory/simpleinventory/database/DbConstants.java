package simpleinventory.simpleinventory.database;

/**
 * Created by gdubs on 6/28/2016.
 */
public class DbConstants {
    public static final String DATABASE_NAME = "SimpleInventory.db";

    public static final String INVENTORY_TABLE_NAME;
    public static final String INVENTORY_COLUMN_ID;
    public static final String INVENTORY_COLUMN_TAG_NUMBER;
    public static final String INVENTORY_COLUMN_DESCRIPTION;
    public static final String INVENTORY_COLUMN_QUANTITY;
    public static final String INVENTORY_COLUMN_UNIT_VALUE;
    public static final String INVENTORY_COLUMN_TOTAL_VALUE;
    public static final String INVENTORY_COLUMN_REMARK_ID;
    public static final String INVENTORY_COLUMN_REPORT_ID;
    public static final String INVENTORY_COLUMN_REMARK;
    public static final String INVENTORY_COLUMN_IMAGE_FILE_NAME;

    public static final String REMARK_TABLE_NAME;
    public static final String REMARK_COLUMN_ID;
    public static final String REMARK_COLUMN_NAME;
    public static final String REMARK_COLUMN_REPORT_ID;


    public static final String REPORT_TABLE_NAME;
    public static final String REPORT_COLUMN_ID;
    public static final String REPORT_COLUMN_NAME;
    public static final String REPORT_COLUMN_CREATEDAT;

    static{
        INVENTORY_TABLE_NAME = "INVENTORY";
        INVENTORY_COLUMN_ID = "ID";
        INVENTORY_COLUMN_TAG_NUMBER = "TAG_NUMBER";
        INVENTORY_COLUMN_DESCRIPTION = "DESCRIPTION";
        INVENTORY_COLUMN_QUANTITY = "QUANTITY";
        INVENTORY_COLUMN_UNIT_VALUE = "UNIT_VALUE";
        INVENTORY_COLUMN_TOTAL_VALUE = "TOTAL_VALUE";
        INVENTORY_COLUMN_REMARK_ID = "REMARKID";
        INVENTORY_COLUMN_REPORT_ID = "REPORTID";
        INVENTORY_COLUMN_REMARK = "REMARK";
        INVENTORY_COLUMN_IMAGE_FILE_NAME = "IMAGEFILENAME";

        REMARK_TABLE_NAME = "REMARK";
        REMARK_COLUMN_ID = "ID";
        REMARK_COLUMN_NAME = "NAME";
        REMARK_COLUMN_REPORT_ID = "REPORTID";


        REPORT_TABLE_NAME = "REPORT";
        REPORT_COLUMN_ID = "ID";
        REPORT_COLUMN_NAME = "NAME";
        REPORT_COLUMN_CREATEDAT = "CREATEDAT";
    }
}
