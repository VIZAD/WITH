package com.example.vizax.with.ui.login.verify;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.ui.login.MainActivity;
import com.example.vizax.with.util.MaxLengthWatcher;
import com.example.vizax.with.util.TextUtil;
import com.example.vizax.with.util.swipeback.MsgVerifyUtil;

import org.apache.http.conn.scheme.HostNameResolver;

import java.util.HashMap;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


/**
 * Created by Administrator on 2016/9/14.
 */
public class VerifyFragment extends Fragment implements VerifyContact.View {

    @BindView(R.id.usernum_edtTxt)
    EditText mUsernum_edtTxt;
    @BindView(R.id.username_edtTxt)
    EditText mUsername_edtTxt;
    @BindView(R.id.sex)
    TextView mSex;
    @BindView(R.id.sex_rdoGrp)
    RadioGroup mSex_rdoGrp;
    @BindView(R.id.verify_btn)
    Button mVerify_btn;
    @BindView(R.id.verify_lLayout)
    LinearLayout verifyLLayout;

    /***************
     * 注册模块
     ***************/
    @BindView(R.id.reg_lLayout)
    LinearLayout regLLayout;
    @BindView(R.id.reg_verify_btn)
    Button mRegVerify_btn;
    @BindView(R.id.reg_usernum_edtTxt)
    EditText mRegUsernumEdtTxt;
    @BindView(R.id.reg_verifynum_edtTxt)
    EditText mRegVerifynumEdtTxt;
    @BindView(R.id.psw_reg_edtTxt)
    EditText mPswRegEdtTxt;
    @BindView(R.id.reg_conpsw_edtTxt)
    EditText mRegConpswEdtTxt;
    @BindView(R.id.reg_btn)
    Button mRegBtn;


    private AppCompatActivity mActivity;
    private View mView, mViewDialog;
    private String mUsernum_str, mUsername_str;
    private String mSex_str = "0";
    private MaterialDialog mDialog, mVerifyDialog;
    private VerifyPresenter mVerifyPresenter;
    private AlertDialog mVerfiryDialog;
    private AlertDialog.Builder builder;
    private String mRegUsernum_str, mRegVerifynum_str, mPswReg_str, mRegConpsw_str;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.verify_fragment, container, false);
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    MainActivity mainActivity;

    private void initView() {
        mRegUsernumEdtTxt.setText("");
        mRegVerifynumEdtTxt.setText("");
        mPswRegEdtTxt.setText("");
        mRegConpswEdtTxt.setText("");

        mUsernum_edtTxt.addTextChangedListener(new MaxLengthWatcher(12, mUsernum_edtTxt));
        mUsername_edtTxt.addTextChangedListener(new MaxLengthWatcher(20, mUsername_edtTxt));

        mRegUsernumEdtTxt.addTextChangedListener(new MaxLengthWatcher(11, mRegUsernumEdtTxt));
        mRegVerifynumEdtTxt.addTextChangedListener(new MaxLengthWatcher(6, mRegVerifynumEdtTxt));
        mPswRegEdtTxt.addTextChangedListener(new MaxLengthWatcher(20, mRegUsernumEdtTxt));
        mRegConpswEdtTxt.addTextChangedListener(new MaxLengthWatcher(20, mRegUsernumEdtTxt));

        mActivity = (AppCompatActivity) getActivity();
        mainActivity = (MainActivity) mActivity;
        verifyLLayout.setVisibility(View.VISIBLE);
        regLLayout.setVisibility(View.GONE);

        mViewDialog = View.inflate(mActivity, R.layout.verify_dialog, null);
        Button button = (Button) mViewDialog.findViewById(R.id.tologin_explainitem_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerfiryDialog.dismiss();
                mainActivity.showLoign();
                mUsernum_edtTxt.setText("");
                mUsername_edtTxt.setText("");
            }
        });

        mVerifyPresenter = new VerifyPresenter();
        mVerifyPresenter.attachView(this);

    }

    /**
     * 执行验证
     */
    private void verify() {
        mDialog = new MaterialDialog.Builder(mActivity)
                .content("正在验证...")
                .progress(true, 0)
                .build();

        mUsernum_str = TextUtil.getText(mUsernum_edtTxt);
        mUsername_str = TextUtil.getText(mUsername_edtTxt);
        mSex_rdoGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.male_rdoBtn)
                    mSex_str = "" + 0;
                else
                    mSex_str = "" + 1;
            }
        });
        Matcher m1 = TextUtil.compileExChar(mUsernum_str);
        Matcher m2 = TextUtil.compileExChar(mUsername_str);
        if (m1.find()) {
            Toast.makeText(mActivity, "不允许输入特殊字符", Toast.LENGTH_SHORT).show();
        } else if (m2.find()) {
            Toast.makeText(mActivity, "不允许输入特殊字符", Toast.LENGTH_SHORT).show();
        } else if (mUsernum_str.length() != 12) {
            Toast.makeText(mActivity, "请输入正确的学号", Toast.LENGTH_SHORT).show();
        } else if (mUsernum_str.isEmpty() == true) {
            Toast.makeText(mActivity, "学号不能为空", Toast.LENGTH_SHORT).show();
        } else if (mUsername_str.isEmpty() == true) {
            Toast.makeText(mActivity, "姓名不能为空", Toast.LENGTH_SHORT).show();
        } else {
            mVerifyPresenter.verify(mUsernum_str, mUsername_str, mSex_str);
        }
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void dimissLoading() {
        mDialog.dismiss();
    }

    @Override
    public void showDialog(String phone) {
        if (builder == null) {
            builder = new AlertDialog.Builder(mActivity);
            builder.setView(mViewDialog);
            mVerfiryDialog = builder.create();
            mVerfiryDialog.setCancelable(false);
        }
        TextView mPhone_txtVi = (TextView) mViewDialog.findViewById(R.id.phone_txtVi);
        mPhone_txtVi.setText(phone);

        mVerfiryDialog.show();

    }

    @Override
    public void verifySuccess(String Msg) {
        //验证成功
        Toast.makeText(mActivity, Msg, Toast.LENGTH_SHORT).show();
        regLLayout.setVisibility(View.VISIBLE);
        verifyLLayout.setVisibility(View.GONE);
        mUsernum_edtTxt.setText("");
        mUsername_edtTxt.setText("");
        mainActivity.setLayout(verifyLLayout, regLLayout,
                mRegUsernumEdtTxt,mRegVerifynumEdtTxt,mPswRegEdtTxt,mRegConpswEdtTxt);

    }

    @Override
    public void verifyFailure(String Error) {
        //验证失败
        Toast.makeText(mActivity,  Error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mVerifyPresenter.detachView();
    }

    @OnClick({R.id.verify_btn, R.id.reg_verify_btn, R.id.reg_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_btn:
                verify();
                break;
            case R.id.reg_verify_btn:
                MsgVerify();
                break;
            case R.id.reg_btn:
                register();
                break;

        }
    }


    /**
     * 注册模块
     */

    private void register() {
        mDialog = new MaterialDialog.Builder(mActivity)
                .content("正在注册...")
                .progress(true, 0)
                .build();
        mRegUsernum_str = TextUtil.getText(mRegUsernumEdtTxt);
        mRegVerifynum_str = TextUtil.getText(mRegVerifynumEdtTxt);
        mPswReg_str = TextUtil.getText(mPswRegEdtTxt);
        mRegConpsw_str = TextUtil.getText(mRegConpswEdtTxt);
        Matcher m = TextUtil.compileExChar(mRegUsernum_str);
        System.out.println(mRegVerifynum_str);
        if (m.find()) {
            Toast.makeText(mActivity, "不允许输入特殊字符", Toast.LENGTH_SHORT).show();
        } else if (mRegUsernum_str.length() != 11) {
            Toast.makeText(mActivity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        } else if (mPswReg_str.length() < 6) {
            Toast.makeText(mActivity, "密码不能小于6位", Toast.LENGTH_SHORT).show();
        } else if (mRegUsernum_str.isEmpty() == true) {
            Toast.makeText(mActivity, "手机号码不能为空", Toast.LENGTH_SHORT).show();
        } else if (mRegVerifynum_str.isEmpty() == true) {
            Toast.makeText(mActivity, "验证码不能为空", Toast.LENGTH_SHORT).show();
        } else if (mPswReg_str.isEmpty() == true) {
            Toast.makeText(mActivity, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (mRegConpsw_str.isEmpty() == true) {
            Toast.makeText(mActivity, "确认密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!mPswReg_str.equals(mRegConpsw_str)) {
            Toast.makeText(mActivity, "两次密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            //注册..
            mVerifyPresenter.register(mRegUsernum_str, mRegVerifynum_str, mPswReg_str, mUsernum_str);
        }

    }

    private void MsgVerify() {
        new MsgVerifyUtil(mRegVerify_btn, "%s秒", 60).start();//倒计时
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1)
                    Toast.makeText(mActivity, "提交验证码成功", Toast.LENGTH_SHORT).show();
                else if (msg.what == 2)
                    Toast.makeText(mActivity, "获取验证码成功", Toast.LENGTH_SHORT).show();
                else if (msg.what == 3)
                    Toast.makeText(mActivity, "回调完成", Toast.LENGTH_SHORT).show();
                else if (msg.what == 4)
                    Toast.makeText(mActivity, "有错", Toast.LENGTH_SHORT).show();
            }
        };
        //回调接口
        SMSSDK.initSDK(mActivity, "1711fda5318b2", "9b07d5a826f77429d7bdeefd79fa9786");
        mRegUsernum_str = TextUtil.getText(mRegUsernumEdtTxt);
        mVerifyPresenter.msgRegister(handler, mRegUsernum_str);
    }

    @Override
    public void showRegLoading() {
        mDialog.show();
    }

    @Override
    public void dimissRegLoading() {
        mDialog.dismiss();
    }

    @Override
    public void RegSuccess(String Msg) {
        Toast.makeText(mActivity,"注册成功"+  Msg, Toast.LENGTH_SHORT).show();
        mainActivity.showLoign();
        mRegUsernumEdtTxt.setText("");
        mRegVerifynumEdtTxt.setText("");
        mPswRegEdtTxt.setText("");
        mRegConpswEdtTxt.setText("");
    }

    @Override
    public void RegFailure(String Error) {
        Toast.makeText(mActivity,  Error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regVerifySuccess(String Msg) {
    }

    @Override
    public void regVerigyFailure(String Msg) {

    }


}
