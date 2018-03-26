package com.ocnyang.propertyanimation;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2018/3/26 14:48.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class MyTypeEvaluator implements TypeEvaluator<PropertyBean> {
    ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    @Override
    public PropertyBean evaluate(float fraction, PropertyBean startPropertyBean, PropertyBean endPropertyBean) {
//        float currentColor = startPropertyBean.getBackgroundColor() + (endPropertyBean.getBackgroundColor() - startPropertyBean.getBackgroundColor()) * fraction;
        int currentColor = (int) mArgbEvaluator.evaluate(fraction, startPropertyBean.getBackgroundColor(), endPropertyBean.getBackgroundColor());
        float currentRotationX = startPropertyBean.getRotationX() + (endPropertyBean.getRotationX() - startPropertyBean.getRotationX()) * fraction;
        float currentSize = startPropertyBean.getSize() + (endPropertyBean.getSize() - startPropertyBean.getSize()) * fraction;
        return new PropertyBean((int) currentColor, currentRotationX, currentSize);
    }
}
