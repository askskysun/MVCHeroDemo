package com.hero.mvcdemo.tools;

import android.widget.EditText;

/**
 * EditText 工具类
 */

public class EditTextUtils {

    /**获取EditText光标所在的位置*/
    public static int getEditTextCursorIndex(EditText mEditText){
        if (mEditText ==null) {
            return 0;
        }
        return mEditText.getSelectionStart();
    }
    /**向EditText指定光标位置插入字符串*/
    public static void insertText(EditText mEditText, String mText){
        if (mEditText == null || EmptyJudgeUtils.stringIsEmpty(mText)) {
            return;
        }
        mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
    }
    /**向EditText指定光标位置删除字符串*/
    public static void deleteText(EditText mEditText){
        if(mEditText != null && !StringUtils.isEmpty(mEditText.getText().toString())){
            mEditText.getText().delete(getEditTextCursorIndex(mEditText)-1, getEditTextCursorIndex(mEditText));
        }
    }
    //光标移到最后
    public static void editTextSetSelectionLast(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.setSelection(editText.getText().length());
    }

}
