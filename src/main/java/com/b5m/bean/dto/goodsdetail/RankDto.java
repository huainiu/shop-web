package com.b5m.bean.dto.goodsdetail;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存储关于产品评分的DTO
 * 
 * @author yk
 */
public class RankDto {

	private int rankInt = 0;

	/**
	 * 商品的平均分
	 */
	private double rank = 0;

	/**
	 * 评论数
	 */
	private long commentCount = 0;

	/**
	 * 有评分的评论数
	 */
	private long hasRankCount = 0;

	/**
	 * 评分个数
	 */
	private int rank1Count = 0;
	private int rank2Count = 0;
	private int rank3Count = 0;
	private int rank4Count = 0;
	private int rank5Count = 0;

	/**
	 * 评分在页面的比率
	 */
	private int rank1Per = 0;
	private int rank2Per = 0;
	private int rank3Per = 0;
	private int rank4Per = 0;
	private int rank5Per = 0;

	// 评论列表
	private Map<String,List<String[]>> commentMap = new HashMap<String, List<String[]>>();

	/**
	 * 计算平均分和分数百分比
	 */
	public void doCount() {
		countAvgRank();
		countRankLength();
	}

	// 计算平均分
	private void countAvgRank() {
		int count = rank1Count + rank2Count + rank3Count + rank4Count + rank5Count;
		hasRankCount = count;

		if (count == 0) {
			this.rank = 0;
			this.rankInt = 0;
			return;
		}

		// 将double 截取到小数后1位
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		double rank = (1 * rank1Count + 2 * rank2Count + 3 * rank3Count + 4
				* rank4Count + 5 * rank5Count)
				/ (double) (count);

		rank = Double.parseDouble(nf.format(rank));
		this.rank = rank;
		this.rankInt = (int) Math.round(rank);
	}

	/**
	 * 计算百分比
	 */
	private void countRankLength() {

		double sum = rank1Count + rank2Count + rank3Count + rank4Count
				+ rank5Count;
		if (sum == 0) {
			return;
		}
		rank1Per = (int) ((rank1Count / sum) * 100 + 0.99);
		rank2Per = (int) ((rank2Count / sum) * 100 + 0.99);
		rank3Per = (int) ((rank3Count / sum) * 100 + 0.99);
		rank4Per = (int) ((rank4Count / sum) * 100 + 0.99);
		rank5Per = (int) ((rank5Count / sum) * 100 + 0.99);
	}

	public int getRankInt() {
		return rankInt;
	}

	public void setRankInt(int rankInt) {
		this.rankInt = rankInt;
	}

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public int getRank1Count() {
		return rank1Count;
	}

	public void setRank1Count(int rank1Count) {
		this.rank1Count = rank1Count;
	}

	public int getRank2Count() {
		return rank2Count;
	}

	public void setRank2Count(int rank2Count) {
		this.rank2Count = rank2Count;
	}

	public int getRank3Count() {
		return rank3Count;
	}

	public void setRank3Count(int rank3Count) {
		this.rank3Count = rank3Count;
	}

	public int getRank4Count() {
		return rank4Count;
	}

	public void setRank4Count(int rank4Count) {
		this.rank4Count = rank4Count;
	}

	public int getRank5Count() {
		return rank5Count;
	}

	public void setRank5Count(int rank5Count) {
		this.rank5Count = rank5Count;
	}

	public int getRank1Per() {
		return rank1Per;
	}

	public void setRank1Per(int rank1Per) {
		this.rank1Per = rank1Per;
	}

	public int getRank2Per() {
		return rank2Per;
	}

	public void setRank2Per(int rank2Per) {
		this.rank2Per = rank2Per;
	}

	public int getRank3Per() {
		return rank3Per;
	}

	public void setRank3Per(int rank3Per) {
		this.rank3Per = rank3Per;
	}

	public int getRank4Per() {
		return rank4Per;
	}

	public void setRank4Per(int rank4Per) {
		this.rank4Per = rank4Per;
	}

	public int getRank5Per() {
		return rank5Per;
	}

	public void setRank5Per(int rank5Per) {
		this.rank5Per = rank5Per;
	}

	public long getHasRankCount() {
		return hasRankCount;
	}

	public void setHasRankCount(long hasRankCount) {
		this.hasRankCount = hasRankCount;
	}

	public Map<String, List<String[]>> getCommentMap() {
		return commentMap;
	}

	public void setCommentMap(Map<String, List<String[]>> commentMap) {
		this.commentMap = commentMap;
	}

    @Override
    public String toString()
    {
        return "SuiProduceRankDto [rankInt=" + rankInt + ", rank=" + rank
                + ", commentCount=" + commentCount + ", hasRankCount="
                + hasRankCount + ", rank1Count=" + rank1Count + ", rank2Count="
                + rank2Count + ", rank3Count=" + rank3Count + ", rank4Count="
                + rank4Count + ", rank5Count=" + rank5Count + ", rank1Per="
                + rank1Per + ", rank2Per=" + rank2Per + ", rank3Per="
                + rank3Per + ", rank4Per=" + rank4Per + ", rank5Per="
                + rank5Per + ", commentMap=" + commentMap + "]";
    }

}
