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
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{
    private final int FOLDER_CODE = 1112;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter mAdapter;
    Button btn_upload;
    public static ArrayList<item> arr= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);


        ArrayList<item> items = new ArrayList();
        items.add(new item(R.color.green,"FFA170728103225026201810","1", "설비팀"));
        items.add(new item(R.color.black,"FFA170728103225026201810","2", "설비팀"));
        items.add(new item(R.color.green,"FFA170728103225026201810","3", "생산팀"));
        items.add(new item(R.color.black,"FFA170728103225026201810","4", "운영팀"));
        items.add(new item(R.color.green,"FFA170728103225026201810","5", "생산팀"));

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
        StringBuffer strBuffer = new StringBuffer();
        try{
            arr.clear();
            InputStream is = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line="";
            int count = 0;
            while((line=br.readLine())!=null){
                String[] sp = line.split("\t");
                arr.add(new item(R.color.green, sp[0],sp[1],sp[2]));
                strBuffer.append(line+"\n");
            }
            br.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
            Log.e("error:", "error");
            return "";
        }
        return strBuffer.toString();
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

            String str = ReadTextFile(stk.toString());
            Log.e("msg: ",str);
            //tv_txt.setText(str);
           // Log.e("path: ", stk.toString());
        }
    }
}
