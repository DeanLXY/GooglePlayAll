package itcast.zz16.googleplay.fragments;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import itcast.zz16.googleplay.protocol.TopProtocol;
import itcast.zz16.googleplay.utils.DrawableUtils;
import itcast.zz16.googleplay.utils.ToastUtils;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.FlowLayout;

/**
 * A simple {@link Fragment} subclass.
 * 排行
 */
public class TopFragment extends BaseFragment {

    private List<String> datas;

    @Override
    protected int load() {
        //  请求数据  缓存数据  解析数据  复用缓存数据
        TopProtocol protocol = new TopProtocol();
        datas = protocol.load(0);

        return checkDatas(datas);//LoadingPage.STATU_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
//        LinearLayout layout = new LinearLayout(UIUtils.getContext());
        FlowLayout layout = new FlowLayout(UIUtils.getContext());
        int padding = UIUtils.dip2px(13);
        layout.setPadding(padding,padding,padding,padding);
        // 都可以
        scrollView.addView(layout);
//        layout.setOrientation(LinearLayout.VERTICAL);//垂直方向
        int textPaddingV = UIUtils.dip2px(4);
        int textPaddingH = UIUtils.dip2px(7);
        TextView textView;
        int backColor = 0xffcecece;//按下的颜色
        Random random = new Random();

        for (int i = 0; i < datas.size(); i++) {
            final String text = datas.get(i);
            textView = new TextView(UIUtils.getContext());
            //颜色随机 Random
            //  0x00000000  ---0xffffffff
            int red = random.nextInt(201) + 30; // 0 ---255   30 - 200
            int green = random.nextInt(201) + 30; // 0 ---255
            int blue = random.nextInt(201) + 30; // 0 ---255
            int color = Color.rgb(red, green, blue);
            textView.setBackgroundDrawable(DrawableUtils.createStateList(DrawableUtils.create(backColor),
                    DrawableUtils.create(color)));
            textView.setTextColor(Color.WHITE);
//            textView.setClickable(true);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);//14dp
            textView.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
            textView.setText(text);
            // 都是包裹内容
            layout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -2));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(text);
                }
            });
        }

        return scrollView;
    }

}
