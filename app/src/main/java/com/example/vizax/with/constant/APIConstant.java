package com.example.vizax.with.constant;

/**
 * Created by wu on 2016/9/13.
 */
public class APIConstant {
    public static final String WITH_BASE  = "http://rap.taobao.org/mockjs/7569/";//获取邀约列表

   //---------------------------------------------------------邀约活动模块---------------------------------------------------------------------
    public static final String INVITATION_GETINVITATIONS           = "invitation/getInvitations";//获取邀约列表
    public static final String INVITATION_PUBLISHINVITATION        = "invitation/publishInvitation";//发起邀约
    public static final String INVITAION_ALTERINVITATION           = "invitation/alterInvitation";//维护、修改邀约
    public static final String INVITAION_PARTICIPATEINVITATION     = "invitation/participateInvitation";//参加邀约/申请加入特批
    public static final String INVITATION_DELETEINVITATION         = "invitation/deleteInvitation";//删除指定邀约
    public static final String INVITATION_GETCONCERNEDUSERS        = "invitation/getConcernedUsers";//获取我关注的用户列表
    public static final String INVITATION_CONCERNUSER              = "invitation/concernUser";//关注/取消关注某用户

    //---------------------------------------------------------坚持模块---------------------------------------------------------------------
    public static final String KEEP_GETTASKS         = "keep/getTasks";//获取任务列表(正在进行的任务和过去的任务)
    public static final String KEEP_CREATETASK       = "keep/createTask";//新建任务
    public static final String KEEP_GETTASKMESSAGE   = "keep/getTaskMessage";//获取某任务指定某月份下的所有相关信息
    public static final String KEEP_SIGNIN           = "keep/signIn ";//打卡签到
    public static final String KEEP_EDITTASKMESSAGE  = "keep/editTaskMessage";//打卡编辑

    //---------------------------------------------------------个人用户模块---------------------------------------------------------------------
    public static final String USER_LOGIN                     = "user/login";//登录
    public static final String USER_REGISTERVERTIFICATION     = "user/registerVertification";//注册验证
    public static final String USER_GETVERTIFICATIONCODE      = "user/getVertificationCode";//获取验证码
    public static final String USER_REGISTER                  = "user/register";//注册
    public static final String USER_FINDBACKPASSWORD          = "user/findBackPassWord";//找回密码
    public static final String USER_UPLOADHEADPIC             = "user/uploadHeadPic";//上传用户头像
    public static final String USER_GETMESSAGES               = "user/getMessages";//获取我的收到的信息列表
    public static final String USER_DELETEMESSAGE             = "user/deleteMessage";//删除消息
    public static final String USER_ACCEPTMESSAGE             = "user/acceptMessage";//批准特批
    public static final String USER_GETUSERINFO               = "user/getUserInfo";//获取指定用户的信息
    public static final String USER_CHANGPASSWORD             = "user/changPassword";//密码修改


    public static String getApi(String url){
        return WITH_BASE+url;
    }
}
