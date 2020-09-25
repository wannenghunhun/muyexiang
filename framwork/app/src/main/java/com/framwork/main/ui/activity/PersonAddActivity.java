package com.framwork.main.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.decard.muye.sdk.FaceCallBack;
import com.decard.muye.sdk.ICardCallBack;
import com.decard.muye.sdk.IPersonFaceManager;
import com.decard.muye.sdk.InitDeviceCallBack;
import com.decard.muye.sdk.MuyeIDCard;
import com.decard.muye.sdk.RemoteFaceService;
import com.decard.muye.sdk.camera.CameraUtils;
import com.decard.muye.sdk.utils.Base64Utils;
import com.decard.muye.sdk.utils.FileUtils;
import com.framwork.common.ui.activity.BaseFragmentActivity;
import com.framwork.common.utils.AppUtil;
import com.framwork.common.utils.LogUtil;
import com.framwork.common.utils.ResUtil;
import com.framwork.common.utils.SPManager;
import com.framwork.common.utils.ToastUtil;
import com.framwork.common.widget.ClearAbleEditText;
import com.framwork.main.GlobalConstants;
import com.framwork.main.R;
import com.framwork.main.bean.ConditionsBean;
import com.framwork.main.bean.UserInfoBean;
import com.framwork.main.event.EditEvent;
import com.framwork.main.ui.contract.PersonAddContract;
import com.framwork.main.ui.presenter.PersonAddPresenter;
import com.framwork.main.util.LoginUtil;
import com.zkteco.android.IDReader.IDPhotoHelper;
import com.zkteco.android.IDReader.WLTService;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonAddActivity extends BaseFragmentActivity<PersonAddContract.Presenter> implements PersonAddContract.View {
    private RelativeLayout mPersonAddLayoutTitle;
    private ImageView mPersonAddImgBack;
    private TextView mPersonAddTvTitle;
    private TextView mPersonAddTvLogout;
    private RelativeLayout mPersonAddLayoutPro;
    private TextView mPersonAddTvProChose;
    private TextView mPersonAddTvProName;
    private LinearLayout mPersonAddLayoutProTitle;
    private LinearLayout mPersonAddLayoutVerify;
    private ImageView mPersonAddImgFace;
    private ImageView mPersonAddImgId;
    private LinearLayout mPersonAddLayoutRight;
    private ClearAbleEditText mPersonAddEtName;
    private ClearAbleEditText mPersonAddEtId;
    private RelativeLayout mPersonAddLayoutSex;
    private TextView mPersonAddTvSex;
    private ClearAbleEditText mPersonAddEtMinzu;
    private LinearLayout mPersonAddLayoutBirthday;
    private TextView mPersonAddTvBirthday;
    private ClearAbleEditText mPersonAddEtAddress;
    private ClearAbleEditText mPersonAddEtPhone;
    private RelativeLayout mPersonAddLayoutZhengzhi;
    private TextView mPersonAddTvZhengzhi;
    private RelativeLayout mPersonAddLayoutJiguan;
    private TextView mPersonAddTvJiguan;
    private RelativeLayout mPersonAddLayoutWenhua;
    private TextView mPersonAddTvWenhua;
    private RelativeLayout mPersonAddLayoutLeader;
    private TextView mPersonAddTvLeader;
    private RelativeLayout mPersonAddLayoutPeixun;
    private TextView mPersonAddTvPeixun;
    private RelativeLayout mPersonAddLayoutChizheng;
    private TextView mPersonAddTvChizheng;
    private ClearAbleEditText mPersonAddEtZhengshu;
    private ClearAbleEditText mPersonAddEtHetong;
    private RelativeLayout mPersonAddLayoutUnit;
    private TextView mPersonAddTvUnit;
    private RelativeLayout mPersonAddLayoutRole;
    private TextView mPersonAddTvRole;
    private RelativeLayout mPersonAddLayoutWorktype;
    private RelativeLayout mPersonAddLayoutTeam;
    private TextView mPersonAddTvWorktype;
    private TextView mPersonAddTvFanchang;
    private TextView mPersonAddTvAddinfo;
    private TextView mPersonAddTvTeam, person_add_tv_result;
    private TextureView person_add_textureView;
    private LinearLayout person_add_layout_info;
    private String projectId, name = "", id_number = "", minzu = "", address = "", phone = "", zhengshu_number, hetong_number;
    private PopupWindow popupWindow_sex, popupWindow_zhengzhi, popupWindow_jiguan, popupWindow_wenhua, popupWindow_leader, popupWindow_peixun,
            popupWindow_chizheng, popupWindow_unit, popupWindow_team, popupWindow_role, popupWindow_worktype, popupWindow_pro;
    private View popView_sex, popView_zhengzhi, popView_jiguan, popView_wenhua, popView_leader, popView_peixun,
            popView_chizheng, popView_unit, popView_team, popView_role, popView_pro, popView_worktype;
    private int index_sex = 0, index_zhengzhi = 0, index_jiguan = 0, index_wenhua = 0, index_leader = 0, index_peixun = 0,
            index_chizheng = 0, index_unit = 0, index_team = 0, index_role = 0, index_worktype = 0, index_pro;
    private ConditionsBean conditionsBean;
    private UserInfoBean userInfo;
    private ArrayList<String> zhengzhiList = new ArrayList<>();
    private ArrayList<String> jiguanList = new ArrayList<>();
    private ArrayList<String> wenhuaList = new ArrayList<>();
    private ArrayList<String> unitList = new ArrayList<>();
    private ArrayList<String> teamList = new ArrayList<>();
    private ArrayList<String> roleList = new ArrayList<>();
    private ArrayList<String> workTypeList = new ArrayList<>();
    private ArrayList<String> pro_names = new ArrayList<>();
    private TimePickerView pvTime;
    private String person_add_unitId = "", person_add_teamId = "", person_add_roleType = "", person_add_workType = "";
    private IPersonFaceManager faceManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected PersonAddContract.Presenter createPresenter() {
        return new PersonAddPresenter(this);
    }
    
    @Override
    protected int layoutContentId() {
        return R.layout.activity_person_add;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCompare();
        CameraUtils.getInstance().closeCamera();
    }
    
    @Override
    protected void loadAgain() {
    
    }
    
    @Override
    protected void loadData() {
        presenter.getConditionsInfo(projectId);
        presenter.getUserInfo();
    }
    
    @Override
    protected void initView() {
        mPersonAddLayoutTitle = findViewById(R.id.person_add_layout_title);
        mPersonAddImgBack = findViewById(R.id.person_add_img_back);
        mPersonAddTvTitle = findViewById(R.id.person_add_tv_title);
        mPersonAddTvLogout = findViewById(R.id.person_add_tv_logout);
        mPersonAddLayoutPro = findViewById(R.id.person_add_layout_pro);
        mPersonAddTvProChose = findViewById(R.id.person_add_tv_pro_chose);
        mPersonAddTvProName = findViewById(R.id.person_add_tv_pro_name);
        mPersonAddLayoutProTitle = findViewById(R.id.person_add_layout_pro_title);
        mPersonAddLayoutVerify = findViewById(R.id.person_add_layout_verify);
        mPersonAddImgFace = findViewById(R.id.person_add_img_face);
        mPersonAddImgId = findViewById(R.id.person_add_img_id);
        mPersonAddLayoutRight = findViewById(R.id.person_add_layout_right);
        mPersonAddEtName = findViewById(R.id.person_add_et_name);
        mPersonAddEtId = findViewById(R.id.person_add_et_id);
        mPersonAddLayoutSex = findViewById(R.id.person_add_layout_sex);
        mPersonAddTvSex = findViewById(R.id.person_add_tv_sex);
        mPersonAddEtMinzu = findViewById(R.id.person_add_et_minzu);
        mPersonAddLayoutBirthday = findViewById(R.id.person_add_layout_birthday);
        mPersonAddTvBirthday = findViewById(R.id.person_add_tv_birthday);
        mPersonAddEtAddress = findViewById(R.id.person_add_et_address);
        mPersonAddEtPhone = findViewById(R.id.person_add_et_phone);
        mPersonAddLayoutZhengzhi = findViewById(R.id.person_add_layout_zhengzhi);
        mPersonAddTvZhengzhi = findViewById(R.id.person_add_tv_zhengzhi);
        mPersonAddLayoutJiguan = findViewById(R.id.person_add_layout_jiguan);
        mPersonAddTvJiguan = findViewById(R.id.person_add_tv_jiguan);
        mPersonAddLayoutWenhua = findViewById(R.id.person_add_layout_wenhua);
        mPersonAddTvWenhua = findViewById(R.id.person_add_tv_wenhua);
        mPersonAddLayoutLeader = findViewById(R.id.person_add_layout_leader);
        mPersonAddTvLeader = findViewById(R.id.person_add_tv_leader);
        mPersonAddLayoutPeixun = findViewById(R.id.person_add_layout_peixun);
        mPersonAddTvPeixun = findViewById(R.id.person_add_tv_peixun);
        mPersonAddLayoutChizheng = findViewById(R.id.person_add_layout_chizheng);
        mPersonAddTvChizheng = findViewById(R.id.person_add_tv_chizheng);
        mPersonAddEtZhengshu = findViewById(R.id.person_add_et_zhengshu);
        mPersonAddEtHetong = findViewById(R.id.person_add_et_hetong);
        mPersonAddLayoutUnit = findViewById(R.id.person_add_layout_unit);
        mPersonAddTvUnit = findViewById(R.id.person_add_tv_unit);
        mPersonAddLayoutTeam = findViewById(R.id.person_add_layout_team);
        mPersonAddLayoutRole = findViewById(R.id.person_add_layout_role);
        mPersonAddTvRole = findViewById(R.id.person_add_tv_role);
        mPersonAddLayoutWorktype = findViewById(R.id.person_add_layout_worktype);
        mPersonAddTvWorktype = findViewById(R.id.person_add_tv_worktype);
        mPersonAddTvFanchang = findViewById(R.id.person_add_tv_fanchang);
        mPersonAddTvAddinfo = findViewById(R.id.person_add_tv_addinfo);
        mPersonAddTvTeam = findViewById(R.id.person_add_tv_team);
        person_add_textureView = findViewById(R.id.person_add_textureView);
        person_add_layout_info = findViewById(R.id.person_add_layout_info);
        person_add_tv_result = findViewById(R.id.person_add_tv_result);
        popView_sex = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_sex = new PopupWindow(popView_sex);
        popView_zhengzhi = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_zhengzhi = new PopupWindow(popView_zhengzhi);
        popView_jiguan = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_jiguan = new PopupWindow(popView_jiguan);
        popView_wenhua = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_wenhua = new PopupWindow(popView_wenhua);
        popView_leader = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_leader = new PopupWindow(popView_leader);
        popView_peixun = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_peixun = new PopupWindow(popView_peixun);
        popView_chizheng = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_chizheng = new PopupWindow(popView_chizheng);
        popView_unit = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_unit = new PopupWindow(popView_unit);
        popView_team = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_team = new PopupWindow(popView_team);
        popView_role = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_role = new PopupWindow(popView_role);
        popView_worktype = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_worktype = new PopupWindow(popView_worktype);
        popView_pro = View.inflate(this, R.layout.view_popupwindow, null);
        popupWindow_pro = new PopupWindow(popView_pro);
        setPickView();
        setOnclick();
        setEdit();
        initSDK();
    }
    
    @Override
    protected void initData(@NonNull Bundle bundle) {
        projectId = bundle.getString("projectId");
    }
    
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
    }
    
    private void initSDK() {
        CameraUtils.getInstance().startCamera(person_add_textureView, this);
        
        //        DialogUtil.getInstance().init(this);
        
        Intent intent = new Intent(this, RemoteFaceService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }
    
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.e("onServiceConnected");
            faceManager = IPersonFaceManager.Stub.asInterface(service);
            initDevice();   //设备初始化
        }
        
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.e("onServiceDisconnected");
        }
    };
    
    public void initDevice() {
        if(faceManager != null) {
            String APP_ID = SPManager.getManager("param").getString(GlobalConstants.SPConstants.APP_ID);
            String SDK_KEY = SPManager.getManager("param").getString(GlobalConstants.SPConstants.SDK_KEY);
            String SOFT_ID = SPManager.getManager("param").getString(GlobalConstants.SPConstants.SOFT_ID);
            String LOG_ID = SPManager.getManager("param").getString(GlobalConstants.SPConstants.LOG_ID);
            String PRO_NUM = SPManager.getManager("param").getString(GlobalConstants.SPConstants.PRO_NUM);
            try {
                faceManager.initDevice(
                        APP_ID,   //虹软分配的APPID
                        SDK_KEY,   //虹软分配的APPKey
                        SOFT_ID,  //授权管理软件appID
                        PRO_NUM,  //项目编号
                        LOG_ID,//日志管理系统密钥
                        new InitDeviceCallBack.Stub() {
                            @Override
                            public void initCallBack(int errorCode) throws RemoteException {
                                LogUtil.e("初始化返回值  " + errorCode);
                                //errorCode == 0 执行初始化人脸算法
                                if(errorCode == 0) {
                                    initFace();  //初始化人脸
                                }
                            }
                        });
                
                
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void initFace() {
        if(faceManager != null) {
            try {
                faceManager.initFace(SPManager.getManager("param").getString(GlobalConstants.SPConstants.FACE_SERVER), new FaceCallBack.Stub() {
                    @Override
                    public void success(String message) {
                        LogUtil.e("初始化人脸  " + message);
                    }
                    
                    @Override
                    public void fail(String errorMsg) {
                        LogUtil.e(errorMsg);
                        showToast("人脸信息异常  " + errorMsg);
                    }
                    
                });
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void readId() {
        if(faceManager != null) {
            try {
                faceManager.startReadId(new ICardCallBack.Stub() {
                    @Override
                    public void onError(String errmsg) {
                        LogUtil.e("onError: " + errmsg);
                    }
                    
                    @Override
                    public void onSuccess(MuyeIDCard idCard) {
                        
                        if(idCard != null) {
                            LogUtil.d("onSuccess: 身份证信息  " + idCard.getName() + "     " + Thread.currentThread().getName());
                            if(idCard.getPhotolength() > 0) {
                                byte[] buf = new byte[WLTService.imgLength];
                                if(1 == WLTService.wlt2Bmp(idCard.getPhoto(), buf)) {
                                    Bitmap bitmap = IDPhotoHelper.Bgr2Bitmap(buf);
                                    
                                    runOnUiThread(() -> mPersonAddImgFace.setImageBitmap(bitmap));
                                }
                            }
                            startCompare(idCard, 0);
                        }
                        
                    }
                    
                });
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void startCompare(MuyeIDCard idCard, int type) {
        
        if(faceManager != null) {
            try {
                //读取到的身份证信息
                faceManager.stopCompare();
                if(idCard.getPhotolength() > 0 && type == 0) {
                    byte[] buf = new byte[WLTService.imgLength];
                    if(1 == WLTService.wlt2Bmp(idCard.getPhoto(), buf)) {
                        Bitmap bitmap = IDPhotoHelper.Bgr2Bitmap(buf);
                        
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPersonAddImgFace.setImageBitmap(bitmap);
                                
                            }
                        });
                        File file = FileUtils.bitmapToFile(bitmap, String.valueOf(System.currentTimeMillis()));  //用身份证照片人脸注册
                        if(file == null) {
                            
                            return;
                        }
                        faceManager.startCompare(file.getPath(), type, new FaceCallBack.Stub() {
                            @Override
                            public void success(String message) {  //成功返回Base64人脸信息
                                LogUtil.d(message);
                                String[] split = message.split("\\|");
                                
                                runOnUiThread(() -> {
                                    //显示人脸信息，相似度
                                    try {
                                        byte[] decodeBytes = Base64Utils.decode(split[0]);  //人脸照片
                                        YuvImage image = new YuvImage(decodeBytes, ImageFormat.NV21, 640, 480, null);
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        image.compressToJpeg(new Rect(0, 0, 640, 480), 80, stream);
                                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                                        Bitmap rotateBitmap = CameraUtils.getInstance().rotate(bitmap1, 90f);
                                        
                                        stopCompare();
                                        deleteFile(file.getName());
                                        String idMsg = idCard.getName().trim() + "|" + idCard.getSex() + "|" + idCard.getNation() + "|" + idCard.getBirth() + "|"
                                                + idCard.getAddress().trim() + "|" + idCard.getId() + "|" + idCard.getValidityTime() + "|"
                                                + Base64Utils.encode(idCard.getPhoto()) + "|";
                                        name = idCard.getName();
                                        minzu = idCard.getNation();
                                        address = idCard.getAddress();
                                        mPersonAddEtName.setText(idCard.getName());
                                        mPersonAddTvSex.setText(idCard.getSex());
                                        mPersonAddEtMinzu.setText(idCard.getNation());
                                        mPersonAddTvBirthday.setText(idCard.getBirth());
                                        mPersonAddEtAddress.setText(idCard.getAddress());
                                        
                                        person_add_textureView.setVisibility(View.GONE);
                                        mPersonAddLayoutRight.setVisibility(View.VISIBLE);
                                        mPersonAddImgId.setImageBitmap(rotateBitmap);
                                        int p = Integer.valueOf(split[1]) * 100;
                                        person_add_tv_result.setText("相似度:" + split[1] + "%");
                                    } catch(UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                            
                            
                            @Override
                            public void fail(String errorMsg) {
                                stopCompare();
                                LogUtil.e(errorMsg);
                                showToast(errorMsg);
                            }
                        });
                    }
                }
                else {
                    LogUtil.e("startCompare: 无身份证信息，直接获取照片并上传");
                    faceManager.startCompare(null, type, new FaceCallBack.Stub() {
                        @Override
                        public void success(String message) {  //成功返回Base64人脸信息
                            LogUtil.d(message);
                            //上传人脸信息，上传人证信息
                            String idMsg = idCard.getName().trim() + "|" + idCard.getSex() + "|" + idCard.getNation() + "|" + idCard.getBirth() + "|" + idCard.getAddress().trim() + "|" + idCard.getId() + "|" + idCard.getValidityTime() + "|" + "|";
                            person_add_textureView.setVisibility(View.GONE);
                            mPersonAddLayoutRight.setVisibility(View.VISIBLE);
                            person_add_tv_result.setText("相似度:" + "0%");
                            name = idCard.getName();
                            minzu = idCard.getNation();
                            address = idCard.getAddress();
                            mPersonAddEtName.setText(idCard.getName());
                            mPersonAddTvSex.setText(idCard.getSex());
                            mPersonAddEtMinzu.setText(idCard.getNation());
                            mPersonAddTvBirthday.setText(idCard.getBirth());
                            mPersonAddEtAddress.setText(idCard.getAddress());
                        }
                        
                        
                        @Override
                        public void fail(String errorMsg) {
                            LogUtil.e(errorMsg);
                            showToast(errorMsg);
                        }
                    });
                }
                
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void showToast(String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(errorMsg);
            }
        });
    }
    
    private void stopCompare() {
        if(faceManager != null) {
            try {
                faceManager.stopCompare();
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void setPickView() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1940, 0, 1);
        selectedDate.set(2000, 0, 1);
        
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = simpleDateFormat.format(date);
                mPersonAddTvBirthday.setText(format);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                //                .setTitleSize(16)//标题文字大小
                //                .setTitleText("Title")//标题文字
                //                .setTitleColor(Color.BLACK)//标题文字颜色
                .setTitleBgColor(ResUtil.getColor(R.color.white))//标题背景颜色
                .setBgColor(ResUtil.getColor(R.color.white))//滚轮背景颜色
                .setContentTextSize(16)//滚轮文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setSubmitColor(ResUtil.getColor(R.color.red_E51010))//确定按钮文字颜色
                .setCancelColor(ResUtil.getColor(R.color.green_015F33))//取消按钮文字颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                //                .setLabel("年", "月", "日")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
    }
    
    private void setOnclick() {
        mPersonAddTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtil.clearUserData();
                Intent i = new Intent(PersonAddActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        mPersonAddImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPersonAddLayoutSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_sex, popView_sex);
            }
        });
        mPersonAddLayoutLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_leader, popView_leader);
            }
        });
        mPersonAddLayoutPeixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_peixun, popView_peixun);
            }
        });
        mPersonAddLayoutChizheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_chizheng, popView_chizheng);
            }
        });
        
        mPersonAddLayoutZhengzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_zhengzhi, popView_zhengzhi);
            }
        });
        mPersonAddLayoutJiguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_jiguan, popView_jiguan);
            }
        });
        mPersonAddLayoutWenhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_wenhua, popView_wenhua);
            }
        });
        mPersonAddLayoutUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_unit, popView_unit);
            }
        });
        mPersonAddLayoutTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_team, popView_team);
            }
        });
        mPersonAddLayoutRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_role, popView_role);
            }
        });
        mPersonAddLayoutWorktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_worktype, popView_worktype);
            }
        });
        mPersonAddTvProChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_pro, popView_pro);
            }
        });
        mPersonAddLayoutBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                pvTime.show();
            }
        });
        person_add_layout_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(View.VISIBLE != person_add_textureView.getVisibility()) {
                    person_add_textureView.setVisibility(View.VISIBLE);
                    readId();
                }
            }
        });
        mPersonAddTvAddinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请填写正确的姓名");
                }
                else if(TextUtils.isEmpty(id_number)) {
                    ToastUtil.showToast("请填写正确的身份证号");
                }
                else if(TextUtils.isEmpty(mPersonAddTvSex.getText().toString())) {
                    ToastUtil.showToast("请选择正确的性别");
                }
                else if(TextUtils.isEmpty(minzu)) {
                    ToastUtil.showToast("请填写正确的民族");
                }
                else if(TextUtils.isEmpty(mPersonAddTvBirthday.getText().toString())) {
                    ToastUtil.showToast("请选择正确的出生日期");
                }
                else if(TextUtils.isEmpty(address)) {
                    ToastUtil.showToast("请填写正确的住址");
                }
                else if(TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请填写正确的手机号码");
                }
                else if(!phone.matches("^[1]\\d{10}$")) {
                    ToastUtil.showToast("请填写正确的手机号码");
                }
                else {
                    presenter.addEmployeesInfo("", "", "", name,
                            id_number, mPersonAddTvSex.getText().toString(), mPersonAddTvBirthday.getText().toString(), minzu,
                            address, phone, mPersonAddTvZhengzhi.getText().toString(), mPersonAddTvJiguan.getText().toString(),
                            mPersonAddTvWenhua.getText().toString(), mPersonAddTvLeader.getText().toString(), mPersonAddTvPeixun.getText().toString(),
                            mPersonAddTvChizheng.getText().toString(), projectId, person_add_unitId, person_add_teamId,
                            person_add_roleType, person_add_workType, "", zhengshu_number, hetong_number
                    );
                }
            }
        });
    }
    
    private void setEdit() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = mPersonAddEtName.getText().toString();
                id_number = mPersonAddEtId.getText().toString();
                minzu = mPersonAddEtMinzu.getText().toString();
                address = mPersonAddEtAddress.getText().toString();
                phone = mPersonAddEtPhone.getText().toString();
                zhengshu_number = mPersonAddEtZhengshu.getText().toString();
                hetong_number = mPersonAddEtHetong.getText().toString();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        mPersonAddEtName.addTextChangedListener(textWatcher);
        mPersonAddEtId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        mPersonAddEtMinzu.addTextChangedListener(textWatcher);
        mPersonAddEtAddress.addTextChangedListener(textWatcher);
        mPersonAddEtPhone.addTextChangedListener(textWatcher);
        mPersonAddEtPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        mPersonAddEtPhone.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        mPersonAddEtZhengshu.addTextChangedListener(textWatcher);
        mPersonAddEtHetong.addTextChangedListener(textWatcher);
        
    }
    
    private void popShow(PopupWindow popupWindow, View view) {
        hideSoftKeyBoard();
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    
    private void setPopupWindow(String title, TextView textView, ArrayList<String> dataList, PopupWindow popupWindow, View view, int key) {
        TextView popupwindow_tv_title = view.findViewById(R.id.popupwindow_tv_title);
        ListView popupwindow_list = view.findViewById(R.id.popupwindow_list);
        popupwindow_tv_title.setText(title);
        //获取屏幕宽高
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 1 / 2;
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);
        popupwindow_list.setAdapter(new ArrayAdapter<String>(PersonAddActivity.this, R.layout.item_popupwindow, R.id.item_tv, dataList));
        popupwindow_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //关闭popupWindow
                popupWindow.dismiss();
                textView.setText(dataList.get(arg2));
                switch(key) {
                    case 0:
                        index_sex = arg2;
                        break;
                    case 1:
                        index_zhengzhi = arg2;
                        break;
                    case 2:
                        index_jiguan = arg2;
                        break;
                    case 3:
                        index_wenhua = arg2;
                        break;
                    case 4:
                        index_leader = arg2;
                        break;
                    case 5:
                        index_peixun = arg2;
                        break;
                    case 6:
                        index_chizheng = arg2;
                        break;
                    case 7:
                        index_unit = arg2;
                        person_add_unitId = conditionsBean.units.get(index_unit).id;
                        break;
                    case 8:
                        index_team = arg2;
                        person_add_teamId = conditionsBean.teams.get(index_team).id;
                        break;
                    case 9:
                        index_role = arg2;
                        person_add_roleType = conditionsBean.types.get(index_role).id;
                        ArrayList<String> workTypeList = new ArrayList<>();
                        for(int x = 0; x < conditionsBean.types.get(index_role).value.size(); x++) {
                            workTypeList.add(conditionsBean.types.get(index_role).value.get(x).name);
                        }
                        setPopupWindow("工种", mPersonAddTvWorktype, workTypeList, popupWindow_worktype, popView_worktype, 10);
                        mPersonAddTvWorktype.setText(workTypeList.get(0));
                        person_add_workType = conditionsBean.types.get(index_role).value.get(0).id;
                        break;
                    case 10:
                        index_worktype = arg2;
                        person_add_workType = conditionsBean.types.get(index_role).value.get(index_worktype).id;
                        break;
                    case 11:
                        index_pro = arg2;
                        break;
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
    }
    
    @Override
    public void setConditionsInfo(ConditionsBean conditionsBean) {
        this.conditionsBean = conditionsBean;
        ArrayList<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        ArrayList<String> booleanList = new ArrayList<>();
        booleanList.add("是");
        booleanList.add("否");
        zhengzhiList = new ArrayList<>();
        for(int i = 0; i < conditionsBean.politicalStatus.size(); i++) {
            zhengzhiList.add(conditionsBean.politicalStatus.get(i).value);
        }
        jiguanList = new ArrayList<>();
        for(int i = 0; i < conditionsBean.provinces.size(); i++) {
            jiguanList.add(conditionsBean.provinces.get(i).value);
        }
        wenhuaList = new ArrayList<>();
        for(int i = 0; i < conditionsBean.education.size(); i++) {
            wenhuaList.add(conditionsBean.education.get(i).value);
        }
        unitList = new ArrayList<>();
        for(int i = 0; i < conditionsBean.units.size(); i++) {
            unitList.add(conditionsBean.units.get(i).name);
        }
        teamList = new ArrayList<>();
        for(int i = 0; i < conditionsBean.teams.size(); i++) {
            teamList.add(conditionsBean.teams.get(i).name);
        }
        roleList = new ArrayList<>();
        for(int i = 0; i < conditionsBean.roles.size(); i++) {
            roleList.add(conditionsBean.roles.get(i).name);
        }
        workTypeList = new ArrayList<>();
        for(int x = 0; x < conditionsBean.types.get(0).value.size(); x++) {
            workTypeList.add(conditionsBean.types.get(0).value.get(x).name);
        }
        setPopupWindow("性别", mPersonAddTvSex, sexList, popupWindow_sex, popView_sex, 0);
        setPopupWindow("政治面貌", mPersonAddTvZhengzhi, zhengzhiList, popupWindow_zhengzhi, popView_zhengzhi, 1);
        mPersonAddTvZhengzhi.setText(zhengzhiList.get(0));
        setPopupWindow("籍贯", mPersonAddTvJiguan, jiguanList, popupWindow_jiguan, popView_jiguan, 2);
        mPersonAddTvJiguan.setText(jiguanList.get(0));
        setPopupWindow("文化程度", mPersonAddTvWenhua, wenhuaList, popupWindow_wenhua, popView_wenhua, 3);
        mPersonAddTvWenhua.setText(wenhuaList.get(0));
        setPopupWindow("班组长", mPersonAddTvLeader, booleanList, popupWindow_leader, popView_leader, 4);
        mPersonAddTvLeader.setText(booleanList.get(0));
        setPopupWindow("参加培训", mPersonAddTvPeixun, booleanList, popupWindow_peixun, popView_peixun, 5);
        mPersonAddTvPeixun.setText(booleanList.get(0));
        setPopupWindow("持证上岗", mPersonAddTvChizheng, booleanList, popupWindow_chizheng, popView_chizheng, 6);
        mPersonAddTvChizheng.setText(booleanList.get(0));
        setPopupWindow("所属单位", mPersonAddTvUnit, unitList, popupWindow_unit, popView_unit, 7);
        mPersonAddTvUnit.setText(unitList.get(0));
        person_add_unitId = conditionsBean.units.get(0).id;
        setPopupWindow("所属班组", mPersonAddTvTeam, teamList, popupWindow_team, popView_team, 8);
        mPersonAddTvTeam.setText(teamList.get(0));
        person_add_teamId = conditionsBean.teams.get(0).id;
        setPopupWindow("角色", mPersonAddTvRole, roleList, popupWindow_role, popView_role, 9);
        mPersonAddTvRole.setText(roleList.get(0));
        person_add_roleType = conditionsBean.types.get(0).id;
        setPopupWindow("工种", mPersonAddTvWorktype, workTypeList, popupWindow_worktype, popView_worktype, 10);
        mPersonAddTvWorktype.setText(workTypeList.get(0));
        person_add_workType = conditionsBean.types.get(0).value.get(0).id;
    }
    
    @Override
    public void setProjectInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
        pro_names = new ArrayList<>();
        for(int i = 0; i < userInfo.projects.size(); i++) {
            pro_names.add(userInfo.projects.get(i).name);
        }
        setPopupWindow("选择项目", mPersonAddTvProName, pro_names, popupWindow_pro, popView_pro, 11);
        mPersonAddTvProName.setText(pro_names.get(0));
    }
    
    
    @Override
    public void editResult(String s) {
        ToastUtil.showToast(s);
        finish();
        EventBus.getDefault().post(new EditEvent());
    }
    
}