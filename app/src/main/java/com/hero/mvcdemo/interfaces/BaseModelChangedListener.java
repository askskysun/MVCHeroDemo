package com.hero.mvcdemo.interfaces;

/**
 *  已经做了一个网络的请求 并且把JSON数据转换成对象  此时应该通知Ac去修改UI
 */
public interface BaseModelChangedListener {
	/**
	 *
	 * @param action  什么类型的数据加载完成了
	 * @param values  后台返回的数据
	 */
	 void onModelChanged(int action, Object... values);
}
