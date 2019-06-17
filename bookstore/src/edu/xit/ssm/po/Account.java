package edu.xit.ssm.po;

public class Account {
    private Integer id;

    private Integer userId;

    private Float smMoney;

    private Float confirmMoney;

    private Integer points;

    private Float balance;

    private String status;

    private String msg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getSmMoney() {
        return smMoney;
    }

    public void setSmMoney(Float smMoney) {
        this.smMoney = smMoney;
    }

    public Float getConfirmMoney() {
        return confirmMoney;
    }

    public void setConfirmMoney(Float confirmMoney) {
        this.confirmMoney = confirmMoney;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}