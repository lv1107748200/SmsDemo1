// Generated code from Butter Knife. Do not modify!
package com.hxj.sms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.roughike.bottombar.BottomBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230959;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_what, "field 'tv_what' and method 'click'");
    target.tv_what = Utils.castView(view, R.id.tv_what, "field 'tv_what'", TextView.class);
    view2131230959 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    target.stub_yhk = Utils.findRequiredViewAsType(source, R.id.stub_yhk, "field 'stub_yhk'", ViewStub.class);
    target.stub_rzxx = Utils.findRequiredViewAsType(source, R.id.stub_rzxx, "field 'stub_rzxx'", ViewStub.class);
    target.stub_yh = Utils.findRequiredViewAsType(source, R.id.stub_yh, "field 'stub_yh'", ViewStub.class);
    target.bottomBar = Utils.findRequiredViewAsType(source, R.id.bottomBar, "field 'bottomBar'", BottomBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_what = null;
    target.stub_yhk = null;
    target.stub_rzxx = null;
    target.stub_yh = null;
    target.bottomBar = null;

    view2131230959.setOnClickListener(null);
    view2131230959 = null;
  }
}
