package com.example.nuaabbs.common;

public class CommonResponse {
    String resCode;
    String resMsg;
    String resParam;

    public CommonResponse(){
    }

    public CommonResponse(String resCode, String resMsg, String resParam){
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.resParam = resParam;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResParam() {
        return resParam;
    }

    public void setResParam(String resParam) {
        this.resParam = resParam;
    }
}
