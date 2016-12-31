package com.example.qapitalinterview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    private long  time; // timestamp as long
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("savingsRuleId")
    @Expose
    private Integer savingsRuleId;
    private int goalId;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    public long getTime() {
        return time;
    }

    /**
     *
     * @param timestamp
     * The timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTime(long timestamp) {
        this.time = timestamp;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The savingsRuleId
     */
    public Integer getSavingsRuleId() {
        return savingsRuleId;
    }

    /**
     *
     * @param savingsRuleId
     * The savingsRuleId
     */
    public void setSavingsRuleId(Integer savingsRuleId) {
        this.savingsRuleId = savingsRuleId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getGoalId() {
        return goalId;
    }
}
