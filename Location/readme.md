# 位置接口
## 使用方法
1.定义一个OurLocation类，并实例化，需要传入当前活动上下文  
`public OurLocation myLocation = new OurLocation(MainActivity.this);`   
2.定义一个地图管理类AMapLocationClient，并实例化  
```Java
public AMapLocationClient mapLocationClient = null;
mapLocationClient = new AMapLocationClient(this.getApplicationContext());
```
3.初始化定位   
`myLocation.initLocation(mapLocationClient);`  
5.开始定位  
`myLocation.startLocation();`  
6.结束定位  
`myLocation.destroyLocation();`  
7.暂停定位  
`myLocation.stopLocation();`  

## 拓展方法
1.设置目的定位点（到时候应该是我们预先导入）  
`myLocation.setLocation(30.539110423443365,114.3586011007117);`  
2.判断是否接近预置点，返回布尔值  
`myLocation.isNear()`  

## 实例代码
```Java
public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button button;

    public OurLocation myLocation = new OurLocation(MainActivity.this);
    public AMapLocationClient mapLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        mapLocationClient = new AMapLocationClient(this.getApplicationContext());
        myLocation.initLocation(mapLocationClient);
        myLocation.setLocation(30.539110423443365,114.3586011007117);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        myLocation.destroyLocation();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button:
                myLocation.startLocation();
                break;
        }
    }


}
```
