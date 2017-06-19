package com.bhz.pojo;



/**
 * TbBaoJingMain generated by MyEclipse - Hibernate Tools
 */

public class TbBaoJingMain  implements java.io.Serializable {


    // Fields    

     private String mainId;
     private String bdCode;
     private String bhzCode;
     private String phbQd;
     private String clName;
     private String isBaojing;
     private String msgContent;
     private String bhTimeVal;
     private String pjbhTimeVal;
     private String ext1;
     private String ext2;


    // Constructors

    /** default constructor */
    public TbBaoJingMain() {
    }

	/** minimal constructor */
    public TbBaoJingMain(String mainId) {
        this.mainId = mainId;
    }
    
    /** full constructor */
    public TbBaoJingMain(String mainId, String bdCode, String bhzCode, String phbQd, String clName, String isBaojing, String msgContent, String bhTimeVal, String pjbhTimeVal, String ext1, String ext2) {
        this.mainId = mainId;
        this.bdCode = bdCode;
        this.bhzCode = bhzCode;
        this.phbQd = phbQd;
        this.clName = clName;
        this.isBaojing = isBaojing;
        this.msgContent = msgContent;
        this.bhTimeVal = bhTimeVal;
        this.pjbhTimeVal = pjbhTimeVal;
        this.ext1 = ext1;
        this.ext2 = ext2;
    }

   
    // Property accessors

    public String getMainId() {
        return this.mainId;
    }
    
    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getBdCode() {
        return this.bdCode;
    }
    
    public void setBdCode(String bdCode) {
        this.bdCode = bdCode;
    }

    public String getBhzCode() {
        return this.bhzCode;
    }
    
    public void setBhzCode(String bhzCode) {
        this.bhzCode = bhzCode;
    }

    public String getPhbQd() {
		return phbQd;
	}

	public void setPhbQd(String phbQd) {
		this.phbQd = phbQd;
	}

	public String getClName() {
        return this.clName;
    }
    
    public void setClName(String clName) {
        this.clName = clName;
    }

    public String getIsBaojing() {
        return this.isBaojing;
    }
    
    public void setIsBaojing(String isBaojing) {
        this.isBaojing = isBaojing;
    }

    public String getMsgContent() {
        return this.msgContent;
    }
    
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getBhTimeVal() {
        return this.bhTimeVal;
    }
    
    public void setBhTimeVal(String bhTimeVal) {
        this.bhTimeVal = bhTimeVal;
    }

    public String getPjbhTimeVal() {
        return this.pjbhTimeVal;
    }
    
    public void setPjbhTimeVal(String pjbhTimeVal) {
        this.pjbhTimeVal = pjbhTimeVal;
    }

    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
   








}