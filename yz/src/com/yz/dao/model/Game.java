package com.yz.dao.model;

import java.util.Date;
import java.util.List;

public class Game {
    private String id;

    private String userId;

    private String tel;

    private String theme;

    private String gameTypeId;

    private Date playTime;

    private String gamePlaceId;

    private String remark;

    private Date updateTime;

    private Integer winner;

    private Integer state;

    private Date createTime;
    
    List<GameDetail> gameDetailList;
    List<GameType> gameTypeList;
    List<GamePlace> gamePlaceList;
    List<GamePlayer> gamePlayerList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public String getGameTypeId() {
        return gameTypeId;
    }

    public void setGameTypeId(String gameTypeId) {
        this.gameTypeId = gameTypeId == null ? null : gameTypeId.trim();
    }

    public Date getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Date playTime) {
        this.playTime = playTime;
    }

    public String getGamePlaceId() {
        return gamePlaceId;
    }

    public void setGamePlaceId(String gamePlaceId) {
        this.gamePlaceId = gamePlaceId == null ? null : gamePlaceId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public List<GameDetail> getGameDetailList() {
		return gameDetailList;
	}

	public void setGameDetailList(List<GameDetail> gameDetailList) {
		this.gameDetailList = gameDetailList;
	}

	public List<GameType> getGameTypeList() {
		return gameTypeList;
	}

	public void setGameTypeList(List<GameType> gameTypeList) {
		this.gameTypeList = gameTypeList;
	}

	public List<GamePlace> getGamePlaceList() {
		return gamePlaceList;
	}

	public void setGamePlaceList(List<GamePlace> gamePlaceList) {
		this.gamePlaceList = gamePlaceList;
	}

	public List<GamePlayer> getGamePlayerList() {
		return gamePlayerList;
	}

	public void setGamePlayerList(List<GamePlayer> gamePlayerList) {
		this.gamePlayerList = gamePlayerList;
	}
    
}