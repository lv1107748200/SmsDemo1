// Generated code from Butter Knife. Do not modify!
package com.hxj.sms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131230835;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.phone_et = Utils.findRequiredViewAsType(source, R.id.phone_et, "field 'phone_et'", EditText.class);
    target.pass_et = Utils.findRequiredViewAsType(source, R.id.pass_et, "field 'pass_et'", EditText.class);
    view = Utils.findRequiredView(source, R.id.login_btn, "field 'login_btn' and method 'click'");
    target.login_btn = Utils.castView(view, R.id.login_btn, "field 'login_btn'", Button.class);
    view2131230835 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.phone_et = null;
    target.pass_et = null;
    target.login_btn = null;

    view2131230835.setOnClickListener(null);
    view2131230835 = null;
  }
}
