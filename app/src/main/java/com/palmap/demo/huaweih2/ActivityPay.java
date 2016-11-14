package com.palmap.demo.huaweih2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.model.ParkInfo;
import com.palmap.demo.huaweih2.view.TitleBar;

/**
 * Created by eric3 on 2016/10/19.
 */

public class ActivityPay extends BaseActivity implements View.OnClickListener {
    TextView tv_car_num;
    TextView tv_car_pos;
    TextView tv_car_hour;
    TextView tv_car_money;
    ParkInfo mParkInfo;
    TitleBar titleBar;
    TextView btn_pay;
    RelativeLayout success;
    RelativeLayout qqpay;
    RelativeLayout wxpay;
    RelativeLayout alipay;
    ImageView qqpaysel;
    ImageView wxpaysel;
    ImageView alipaysel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);


        success = (RelativeLayout) findViewById(R.id.success);
        success.setVisibility(View.GONE);

        qqpay = (RelativeLayout) findViewById(R.id.qqpay);
        qqpay.setOnClickListener(this);
        wxpay = (RelativeLayout) findViewById(R.id.wxpay);
        wxpay.setOnClickListener(this);
        alipay = (RelativeLayout) findViewById(R.id.alipay);
        alipay.setOnClickListener(this);
        qqpaysel = (ImageView) findViewById(R.id.qqpaysel);
        wxpaysel = (ImageView) findViewById(R.id.wxpaysel);
        alipaysel = (ImageView) findViewById(R.id.alipaysel);

        tv_car_num = (TextView) findViewById(R.id.carNum);
        tv_car_pos = (TextView) findViewById(R.id.carPosi);
        tv_car_hour = (TextView) findViewById(R.id.carTime);
        tv_car_money = (TextView) findViewById(R.id.carMoney);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        mParkInfo = (ParkInfo) getIntent().getExtras().getParcelable("parkInfo");
        btn_pay = (TextView) findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                success.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }
                }, 1000);

//        DialogUtils.showDialog(ActivityPay.this, "支付成功", new DialogUtils.DialogCallBack() {
//          @Override
//          public void onComplete() {
////            Bundle bundle = new Bundle();
////            bundle.putString("pay", edittext.getText().toString());//给 bundle 写入数据
////            Intent mIntent = new Intent();
////            mIntent.putExtras(bundle);
//
//
//
//          }
//
//          @Override
//          public void onCancel() {
//
//          }
//        });
            }
        });
        titleBar.show(null, "在线支付", null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                setResult(RESULT_OK, getIntent());
                finish();
            }

            @Override
            public void onRight() {
            }
        });
        tv_car_num.setText(mParkInfo.getCarNum());
        tv_car_pos.setText(mParkInfo.getArea() + "-" + mParkInfo.getCarPosition());
        tv_car_hour.setText(mParkInfo.getHour());
        tv_car_money.setText(mParkInfo.getMoney());
    }

    @Override
    public void onClick(View v) {
        wxpaysel.setBackgroundResource(R.drawable.ico_pay_nor);
        qqpaysel.setBackgroundResource(R.drawable.ico_pay_nor);
        alipaysel.setBackgroundResource(R.drawable.ico_pay_nor);
        switch (v.getId()) {
            case R.id.wxpay:
                wxpaysel.setBackgroundResource(R.drawable.ico_pay_sel);
                break;
            case R.id.alipay:
                alipaysel.setBackgroundResource(R.drawable.ico_pay_sel);
                break;
            case R.id.qqpay:
                qqpaysel.setBackgroundResource(R.drawable.ico_pay_sel);
                break;
        }
    }

//  @Override
//  public View onView(LayoutInflater inflater,
//                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//    // TODO Auto-generated method stub
//    View fragmentView = inflater.inflate(R.layout.pay, container, false);
//
//    tv_car_num = (TextView)fragmentView.findViewById(R.id.carNum);
//    tv_car_pos = (TextView)fragmentView.findViewById(R.id.carPosi);
//    tv_car_hour = (TextView)fragmentView.findViewById(R.id.carTime);
//    tv_car_money = (TextView)fragmentView.findViewById(R.id.carMoney);
//    mParkInfo = (ParkInfo) getArguments().getParcelable("parkInfo");
//
//
//
//    return fragmentView;
//  }
//
//  @Override
//  public void onResume() {
//    super.onResume();
//    tv_car_num.setText(mParkInfo.getCarNum());
//    tv_car_pos.setText(mParkInfo.getArea()+"-"+mParkInfo.getCarPosition());
//    tv_car_hour.setText(mParkInfo.getHour());
//    tv_car_money.setText(mParkInfo.getMoney());
//  }
}