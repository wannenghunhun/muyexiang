package com.framwork.main.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framwork.common.adapter.recycleview.SimpleRecycleParentListAdapter;
import com.framwork.common.adapter.recycleview.SimpleRecycleParentViewHolder;
import com.framwork.common.utils.LoadingUtil;
import com.framwork.common.utils.ResUtil;
import com.framwork.common.utils.ToastUtil;
import com.framwork.common.widget.OutDialog;
import com.framwork.main.event.EditEvent;
import com.framwork.main.GlobalConstants;
import com.framwork.main.R;
import com.framwork.main.bean.BaseBean;
import com.framwork.main.bean.EmployeesBean;
import com.framwork.main.http.GsonHttpCallback;
import com.framwork.main.http.RestClient;
import com.framwork.main.http.ResultBean;
import com.framwork.main.ui.activity.PersonEditActivity;
import com.framwork.main.util.ImageUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;


public class EmployeesAdapter extends SimpleRecycleParentListAdapter<EmployeesBean.employe> {
    Context context;
    String projectId;
    
    public EmployeesAdapter(Context c, String projectId) {
        super(R.layout.item_employee);
        this.context = c;
        this.projectId = projectId;
    }
    
    
    @Override
    protected void fillData(SimpleRecycleParentViewHolder viewHolder, int position, EmployeesBean.employe items, List<EmployeesBean.employe> datas) {
        EmployeesBean.employe item = datas.get(position);
        viewHolder.setText(R.id.item_employee_tv_name, item.name);
        viewHolder.setText(R.id.item_employee_tv_phone, item.tel);
        viewHolder.setText(R.id.item_employee_tv_unit, item.unit);
        viewHolder.setText(R.id.item_employee_tv_group, item.team);
        viewHolder.setText(R.id.item_employee_tv_duty, item.workType);
        
        ImageView item_employee_img_header = viewHolder.findViewById(R.id.item_employee_img_header);
        Glide.with(context).load(item.photoPath).apply(ImageUtil.getRequestOptions()).into(item_employee_img_header);
        
        TextView item_employee_tv_state = viewHolder.findViewById(R.id.item_employee_tv_state);
        if(0 == item.status) {//离场
            item_employee_tv_state.setBackground(ResUtil.getDrawable(R.drawable.icon_off));
        }
        else if(1 == item.status) {//zai场
            item_employee_tv_state.setBackground(ResUtil.getDrawable(R.drawable.icon_on));
            item_employee_tv_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OutDialog outDialog = new OutDialog(context, new OutDialog.OnConfirmClickListener() {
                        @Override
                        public void onConfirmClick() {
                            Map<String, String> params = new HashMap<>();
                            RestClient.postWithParam(GlobalConstants.InterfaceNameConstants.PROJECT + GlobalConstants.InterfaceNameConstants.EMPLOYEE, params,
                                    item.id + GlobalConstants.InterfaceNameConstants.OUT, new GsonHttpCallback<BaseBean>() {
                                        @Override
                                        public void onBefore(Request request) {
                                            LoadingUtil.showLoading(context);
                                        }
                                        
                                        @Override
                                        public void onAfter() {
                                            LoadingUtil.dismissLoading(context);
                                        }
                                        
                                        @Override
                                        protected void error(ResultBean<BaseBean> t) {
                                            ToastUtil.showToast(t.msg);
                                        }
                                        
                                        @Override
                                        protected void response(ResultBean<BaseBean> t) {
                                            ToastUtil.showToast(t.msg);
                                            EventBus.getDefault().post(new EditEvent());
                                        }
                                        
                                        @Override
                                        protected void onNetFail(ResultBean<BaseBean> t) {
                                            ToastUtil.showNetError();
                                        }
                                    });
                        }
                    });
                    outDialog.show();
                }
            });
        }
        
        ImageView item_employee_img_editor = viewHolder.findViewById(R.id.item_employee_img_editor);
        RelativeLayout item_employee_layout_img = viewHolder.findViewById(R.id.item_employee_layout_img);
        item_employee_layout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PersonEditActivity.class);
                Bundle b = new Bundle();
                b.putString("projectId", projectId);
                b.putString("id", item.id);
                i.putExtras(b);
                context.startActivity(i);
            }
        });
        
    }
}
