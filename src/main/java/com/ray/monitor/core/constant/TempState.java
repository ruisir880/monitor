package com.ray.monitor.core.constant;

/**
 * Created by rui on 2018/9/1.
 */
public enum TempState {
    FALSE,
    TRUE;


    public static TempState parseToStempSate(String state){
        for(TempState value : TempState.values()){
            if(value.toString().equals(state)){
                return value;
            }
        }
        return null;
    }
}
