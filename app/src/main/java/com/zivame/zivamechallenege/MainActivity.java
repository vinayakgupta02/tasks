package com.zivame.zivamechallenege;

import android.util.Log;
import android.os.Bundle;
import java.util.HashMap;
import android.view.View;
import java.util.ArrayList;
import android.view.Gravity;
import android.widget.Toast;
import android.graphics.Color;
import android.content.Context;
import android.widget.TextView;
import android.util.SparseArray;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.CheckedTextView;
import android.support.v7.widget.Toolbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.zivame.zivamechallenege.utils.AnimUtils;
import com.zivame.zivamechallenege.modal.ItemFeatures;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private DataFeederClass dataFeederClass;
    private HashMap<String,Integer> itemNameList;
    private static final String TAG = "MainActivity";
    private SparseArray<RelativeLayout> itemDescList;
    private SparseArray<CheckedTextView> itemNameViewList;
    private ArrayList<ItemFeatures.ItemDetails> itemDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemNameList = new HashMap<>();
        itemDescList = new SparseArray<>();
        itemNameViewList = new SparseArray<>();

        fetchDataFromFile();
        mainLayout = findViewById(R.id.item_parent);
    }

    /**
     * THIS WILL FETCH CONTENT EITHER FROM SERVER OR FROM LOCAL STORED FILE IN ASSETS
     * FOLDER BASED ON THE REQ_EVENT PASSED.
     *
     * HERE REQ_EVENT : REQ_ITEM_FEATURE => THIS WILL AS PER THE TASK READ FILE "Features.json"
     * FROM ASSETS FOLDER AND WILL RETURN ARRAYLIST OF ITEM_DETAILS TYPE IN THE FORM OF OBJECT.
     *
     * THIS METHOD OVERRIDES 2 OTHERS METHODS -
     * 1. onCustomObjectRequest() -> WILL DELIVER THE EXPECTED CUSTOM OBJECT
     * 2. onResponseError() -> WILL UPDATE USER OF SOME MISHAP IN DURING THE REQUEST.
     */
    private void fetchDataFromFile() {
        dataFeederClass = new DataFeederClass(
            MainActivity.this,
            AppConstants.REQ_ITEM_FEATURE,
            null,
            new AppCallbacks() {
                @Override
                public void onCustomObjectRequest(Object object) {
                    super.onCustomObjectRequest(object);
                    itemDetailsList = (ArrayList<ItemFeatures.ItemDetails>) object;
                    Log.i(TAG,"RESPONSE : " + itemDetailsList.size());
                    prepareLayout();
                    clearObjects();
                }

                @Override
                public void onResponseError(int RES_CODE) {
                    super.onResponseError(RES_CODE);
                    Log.i(TAG,"ERROR RESPONSE : " + RES_CODE);
                }
            }
        );

        dataFeederClass.execute();
    }

    /**
     * CLEARING THE OBJECTS OF DATA FEEDER CLASS.
     */
    private void clearObjects() {
        if(dataFeederClass!=null){
            dataFeederClass.cancel(true);
            dataFeederClass = null;
        }
    }

    /**
     * THIS METHODS IDENTIFIES THE NUMBER OF ROWS TO BE CREATED DYNAMICALLY.
     * WE HAVE FIXED '3' COLUMNS BUT ROWS REMAINS DYNAMIC, DIRECTLY DEPENDING UPON THE LIST SIZE.
     */
    private void prepareLayout() {
        int count = itemDetailsList.size();
        int row;
        int remainder = count %3;
        int quotient  = count /3;

        if(remainder==0)
            row = quotient;
        else
            row = quotient+1;
        Log.i(TAG,"COUNT AND ROW : " + count + " : " + row);
        createLayout(row);
    }

    /**
     *
     * @param rowNum EXPECTS INTEGER TYPE ROW NUMBER i.e. NUMBER OF ROWS TO BE CREATED
     * THIS METHOD IS THE HEART OF THE TASK.
     * HERE 2 LOOPS RUN TO CREATE
     * 1. LOOP 1 : NUMBER OF ROWS
     * 2. LOOP 2 : NUMBER OF ITEMS IN EACH ROW i.e. '3'
     * IN OUR CASE i.e. THE JSON FILE PROVIDED '3' ROWS AND '7' ITEMS ARE EXPECTED
     * TO SHOW IN THE GRID FORM.
     */
    private void createLayout(int rowNum){
        LayoutInflater li =  (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int itemCount=0;
        for(int rowCount=0;rowCount<rowNum;rowCount++) {
            LinearLayout rowParent = (LinearLayout) li.inflate(R.layout.row_parent, null);
            LinearLayout rowDescp = (LinearLayout) li.inflate(R.layout.row_desc, null);
            int rowItemCount =0;

            for (int i = 0; i < 3; i++) {
                View tempView = li.inflate(R.layout.layout_item_name, null);
                final LinearLayout linearNameMain = tempView.findViewById(R.id.item_name_parent);
                final CheckedTextView textView = linearNameMain.findViewById(R.id.item_name);
                final View nameDivider = linearNameMain.findViewById(R.id.name_divider);
                textView.setText(itemDetailsList.get(itemCount).getName());
                final int finalItemCount = itemCount;
                itemNameList.put(itemDetailsList.get(itemCount).getName(), rowCount);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = textView.getText().toString();
                        showViewWithDesc(itemNameList.get(name), finalItemCount, textView);
                    }
                });

                linearNameMain.setLayoutParams(new LinearLayout.LayoutParams(0, 80 ,1));
                itemNameViewList.put(itemCount,textView);
                rowParent.setWeightSum(3);
                rowParent.setGravity(Gravity.CENTER_VERTICAL);
                rowParent.addView(linearNameMain);

                rowItemCount++;itemCount++;
                if(rowItemCount==3 || itemCount>=itemDetailsList.size()){
                    nameDivider.setVisibility(View.GONE);
                    break;
                }
            }

            mainLayout.addView(rowParent);
            RelativeLayout descParent = rowDescp.findViewById(R.id.item_desc_parent);
            itemDescList.put(rowCount, descParent);
            mainLayout.addView(rowDescp);
        }
    }

    /**
     *
     * @param integer TO GET THE ITEM DESCRIPTION BOX OF THAT PARTICULAR ROW WHERE CLICKED ITEM EXISTS
     * @param finalRowItemCount TO GET THE OBJECT OF THAT PARTICULAR CLICKED ITEM FROM THE LIST WE
     *                          RECEIVED FROM JSON FILE.
     * @param textView TO UPDATE VIEW WITH FOLLOWING -
     *                 1. TEXT VIEW COLOR CHANGED TO PINK
     *                 2. SET MARK DRAWABLE DEPICTING THE ITEM IS SELECTED AT CURRENT
     */
    private void showViewWithDesc(Integer integer, int finalRowItemCount,CheckedTextView textView) {
        try {
            resetAllViews();


            final RelativeLayout layout = itemDescList.get(integer);
            TextView itemDesc = layout.findViewById(R.id.item_desc);
            itemDesc.setText(itemDetailsList.get(finalRowItemCount).getDescription());

            textView.setCheckMarkDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_selected));
            textView.setChecked(true);
            textView.setTextColor(Color.parseColor("#ea487f"));
            AnimUtils.slideOutFromTop(MainActivity.this, layout);

            layout.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetAllViews();
                }
            });

            layout.getChildAt(2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Learn More clicked", Toast.LENGTH_SHORT).show();
                }
            });

            layout.getChildAt(3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "See More clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * TO MAKE SURE ONLY ONE ITEM CAN BE SELECTED AT A TIME FOLLOWING LOOPS RUN TO ENSURE THE SAME -
     * 1. LOOP 1 : TO RESET PREVIOUSLY SELECTED ITEMS
     * 2. LOOP 2 : TO RESET PREVIOUSLY OPENED ITEM DESCRIPTION DIALOG BOX
     */
    private void resetAllViews() {
        for (int i = 0; i < itemNameViewList.size(); i++) {
            itemNameViewList.get(i).setChecked(false);
            itemNameViewList.get(i).setTextColor(Color.parseColor("#9c55e9"));
            itemNameViewList.get(i).setCheckMarkDrawable(null);
        }
        for (int j=0;j<itemDescList.size();j++)
            itemDescList.get(j).setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        itemDescList = null;
        itemNameList = null;
        itemNameViewList = null;
    }
}