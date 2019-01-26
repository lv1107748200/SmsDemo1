// Generated code from Butter Knife. Do not modify!
package com.hxj.sms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity$StubYHK_ViewBinding implements Unbinder {
  private MainActivity.StubYHK target;

  @UiThread
  public MainActivity$StubYHK_ViewBinding(MainActivity.StubYHK target, View source) {
    this.target = target;

    target.recyle = Utils.findRequiredViewAsType(source, R.id.recyle, "field 'recyle'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity.StubYHK target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyle = null;
  }
}
