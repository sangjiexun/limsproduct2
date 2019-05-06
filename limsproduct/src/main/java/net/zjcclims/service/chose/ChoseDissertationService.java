package net.zjcclims.service.chose;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;


public interface ChoseDissertationService {

	/******************************************************************************************
	 * 分权限找到所有的毕业论文互选大纲
	 * 作者：孙虎
	 * 时间：2017-12-22
	 ******************************************************************************************/
	public List<ChoseTheme> findChoseThemesForCD(ChoseTheme choseTheme, int currpage, int pagesize);
	/******************************************************************************************
	 * 分权限找到所有的毕业论文互选大纲数量
	 * 作者：孙虎
	 * 时间：2017-12-22
	 ******************************************************************************************/
	public int findChoseThemesForCDCount(ChoseTheme choseTheme);
	/**************************************************************************
	 * @Description 保存ChoseTheme
	 * @return ChoseTheme
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	public ChoseTheme saveChoseTheme(ChoseTheme choseTheme);
	/**************************************************************************
	 * @Description 根据届找到同届的的大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2018-1-16
	 **************************************************************************/
	public List<ChoseTheme> sameTheYearChoseThemeList(Integer theYear);
	/**************************************************************************
	 * @Description 找到所有方向
	 * @return List<ChoseDissertationDirection>
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public List<ChoseDissertationDirection> findAllDirection();
	/**************************************************************************
	 * @Description 根据导师的username和大纲id找到导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public ChoseProfessor findChoseProfessorByProfessorUsername(String username, Integer themeId);
	/**************************************************************************
	 * @Description 根据导师的username论文互选的所有大纲
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public List<ChoseProfessor> findchoseProfessorList(String username);
	/**************************************************************************
	 * @Description 根据themeId找到所有的导师列表
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public List<ChoseProfessor> findChoseProfessorListByThemeId(
            Integer themeId, Integer currpage, int pageSize);
	/**************************************************************************
	 * @Description 根据themeId找到所有的导师列表数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public int findChoseProfessorListByThemeIdCount(Integer themeId);
	/**************************************************************************
	 * @Description 根据professorId找到所有的论文列表
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public List<ChoseDissertation> findChoseDissertationListByProfessorId(
            Integer professorId, Integer currpage, int pageSize);
	/**************************************************************************
	 * @Description 根据professorId找到所有的论文记录数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public int findChoseDissertationListByProfessorIdCount(Integer professorId);
	/**************************************************************************
	 * @Description 找到大纲中非本届的没有导师学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public List<User> findNoProfessorStudentList(String attendanceTime);
	/***************************************************************************
	 * @Description 找到不在某届的所有学生记录
	 * @return List<User>
	 * @author 赵晶
	 * @param usernameArray
	 * @date 2017-12-28
	 **************************************************************************/
	public List<User> findOtherBatchStudentByuser(Integer currpage,
                                                  int pageSize, User user, String[] usernameArray);
	/***************************************************************************
	 * @Description 统计不在某届的所有学生记录
	 * @return int
	 * @author 赵晶
	 * @param usernameArray
	 * @date 2017-12-28
	 **************************************************************************/
	public int findCountOtherBatchStudentByuser(User user, String[] usernameArray);
	/****************************************************************************
	 * @Description 查询本次大纲下attendanceTime等于所选届的学生（没有论文）数量
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-18
	 ****************************************************************************/
	public int findStudentByAttendanceTimeCount(Integer theYear);
	//-------------------------学生填报志愿----------------------------------------
	/***************************************************************************
	 * @Description 找到所属的备选大纲列表(已发布)
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public List<ChoseTheme> BelongChoseThemeList(ChoseTheme choseTheme, User user,
                                                 int currpage, int pageSize);
	/***************************************************************************
	 * @Description 统计找到所属的备选大纲(已发布)
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public int findBelongChoseThemeListCount(ChoseTheme choseTheme, User user);
	/****************************************************************************
	 * @Description 找到当前志愿下未被选的论文-学生填志愿
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-01-19
	 ****************************************************************************/
	public List<ChoseDissertation> findNoSelectedDissertationList(
            List<Integer> professorIds, Integer currpage,
            ChoseDissertation choseDissertation,
            List<Integer> choseDissertationIds, int pageSize);
	/****************************************************************************
	 * @Description 统计找到当前志愿下未被选的论文数-学生填志愿
	 * @return int
	 * @author 赵晶
	 * @date 2018-01-19
	 ****************************************************************************/
	public int findNoSelectedProfessorListQueryCount(
            List<Integer> professorIds, ChoseDissertation choseDissertation,
            List<Integer> choseDissertationIds);
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-1-19
	 **************************************************************************/
	public int choseProfessorBatchListForProfessorCount(Integer themeId,
                                                        String username);
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表-导师
	 * @return List<Object[]>
	 * @author 赵晶
	 * @param pageSize
	 * @param currpage
	 * @date 2018-1-19
	 **************************************************************************/
	public List<Object[]> choseProfessorBatchListForProfessor(
            Integer themeId, String username, Integer currpage, int pageSize);
	/***************************************************************************
	 * @Description 根据导师username和themeId找到大纲下的属于老师的立题列表
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-1-19
	 **************************************************************************/
	public List<ChoseDissertation> findDissertationListByProUsername(
            String username, Integer themeId, Integer currpage, Integer pageSize);
	/***************************************************************************
	 * @Description 根据导师username和themeId找到大纲下的属于老师的立题列表记录数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-20
     **************************************************************************/
	public int findDissertationListByProUsernameCount(String username,
                                                      Integer themeId);
	/***************************************************************************
	 * @Description 根据大纲的id和导师username找到备选导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public ChoseProfessor findChoseProfessorByThemeIdAndTUsername(
            Integer themeId, String username);
	/***************************************************************************
	 * @Description 导师审核列表页面
	 * @return List<String>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<Object[]> findAduitDissertationList(Integer currpage,
                                                    int pageSize, Integer batchId, String username);
	/***************************************************************************
	 * @Description 导师审核列表记录数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int findAduitDissertationListCount(Integer batchId, String username);
	/***************************************************************************
	 * @Description 导师审核学生记录列表
	 * @return List<ChoseDissertationRecord>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<ChoseDissertationRecord> findAduitStudentList(Integer currpage,
                                                              int pageSize, Integer batchId, String username, Integer dissertationId);
	/***************************************************************************
	 * @Description 导师审核学生记录数
	 * @return List<ChoseDissertationRecord>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int findAduitStudentListCount(Integer batchId, String username, Integer dissertationId);
	/***************************************************************************
	 * @Description 导师审核没被选上的学生记录列表
	 * @return List<ChoseDissertationRecord>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<ChoseDissertationRecord> findNoAduitStudentList(Integer batchId, String username, String name) ;
	/***************************************************************************
	 * @Description 查询没有导师的学生数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int noDissertationStudentListCount(Integer attendanceTime,
                                              Integer currpage, int pageSize);
	/***************************************************************************
	 * @Description 查询没有导师的学生记录
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<User> noDissertationStudentList(Integer attendanceTime,
                                                Integer currpage, int pageSize);
	/***************************************************************************
	 * @Description 查询没有选立题列表
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<Object[]> havePositionDissertationList(String tittle,
                                                       Integer choseThemeId, Integer currpage, int pageSize);
	/***************************************************************************
	 * @Description 查询没有选立题记录数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int havePositionDissertationListCount(String tittle,
                                                 Integer choseThemeId);
	/***************************************************************************
	 * @Description 找到所有的导师结果数量--管理员功能
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int findChoseResultCount(ChoseTheme choseTheme, int themeId);
	/***************************************************************************
	 * @Description 找到所有的导师结果列表--管理员功能
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-22
     **************************************************************************/
	public List<ChoseProfessor> findChoseResultList(ChoseTheme choseTheme, int currpage, int pageSize, int themeId);
	/****************************************************************************
	 * @Description 根据批次ids数组和导师id找到某大纲下某导师中所有审核通过的学生
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public List<ChoseDissertationRecord> passStudentListForProfessor(
            int[] batchIds, Integer professorId) ;
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到审核通过的学生名单-导师
	 * @return List<ChoseDisssertationRecord>
	 * @author 赵晶
	 * @date 2017-1-22
	 **************************************************************************/
	public List<ChoseDissertationRecord> studentList(String username,
                                                     Integer batchId, Integer pageSize, Integer currpage);
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到审核通过的学生名单-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-1-22
	 **************************************************************************/
	public int studentListCount(String username, Integer batchId);
	/***************************************************************************
	 * @Description 保存毕业论文，返回存放的相对路径
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-23
	 **************************************************************************/
	public List<String> uploadDessistationForYear(HttpServletRequest request, HttpServletResponse response);
	/***************************************************************************
	 * @Description 保存毕业论文对应的commonDocument
	 * @return CommonDocument
	 * @author 赵晶
	 * @date 2018-1-23
	 **************************************************************************/
	public CommonDocument saveDessitationDocument(List<String> saveFileAddressName);
	/***************************************************************************
	 * @Description 审核通过的记录-导师
	 * @return List<ChoseDissertationRecord>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseDissertationRecord> findMyAduitPassDissertationRecords(
            String username, Integer currpage, Integer pageSize);
	/***************************************************************************
	 * @Description 审核通过的记录数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public int findMyAduitPassDissertationRecordCount(String username);
	/***************************************************************************
	 * @Description 根据导师id找到所有的立题列表-导师
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-1-29
	 **************************************************************************/
	public List<ChoseDissertation> findChoseDissertationByProfessorId(
            Integer id, Integer currpage, int pageSize);
	/***************************************************************************
	 * @Description 根据导师id找到所有的立题记录数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-29
	 **************************************************************************/
	public int findChoseDissertationByProfessorIdCount(Integer id);
	/****************************************************************************
	 * @Description 找到属于当前时间的批次
	 * @return ChoseProfessorBatch
	 * @author 赵晶
	 * @date 2017-01-29
	 ****************************************************************************/
	public ChoseProfessorBatch belongCurrentTimeChoseProfessorBath(
            Calendar currTime);
	/****************************************************************************
	 * @Description 根据届找到未被选人数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public int findNoSelectedNumberBythemeId(Integer theYear, Integer id);
}