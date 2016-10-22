package com.palmap.demo.huaweih2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.palmap.demo.huaweih2.model.ParkInfo;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

/**
 * Created by eric3 on 2016/10/19.
 */

public class ActivityPay extends BaseActivity {
  TextView tv_car_num;
  TextView tv_car_pos;
  TextView tv_car_hour;
  TextView tv_car_money;
  ParkInfo mParkInfo;
  TitleBar titleBar;
  TextView btn_pay;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pay);
    tv_car_num = (TextView)findViewById(R.id.carNum);
    tv_car_pos = (TextView)findViewById(R.id.carPosi);
    tv_car_hour = (TextView)findViewById(R.id.carTime);
    tv_car_money = (TextView)findViewById(R.id.carMoney);
    titleBar = (TitleBar)findViewById(R.id.title_bar);
    mParkInfo = (ParkInfo) getIntent().getExtras().getParcelable("parkInfo");
    btn_pay = (TextView) findViewById(R.id.btn_pay);
    btn_pay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DialogUtils.showDialog(ActivityPay.this, "支付成功", new DialogUtils.DialogCallBack() {
          @Override
          public void onComplete() {
//            Bundle bundle = new Bundle();
//            bundle.putString("pay", edittext.getText().toString());//给 bundle 写入数据
//            Intent mIntent = new Intent();
//            mIntent.putExtras(bundle);
            setResult(RESULT_OK);
            finish();
          }

          @Override
          public void onCancel() {

          }
        });
      }
    });
    titleBar.show(null,"支付",null);
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        setResult(RESULT_CANCELED);
        finish();
      }
      @Override
      public void onRight() {
      }
    });
    tv_car_num.setText(mParkInfo.getCarNum());
    tv_car_pos.setText(mParkInfo.getArea()+"-"+mParkInfo.getCarPosition());
    tv_car_hour.setText(mParkInfo.getHour());
    tv_car_money.setText(mParkInfo.getMoney());
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