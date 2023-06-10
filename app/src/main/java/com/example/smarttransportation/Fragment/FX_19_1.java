package com.example.smarttransportation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Been.SJFX;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FX_19_1 extends Fragment {
    private PieChart pc;
    private TextView tv1;
    private TextView tv2;
    private int not=0;
    private int own=0;


    List<SJFX> list=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.piechart_15,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pc=view.findViewById(R.id.pc);
        tv1=view.findViewById(R.id.tv1);
        tv2=view.findViewById(R.id.tv2);
        tv1.setText("有违章");
        tv2.setText("无违章");
        getData();
    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","User1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<SJFX>>(){}.getType());

                    for (int i = 0; i < list.size(); i++) {

                        for (int j = 0; j < list.size(); j++) {
                            if(list.get(j).getPaddr().equals("")){
                                list.remove(j);
                                not++;
                            }
                        }

                    }

                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < list.size(); j++) {
                            if(list.get(i).getCarnumber().equals(list.get(j).getCarnumber())  && i!=j){
                                list.remove(j);
                            }
                        }
                    }

                    own=list.size();

                    setPC();



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setPC(){
        pc.getDescription().setEnabled(false);
        pc.getLegend().setEnabled(false);

        pc.setExtraTopOffset(30);
        pc.setExtraBottomOffset(30);

        Float f1= Float.valueOf(not);
        Float f2= Float.valueOf(own);
        //无违章百分比
        Float f=(f1/(f2+f1))*100;

        List<PieEntry>entries=new ArrayList<>();
        entries.add(new PieEntry(f));
        entries.add(new PieEntry(100-f));

        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setColors(getResources().getColor(R.color.pc_hong),getResources().getColor(R.color.pc_lan));
        //自定义描述
        final String strs[] = {"无违章：","有违章："};

        pieDataSet.setValueFormatter(new ValueFormatter() {
            private int indd = -1;
            @Override
            public String getPieLabel(float value, PieEntry pieEntry) {
                indd ++;
                if(indd >= strs.length){
                    indd = 0;
                }
                return strs[indd] + value + "%";
            }
        });

        //设置饼块之间的间隔
        pieDataSet.setSliceSpace(3f);
        //设置点击某一饼快多出来的距离
        pieDataSet.setSelectionShift(15f);
        //设置连接线显示在外面
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //设置连接线距离饼图的距离，为百分数
        pieDataSet.setValueLinePart1OffsetPercentage(100f);
        //定义连接线的长度
        pieDataSet.setValueLinePart1Length(1.5f);
        pieDataSet.setValueTextSize(20f);

        //不绘制空洞
        pc.setDrawHoleEnabled(false);
        //不可旋转
        pc.setRotationEnabled(false);
        PieData pieData = new PieData(pieDataSet);
        //设置数据
        pc.setData(pieData);
        pc.invalidate();

    }
}
