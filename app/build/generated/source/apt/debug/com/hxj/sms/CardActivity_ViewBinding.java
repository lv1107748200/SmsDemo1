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

public class CardActivity_ViewBinding implements Unbinder {
  private CardActivity target;

  @UiThread
  public CardActivity_ViewBinding(CardActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CardActivity_ViewBinding(CardActivity target, View source) {
    this.target = target;

    target.recyle = Utils.findRequiredViewAsType(source, R.id.recyle, "field 'recyle'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CardActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyle = null;
  }
}
