package com.bhz.pojo;

import java.util.Date;


/**
 * TbSendMsgLog generated by MyEclipse - Hibernate Tools
 */

public class TbSendMsgLog  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String bdCode;
     private String mobile;
     private String msgContent;
     private Date sendDate;
     private String sendFlag;
     private String ext1;


    // Constructors

    /** default constructor */
    public TbSendMsgLog() {
    }

    
    /** full constructor */
    public TbSendMsgLog(String bdCode, String mobile, String msgContent, Date sendDate, String sendFlag, String ext1) {
        this.bdCode = bdCode;
        this.mobile = mobile;
        this.msgContent = msgContent;
        this.sendDate = sendDate;
        this.sendFlag = sendFlag;
        this.ext1 = ext1;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBdCode() {
        return this.bdCode;
    }
    
    public void setBdCode(String bdCode) {
        this.bdCode = bdCode;
    }

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsgContent() {
        return this.msgContent;
    }
    
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getSendDate() {
        return this.sendDate;
    }
    
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendFlag() {
        return this.sendFlag;
    }
    
    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
   








}