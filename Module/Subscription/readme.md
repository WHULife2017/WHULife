# 自主订阅模块说明
## 1.偏好设置

### 1.1 用户偏好设定说明

用户在首次进入该推送模块的时候，由于系统中没有存储用户的喜好设置文件，需要第一次设定用户的喜好，以方便我们后期可以按照用户所设置的喜好进行相关信息的推送任务。

### 1.2 偏好设定实现

在该界面中提供 CheckBox 组件，将我们先前设定好的模块供用户进行选择，当用户点击提交之后，如果用户没有勾选任何选项，那我们弹出警示框告诉用户还未勾选任何选项；如果用户完成选择并顺利提交，将使用 Android Studio 中提供的 SharedPreference 数据存储，以 .xml 的格式将用户的勾选信息以键值对 <String,Boolean> 的形式进行存储，以便后续使用。

### 1.3 偏好检查

用户只有第一回进入该模块的时候会进行自动进入偏好设置，后面我们会在进入该模块的时候先进行检查用户的偏好设定，如果用户的偏好设定不为空的话，那我们就不再让用户进行设置，而是直接进入相关偏好的推送板块。

### 1.4 偏好重置

SharedPreference 是以 MODE-PRIVATE 模式进行数据存储的，如果用户要进行偏好的重置，那我们就直接将新设置的部分覆盖掉原来的文件即可，界面和逻辑都和首次设定使用一致。

### 1.5 部分代码实现

- 用户偏好设置代码实现

```
/**
*处理用户的偏好设置 如果没选择则让用户进行重选或者不想选了就返回到上一级页面
* */
protected void SavePreferenceToSP(){
        InitCheckBox();
        /*初始化所有的checkbox组件 进行绑定操作*/
        /*flag确保至少有一个选项框被选择了*/
        boolean flag=false;
        for(CheckBox checkBox:checkBoxList){
            flag|=checkBox.isChecked();
            if(flag==true)
                break;
        }
        if(flag){
            SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
            editor.putString("Status","edited");
            //方便后续进行判断有无设置完成
            int i=0;
            for(CheckBox checkBox:checkBoxList){
                editor.putBoolean(attrbute[i++],checkBox.isChecked());
            }
            editor.apply();
            //editor.commit();由于并发性原因被弃用的提交用法
            Toast.makeText(this,"你已经设置好了你的喜好，快来看看吧",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, GetNewsActivity.class);
            startActivity(intent);
        }/*否则一个选择框都没有选 我们弹出一个消息窗让其重选或退出*/
        else {
            AlertDialog.Builder dialog=new AlertDialog.Builder(PreferenceActivity.this);
            dialog.setTitle("错误提醒");
            dialog.setMessage("你没有选择任何选项就提交了哦");
            dialog.setCancelable(false);
            dialog.setPositiveButton("重新选择",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog,int which){

                }
            });
            dialog.setNegativeButton("返回上页", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }
    }
```

- 用户首次进入该模块检查

```
/**
     * 判断用户是否已经设置了自己的喜好信息，如果没设置的话则紧喜好设置，否则跳转进推送主页
     * */
    protected boolean IsPreferenceEmpty(){
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        /*其实理论上如果还没有设置的话应该没有该文件 但也是返回true 为空*/
        if(pref.getString("Status","None").equals("edited"))
            return false;
        else
            return true;
    }
}
```

---------------------

## 2. 相关消息推送展示模块

### 2.1 消息推送展示模块说明

该模块可以根据用户之前的偏好设定进行新闻、通知等的推送，由以小标题框块展示新闻简介的链接形式供用户进行选择，当用户点击相关的推送之后就会跳转到内嵌的浏览器 WebView 组件在我们的APP内部进行网页的阅读。

### 2.2 部分代码实现

```

```

---------------

## 3. 活动备忘录生成模块

### 3.1 活动备忘录生成模块说明

该模块供用户在自己偏好设置内的相关活动设置备忘录，我们事先会根据用户的偏好将相关活动的摘要、时间以及地点信息作为选项整理到该页面，用户可以通过勾选并提交的方式对自己感兴趣的活动进行设置提醒，我们随后会按照用户的设置进行相关提醒。

### 3.2 活动备忘录生成模块设计

在该模块中，我们使用了 AndroidX 提供的组件 RecyclerView，通过重新编写 Adapter 适配器，将备忘录项的相关信息以 TextView 嵌入，将备忘录勾选情况以 CheckBox 组件嵌入，并通过重新编写相应监听事件以及使用 SharedPreference 进行存储，实现了备忘录的生成。

### 3.3 部分实现代码

- Adapter重写

```
/**
 * 备忘录生成模块的RecyclerView的Adapter
 */
public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {
    private List<Check> myCheck;
    public final HashMap<Integer,Boolean> map=new HashMap<>();
    static class ViewHolder extends RecyclerView.ViewHolder{
        View checklistview;
        CheckBox Selection;
        TextView Num,Event,Time,Place;
        public ViewHolder(View view){
            super(view);
            checklistview=view;
            Selection=(CheckBox)view.findViewById(R.id.checkbox_checkboxlist_check);
            Num=(TextView)view.findViewById(R.id.txtview_checkboxlist_num);
            Event=(TextView)view.findViewById(R.id.textview_event);
            Time=(TextView)view.findViewById(R.id.textview_time);
            Place=(TextView)view.findViewById(R.id.textview_place);
        }
    }


    public CheckBoxAdapter(List<Check> Checklist){
        myCheck=Checklist;
        //初始化时默认全为false
        for(int i=0;i<myCheck.size();i++){
            map.put(i,false);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewtype){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.checkboxlist_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Log.d("POSITION","position");
        final Check check=myCheck.get(position);
        holder.Num.setText(String.valueOf(position));
        holder.Event.setText(check.getEvent());
        holder.Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(v.getContext());
                dialog.setTitle("详情");
                dialog.setMessage(check.getDetails());
                dialog.setCancelable(false);
                dialog.setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
        holder.Time.setText(check.getTime());
        holder.Place.setText(check.getPlace());
        holder.Selection.setChecked(map.get(position));
        holder.Selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("POSITION",String.valueOf(position));
                map.put(position,!map.get(position));
                notifyDataSetChanged();
            }
        });
        //holder.Selection.setChecked(ischecked[position]);
    }
    @Override
    public int getItemCount(){
        return myCheck.size();
    }

    public void SelectAll(){
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for(Map.Entry<Integer,Boolean>entry:entries){
            if(entry.getValue()==false)
                entry.setValue(true);
        }
        notifyDataSetChanged();
    }
    public void ResetAll(){
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for(Map.Entry<Integer,Boolean>entry:entries){
            if(entry.getValue()==true)
                entry.setValue(false);
        }
        notifyDataSetChanged();
    }
}
```

- 用户新加备忘录记录

```
/**
     * Adapter初始化所需的数据是服务器传来的
     * 两者应该在同一个线程里
     * 需要将适配器的设置与处理服务器返回数据放一起处理
     * @param response
     */
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final List<MMessage> msgList = parseJSON(response);
                Log.d("INFOMITION",msgList.toString());
                for(MMessage mMessage:msgList){
                    Check check=new Check(mMessage);
                    checkList.add(check);
                }
                for(Check check:checkList){
                    Log.d("INFOxxxxxxxxxxxxx",check.getEvent());
                }

                RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleview_checkbox);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(NoticeActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                final CheckBoxAdapter adapter=new CheckBoxAdapter(checkList);
                recyclerView.setAdapter(adapter);
                Button button1=(Button)findViewById(R.id.button_NoticeCommit);
                Button button2=(Button)findViewById(R.id.button_NoticeSelectAll);
                Button button3=(Button)findViewById(R.id.button_NoticeReset);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean flag=false;
                        for (int i=0;i<checkList.size();i++){
                            flag|=adapter.map.get(i);
                            if(flag==true)
                                break;;
                        }
                        /*此意味着用户一个都没有选择*/
                        if(!flag){
                            AlertDialog.Builder dialog=new AlertDialog.Builder(NoticeActivity.this);
                            dialog.setTitle("错误提醒");
                            dialog.setMessage("你没有选择任何备忘事项就提交了哦");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("重新选择",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog,int which){

                                }
                            });
                            dialog.setNegativeButton("返回上页", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.show();
                        }
                        else{
                            SharedPreferences.Editor editor=getSharedPreferences("notice",MODE_PRIVATE).edit();
                            editor.putString("status","edited");
                            int i=0;//前面使用了位置按序标号存储勾选状态 这里需要存储真正的ID号码
                            for(Check check:checkList)
                                editor.putBoolean(check.getNum(),adapter.map.get(i++));
                            editor.apply();
                            Toast.makeText(NoticeActivity.this,"备忘录已经为您设置完成",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.SelectAll();
                    }
                });
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.ResetAll();
                    }
                });
                //TextView textView = findViewById(R.id.msg_text);
                //.setText(msgList.toString());

                //MsgAdapter adapter = new MsgAdapter(msgList);
                //recyclerView.setAdapter(adapter);
            }
        });
    }
```

