package simpleinventory.simpleinventory.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import simpleinventory.simpleinventory.R;
import simpleinventory.simpleinventory.activities.InventoryActivity;
import simpleinventory.simpleinventory.domain.Inventory;
import simpleinventory.simpleinventory.domain.Remark;
import simpleinventory.simpleinventory.domain.Report;
import simpleinventory.simpleinventory.interfaces.SimpleInventoryClicklistener;
import simpleinventory.simpleinventory.utilities.Constants;

/**
 * Created by gdubs on 6/24/2016.
 */
public class NewInventoryItemFragment extends Fragment {


    Context mContext;
    String mDirectory;
    public static int count = 0;

    @BindView(R.id.description_new_inventory_item_fragment) EditText mDescription;
    @BindView(R.id.tag_number_new_inventory_item_fragment) EditText mTagNumber;
    @BindView(R.id.quantity_new_inventory_item_fragment) EditText mQuantity;
    @BindView(R.id.unit_value_new_inventory_item_fragment) EditText mUnitValue;
    @BindView(R.id.description_ti_new_inventory_item_fragment) TextInputLayout mDescriptionTI;
    @BindView(R.id.tag_number_ti_new_inventory_item_fragment) TextInputLayout mTagNumberTI;
    @BindView(R.id.quantity_ti_new_inventory_item_fragment) TextInputLayout mQuantityTI;
    @BindView(R.id.unit_value_ti_new_inventory_item_fragment) TextInputLayout mUnitValueTI;
    @BindView(R.id.save_new_inventory_item_fragment) Button mSave;
    @BindView(R.id.camera_new_inventory_item_fragment) Button mCamera;
    @BindView(R.id.image_new_inventory_item_fragment) ImageView mImageView;
    //@BindView(R.id.header_new_inventory_item_fragment) TextView mHeader;
    @BindView(R.id.spinner_remarks_new_inventory_item_fragment) Spinner mSpinnerRemarks;
    @BindView(R.id.btn_new_remark_new_inventory_item_fragment) ImageButton mNewRemark;


    private Inventory mCurrentItem;
    private File mSelectedImageFile;
    private Uri mOutputFileUri;
    private Bitmap mBitmapPhotoTaken;
    private String mTempFileName;
    private Remark mSelectedRemark;
    private List<Remark> mRemarks;
    private ArrayAdapter<Remark> mRemarksAdapter;
    private Report mCurrentReport;

    SimpleInventoryClicklistener mClickListener;

    public static NewInventoryItemFragment newInstance(Context context, Inventory item, List<Remark> remarks, Report report){
        NewInventoryItemFragment fragment = new NewInventoryItemFragment();
        fragment.mContext = context;

        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.INVENTORY_CURRENT, item);
            fragment.setArguments(bundle);
        }

        fragment.mRemarks = remarks;
        fragment.mCurrentReport = report;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_new_inventory_item, container, false);

        ButterKnife.bind(this, view);


        Bundle bundle = getArguments();
        if(bundle != null) {
            mCurrentItem = bundle.getParcelable(Constants.INVENTORY_CURRENT);
        }

        mClickListener = (SimpleInventoryClicklistener) getContext();

        String directoryReportName = mCurrentReport.Name.replaceAll(" ", "").trim();

        mDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/SimpleInventory/" + directoryReportName;

//        if(mCurrentItem != null)
//            mHeader.setText("Edit Item");
//        else
//            mHeader.setText("New Item");

        File newdir = new File(mDirectory);
        if(!newdir.isDirectory())
            newdir.mkdirs();

        if(mCurrentItem != null){
            fillInventoryDetails();
        }

        if(mRemarksAdapter == null){
            mRemarksAdapter = new ArrayAdapter<Remark>(mContext, android.R.layout.simple_spinner_item, mRemarks);
            mRemarksAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        }

        mSpinnerRemarks.setAdapter(mRemarksAdapter);

        return view;
    }

    @OnClick(R.id.camera_new_inventory_item_fragment)
    public void captureImage(){
        Calendar cal = Calendar.getInstance();
        int uniqueCode = (int)cal.getTimeInMillis();
        mTempFileName = "UnMapped_SI_IMG" + uniqueCode + ".jpg";

        mSelectedImageFile = new File(mDirectory, mTempFileName);

        if(mSelectedImageFile.exists())
            mSelectedImageFile.delete();

        try{
            mSelectedImageFile.createNewFile();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        mOutputFileUri = Uri.fromFile(mSelectedImageFile);

        //initUriImageFiles(mTempFileName);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
        startActivityForResult(cameraIntent, Constants.TAKE_PHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.TAKE_PHOTO_CODE){
            if(resultCode == getActivity().RESULT_OK) {

                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                try {
                    mBitmapPhotoTaken = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mOutputFileUri);
                    //mBitmapPhotoTaken = (Bitmap) data.getExtras().get("data");
                    mImageView.setImageBitmap(mBitmapPhotoTaken);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                // do you even need to do this??? you can probably just not create a new file before the camera opens....
                if(mSelectedImageFile != null){
                    if(mSelectedImageFile.exists()){
                        mSelectedImageFile.delete();
                    }
                }
            }
        }
    }

    @OnClick(R.id.btn_new_remark_new_inventory_item_fragment)
    public void addNewRemark(View view){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View remarkView = inflater.inflate(R.layout.dialog_new_remark, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(remarkView);

        final EditText remark = (EditText) remarkView.findViewById(R.id.edit_remark_new_remark_dialog);

        builder.setCancelable(true)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newRemarkName = remark.getText().toString();
                            Remark newRemark = new Remark();
                            newRemark.Name = newRemarkName;
                            newRemark.ReportId = mCurrentReport.Id;

                            int id = mClickListener.onFragmentToActivityAddNewRemarkClicked(newRemark);
                            if(id != -1){
                                newRemark.Id = id;
                                mRemarksAdapter.add(newRemark);
                                mRemarksAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                                mSpinnerRemarks.setSelection(mRemarksAdapter.getPosition(newRemark));
                                //Toast.makeText(mContext, "Remark Added", Toast.LENGTH_SHORT);
                                ((InventoryActivity)mContext).showSnackBarMessage("Remark Added");
                            }else{
                                //Toast.makeText(mContext, "Error adding remark", Toast.LENGTH_SHORT);
                                ((InventoryActivity)mContext).showSnackBarMessage("Error adding remark");
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

        builder.show();
    }

    @OnClick(R.id.save_new_inventory_item_fragment)
    public void saveNewInventoryItem(View view){
        Inventory inventory = (mCurrentItem != null) ? mCurrentItem : new Inventory();

        boolean valid = true;
        if(mDescription.getText().toString().trim().equals("")){
            mDescriptionTI.setErrorEnabled(true);
            mDescriptionTI.setError("Description Required");
            valid = false;
        }else{
            mDescriptionTI.setErrorEnabled(false);
        }

        if(mTagNumber.getText().toString().trim().equals("")){
            mTagNumberTI.setErrorEnabled(true);
            mTagNumberTI.setError("Tag Number Required");
            valid = false;
        }else{
            mTagNumberTI.setErrorEnabled(false);
        }

        if(mQuantity.getText().toString().trim().equals("")){
            mQuantityTI.setErrorEnabled(true);
            mQuantityTI.setError("Quantity Required");
            valid = false;
        }else{
            mQuantityTI.setErrorEnabled(false);
        }

        if(mUnitValue.getText().toString().trim().equals("")){
            mUnitValueTI.setErrorEnabled(true);
            mUnitValueTI.setError("Unit Value Required");
            valid = false;
        }else{
            mUnitValueTI.setErrorEnabled(false);
        }



        if(valid){
            inventory.Description = mDescription.getText().toString();
            inventory.TagId = mTagNumber.getText().toString();
            inventory.Quantity = Integer.parseInt(mQuantity.getText().toString());
            inventory.UnitValue = Double.parseDouble(mUnitValue.getText().toString());
            inventory.TotalValue = inventory.UnitValue * inventory.Quantity;
            int remarkPos = mSpinnerRemarks.getSelectedItemPosition();
            inventory.Remark = mRemarks.get(remarkPos);
            inventory.RemarkId = mRemarks.get(remarkPos).Id;
            inventory.ReportId = mCurrentReport.Id;

            inventory.ImageFileName ="SI_" + inventory.TagId + ".jpg";
            boolean renamed = renameFile(mSelectedImageFile, inventory.ImageFileName);

            /*try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                mBitmapPhotoTaken.compress(Bitmap.CompressFormat.JPEG, 100, os);
                byte[] bitmapdata = os.toByteArray();

                File f = new File(mDirectory, "SI_" + inventory.TagId + ".jpg");
                if (!f.exists()) {
                    f.createNewFile();
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }*/
            if(renamed) {
                if(inventory.Id != null && inventory.Id != 0)
                    mClickListener.onFragmentToActivityUpdateItemClicked(inventory);
                else
                    mClickListener.onFragmentToActivityAddItemClicked(inventory);
            }
            else {
                Toast.makeText(mContext, "Renaming file issue", Toast.LENGTH_SHORT);
            }
        }
    }

    public boolean renameFile(File oldFile, String newFileName){
        File dir = new File(mDirectory);
        if(dir.exists()){
            if(oldFile != null && oldFile.exists()){
                try {
                    File newFile = new File(dir, newFileName);
                    if(!newFile.exists()) {
                        oldFile.renameTo(newFile);
                        oldFile.delete();
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        // just pass through even if without picture???
        return true;
    }

    public void fillInventoryDetails(){
        mTagNumber.setText(mCurrentItem.TagId);
        mDescription.setText(mCurrentItem.Description);
        mUnitValue.setText(mCurrentItem.UnitValue.toString());
        mQuantity.setText(mCurrentItem.Quantity.toString());


        try {
            //initUriImageFiles(mCurrentItem.ImageFileName);
            File fileDirectory = new File(mDirectory);
            String stringAbsPath = new File(mDirectory, mCurrentItem.ImageFileName).getAbsolutePath();
            if(fileDirectory.exists()) {
                mBitmapPhotoTaken = BitmapFactory.decodeFile(stringAbsPath);
                mImageView.setImageBitmap(mBitmapPhotoTaken);
            }
        } catch (Resources.NotFoundException e){
            e.printStackTrace();
        } catch(OutOfMemoryError e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initUriImageFiles(String fileName){
        mSelectedImageFile = new File(mDirectory, fileName);

        if(mSelectedImageFile.exists())
            mSelectedImageFile.delete();

        try{
            mSelectedImageFile.createNewFile();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        mOutputFileUri = Uri.fromFile(mSelectedImageFile);
    }
}
