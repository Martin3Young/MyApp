package yunfucloud.com.myapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yunfucloud.com.myapp.R;
import yunfucloud.com.myapp.adapter.ShopcartExpandableListViewAdapter;
import yunfucloud.com.myapp.bean.DataBean;
import yunfucloud.com.myapp.bean.GroupInfo;
import yunfucloud.com.myapp.bean.ProductInfo;
import yunfucloud.com.myapp.view.SuperExpandableListView;

/**
 * Created by Martin on 2018/1/12.
 *
 * @新浪微博: http://weibo.com/2603687001
 * @GitHub: https://github.com/Martin3Young
 * @CSDN: http://blog.csdn.net/qq_32346021
 * @简书: http://www.jianshu.com/u/6d64225b1910
 */

public class ShoppingCarActivity extends AppCompatActivity implements ShopcartExpandableListViewAdapter.CheckInterface, View.OnClickListener,
        ShopcartExpandableListViewAdapter.ModifyCountInterface{

    SuperExpandableListView exListView;
    CheckBox mAllChekbox;
    TextView mTvTotalPrice;
    TextView mTvGoToPay;
    LinearLayout mCaculateView;
    TextView mTvForwardPay;

    private ShopcartExpandableListViewAdapter mAdapter;

    private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
    private Map<String, List<ProductInfo>> children = new HashMap<>();// 子元素数据列表

    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    private String itemId = "";   //项目id
    private ArrayList<String> repayIdList = new ArrayList<>();    //还款计划id
    private String repayIds = "";

    private List<DataBean> dataBeans = new ArrayList<>();

    private boolean isShowSelect = false;    //展示前面选择按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        intiView();
        initClick();
        getData();
        initData();
        initEvents();

    }

    private void intiView() {
        exListView = (SuperExpandableListView) findViewById(R.id.exListView);
        mAllChekbox = (CheckBox) findViewById(R.id.all_chekbox);
        mTvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        mTvGoToPay = (TextView) findViewById(R.id.tv_go_to_pay);
        mTvForwardPay = (TextView) findViewById(R.id.tv_forward_pay);
        mCaculateView = (LinearLayout) findViewById(R.id.caculate_view);
    }

    private void initClick() {
        ButtonListener listener = new ButtonListener();
        mTvForwardPay.setOnClickListener(listener);
    }

    private void getData() {
//        dataBeans
    }

    private void initData() {
        for (int i = 0; i <2; i++) {
            groups.add(new GroupInfo(i + "", 2017+i+"年"));
            List<ProductInfo> products = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Double repayAmount = 25.5;
                String repayInterest = "利息：0.5"  + "元";
                String repayFee = "服务费：0" +"元";
                String lateFee = "";
                String time = "";
                String name = "第" + j + "期";
                time = "还款时间：10/05" ;
                lateFee = "逾期费：0.5";
                products.add(new ProductInfo("1", "名称", name, repayAmount, 1, repayInterest, repayFee, lateFee, time, 1));
            }
            children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
        }
    }

    private void initEvents() {
        mAdapter = new ShopcartExpandableListViewAdapter(groups, children, this);
        mAdapter.setCheckInterface(this);// 关键步骤1,设置复选框接口
        mAdapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        exListView.setAdapter(mAdapter);
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }
        mAllChekbox.setOnClickListener(this);
        mTvGoToPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.all_chekbox:
                doCheckAll();
                break;
            case R.id.tv_go_to_pay:
                AlertDialog alert;
                if (totalCount == 0) {
                    Toast.makeText(this, "请选择", Toast.LENGTH_LONG).show();
                    return;
                }
                if (repayIdList.size() > 0) {
                    for (int i = 0; i < repayIdList.size(); i++) {
                        if (i == 0)
                            repayIds = repayIdList.get(i);
                        else
                            repayIds = "," + repayIdList.get(i);
                    }
                }
                alert = new AlertDialog.Builder(this).create();
                alert.setTitle("操作提示");
                alert.setMessage("总计:\n" +String.valueOf(totalPrice) + "元\n" + "ID:" + repayIds);
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alert.show();
                break;
        }
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            mAllChekbox.setChecked(true);
        else
            mAllChekbox.setChecked(false);
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    private boolean isAllCheck() {
//        for (GroupInfo group : groups) {
//            if (!group.isChoosed())
//                return false;
//        }
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
//                if (!"1".equals(billList.get(i).getList().get(j).getStatus()))
                    if(!childs.get(j).isChoosed()){
                        return false;
                    }
            }
        }
        return true;
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                ProductInfo product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                    repayIdList.add(product.getId());
                }
            }
        }
        if (totalPrice == 0) {
            mTvTotalPrice.setText("￥0.00");
        } else {
            mTvTotalPrice.setText("￥" + String.valueOf(totalPrice));
        }
        mTvGoToPay.setText("立即付款");
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            mAllChekbox.setChecked(true);
        else
            mAllChekbox.setChecked(false);
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

    }

    /**
     * 显示隐藏选择框
     */
    private void changeCheck() {
        if (isShowSelect) {
            //如果隐藏，则显示出来
            mTvForwardPay.setText("立即还款");
            isShowSelect = false;
            for (int i = 0; i < groups.size(); i++) {
                groups.get(i).setShowCheck(isShowSelect);
                GroupInfo group = groups.get(i);
                List<ProductInfo> childs = children.get(group.getId());
                for (int j = 0; j < childs.size(); j++) {
//                    if (!"1".equals(billList.get(i).getList().get(j).getStatus()))
                        childs.get(j).setShowCheck(isShowSelect);
                }
            }
            mCaculateView.setVisibility(View.GONE);
        } else {
            //如果已经显示，则隐藏
            mTvForwardPay.setText("取消");
            isShowSelect = true;
            for (int i = 0; i < groups.size(); i++) {
                groups.get(i).setShowCheck(isShowSelect);
                GroupInfo group = groups.get(i);
                List<ProductInfo> childs = children.get(group.getId());
                for (int j = 0; j < childs.size(); j++) {
//                    if (!"1".equals(billList.get(i).getList().get(j).getStatus()))
                        childs.get(j).setShowCheck(isShowSelect);
                }
            }
            mCaculateView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(mAllChekbox.isChecked());
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
//                if (!"1".equals(billList.get(i).getList().get(j).getStatus()))
                    childs.get(j).setChoosed(mAllChekbox.isChecked());
            }
        }
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.tv_forward_pay:
                    changeCheck();
                    break;
                case R.id.all_chekbox:
                    doCheckAll();
                    break;
                case R.id.tv_go_to_pay:
                    Toast.makeText(ShoppingCarActivity.this,"asd",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
