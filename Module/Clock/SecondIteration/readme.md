# 时间接口

#### **使用条件：**

在res文件夹下新建raw文件夹，存入音乐（MP3）

#### **使用方法：**

（1）定义一个clockmanager类，并实例化，需要传入当前活动上下文

`clock=clockmanager.getInstance(this);`

（2）初始化函数，参数id区分不同的闹钟

`clock.createAlarmManager(0x101);`

（3）设置闹钟，参数为时间

`clock.setAlarm(2019, 11, 6, 10, 45, 00);`

（2）取消闹钟，参数为要取消的闹钟的id

`clock.cancelAlarm(0x101);</a></p>`

#### **实例代码**

```public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private clockmanager clock;

@Override

 protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        clock=clockmanager.getInstance(this);

        clock.createAlarmManager(0x101);

        Button button_1=findViewById(R.id.button_1);

        button_1.setOnClickListener(this);

        Button button_2=findViewById(R.id.button_2);

        button_2.setOnClickListener(this);

    }

    @Override

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_1:

                clock.setAlarm(2019, 11, 6, 10, 45, 00);

                 break;

            case R.id.button_2:

                clock.cancelAlarm(0x101);

                break;

      }

   }

}
```

