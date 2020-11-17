package com.example.design_model.template_pattern;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:15
 */
public class Main {
    public static void main(String[] args) {
        Template template1 = new RealTemplateOne();
        Template template2 = new RealTemplateTwo();

        template1.templateMethod();
        template2.templateMethod();
    }
}
