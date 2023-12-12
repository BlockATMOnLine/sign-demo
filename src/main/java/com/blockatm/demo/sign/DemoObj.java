package com.blockatm.demo.sign;

/**
 * @author
 * @description demo object for sign
 */

public class DemoObj {
    private Integer type = 1;
    private Long merchantId = 86000090l;
    private String custNo = "OrderNO_123456";

    private String orderNo = null;

    private String platOrderNo = "8210000374";
    private String symbol = "USDT";
    private String amount = "13.410037";
    private String fee = "2";
    private String paySymbol = null;
    private Integer status = 1;
    private String txId = "1t";
    private String network = "TRON";
    private String chainId = "5";

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPlatOrderNo() {
        return platOrderNo;
    }

    public void setPlatOrderNo(String platOrderNo) {
        this.platOrderNo = platOrderNo;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPaySymbol() {
        return paySymbol;
    }

    public void setPaySymbol(String paySymbol) {
        this.paySymbol = paySymbol;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }
}
