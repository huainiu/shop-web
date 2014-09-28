package com.b5m.bean.entity;

import java.util.HashMap;
import java.util.Map;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Name;
import com.b5m.dao.annotation.Table;

@Table("t_shop_info")
public class ShopInfo {

	@Column(name = "serv_att_higher_rate")
	private String servAttHigherRate;

	@Column(name = "des_agreed")
	private String desAgreed;

	@Column(name = "serv_sc_higher_score")
	private String servScHigherScore;

	@Column(name = "time_liness_score")
	private String timeLinessScore;

	@Column(name = "cons_speed_rate")
	private String consSpeedRate;

	@Column(name = "score")
	private String score;

	@Column(name = "des_higher_rate")
	private String desHigherRate;

	@Column(name = "cons_speed")
	private String consSpeed;

	@Column(name = "time_sc_higher_rate")
	private String timeScHigherRate;

	@Column(name = "url")
	private String url;

	@Column(name = "serv_attitude")
	private String servAttitude;

	@Column(name = "service_score")
	private String serviceScore;

	@Column(name = "address")
	private String address;

	@Column(name = "score_higher_rate")
	private String scoreHigherRate;

	@Name
	@Column(name = "name")
	private String name;

	@Column(name = "grade")
	private String grade;

	@Column(name = "docId")
	private String docId;

	private Map<String, String> map = new HashMap<String, String>();

	public void setServAttHigherRate(String servAttHigherRate) {
		this.servAttHigherRate = servAttHigherRate;
	}

	public String getServAttHigherRate() {
		return servAttHigherRate;
	}

	public void setDesAgreed(String desAgreed) {
		this.desAgreed = desAgreed;
	}

	public String getDesAgreed() {
		return desAgreed;
	}

	public void setServScHigherScore(String servScHigherScore) {
		this.servScHigherScore = servScHigherScore;
	}

	public String getServScHigherScore() {
		return servScHigherScore;
	}

	public void setTimeLinessScore(String timeLinessScore) {
		this.timeLinessScore = timeLinessScore;
	}

	public String getTimeLinessScore() {
		return timeLinessScore;
	}

	public void setConsSpeedRate(String consSpeedRate) {
		this.consSpeedRate = consSpeedRate;
	}

	public String getConsSpeedRate() {
		return consSpeedRate;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getScore() {
		return score;
	}

	public void setDesHigherRate(String desHigherRate) {
		this.desHigherRate = desHigherRate;
	}

	public String getDesHigherRate() {
		return desHigherRate;
	}

	public void setConsSpeed(String consSpeed) {
		this.consSpeed = consSpeed;
	}

	public String getConsSpeed() {
		return consSpeed;
	}

	public void setTimeScHigherRate(String timeScHigherRate) {
		this.timeScHigherRate = timeScHigherRate;
	}

	public String getTimeScHigherRate() {
		return timeScHigherRate;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setServAttitude(String servAttitude) {
		this.servAttitude = servAttitude;
	}

	public String getServAttitude() {
		return servAttitude;
	}

	public void setServiceScore(String serviceScore) {
		this.serviceScore = serviceScore;
	}

	public String getServiceScore() {
		return serviceScore;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setScoreHigherRate(String scoreHigherRate) {
		this.scoreHigherRate = scoreHigherRate;
	}

	public String getScoreHigherRate() {
		return scoreHigherRate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGrade() {
		return grade;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocId() {
		return docId;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}