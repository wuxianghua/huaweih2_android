package com.palmap.demo.huaweih2.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.util.QQShareUtils;
import com.palmap.demo.huaweih2.util.WXShareUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * Created by 王天明 on 2016/10/22.
 * 分享UI视图
 */
public class SharePopView {

    private static Activity mContext = null;
    public static class ShareModel{
        public String title;
        public String text;
        public String imgUrl;
        public Bitmap urlBmp;
    }

    public static void showSharePop(Activity context,ShareModel shareModel) {

        ContentPopWindow popupWindow = new ContentPopWindow(context,shareModel);

        popupWindow.showAtLocation(context.findViewById(android.R.id.content),
                Gravity.BOTTOM, 0, 0);

        mContext = context;
    }

    private static class ContentPopWindow extends PopupWindow implements View.OnClickListener{

        private ShareModel shareModel;

        private ViewGroup layout_share_weChat, layout_share_weiChat_friends, layout_share_weibo, layout_share_qq;

        public ContentPopWindow(Context context,ShareModel shareModel) {
            super(context);
            this.shareModel = shareModel;
            View contentView = LayoutInflater.from(context).inflate(R.layout.pop_share, null, false);
            //设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            //设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            //设置SelectPicPopupWindow弹出窗体动画效果
            this.setAnimationStyle(R.style.mypopwindow_anim_style);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);
            bindView(contentView);
            this.setContentView(contentView);
        }

        private void bindView(View rootView) {
            View.OnClickListener nullClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };
            View.OnClickListener disMissClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            };

            rootView.findViewById(R.id.tv_shareTitle).setOnClickListener(nullClick);
            rootView.findViewById(R.id.layout_share_cancel).setOnClickListener(nullClick);
            rootView.findViewById(R.id.tv_cancel).setOnClickListener(disMissClick);
            rootView.setOnClickListener(disMissClick);


            layout_share_weChat = (ViewGroup) rootView.findViewById(R.id.layout_share_weChat);
            layout_share_weiChat_friends = (ViewGroup) rootView.findViewById(R.id.layout_share_weiChat_friends);
            layout_share_weibo = (ViewGroup) rootView.findViewById(R.id.layout_share_weibo);
            layout_share_qq = (ViewGroup) rootView.findViewById(R.id.layout_share_qq);

            layout_share_weChat.setOnClickListener(this);
            layout_share_weiChat_friends.setOnClickListener(this);
            layout_share_weibo.setOnClickListener(this);
            layout_share_qq.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getContentView().getContext(),"点击了分享",1).show();
            switch (v.getId()) {
                case R.id.layout_share_weChat:
                    //分享到微信
                    WXShareUtils.sendToWeChat(shareModel.urlBmp,shareModel.text, SendMessageToWX.Req.WXSceneSession);
//                    WXShareUtils.sendTextToWeChat(shareModel.text);
                    break;

                case R.id.layout_share_weiChat_friends:
                    //分享到朋友圈
                    WXShareUtils.sendToWeChat(shareModel.urlBmp,shareModel.text, SendMessageToWX.Req.WXSceneTimeline);
                    break;

                case R.id.layout_share_weibo:
                    //分享到微博
                    break;

                case R.id.layout_share_qq:
                    //分享到QQ
                    QQShareUtils.getInstance().shareToQQ(mContext,shareModel);
//                    QQShareUtils.getInstance().shareToQzone(mContext,shareModel);
                    break;
            }
            dismiss();
        }
    }

}

