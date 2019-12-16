package com.example.WhuLife.course;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WhuLife.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class load extends AppCompatActivity {
    private Button mBtnLogin;
    private Button Change_Img;
    private EditText mEtUserName;
    private EditText randcode;
    private EditText pwd;
    public String imp_cookie;
    public String sess;
    public String csrf;
    private Map<String, List<Cookie>> cookieStore = new HashMap<>();
    private ImageView photo;
    private ImageView img_identy;
    public  String cookie;
    private MySqliteHelper dbhelper;
    private int flag=0;
    String Cnos[]=new String[20];String Cname[]=new String[20];String type[]=new String[20];String dept[]=new String[20];String teacher[]=new String[20];String sco[]=new String[20];String length[]=new String[20];String location[]=new String[20];String day[]=new String [20];int start_week[]=new int[20];int end_week[]=new int[20];int start_time[]=new int[20];int end_time[]=new int[20];double Latitude[]=new double[20];double Longtitude[]=new double[20];

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case 1:
                    //通过message，拿到字节数组

                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    //通过imageview，设置图片
                    photo.setImageBitmap(bitmap);

                    break;
                //当加载网络失败执行的逻辑代码
                case 2:
                    Toast.makeText(load.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        mBtnLogin=(Button)findViewById(R.id.tijiao);
        Change_Img=(Button)findViewById(R.id.btn_changeImg);
        randcode=(EditText)findViewById(R.id.randcode);
        mEtUserName=(EditText) findViewById(R.id.id_ed);
        pwd=(EditText) findViewById(R.id.pwd_ed);
        photo=findViewById(R.id.photo);


        Change_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeImage();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginServer();
                if(flag==1)
                    Get_courses();
                if(flag!=2)
                    Toast.makeText(load.this,"登录失败，请重试",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void Get_courses(){
        Request request2 = new Request.Builder()
                //    .url("http://210.42.121.241/"+csrf+"&action=normalLsn&year=2019&term=1&state=")
                .url("http://210.42.121.241/"+csrf.substring(0,26)+csrf.substring(45,91)+"&action=normalLsn&year=2019&term=1&state=")
                // .url("http://210.42.121.241/stu/stu_course_parent.jsp")
                .addHeader("Cookie",cookie)//imp_cookie
                //   .header("Host","210.42.121.241")
                // .header("Referer","Referer: http://210.42.121.241/stu/stu_course_parent.jsp")
                //  .header("Referer","http://210.42.121.241/"+csrf+"&action=normalLsn&year=2019&term=1&state=")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                //.post(formBody2)
                .build();
        OkHttpClient mOkHttpClient2 = new OkHttpClient();
        mOkHttpClient2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response2) throws IOException {
                byte[] b = response2.body().bytes();
                String temp = new String(b, "GB2312");
                System.out.println("successful get"+temp);
                //Log.d("info_headers", "header " + temp);
                System.out.println("successful get"+response2.headers().toString());
                getViewStateValue(temp);

                       /* Intent intent=new Intent(load.this,EditTextActivity.class);
                        Bundle bundle = new Bundle() ;
                        bundle.putStringArray("DATA0", Cnos) ;
                        bundle.putStringArray("DATA1", Cname) ;
                        bundle.putStringArray("DATA2",type) ;
                        bundle.putStringArray("DATA3",dept) ;
                        bundle.putStringArray("DATA4",teacher) ;
                        bundle.putStringArray("DATA5",sco) ;
                        bundle.putStringArray("DATA6",length) ;
                        bundle.putStringArray("DATA7",location) ;
                        intent.putExtras(bundle) ;
                        startActivity(intent);*/
            }
        });

    }
    private void LoginServer() {
        RequestBody formBody1 = new FormBody.Builder()
                .add("id", mEtUserName.getText().toString())//mEtUserName.getText().toString()
                .add("pwd",md5(pwd.getText().toString()))//md5 pwd.getText().toString()
                .add("xdvfb",randcode.getText().toString())//在xml中定义文本框
                .build();
        System.out.println("code:"+randcode.getText().toString());
        System.out.println("code:"+cookie);
        Request request = new Request.Builder()
                .url("http://210.42.121.241/servlet/Login")
                /*      .header("Accept", "text/html, application/xhtml+xml, image/jxr, /")                            //
                      .header("Accept-Encoding", "gzip, deflate")
                       .header("Accept-Language", "zh-CN")
                       .header("Cache-Control","no-cache")
                       .header("Connection", "Keep-Alive")
                       .header("Content-Length","64")*/
                .addHeader("Cookie", cookie)
                .header("Host", "210.42.121.241")
                .header("Referer", "http://210.42.121.241/stu/stu_index.jsp")//servlet/Login
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .post(formBody1)
                .build();

        OkHttpClient mOkHttpClient = new OkHttpClient();//.newBuilder().cookieJar(mCookieJar).build()

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("failure");
            }
            Headers headers1;String session1;
            @Override
            public void onResponse(Call call, Response response1) throws IOException {
                byte[] b = response1.body().bytes();
                String temp = new String(b, "GB2312");
                if(response1.isSuccessful()){
                    Log.i("info_call2success",temp);
                    System.out.println(temp);
                    //getViewStateValue(temp);
                    //  Intent intent = new Intent(EditTextActivity.this, load.class);
                    //  startActivity(intent);
                    headers1= response1.headers();
                    List<String> cookies=headers1.values("Set-Cookie");
                    session1=cookies.get(0);
                    imp_cookie=cookies.toString();

                }

                System.out.println("headers1 is"+headers1.toString());
                get_crsftoken(temp);

            }
        });
    }
    private  void get_crsftoken(String html) {
        if (null != html) {
            Document document= Jsoup.parse(html);
            //Elements e = document.getElementsByTag("script").eq(6);
            Elements elements = document.getElementsByTag("script");
            Iterator it = elements.iterator();
            while(it.hasNext()) {
                Element element = (Element) it.next();
                // System.out.println("www"+element);
                String[] data = element.data().toString().split("var");
                for(int i=0;i<data.length;i++)
                    if(data[i].contains("url=\"/servlet/Svlt_QueryStuLsn?action"))
                    {    csrf=data[i];
                        csrf=csrf.substring(6,97);
                        System.out.println(csrf);
                       flag=1;
                    }
            }
        }
    }
    private void getViewStateValue(String html){
        if(null!=html){
            flag=2;
            Document doc= Jsoup.parse(html);
            Elements trs = doc.select("table").select("tr");
            System.out.println(trs.size());
            for(int i = 0;i<trs.size();i++){
                Elements tds = trs.get(i).select("td");
                for(int j = 0;j<tds.size();j++){
                    String text = tds.get(j).text();
                  //  System.out.println("ddddd"+i+" "+j+" "+text);
                    if(i<11){
                        switch (j){
                            case 0:Cnos[i-1]=text;System.out.println(i+"Cons:"+Cnos[i-1]);break;
                            case 1:Cname[i-1]=text;break;
                            case 2:type[i-1]=text;break;
                            case 4:dept[i-1]=text;break;
                            case 5:teacher[i-1]=text;break;
                            case 7:sco[i-1]=text;break;
                            case 8:length[i-1]=text;break;
                            case 9:
                                Pattern p = Pattern.compile("[:,;周节-]");
                                String[] r=new String[10];
                                for(int index=0;index<10;index++)
                                    r[index]="";
                                r =p.split(text);
                                Matcher m=p.matcher(text);
                                day[i-1]=r[0];
                                start_week[i-1]=Integer.parseInt(r[1]);
                                end_week[i-1]=Integer.parseInt(r[2]);
                                start_time[i-1]=Integer.parseInt(r[6]);
                                end_time[i-1]=Integer.parseInt(r[7]);
                                if(r.length>=10)
                                {//目前是这样，整合之后我觉得可以把所有需要的地点都用数据库存储？目前还没有用数据库存地点
                                    location[i-1]=r[9]+r[10]+"-"+r[11];
                                    if( (r[9]+r[10]).equals("3区附3")){
                                        Latitude[i-1]=114.36003731951895;//附三的坐标
                                        Longtitude[i-1]=30.52670197941972;
                                    }

                                }
                                else{
                                    Latitude[i-1]=114.35745598430768;//计院的坐标
                                    Longtitude[i-1]=30.53842642740077;
                                }
                        }
                    }

                }
            }
            if(trs.size()!=0) {
                dbhelper = new MySqliteHelper(this);
                SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
                dbhelper.onCreate(sqLiteDatabase);
                ContentValues values = new ContentValues();
                //更新课表，先删除，再插入
                //sqLiteDatabase.delete("courses_info",null,null);
                sqLiteDatabase.execSQL("DELETE FROM courses_info");
                for (int t = 0; t < trs.size() - 1; t++) {
                    System.out.print(Cnos[t] + " " + Cname[t] + " " + type[t] + " " + dept[t] + " " + teacher[t] + " " + sco[t] + " " + length[t] + " " +start_time[t]+" "+end_time[t]+" "+ location[t] + Latitude[t]+Longtitude[t]+"\n");
                   if(day[t]=="Fir")
                       day[t]="Fri";
                    values.put("Cno", Cnos[t]);
                    values.put("Cname", Cname[t]);
                    values.put("Type", type[t]);
                    values.put("Dept", dept[t]);
                    values.put("Teacher", teacher[t]);
                    values.put("Sco", sco[t]);
                    values.put("Length", length[t]);
                    values.put("location", location[t]);
                    values.put("day", day[t]);
                    values.put("start_week", start_week[t]);
                    values.put("end_week", end_week[t]);
                    values.put("start_time", start_time[t]);
                    values.put("end_time",end_time[t]);
                    values.put("Latitude", Latitude[t]);
                    values.put("Longtitude", Longtitude[t]);
                    sqLiteDatabase.insert("courses_info", null, values);
                }
                Intent intent=new Intent(load.this,EditTextActivity.class);
                startActivity(intent);
            }
        }
    }
    //发送请求获取验证码照片
    private void ChangeImage() {

        Request request = new Request.Builder()
                .url("http://210.42.121.241/servlet/GenImg")
                //.header("Host","210.42.121.241")
                //   .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        okhttp3.OkHttpClient  okHttpClient=new okhttp3.OkHttpClient();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] byte_image =  response.body().bytes();

                //   Handler handler=new Handler();

                //通过handler更新UI
                Message message = handler.obtainMessage();
                message.obj = byte_image;
                message.what = 1;
                Log.i("info_handler","handler");
                handler.sendMessage(message);
                //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                Headers headers = response.headers();
                Log.d("info_headers", "header " + headers);
                //  System.out.println("header " + headers);
                List<String> cookies = headers.values("Set-Cookie");
                String session = cookies.get(0);
                Log.d("info_cookies", "onResponse-size: " + cookies);
                //   System.out.println("header " + cookies);
                cookie= session.substring(0, session.indexOf(";"));
                Log.i("info_s", "session is  :" + cookie);
                System.out.println("s: " + cookie);

            }
        });
    }
    public static String md5(String string) {/*md5加密，用于密码加密*/
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String httpPost(String url,Map<String,String> map,String cookie) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //遍历生成参数
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //添加参数
                con.data(entry.getKey(), entry.getValue());
            }
        }
        //插入cookie（头文件形式）
        con.header("Cookie", cookie);
        Document doc = con.post();
        System.out.println(doc);
        return doc.toString();
    }



}
