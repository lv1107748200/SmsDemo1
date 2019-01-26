// Generated code from Butter Knife. Do not modify!
package com.hxj.sms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity$StubYH_ViewBinding implements Unbinder {
  private MainActivity.StubYH target;

  @UiThread
  public MainActivity$StubYH_ViewBinding(MainActivity.StubYH target, View source) {
    this.target = target;

    target.tv_users = Utils.findRequiredViewAsType(source, R.id.tv_users, "field 'tv_users'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity.StubYH target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_users = null;
  }
}
