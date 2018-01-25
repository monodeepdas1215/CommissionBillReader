package com.monodeepdas112.commissionbillreader.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.monodeepdas112.commissionbillreader.Adapters.CommisionListAdapter;
import com.monodeepdas112.commissionbillreader.Utils.PathUtils;
import com.monodeepdas112.commissionbillreader.Utils.PdfParserAsync;
import com.monodeepdas112.commissionbillreader.R;
import com.monodeepdas112.commissionbillreader.Models.CommissionEntity;

import java.util.List;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener,PdfParserAsync.ParserResponse {

    private static final String TAG = "DashboardActivity";
    private static final int READ_EXTERNAL_DRIVE_PERMISSION_REQUEST_CODE = 1;
    private static final int FILE_SELECT_CODE = 0;

    FloatingActionButton floatingActionButton;
    Toolbar mToolbar;

    RecyclerView recyclerView;
    CommisionListAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    LinearLayout commissionList;
    RelativeLayout dashboard;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDashboardUI();
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (!checkPermission()){
                requestPermission();
            }
        }

        recyclerAdapter=null;
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }else return false;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(DashboardActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(DashboardActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_DRIVE_PERMISSION_REQUEST_CODE);
        }
    }
    private void setUpDashboardUI() {
        setContentView(R.layout.activity_dashboard);
        dashboard= (RelativeLayout) findViewById(R.id.dashboard);
        commissionList= (LinearLayout) findViewById(R.id.commission_list);
        dashboard.setVisibility(View.VISIBLE);
        commissionList.setVisibility(View.GONE);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Commission Bill Reader");

        floatingActionButton = (FloatingActionButton) findViewById(R.id.loadDoc);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loadDoc:
                loadDocument();
                break;
        }
    }

    private void loadDocument() {
        floatingActionButton.setEnabled(false);
        if(checkPermission())
            openFilePicker();
        else requestPermission();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(Intent.createChooser(intent, "Select the desired commission bill"),FILE_SELECT_CODE);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Please install a file manager",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String filePath = PathUtils.getPath(getApplicationContext(),uri);

                    Log.d(TAG,"Abs Path : "+filePath);
                    operateUponFile(filePath);
                }
                break;
        }
    }

    private void operateUponFile(String filePath) {
        setUpCommissionListUI();

        PdfParserAsync parser=new PdfParserAsync(getApplicationContext());
        parser.delegade=this;
        parser.execute(filePath);
    }

    private void setUpCommissionListUI() {
        getSupportActionBar().setTitle("Commission List");

        commissionList.setVisibility(View.VISIBLE);
        dashboard.setVisibility(View.GONE);

        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.GONE);

        relativeLayout= (RelativeLayout) findViewById(R.id.compoundProgress);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void populateRecyclerAdapter(List<CommissionEntity> entities) {
        relativeLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerAdapter=new CommisionListAdapter(entities,getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.popup_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_premium_asc:
                if (recyclerAdapter!=null)
                    recyclerAdapter.sortTheDataset(CommisionListAdapter.PREMIUM,CommisionListAdapter.ASCENDING);
                return true;
            case R.id.sort_premium_dsc:
                if (recyclerAdapter!=null)
                    recyclerAdapter.sortTheDataset(CommisionListAdapter.PREMIUM,CommisionListAdapter.DESCENDING);
                return true;
            case R.id.sort_com_asc:
                if (recyclerAdapter!=null)
                    recyclerAdapter.sortTheDataset(CommisionListAdapter.COMMISSION,CommisionListAdapter.ASCENDING);
                return true;
            case R.id.sort_com_dsc:
                if (recyclerAdapter!=null)
                    recyclerAdapter.sortTheDataset(CommisionListAdapter.COMMISSION,CommisionListAdapter.DESCENDING);
                return true;

            default:return true;
        }
    }
}
