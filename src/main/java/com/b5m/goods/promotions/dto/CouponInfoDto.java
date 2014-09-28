package com.b5m.goods.promotions.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class CouponInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3700194386749923899L;

	private Long id;
	// 优惠券名称
	private String name;
	// 优惠券显示图片
	private String image;
	// 优惠券金额类型
	private String offsetType;
	/**
     * 1：金额类型为满多少返多少时 amtForOffset代表满多少元钱 offsetAmt代表返多少元钱 2：金额类型为代金券时
     * offsetAmt代表代金券金额
     */
	private Double offsetAmt;
	
	private Double amtForOffset;
	//商家
	private SuppliserDto suppliser;
	
	private Long brandId;
	// 优惠券类型(公用券、私用券、密码券三种)
	private String type;
	// FOREVER（永久有效）或者SELFDEFINATION（自定义）
	private String validTimeFlg;
	// 【有效日期开始时间】当有效日期FLG为SELFDEFINATION（自定义）状态时使用
	private String startValidTime;
	// 【有效日期结束时间】当有效日期FLG为SELFDEFINATION（自定义）状态时使用
	private String endValidTime;
	// 是否可返利(YES或NO)
	private String isRabate;
	// 使用范围(全场通用ALL、部分商品可用SUB、全场通用除特殊商品EXCEPT、自定义范围)
	private String useRange;
	// 领取限制（每人限制领取张数，限数字，0为不限制）
	private Integer receiveLimit;
	// 积分 领取该优惠券所用积分（0表示免费）
	private Integer integral;
	// 使用说明
	private String remark;
	// 是否立即发布(YES或NO)
	private String release;
	// 优惠券券号总数量
	private Integer totalCount;
	// 被领取的优惠券券号数量
	private Integer receivedCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOffsetType() {
		return offsetType;
	}

	public void setOffsetType(String offsetType) {
		this.offsetType = offsetType;
	}

	public Double getAmtForOffset() {
		return amtForOffset;
	}

	public void setAmtForOffset(Double amtForOffset) {
		this.amtForOffset = amtForOffset;
	}

	public SuppliserDto getSuppliser() {
		return suppliser;
	}

	public void setSuppliser(SuppliserDto suppliser) {
		this.suppliser = suppliser;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidTimeFlg() {
		return validTimeFlg;
	}

	public void setValidTimeFlg(String validTimeFlg) {
		this.validTimeFlg = validTimeFlg;
	}

	public String getStartValidTime() {
		return startValidTime;
	}

	public void setStartValidTime(String startValidTime) {
		this.startValidTime = startValidTime;
	}

	public String getEndValidTime() {
		return endValidTime;
	}

	public void setEndValidTime(String endValidTime) {
		this.endValidTime = endValidTime;
	}

	public String getUseRange() {
		return useRange;
	}

	public void setUseRange(String useRange) {
		this.useRange = useRange;
	}

	public Integer getReceiveLimit() {
		return receiveLimit;
	}

	public void setReceiveLimit(Integer receiveLimit) {
		this.receiveLimit = receiveLimit;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getReceivedCount() {
		return receivedCount;
	}

	public void setReceivedCount(Integer receivedCount) {
		this.receivedCount = receivedCount;
	}

	public String getIsRabate() {
		return isRabate;
	}

	public void setIsRabate(String isRabate) {
		this.isRabate = isRabate;
	}
	
	public Double getOffsetAmt(){
        return offsetAmt;
    }

    public void setOffsetAmt(Double offsetAmt){
        this.offsetAmt = offsetAmt;
    }

    @Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
