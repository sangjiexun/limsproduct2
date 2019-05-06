package net.zjcclims.service.chose;

import net.zjcclims.domain.ChoseAttention;
import net.zjcclims.domain.ChoseTheme;

import java.util.List;


public interface ChoseProfessorService1 {
	/*
	 * 找到所有的导师互选大纲
	 * 作者：孙虎
	 * 时间：2017-12-1
	
	public List<ChoseTheme> findChoseThemes(ChoseTheme choseTheme,int currpage,int pagesize); */
	/*
	 * 找到所有的导师互选——注意事项数量
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	public int findChoseAttentionsCount(ChoseAttention choseAttention);
	/*
	 * 找到所有的导师互选——注意事项
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	public List<ChoseAttention> findChoseAttentions(ChoseAttention choseAttention, int currpage, int pagesize);

	/*
	 * 删除：导师互选——注意事项
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	public boolean deleteChoseAttention(Integer choseAttentionId);
	
	/*
	 * 通过主键获得所有的导师互选——注意事项
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	public ChoseAttention findChoseAttentionByPrimaryKey(Integer choseAttentionId);
	
	/**
	 * 保存实验中心数据
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	public ChoseAttention saveChoseAttention(ChoseAttention choseAttention);
	/**
	 * 获取所有的学期数据
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	public List<ChoseTheme> findAllChoseTheme();
	public List<ChoseAttention> findAllChoseAttention();
	/***********************************************************************************************
	 * 查询所有的十二个学院
	 * 作者：屈晓瑞
	 ***********************************************************************************************/
	public List<ChoseTheme> findAllChoseThemes();
	
}