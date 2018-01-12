package yunfucloud.com.myapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import yunfucloud.com.myapp.R;
import yunfucloud.com.myapp.bean.GroupInfo;
import yunfucloud.com.myapp.bean.ProductInfo;

public class ShopcartExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<GroupInfo> groups;
    private Map<String, List<ProductInfo>> children;
    private Context context;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private boolean isShow = false;

    /**
     * 构造函数
     *
     * @param groups   组元素列表
     * @param children 子元素列表
     * @param context
     */
    public ShopcartExpandableListViewAdapter(List<GroupInfo> groups, Map<String, List<ProductInfo>> children, Context context) {
        super();
        this.groups = groups;
        this.children = children;
        this.context = context;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = groups.get(groupPosition).getId();
        return children.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ProductInfo> childs = children.get(groups.get(groupPosition).getId());
        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder gholder;
        if (convertView == null) {
            gholder = new GroupHolder();
            convertView = View.inflate(context, R.layout.item_shopcart_group, null);
            gholder.cb_check = (CheckBox) convertView.findViewById(R.id.determine_chekbox);
            gholder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_source_name);
            //groupMap.put(groupPosition, convertView);
            //点击不回收子列表
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            convertView.setTag(gholder);
        } else {
            // convertView = groupMap.get(groupPosition);
            gholder = (GroupHolder) convertView.getTag();
        }
        final GroupInfo group = (GroupInfo) getGroup(groupPosition);
//        //隐藏第一个组元素头
//        if (groupPosition==0){
//            convertView.setVisibility(View.GONE);
//        }
        if (group != null) {
            gholder.tv_group_name.setText(group.getName());
            gholder.cb_check.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    group.setChoosed(((CheckBox) v).isChecked());
                    checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
                }
            });
            gholder.cb_check.setChecked(group.isChoosed());
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder cholder;
        if (convertView == null) {
            cholder = new ChildHolder();
            View view = View.inflate(context, R.layout.item_shopcart_product, null);
            convertView = view;
            cholder.cb_check = (CheckBox) convertView.findViewById(R.id.check_box);
            cholder.tv_product_desc = (TextView) convertView.findViewById(R.id.tv_intro);
            cholder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            cholder.tv_repayInterest = (TextView) convertView.findViewById(R.id.item_repay_interest);
            cholder.tv_repayFee = (TextView) convertView.findViewById(R.id.item_repay_fee);
            cholder.tv_lateFee = (TextView) convertView.findViewById(R.id.item_late_fee);
            cholder.tv_time = (TextView) convertView.findViewById(R.id.item_time);
            cholder.detail = (LinearLayout) convertView.findViewById(R.id.detail);
            cholder.title = (LinearLayout) convertView.findViewById(R.id.title);
            cholder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            cholder.item_status = (TextView) convertView.findViewById(R.id.item_status);
            // childrenMap.put(groupPosition, convertView);
            convertView.setTag(cholder);
        } else {
            // convertView = childrenMap.get(groupPosition);
            cholder = (ChildHolder) convertView.getTag();
        }
        final ProductInfo product = (ProductInfo) getChild(groupPosition, childPosition);
        if (product != null) {
            cholder.tv_product_desc.setText(product.getDesc());
            cholder.tv_price.setText("￥" + product.getPrice() + "");
            cholder.tv_repayInterest.setText(product.getRepayInterest());
            cholder.tv_repayFee.setText(product.getRepayFee());
            cholder.tv_lateFee.setText(product.getLateFee());
            cholder.tv_time.setText(product.getTime());
            cholder.cb_check.setChecked(product.isChoosed());
            if (product.getStatus()==0) {
                cholder.item_status.setText("未还款");
                cholder.item_status.setTextColor(Color.argb(255, 0, 144, 255));
            }
            else if(product.getStatus()==1) {
                cholder.item_status.setText("已还款");
                cholder.item_status.setTextColor(Color.argb(255,153,153,153));
                cholder.cb_check.setVisibility(View.GONE);
            }
            else if(product.getStatus()==2) {
                cholder.item_status.setText("已逾期");
                cholder.item_status.setTextColor(Color.argb(255, 254, 70, 28));
            }
            cholder.cb_check.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.setChoosed(((CheckBox) v).isChecked());
                    cholder.cb_check.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });
            cholder.title.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isShow) {
                        cholder.detail.setVisibility(View.VISIBLE);
                        cholder.item_icon.setImageResource(R.mipmap.item_up);
                        isShow=true;
                    }
                    else {
                        cholder.detail.setVisibility(View.GONE);
                        cholder.item_icon.setImageResource(R.mipmap.item_down);
                        isShow=false;
                    }
                }
            });
        }
        if (!product.isShowCheck()){
            cholder.cb_check.setVisibility(View.GONE);
        }else
            cholder.cb_check.setVisibility(View.VISIBLE);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 组元素绑定器
     */
    private class GroupHolder {
        CheckBox cb_check;
        TextView tv_group_name;
    }

    /**
     * 子元素绑定器
     */
    private class ChildHolder {
        LinearLayout detail;
        LinearLayout title;
        CheckBox cb_check;
        TextView tv_product_name;
        TextView tv_product_desc;
        TextView tv_price;
        TextView iv_increase;
        TextView tv_count;
        TextView iv_decrease;
        TextView tv_delete;
        TextView tv_repayInterest;
        TextView tv_repayFee;
        TextView tv_lateFee;
        TextView tv_time;
        ImageView item_icon;
        TextView item_status;
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        public void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        public void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
    }

}
