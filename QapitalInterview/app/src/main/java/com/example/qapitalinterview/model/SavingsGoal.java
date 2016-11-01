package com.example.qapitalinterview.model;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavingsGoal {

    @SerializedName("goalImageURL")
    @Expose
    private String goalImageURL;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("targetAmount")
    @Expose
    private Float targetAmount;
    @SerializedName("currentBalance")
    @Expose
    private Integer currentBalance;
    @SerializedName("created")
    @Expose
    private List<Integer> created = new ArrayList<Integer>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("connectedUsers")
    @Expose
    private Object connectedUsers;

    /**
     *
     * @return
     * The goalImageURL
     */
    public String getGoalImageURL() {
        return goalImageURL;
    }

    /**
     *
     * @param goalImageURL
     * The goalImageURL
     */
    public void setGoalImageURL(String goalImageURL) {
        this.goalImageURL = goalImageURL;
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
     * The targetAmount
     */
    public Float getTargetAmount() {
        return targetAmount;
    }

    /**
     *
     * @param targetAmount
     * The targetAmount
     */
    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     *
     * @return
     * The currentBalance
     */
    public Integer getCurrentBalance() {
        return currentBalance;
    }

    /**
     *
     * @param currentBalance
     * The currentBalance
     */
    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    /**
     *
     * @return
     * The created
     */
    public List<Integer> getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(List<Integer> created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The connectedUsers
     */
    public Object getConnectedUsers() {
        return connectedUsers;
    }

    /**
     *
     * @param connectedUsers
     * The connectedUsers
     */
    public void setConnectedUsers(Object connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

}
