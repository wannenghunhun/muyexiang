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
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
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
import com.bumptech.glide.Glide;
import com.decard.muye.sdk.FaceCallBack;
import com.decard.muye.sdk.ICardCallBack;
import com.decard.muye.sdk.IDCardTools;
import com.decard.muye.sdk.IPersonFaceManager;
import com.decard.muye.sdk.InitDeviceCallBack;
import com.decard.muye.sdk.MuyeIDCard;
import com.decard.muye.sdk.RemoteFaceService;
import com.decard.muye.sdk.camera.CameraUtils;
import com.decard.muye.sdk.utils.Base64Utils;
import com.decard.muye.sdk.utils.FileUtils;
import com.framwork.common.ui.activity.BaseFragmentActivity;
import com.framwork.common.utils.LogUtil;
import com.framwork.common.utils.ResUtil;
import com.framwork.common.utils.SPManager;
import com.framwork.common.utils.ToastUtil;
import com.framwork.common.widget.ClearAbleEditText;
import com.framwork.main.GlobalConstants;
import com.framwork.main.R;
import com.framwork.main.bean.ConditionsBean;
import com.framwork.main.bean.EmployeeBean;
import com.framwork.main.bean.UserInfoBean;
import com.framwork.main.event.EditEvent;
import com.framwork.main.ui.contract.PersonEditContract;
import com.framwork.main.ui.presenter.PersonEditPresenter;
import com.framwork.main.util.ImageUtil;
import com.framwork.main.util.LoginUtil;
import com.zkteco.android.IDReader.IDPhotoHelper;
import com.zkteco.android.IDReader.WLTService;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonEditActivity extends BaseFragmentActivity<PersonEditContract.Presenter> implements PersonEditContract.View {
    private RelativeLayout mPersonEditLayoutTitle;
    private ImageView mPersonEditImgBack;
    private TextView mPersonEditTvTitle;
    private TextView mPersonEditTvLogout;
    private RelativeLayout mPersonEditLayoutPro;
    private TextView mPersonEditTvProChose;
    private TextView mPersonEditTvProName;
    private LinearLayout mPersonEditLayoutProTitle;
    private LinearLayout mPersonEditLayoutVerify;
    private ImageView mPersonEditImgFace;
    private ImageView mPersonEditImgId;
    private LinearLayout mPersonEditLayoutRight;
    private LinearLayout person_edit_layout_info;
    private ClearAbleEditText mPersonEditEtName;
    private ClearAbleEditText mPersonEditEtId;
    private RelativeLayout mPersonEditLayoutSex;
    private TextView mPersonEditTvSex;
    private ClearAbleEditText mPersonEditEtMinzu;
    private LinearLayout mPersonEditLayoutBirthday;
    private TextView mPersonEditTvBirthday;
    private ClearAbleEditText mPersonEditEtAddress;
    private ClearAbleEditText mPersonEditEtPhone;
    private RelativeLayout mPersonEditLayoutZhengzhi;
    private TextView mPersonEditTvZhengzhi;
    private RelativeLayout mPersonEditLayoutJiguan;
    private TextView mPersonEditTvJiguan;
    private RelativeLayout mPersonEditLayoutWenhua;
    private TextView mPersonEditTvWenhua;
    private RelativeLayout mPersonEditLayoutLeader;
    private TextView mPersonEditTvLeader;
    private RelativeLayout mPersonEditLayoutPeixun;
    private TextView mPersonEditTvPeixun;
    private RelativeLayout mPersonEditLayoutChizheng;
    private TextView mPersonEditTvChizheng;
    private ClearAbleEditText mPersonEditEtZhengshu;
    private ClearAbleEditText mPersonEditEtHetong;
    private RelativeLayout mPersonEditLayoutUnit;
    private TextView mPersonEditTvUnit;
    private RelativeLayout mPersonEditLayoutRole;
    private TextView mPersonEditTvRole;
    private RelativeLayout mPersonEditLayoutWorktype;
    private RelativeLayout mPersonEditLayoutTeam;
    private TextView mPersonEditTvWorktype;
    private TextView mPersonEditTvFanchang;
    private TextView mPersonEditTvAddinfo;
    private TextView mPersonEditTvTeam, person_edit_tv_result;
    
    private String projectId, id, name = "", id_number = "", minzu = "", address = "", phone = "", zhengshu_number, hetong_number;
    
    private PopupWindow popupWindow_sex, popupWindow_zhengzhi, popupWindow_jiguan, popupWindow_wenhua, popupWindow_leader, popupWindow_peixun,
            popupWindow_chizheng, popupWindow_unit, popupWindow_team, popupWindow_role, popupWindow_worktype, popupWindow_pro;
    private View popView_sex, popView_zhengzhi, popView_jiguan, popView_wenhua, popView_leader, popView_peixun,
            popView_chizheng, popView_unit, popView_team, popView_role, popView_pro, popView_worktype;
    private int index_sex, index_zhengzhi, index_jiguan, index_wenhua, index_leader, index_peixun,
            index_chizheng, index_unit = -1, index_team = -1, index_role = -1, index_worktype = -1, index_pro;
    private TextureView person_edit_textureView;
    private ConditionsBean conditionsBean;
    private UserInfoBean userInfo;
    private EmployeeBean employeeBean;
    private ArrayList<String> zhengzhiList = new ArrayList<>();
    private ArrayList<String> jiguanList = new ArrayList<>();
    private ArrayList<String> wenhuaList = new ArrayList<>();
    private ArrayList<String> unitList = new ArrayList<>();
    private ArrayList<String> teamList = new ArrayList<>();
    private ArrayList<String> roleList = new ArrayList<>();
    private ArrayList<String> workTypeList = new ArrayList<>();
    private ArrayList<String> pro_names = new ArrayList<>();
    private TimePickerView pvTime;
    private String person_edit_unitId = "", person_edit_teamId = "", person_edit_roleType = "", person_edit_workType = "";
    private IPersonFaceManager faceManager;
    public String idPhoto = "";
    public String facePhoto = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected PersonEditContract.Presenter createPresenter() {
        return new PersonEditPresenter(this);
    }
    
    @Override
    protected int layoutContentId() {
        return R.layout.activity_person_edit;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCompare();
        CameraUtils.getInstance().closeCamera();
        unbindService(connection);
        IDCardTools.getInstance(this).stopReadIdCard();
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
        mPersonEditLayoutTitle = findViewById(R.id.person_edit_layout_title);
        mPersonEditImgBack = findViewById(R.id.person_edit_img_back);
        mPersonEditTvTitle = findViewById(R.id.person_edit_tv_title);
        mPersonEditTvLogout = findViewById(R.id.person_edit_tv_logout);
        mPersonEditLayoutPro = findViewById(R.id.person_edit_layout_pro);
        mPersonEditTvProChose = findViewById(R.id.person_edit_tv_pro_chose);
        mPersonEditTvProName = findViewById(R.id.person_edit_tv_pro_name);
        mPersonEditLayoutProTitle = findViewById(R.id.person_edit_layout_pro_title);
        mPersonEditLayoutVerify = findViewById(R.id.person_edit_layout_verify);
        mPersonEditImgFace = findViewById(R.id.person_edit_img_face);
        mPersonEditImgId = findViewById(R.id.person_edit_img_id);
        mPersonEditLayoutRight = findViewById(R.id.person_edit_layout_right);
        mPersonEditEtName = findViewById(R.id.person_edit_et_name);
        mPersonEditEtId = findViewById(R.id.person_edit_et_id);
        mPersonEditLayoutSex = findViewById(R.id.person_edit_layout_sex);
        mPersonEditTvSex = findViewById(R.id.person_edit_tv_sex);
        mPersonEditEtMinzu = findViewById(R.id.person_edit_et_minzu);
        mPersonEditLayoutBirthday = findViewById(R.id.person_edit_layout_birthday);
        mPersonEditTvBirthday = findViewById(R.id.person_edit_tv_birthday);
        mPersonEditEtAddress = findViewById(R.id.person_edit_et_address);
        mPersonEditEtPhone = findViewById(R.id.person_edit_et_phone);
        mPersonEditLayoutZhengzhi = findViewById(R.id.person_edit_layout_zhengzhi);
        mPersonEditTvZhengzhi = findViewById(R.id.person_edit_tv_zhengzhi);
        mPersonEditLayoutJiguan = findViewById(R.id.person_edit_layout_jiguan);
        mPersonEditTvJiguan = findViewById(R.id.person_edit_tv_jiguan);
        mPersonEditLayoutWenhua = findViewById(R.id.person_edit_layout_wenhua);
        mPersonEditTvWenhua = findViewById(R.id.person_edit_tv_wenhua);
        mPersonEditLayoutLeader = findViewById(R.id.person_edit_layout_leader);
        mPersonEditTvLeader = findViewById(R.id.person_edit_tv_leader);
        mPersonEditLayoutPeixun = findViewById(R.id.person_edit_layout_peixun);
        mPersonEditTvPeixun = findViewById(R.id.person_edit_tv_peixun);
        mPersonEditLayoutChizheng = findViewById(R.id.person_edit_layout_chizheng);
        mPersonEditTvChizheng = findViewById(R.id.person_edit_tv_chizheng);
        mPersonEditEtZhengshu = findViewById(R.id.person_edit_et_zhengshu);
        mPersonEditEtHetong = findViewById(R.id.person_edit_et_hetong);
        mPersonEditLayoutUnit = findViewById(R.id.person_edit_layout_unit);
        mPersonEditTvUnit = findViewById(R.id.person_edit_tv_unit);
        mPersonEditLayoutTeam = findViewById(R.id.person_edit_layout_team);
        mPersonEditLayoutRole = findViewById(R.id.person_edit_layout_role);
        mPersonEditTvRole = findViewById(R.id.person_edit_tv_role);
        mPersonEditLayoutWorktype = findViewById(R.id.person_edit_layout_worktype);
        mPersonEditTvWorktype = findViewById(R.id.person_edit_tv_worktype);
        mPersonEditTvFanchang = findViewById(R.id.person_edit_tv_fanchang);
        mPersonEditTvAddinfo = findViewById(R.id.person_edit_tv_addinfo);
        mPersonEditTvTeam = findViewById(R.id.person_edit_tv_team);
        person_edit_textureView = findViewById(R.id.person_edit_textureView);
        person_edit_layout_info = findViewById(R.id.person_edit_layout_info);
        person_edit_tv_result = findViewById(R.id.person_edit_tv_result);
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
        id = bundle.getString("id");
    }
    
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
    }
    
    private void initSDK() {
        CameraUtils.getInstance().startCamera(person_edit_textureView, this);
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
                                    runOnUiThread(() -> {
                                        mPersonEditImgId.setImageBitmap(bitmap);
                                        idPhoto = ImageUtil.bitmapToBase64(bitmap);
                                        name = idCard.getName();
                                        minzu = idCard.getNation();
                                        address = idCard.getAddress();
                                        id_number = idCard.getId();
                                        String birth = idCard.getBirth();
                                        birth = birth.replace("日", "").replace("年", "-").replace("月", "-");
                                        mPersonEditEtName.setText(idCard.getName());
                                        mPersonEditEtId.setText(idCard.getId());
                                        mPersonEditTvSex.setText(idCard.getSex());
                                        mPersonEditEtMinzu.setText(idCard.getNation() + "族");
                                        mPersonEditTvBirthday.setText(birth);
                                        mPersonEditEtAddress.setText(idCard.getAddress());
                                    });
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
                                        mPersonEditImgFace.setImageBitmap(rotateBitmap);
                                        facePhoto = ImageUtil.bitmapToBase64(rotateBitmap);
                                        BigDecimal bigDecimal_split = new BigDecimal(split[1]);
                                        BigDecimal bigDecimal_p = bigDecimal_split.setScale(2, RoundingMode.HALF_UP);//保留两位小数
                                        BigDecimal bigDecimal_100 = new BigDecimal("100");
                                        BigDecimal bigDecimal_result = bigDecimal_p.multiply(bigDecimal_100);
                                        person_edit_tv_result.setText("相似度:" + bigDecimal_result.toString() + "%");
                                        person_edit_textureView.setVisibility(View.GONE);
                                        mPersonEditLayoutRight.setVisibility(View.VISIBLE);
                                        stopCompare();
                                        deleteFile(file.getName());
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
                            String[] split = message.split("\\|");
                            try {
                                byte[] decodeBytes = Base64Utils.decode(split[0]);  //人脸照片
                                YuvImage image = new YuvImage(decodeBytes, ImageFormat.NV21, 640, 480, null);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                image.compressToJpeg(new Rect(0, 0, 640, 480), 80, stream);
                                Bitmap bitmap1 = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                                Bitmap rotateBitmap = CameraUtils.getInstance().rotate(bitmap1, 90f);
                                mPersonEditImgFace.setImageBitmap(rotateBitmap);
                                facePhoto = ImageUtil.bitmapToBase64(rotateBitmap);
                                person_edit_textureView.setVisibility(View.GONE);
                                stopCompare();
                                
                            } catch(UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
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
                mPersonEditTvBirthday.setText(format);
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
        mPersonEditTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtil.clearUserData();
                Intent i = new Intent(PersonEditActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        mPersonEditImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPersonEditLayoutSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_sex, popView_sex);
            }
        });
        mPersonEditLayoutLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_leader, popView_leader);
            }
        });
        mPersonEditLayoutPeixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_peixun, popView_peixun);
            }
        });
        mPersonEditLayoutChizheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_chizheng, popView_chizheng);
            }
        });
        
        mPersonEditLayoutZhengzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_zhengzhi, popView_zhengzhi);
            }
        });
        mPersonEditLayoutJiguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_jiguan, popView_jiguan);
            }
        });
        mPersonEditLayoutWenhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_wenhua, popView_wenhua);
            }
        });
        mPersonEditLayoutUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_unit, popView_unit);
            }
        });
        mPersonEditLayoutTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_team, popView_team);
            }
        });
        mPersonEditLayoutRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_role, popView_role);
            }
        });
        mPersonEditLayoutWorktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_worktype, popView_worktype);
            }
        });
        mPersonEditTvProChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShow(popupWindow_pro, popView_pro);
            }
        });
        mPersonEditLayoutBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                pvTime.show();
            }
        });
        person_edit_layout_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(View.VISIBLE != person_edit_textureView.getVisibility()) {
                    person_edit_textureView.setVisibility(View.VISIBLE);
                    readId();
                }
            }
        });
        mPersonEditTvAddinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请填写正确的姓名");
                }
                else if(TextUtils.isEmpty(id_number)) {
                    ToastUtil.showToast("请填写正确的身份证号");
                }
                else if(TextUtils.isEmpty(mPersonEditTvSex.getText().toString())) {
                    ToastUtil.showToast("请选择正确的性别");
                }
                else if(TextUtils.isEmpty(minzu)) {
                    ToastUtil.showToast("请填写正确的民族");
                }
                else if(TextUtils.isEmpty(mPersonEditTvBirthday.getText().toString())) {
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
                    presenter.editEmployeesInfo(id, facePhoto, idPhoto, name,
                            id_number, mPersonEditTvSex.getText().toString(), mPersonEditTvBirthday.getText().toString(), minzu,
                            address, phone, mPersonEditTvZhengzhi.getText().toString(), mPersonEditTvJiguan.getText().toString(),
                            mPersonEditTvWenhua.getText().toString(), mPersonEditTvLeader.getText().toString(), mPersonEditTvPeixun.getText().toString(),
                            mPersonEditTvChizheng.getText().toString(), projectId, person_edit_unitId, person_edit_teamId,
                            person_edit_roleType, person_edit_workType, "", zhengshu_number, hetong_number);
                }
            }
        });
        mPersonEditTvFanchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.employeeBack(id);
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
                name = mPersonEditEtName.getText().toString();
                id_number = mPersonEditEtId.getText().toString();
                minzu = mPersonEditEtMinzu.getText().toString();
                address = mPersonEditEtAddress.getText().toString();
                phone = mPersonEditEtPhone.getText().toString();
                zhengshu_number = mPersonEditEtZhengshu.getText().toString();
                hetong_number = mPersonEditEtHetong.getText().toString();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        mPersonEditEtName.addTextChangedListener(textWatcher);
        mPersonEditEtId.addTextChangedListener(textWatcher);
        mPersonEditEtMinzu.addTextChangedListener(textWatcher);
        mPersonEditEtAddress.addTextChangedListener(textWatcher);
        mPersonEditEtPhone.addTextChangedListener(textWatcher);
        mPersonEditEtZhengshu.addTextChangedListener(textWatcher);
        mPersonEditEtHetong.addTextChangedListener(textWatcher);
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
        popupwindow_list.setAdapter(new ArrayAdapter<String>(PersonEditActivity.this, R.layout.item_popupwindow, R.id.item_tv, dataList));
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
                        person_edit_unitId = conditionsBean.units.get(index_unit).id;
                        break;
                    case 8:
                        index_team = arg2;
                        person_edit_teamId = conditionsBean.teams.get(index_team).id;
                        break;
                    case 9:
                        index_role = arg2;
                        person_edit_roleType = conditionsBean.types.get(index_role).id;
                        ArrayList<String> workTypeList = new ArrayList<>();
                        for(int x = 0; x < conditionsBean.types.get(index_role).value.size(); x++) {
                            workTypeList.add(conditionsBean.types.get(index_role).value.get(x).name);
                        }
                        setPopupWindow("工种", mPersonEditTvWorktype, workTypeList, popupWindow_worktype, popView_worktype, 10);
                        mPersonEditTvWorktype.setText(workTypeList.get(0));
                        person_edit_workType = conditionsBean.types.get(index_role).value.get(0).id;
                        break;
                    case 10:
                        index_worktype = arg2;
                        for(int i = 0; i < conditionsBean.types.size(); i++) {
                            if(person_edit_roleType.equals(conditionsBean.types.get(i).id)) {
                                for(int x = 0; x < conditionsBean.types.get(i).value.size(); x++) {
                                    person_edit_workType = conditionsBean.types.get(i).value.get(index_worktype).id;
                                }
                            }
                        }
                        break;
                    case 11:
                        index_pro = arg2;
                        break;
                }
            }
        });
        //popupWindow消失屏幕变为不透明
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
        setPopupWindow("性别", mPersonEditTvSex, sexList, popupWindow_sex, popView_sex, 0);
        setPopupWindow("政治面貌", mPersonEditTvZhengzhi, zhengzhiList, popupWindow_zhengzhi, popView_zhengzhi, 1);
        setPopupWindow("籍贯", mPersonEditTvJiguan, jiguanList, popupWindow_jiguan, popView_jiguan, 2);
        setPopupWindow("文化程度", mPersonEditTvWenhua, wenhuaList, popupWindow_wenhua, popView_wenhua, 3);
        setPopupWindow("班组长", mPersonEditTvLeader, booleanList, popupWindow_leader, popView_leader, 4);
        setPopupWindow("参加培训", mPersonEditTvPeixun, booleanList, popupWindow_peixun, popView_peixun, 5);
        setPopupWindow("持证上岗", mPersonEditTvChizheng, booleanList, popupWindow_chizheng, popView_chizheng, 6);
        setPopupWindow("所属单位", mPersonEditTvUnit, unitList, popupWindow_unit, popView_unit, 7);
        setPopupWindow("所属班组", mPersonEditTvTeam, teamList, popupWindow_team, popView_team, 8);
        setPopupWindow("角色", mPersonEditTvRole, roleList, popupWindow_role, popView_role, 9);
        presenter.getEmployeeInfo(id);
    }
    
    @Override
    public void setProjectInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
        pro_names = new ArrayList<>();
        for(int i = 0; i < userInfo.projects.size(); i++) {
            pro_names.add(userInfo.projects.get(i).name);
        }
        setPopupWindow("选择项目", mPersonEditTvProName, pro_names, popupWindow_pro, popView_pro, 11);
        mPersonEditTvProName.setText(pro_names.get(0));
    }
    
    @Override
    public void setEmployeeInfo(EmployeeBean employeeBean) {
        this.employeeBean = employeeBean;
        Glide.with(PersonEditActivity.this).load(employeeBean.employee.idPhotoPath).apply(ImageUtil.getRequestOptions()).into(mPersonEditImgFace);
        Glide.with(PersonEditActivity.this).load(employeeBean.employee.photoPath).apply(ImageUtil.getRequestOptions()).into(mPersonEditImgId);
        mPersonEditEtName.setText(employeeBean.employee.name);
        mPersonEditEtId.setText(employeeBean.employee.idCard);
        mPersonEditTvSex.setText(employeeBean.employee.sex);
        mPersonEditEtMinzu.setText(employeeBean.employee.nation);
        mPersonEditTvBirthday.setText(employeeBean.employee.birthday);
        mPersonEditEtAddress.setText(employeeBean.employee.address);
        mPersonEditEtPhone.setText(employeeBean.employee.tel);
        mPersonEditTvZhengzhi.setText(employeeBean.employee.politicalStatus);
        mPersonEditTvJiguan.setText(employeeBean.employee.nativePlace);
        mPersonEditTvWenhua.setText(employeeBean.employee.education);
        mPersonEditTvLeader.setText(employeeBean.employee.ifForeman);
        mPersonEditTvPeixun.setText(employeeBean.employee.ifTrain);
        mPersonEditTvChizheng.setText(employeeBean.employee.ifCertificate);
        mPersonEditEtHetong.setText(employeeBean.employee.contractSn);
        mPersonEditEtZhengshu.setText(employeeBean.employee.certificateSn);
        for(int i = 0; i < conditionsBean.units.size(); i++) {
            if(employeeBean.employee.unitId.equals(conditionsBean.units.get(i).id)) {
                person_edit_unitId = conditionsBean.units.get(i).id;
                mPersonEditTvUnit.setText(conditionsBean.units.get(i).name);
            }
            
        }
        for(int i = 0; i < conditionsBean.teams.size(); i++) {
            if(employeeBean.employee.teamId.equals(conditionsBean.teams.get(i).id)) {
                mPersonEditTvTeam.setText(conditionsBean.teams.get(i).name);
                person_edit_teamId = conditionsBean.teams.get(i).id;
            }
            
        }
        for(int i = 0; i < conditionsBean.roles.size(); i++) {
            if(employeeBean.employee.roleType.equals(conditionsBean.roles.get(i).id)) {
                mPersonEditTvRole.setText(conditionsBean.roles.get(i).name);
                person_edit_roleType = conditionsBean.roles.get(i).id;
            }
            
        }
        workTypeList = new ArrayList<>();
        for(int i = 0; i < conditionsBean.types.size(); i++) {
            for(int x = 0; x < conditionsBean.types.get(i).value.size(); x++) {
                if(employeeBean.employee.workType.equals(conditionsBean.types.get(i).value.get(x).id)) {
                    mPersonEditTvWorktype.setText(conditionsBean.types.get(i).value.get(x).name);
                    person_edit_workType = conditionsBean.types.get(i).value.get(x).id;
                }
                if(employeeBean.employee.roleType.equals(conditionsBean.types.get(i).id)) {
                    workTypeList.add(conditionsBean.types.get(i).value.get(x).name);
                }
            }
            
        }
        setPopupWindow("工种", mPersonEditTvWorktype, workTypeList, popupWindow_worktype, popView_worktype, 10);
        if(employeeBean.employee.returnStatus) {
            mPersonEditTvFanchang.setVisibility(View.VISIBLE);
        }
        else {
            mPersonEditTvFanchang.setVisibility(View.GONE);
        }
        
    }
    
    @Override
    public void editResult(String s) {
        ToastUtil.showToast(s);
        finish();
        EventBus.getDefault().post(new EditEvent());
    }
    
}
