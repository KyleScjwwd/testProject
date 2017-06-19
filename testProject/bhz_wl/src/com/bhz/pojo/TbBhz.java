package com.bhz.pojo;



/**
 * TbBhz generated by MyEclipse - Hibernate Tools
 */

public class TbBhz  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String bdCode;
     private String bhzCode;
     private String bhzName;
     private String upNum;
     private String downNum;
     private String ext1;	//拌合站密码
     private String mergeClos;
     private String clientVersion;
     private String sgpbTest;
     private String lastOnlineTime;
     private String cjParam;
     private String cjStartTime;
     private String lastCollTime;

	 private String lastTime;
     private String gcmc;
     private String isWeb;
     private String isSignIn;

    // Constructors

	public String getGcmc() {
		return gcmc;
	}


	public void setGcmc(String gcmc) {
		this.gcmc = gcmc;
	}


	/** default constructor */
    public TbBhz() {
    }

    
    /** full constructor */
    public TbBhz(String bdCode, String bhzCode, String bhzName, String upNum, String downNum, String ext1, String mergeClos, String clientVersion) {
        this.bdCode = bdCode;
        this.bhzCode = bhzCode;
        this.bhzName = bhzName;
        this.upNum = upNum;
        this.downNum = downNum;
        this.ext1 = ext1;
        this.mergeClos = mergeClos;
        this.clientVersion = clientVersion;
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

    public String getBhzCode() {
        return this.bhzCode;
    }
    
    public void setBhzCode(String bhzCode) {
        this.bhzCode = bhzCode;
    }

    public String getBhzName() {
        return this.bhzName;
    }
    
    public void setBhzName(String bhzName) {
        this.bhzName = bhzName;
    }

    public String getUpNum() {
        return this.upNum;
    }
    
    public void setUpNum(String upNum) {
        this.upNum = upNum;
    }

    public String getDownNum() {
        return this.downNum;
    }
    
    public void setDownNum(String downNum) {
        this.downNum = downNum;
    }

    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

	public String getMergeClos() {
		return mergeClos;
	}

	public void setMergeClos(String mergeClos) {
		this.mergeClos = mergeClos;
	}
	
    public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getIsWeb() {
		return isWeb;
	}

	public void setIsWeb(String isWeb) {
		this.isWeb = isWeb;
	}

    public String getIsSignIn() {
		return isSignIn;
	}

	public void setIsSignIn(String isSignIn) {
		this.isSignIn = isSignIn;
	}

	public String getSgpbTest() {
		return sgpbTest;
	}

	public void setSgpbTest(String sgpbTest) {
		this.sgpbTest = sgpbTest;
	}

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getCjParam() {
        return cjParam;
    }

    public void setCjParam(String cjParam) {
        this.cjParam = cjParam;
    }

    public String getCjStartTime() {
        return cjStartTime;
    }

    public void setCjStartTime(String cjStartTime) {
        this.cjStartTime = cjStartTime;
    }

    public String getLastCollTime() {
        return lastCollTime;
    }

    public void setLastCollTime(String lastCollTime) {
        this.lastCollTime = lastCollTime;
    }
}