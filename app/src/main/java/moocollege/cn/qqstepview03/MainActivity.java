package moocollege.cn.qqstepview03;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QQStepView qqStepView = (QQStepView) findViewById(R.id.qq_step_view);
        qqStepView.setmMaxStepCount(20000);
        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 12000);
        //设置差值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStepCount = (float) animation.getAnimatedValue();
                qqStepView.setmCurrentStepCount((int) currentStepCount);
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }
}
