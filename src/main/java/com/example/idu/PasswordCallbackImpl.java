package com.example.idu;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/7/30 14:24
 */
public class PasswordCallbackImpl implements PasswordCallback{
    @Override
    public void call(char[] password) {
        System.out.println(password);
    }
}
