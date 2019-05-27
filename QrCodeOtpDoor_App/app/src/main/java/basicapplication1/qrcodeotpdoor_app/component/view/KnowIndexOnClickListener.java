package basicapplication1.qrcodeotpdoor_app.component.view;

import android.view.View;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-24.
 */
public abstract class KnowIndexOnClickListener implements View.OnClickListener {

    protected int index;

    public KnowIndexOnClickListener(int index) {
        this.index = index;
    }
}