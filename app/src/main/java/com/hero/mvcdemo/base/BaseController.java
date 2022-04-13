package com.hero.mvcdemo.base;

import android.content.Context;

import com.hero.mvcdemo.interfaces.BaseModelChangedListener;

public abstract class BaseController {

	protected BaseModelChangedListener mListener;
	protected Context mContext;
	
	public BaseController(Context mContext) {
		this.mContext = mContext;
	}

	public void setListener(BaseModelChangedListener listener) {
		this.mListener = listener;
	}

	/**
	 *  action (一个页面有多个请求  通过action来区别不同的请求)
	 */
	public void sendAsyncMessage(final int action,final Object... values){
		new Thread(){
			public void run() {
				handlerMessage(action, values);
			}
		}.start();
	}
	
	/**
	 *  让所有的子类做具体的网络操作
	 */
	public abstract void handlerMessage(int action,Object... values);
	
}
