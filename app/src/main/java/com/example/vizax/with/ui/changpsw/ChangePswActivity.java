package com.example.vizax.with.ui.changpsw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.customView.BaseToolBar;

import com.example.vizax.with.ui.myhome.HomeActivity;
import com.example.vizax.with.util.AnimationUtil;
import com.example.vizax.with.util.SnackbarUtils;
import com.example.vizax.with.util.TextUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePswActivity extends BaseActivity implements  ChangePswContact.View{

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.changepsw_oldpassword_edtTxt)
    EditText changepswOldpasswordEdtTxt;
    @BindView(R.id.changepsw_oldpassword_textInputLayout)
    TextInputLayout changepswOldpasswordTextInputLayout;
    @BindView(R.id.changepsw_newpassword_edtTxt)
    EditText changepswNewpasswordEdtTxt;
    @BindView(R.id.changepsw_newpassword_textInputLayout)
    TextInputLayout changepswNewpasswordTextInputLayout;
    @BindView(R.id.changepsw_surenewpassword_edtTxt)
    EditText changepswSurenewpasswordEdtTxt;
    @BindView(R.id.changepsw_surenewpassword_textInputLayout)
    TextInputLayout changepswSurenewpasswordTextInputLayout;
    @BindView(R.id.changepsw_changepsw_btn)
    Button changepswChangepswBtn;
    @BindView(R.id.root)
    LinearLayout root;
    private ChangePswPresenter mPresenter;
    private MaterialDialog mDialog;

    @Override
    protected int initContentView() {
        return R.layout.activity_changepsw;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mDialog = new MaterialDialog.Builder(this)
                .content("正在提交...")
                .progress(true, 0)
                .build();

        toolbar.setCenterText("个人资料");
        toolbar.setLeftIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left));
        AnimationUtil.showCircularReveal(root,2,2000);

        /*Presenter和视图进行绑定
        大家以后有时间可以学学dragger2
        因为，解耦，可以实现依赖注入，不用构造方法。好处还有好多好多。。。*/
        mPresenter = new ChangePswPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void changepswSuccess(String Msg) {
        SnackbarUtils.show(root, Msg, 0, null);///??????????????????

        Intent intent = new Intent(this, HomeActivity.class);
        /*动画效果开启下个activity。有toolbar的话，toolbar可以不动，只改变视图
          建议自己可以加个toolbar试试*/
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void changepswFailure(String Error) {
        SnackbarUtils.show(root, Error, 0, null);
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void dimissloading() {
        mDialog.dismiss();
    }


    @Override
    public void passwordsetError() {//修改的密码不一致
        changepswNewpasswordEdtTxt.setError("密码不一致");
        changepswSurenewpasswordEdtTxt.setError("密码不一致");
    }
//匹配长度4444
    private  static  boolean match(String regex,String str){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return  matcher.matches();
    }
    private static  boolean passwordLong(String str){
        String regex = "^\\d{2,18}$";
        return  match(regex,str);
    }


    @OnClick(R.id.changepsw_changepsw_btn)
    public void Onclik(){
        if(TextUtil.getText(changepswOldpasswordEdtTxt).equals("")){
            changepswOldpasswordEdtTxt.setError("不能为空");
        }
        else if(!passwordLong(TextUtil.getText(changepswNewpasswordEdtTxt))){

            changepswNewpasswordEdtTxt.setError("密码长度2-16位");
        }
        else if(TextUtil.getText(changepswNewpasswordEdtTxt).equals(TextUtil.getText(changepswSurenewpasswordEdtTxt))){
            mPresenter.changepsw(TextUtil.getText(changepswOldpasswordEdtTxt), TextUtil.getText(changepswNewpasswordEdtTxt));
        }
        else {
            passwordsetError();
        }

    }


    ;


}
