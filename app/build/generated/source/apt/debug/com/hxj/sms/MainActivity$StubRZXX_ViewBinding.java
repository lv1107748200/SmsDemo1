// Generated code from Butter Knife. Do not modify!
package com.hxj.sms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity$StubRZXX_ViewBinding implements Unbinder {
  private MainActivity.StubRZXX target;

  @UiThread
  public MainActivity$StubRZXX_ViewBinding(MainActivity.StubRZXX target, View source) {
    this.target = target;

    target.tv_rizhi = Utils.findRequiredViewAsType(source, R.id.tv_rizhi, "field 'tv_rizhi'", TextView.class);
    target.recyle_rzxx = Utils.findRequiredViewAsType(source, R.id.recyle_rzxx, "field 'recyle_rzxx'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity.StubRZXX target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_rizhi = null;
    target.recyle_rzxx = null;
  }
}
