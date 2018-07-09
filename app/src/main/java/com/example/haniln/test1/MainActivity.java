package com.example.haniln.test1;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.haniln.test1.DB.DBHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{
    private final int FOLDER_CODE = 1112;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter mAdapter;
    Button btn_upload, btn_download;
    ImageButton btn_setting;
    public static ArrayList<item> arr= new ArrayList<>();
    private ArrayList<item> get_item = new ArrayList<>();
    DBHelper dbHelper;
    static String content = "";
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/S_asset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);

        dbHelper = new DBHelper(getApplicationContext());
//        get_item = dbHelper.get_item();
//        Log.e("get_item: ", "" + get_item.size());


        btn_setting = findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.clear();
                mAdapter = new RecyclerViewAdapter(get_item);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content!=""){
                    final String fname =new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date()) + "asset.txt";
                    WriteTextFiles(foldername,fname, content);
                }
                else Toast.makeText(getApplicationContext(), "업로드를 먼저 해주세요.",Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(arr);
        mRecyclerView.setAdapter(mAdapter);

        btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile();
            }
        });

        // 파일 접근 권한 승인
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한이 거부되었습니다. 권한거부시 앱기능 일부분을 사용하실수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(MainActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new RecyclerViewAdapter(arr);
        mRecyclerView.setAdapter(mAdapter);
    }

    //폴더 위치 후 열기.
    private void getFile(){
        Uri uri = Uri.parse(Environment.getDataDirectory().getPath()); //처음 폴더 위치
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        i.setData(uri);
        i.setType("*/*"); //txt만 가져오게 하기.
        startActivityForResult(i, FOLDER_CODE);
    }

    //경로의 텍스트 파일읽기
    public String ReadTextFile(String path){
        StringBuilder st = new StringBuilder();
//        StringBuffer strBuffer = new StringBuffer();
        try{
            //초기화
            arr.clear();
            dbHelper.db_clear();
            InputStream is = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line="";
            int count = 0;
            while((line=br.readLine())!=null){
                String[] sp = line.split("\t");
//                Log.e("test: ", sp[0]);
//                Log.e("test2 : ", line);
                arr.add(new item(R.color.green, sp[0],sp[1],sp[2],sp[3],sp[4],sp[5],sp[6],sp[7],sp[8],sp[9], ""));
                st.append(line);
                st.append("\n");
            }
            for(int i=0; i<arr.size(); i++){
                dbHelper.insert(arr.get(i).img, arr.get(i).zcode, arr.get(i).zname, arr.get(i).zmodel,arr.get(i).zserial, arr.get(i).zmaker, arr.get(i).zday, arr.get(i).zdpt1, arr.get(i).zdpt2, arr.get(i).zdpt3, arr.get(i).zdpt4, arr.get(i).etc);
            }
            Log.e("db_size:", "" + dbHelper.get_item().size());
            br.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
            Log.e("error:", "error");
            return "";
        }
        return st.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            StringBuilder stk = new StringBuilder();
            String temp = Environment.getExternalStorageDirectory().getAbsolutePath();
            stk.append(temp);

            //여기서 result에 맞게 변형해서 집어넣어줘야 할 듯.
            String s = data.getData().getLastPathSegment();
            s = s.replace("primary:","");
            stk.append("/"+s);
            //Log.e("result: ", stk.toString());
            content = "";
            content = ReadTextFile(stk.toString());
            Log.e("msg: ",content);
        }
    }

    public void WriteTextFiles(String foname, String fname, String contents){
        try{
            File dir = new File(foname);
            if(!dir.exists()){
                dir.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(foname + "/" + fname, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(contents);
            bw.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
