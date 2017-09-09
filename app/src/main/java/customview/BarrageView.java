package customview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.you.R;

import java.util.Random;

/**
 * Created by lixueyong on 16/2/19.
 */
public class BarrageView extends RelativeLayout {

    private final int DAN_MU_LINE = 12;

    private Context mContext;
    private BarrageHandler mHandler = new BarrageHandler();
    private Random random = new Random(System.currentTimeMillis());
    private static final long BARRAGE_GAP_MIN_DURATION = 200;//两个弹幕的最小间隔时间
    private static final long BARRAGE_GAP_MAX_DURATION = 1000;//两个弹幕的最大间隔时间
    private int maxSpeed = 10000;//速度，ms
    private int minSpeed = 2000;//速度，ms
    private int maxSize = 30;//文字大小，dp
    private int minSize = 15;//文字大小，dp

    private int totalHeight = 0;
    private int lineHeight = 0;//每一行弹幕的高度
    private int totalLine = DAN_MU_LINE;//弹幕的行数
    private String[] itemText = {
            "一大波弹幕即将来袭，请做好防护！",
            "what are you 弄啥嘞",
            "哇塞！小姐姐怎么可以这么美腻......",
            "我是来抢占沙发的",
            "我在弹幕中凌乱的飘过。。。。",
            "前边的请让一下，老司机我刹不住车",
            "这是什么鬼？一双脚？？？？",
            "小姐姐最咩里，小姐姐最可爱无敌！！！！",
            "嘿嘿，小姐姐约么",
            "高老师！你会唱小星星吗？",
            "请记住！梁衍，就是杀死你的人的名字",
            "喂喂喂，前边的走错片场了吧，别在这等着蹭盒饭吃，剧组都揭不开锅了",
            "高老师最美，高老师最美",
            "你是我天边最美的云彩，让我把你用心留下来。。。。",
            "么么哒！~~~",
            "小姐姐的美丽无人能敌",
            "咳咳，高老师来了，大家都快做好准备上课了！！！！",
            "蛋壳里的哪位姑娘，请问你叫什么名字？",
            "ka~~wa~~yi~~ne~~~~~",
            "姑娘你怎么这么的可爱啊",
            "温柔善良，美丽大方，可爱靓丽，娉婷婉順，裊娜嫵媚的小姐姐",
            "敢问这位手捧鲜花的美丽的小姐姐",
            "前方发现麗人賢惠，賢慧魅力，典雅感性，優雅高貴，溫柔體貼的小姐姐",
            "魔镜魔镜谁是这世上最美丽的女人，高老师！高老师！高老师！",
    "多希望有个如你一般的人，如山间清爽的风，如古城温暖的光",
    "只要最后是你就好......"};
    private int textCount;
//    private List<BarrageItem> itemList = new ArrayList<BarrageItem>();

    public BarrageView(Context context) {
        this(context, null);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        textCount = itemText.length;

        int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
        mHandler.sendEmptyMessageDelayed(0, duration);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        totalHeight = getMeasuredHeight();
        lineHeight = getLineHeight();
    }

    //初始化设置单个弹幕View
    private void generateItem() {
        final BarrageItem item = new BarrageItem();
        final String tx = itemText[(int) (Math.random() * textCount)];
        int sz = (int) (minSize + (maxSize - minSize) * Math.random());

        item.redPackageView = new RedPackageView(mContext);
        item.redPackageView.setText(tx);
        item.redPackageView.setImageResource(R.mipmap.ic_launcher);
        item.redPackageView.setNextImageResource(R.mipmap.ic_launcher);

        item.textMeasuredWidth = (int) getTextWidth(item, tx, sz);
        item.moveSpeed = 6000;
        if (totalLine == 0) {
            totalHeight = getMeasuredHeight();
            lineHeight = getLineHeight();
            totalLine = DAN_MU_LINE;
        }
        item.verticalPos = random.nextInt(totalLine) * lineHeight;
        item.redPackageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tx, Toast.LENGTH_SHORT).show();
            }
        });
//        itemList.add(item);
        showBarrageItem(item);
    }

    private void showBarrageItem(final BarrageItem item) {

        int leftMargin = this.getRight() - this.getLeft() - this.getPaddingLeft();

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = item.verticalPos;
        this.addView(item.redPackageView, params);
        Animation anim = generateTranslateAnim(item, leftMargin);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                item.redPackageView.clearAnimation();
                BarrageView.this.removeView(item.redPackageView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        item.redPackageView.startAnimation(anim);
    }

    private TranslateAnimation generateTranslateAnim(BarrageItem item, int leftMargin) {
        TranslateAnimation anim = new TranslateAnimation(leftMargin, -item.textMeasuredWidth, 0, 0);
        anim.setDuration(item.moveSpeed);
        anim.setInterpolator(new LinearInterpolator());
        anim.setFillAfter(true);
        return anim;
    }

    /**
     * 计算TextView中字符串的长度
     *
     * @param text 要计算的字符串
     * @param Size 字体大小
     * @return TextView中字符串的长度
     */
    public float getTextWidth(BarrageItem item, String text, float Size) {
        Rect bounds = new Rect();
        TextPaint paint;
        paint = item.redPackageView.getTextView().getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width() + 300;
    }

    /**
     * 获得每一行弹幕的最大高度
     *
     * @return
     */
    private int getLineHeight() {
        BarrageItem item = new BarrageItem();
        String tx = itemText[0];
        item.textView = new TextView(mContext);
        item.textView.setText(tx);
        item.textView.setTextSize(maxSize);

        Rect bounds = new Rect();
        TextPaint paint;
        paint = item.textView.getPaint();
        paint.getTextBounds(tx, 0, tx.length(), bounds);
        return bounds.height();
    }

    class BarrageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            generateItem();
            //每个弹幕产生的间隔时间随机
            int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
//            int duration = 4000;
            this.sendEmptyMessageDelayed(0, duration);
        }
    }

}
