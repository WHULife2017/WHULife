package com.example.WhuLife.subscription;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.WhuLife.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        holder.Num.setText(String.valueOf(position+1));
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
