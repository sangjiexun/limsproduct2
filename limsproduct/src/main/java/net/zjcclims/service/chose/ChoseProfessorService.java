package net.zjcclims.service.chose;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;


public interface ChoseProfessorService {
	/*
	 * 找到所有的导师互选大纲
	 * 作者：孙虎
	 * 时间：2017-12-1
	 */
	public List<ChoseTheme> findChoseThemes(ChoseTheme choseTheme, int currpage, int pagesize);
	/*
	 * 找到所有的导师互选大纲数量
	 * 作者：孙虎
	 * 时间：2017-12-1
	 */
	public int findChoseThemesCount(ChoseTheme choseTheme);
	/*
	 * 找到所有的导师结果根据大纲id
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public List<ChoseProfessor> findChoseResultList(ChoseTheme choseTheme, int currpage, int pageSize, int themeId);
	/*
	 * 找到所有的导师结果根据大纲id数量
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public int findChoseResultCount(ChoseTheme choseTheme, int themeId);
	/*
	 * 找到所有的学生申请 根据themId和导师
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public List<ChoseProfessorRecord> findChoseStudents(ChoseTheme choseTheme,
                                                        int currpage, int pageSize, int themeId);
	/*
	 * 找到所有的学生申请数量 根据themId和导师
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public int findChoseStudentsCount(ChoseTheme choseTheme, int themeId);
	/**
	 * 根据主键查找大纲
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	public ChoseTheme findChoseThemeByPrimaryKey(Integer choseThemeId);

	/***********************************************************************************************
     * 查询所有的大纲
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 ***********************************************************************************************/
	public List<ChoseTheme> findAllChoseThemes();

	/**
	 * 根据大纲id找出备选导师名单
	 * @author 赵晶
	 * @date 2017.12.08
	 */

	public List<ChoseProfessor> findChoseProfessorByThemeId(Integer termId, Integer currpage, Integer pageSize, ChoseProfessor choseProfessor);
	/**
	 * 根据大纲id找出备选导师名单的总记录数
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	public int findCountChoseProfessorByThemeId(Integer termId, ChoseProfessor choseProfessor);
	/**
	 * 根据user表找出所有的导师列表
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	public List<User> findChoseProfessorByUser(Integer currpage, Integer pageSize);
	/**
	 *  根据user表找出所有的导师列表总记录数
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	public int findCountChoseProfessorByUser();
	/**
	 *功能: 从user表里根据cname和username找到所有的导师
	 *@author 赵晶
	 *@date 2017.12.09
	 */
	public List<User> findUserByCnameAndUsername(User user,
                                                 Integer currpage, int pageSize, String[] usernameArray);
	/**
	 * 从user表里根据cname和username找到所有的导师的记录数
	 *@author 赵晶
	 *@date 2017.12.09
	 */
	public int findCountUserByCnameAndUsername(User user, String[] usernameArray);
	/**
	 * 根据termId、cname找到所有的备选导师
	 *@author 赵晶
	 *@date 2017.12.11
	 */
	public List<ChoseProfessor> choseProfessorList(
            ChoseProfessor choseProfessor, Integer currpage, Integer pageSize);
	/***************************************************************************
	 * @Description 根据termId、cname找到所有的备选导师的总记录数
	 * @return int
	 * @author 赵晶
	 * @date 2017.12.11
	 **************************************************************************/
	public int countProfessorList(ChoseProfessor choseProfessor);
	/***************************************************************************
	 * @Description 根据termId、username找到的备选导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2017.12.15
	 **************************************************************************/
	public ChoseProfessor findChoseProfessor(ChoseProfessor choseProfessor);
	/***************************************************************************
	 * @Description 找到不在某大纲下的剩余导师
	 * @return List<User>
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	public List<User> findUserNotInProfessor(int themeId, int currpage, int pagesize);
	/***************************************************************************
	 * @Description 找到不在某大纲下的剩余导师数量
	 * @return List<User>
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	public int findUserNotInProfessorCount(int themeId);
	/***************************************************************************
	 * @Description 找到不在某届的所有学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-12-25
	 **************************************************************************/
	public List<User> findStudentByUser(Integer currpage, int pageSize,
                                        User user);
	/***************************************************************************
	 * @Description 统计找到不在某届的所有学生数
	 * @return int
	 * @author 赵晶
	 * @date 2017-12-25
	 **************************************************************************/
	public int findCountStudentByUser(User user);
	/***************************************************************************
	 * @Description 找出选中其它届的所有学生realAttendanceTime为选中的届
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-12-28
	 **************************************************************************/
	public List<User> findSelectStudentByUser(int currpage, int pageSize,
                                              User user);
	/***************************************************************************
	 * @Description 统计选中其它届的所有学生realAttendanceTime为选中的届
	 * @return int
	 * @author 赵晶
	 * @date 2017-12-28
	 **************************************************************************/
	public int findCountSelectStudentByUser(User user);
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
	/***************************************************************************
	 * @Description 统计找到所属的备选大纲
	 * @return int
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public int findBelongChoseThemeListCount(ChoseTheme choseTheme, User user);
	/***************************************************************************
	 * @Description 找到所属的备选大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public List<ChoseTheme> BelongChoseThemeList(ChoseTheme choseTheme, User user,
                                                 int currpage, int pageSize);
	/***************************************************************************
	 * @Description 根据备选大纲id找到可选的所有导师-学生
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public List<ChoseProfessor> findProfessorListByChemeId(Integer id);
	/***************************************************************************
	 * @Description 根据备选大纲id找到所属互选志愿批次-学生
	 * @return ChoseProfessorBatch
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public List<ChoseProfessorBatch> belongChoseProfessorBatchList(Integer id);
	/***************************************************************************
	 * @Description 根据已经选择的批次-学生
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-1-2
	 **************************************************************************/
	public ChoseProfessorRecord getChoseProfessorRecordByIdAndUsername(
            Integer id, String username);
	/***************************************************************************
	 * @Description 根据导师username找到符合条件的备选导师列表-导师
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @param pageSize
	 * @param currpage
	 * @date 2017-1-3
	 **************************************************************************/
	public List<ChoseProfessor> choseProfessorListByUsername(String username);
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表-导师
	 * @return List<Object[]>
	 * @author 赵晶
	 * @param pageSize
	 * @param currpage
	 * @date 2017-1-3
	 **************************************************************************/
	public List<Object[]> choseProfessorBatchListForProfessor(
            Integer themeId, String username, Integer currpage, int pageSize);
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public int choseProfessorBatchListForProfessorCount(Integer themeId,
                                                        String username);
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到未审核的学生名单-导师
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public List<ChoseProfessorRecord> aduitStudentList(String tname,
                                                       Integer batchId);
	/***************************************************************************
	 * @Description 获取所属大纲的已选学生数量-导师
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public Object[] choseThemeStudentCount(String username, Integer themeId);
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到审核通过的学生名单-导师
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-1-4
	 **************************************************************************/
	public List<ChoseProfessorRecord> studentList(String username,
                                                  Integer batchId);
	/****************************************************************************
	 * @Description 查询没有导师的学生列表-导师
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-01-05
	 ****************************************************************************/
	public List<User> noProfessorStudentList(Integer attendanceTime, Integer currpage, Integer pageSize);
	/****************************************************************************
	 * @Description 统计没有导师的学生数量-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-05
	 ****************************************************************************/
	public int noProfessorStudentListCount(Integer attendanceTime,
                                           Integer currpage, Integer pageSize);
	/****************************************************************************
	 * @Description 有剩余位置的导师列表-管理员
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2017-01-06
	 ****************************************************************************/
	public List<Object[]> havePositionProfessorList(User user, Integer choseThemeId,
                                                    Integer currpage, int pageSize);
	/****************************************************************************
	 * @Description 有剩余位置的导师列表数-管理员
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public int findhavePositionProfessorListCount(User user, Integer choseThemeId);
	/****************************************************************************
	 * @Description 根据导师的username和所属的大纲choseThemeId找到导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public ChoseProfessor getChoseProfessorByUsernameAndThemeId(
            String professor, Integer choseThemeId);
	/****************************************************************************
	 * @Description 找到属于当前时间的批次
	 * @return ChoseProfessorBatch
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public ChoseProfessorBatch belongCurrentTimeChoseProfessorBath(
            Calendar currTime);
	/****************************************************************************
	 * @Description 根据choseThemeId找到参选人数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public int findParticipantNumberBythemeId(Integer theYear);
	/****************************************************************************
	 * @Description 根据choseThemeId找到未被选人数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public int findNoSelectedNumberBythemeId(Integer theYear, Integer themeId);
	/****************************************************************************
	 * @Description 根据批次ids数组和导师id找到某大纲下某导师中所有审核通过的学生
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public List<ChoseProfessorRecord> passStudentListForProfessor(
            int[] batchIds, Integer professorId);
	/****************************************************************************
	 * @Description 根据导师的username找到所属的导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public ChoseProfessor findProfessorByProfessorUsername(String professor);
	/****************************************************************************
	 * @Description 根据导师username找到导师所有的学生列表
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public List<User> myStudentForProfessor(String username);
	/****************************************************************************
	 * @Description 根据导师username找到导师所有的导师列表
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2017-01-10
	 ****************************************************************************/
	public List<ChoseProfessor> findchoseProfessorList(String tUsername);
	/****************************************************************************
	 * @Description 根据大纲id找到大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-01-10
	 ****************************************************************************/
	public List<ChoseTheme> findChoseThemeListByThemeIds(Integer[] themeIds,
                                                         ChoseTheme choseTheme, Integer currpage, int pageSize);
	/****************************************************************************
	 * @Description 根据大纲id找到大纲记录数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-10
	 ****************************************************************************/
	public int findChoseThemeListByThemeIdsCount(Integer[] themeIds,
                                                 ChoseTheme choseTheme);
	/****************************************************************************
	 * @Description 找到所有的学部
	 * @return List<String>
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public List<String> findAllTeachingDepartment();
	/****************************************************************************
	 * @Description 找到未被选的导师-学生填志愿
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public List<ChoseProfessor> findNoSelectedProfessorListQuery(Integer id,
                                                                 Integer currpage, ChoseProfessor choseProfessor, List<Integer> professorIds, Integer pageSize);
	/****************************************************************************
	 * @Description 找到未被选的导师记录-学生填志愿
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public int findNoSelectedProfessorListQueryCount(Integer id,
                                                     ChoseProfessor choseProfessor, List<Integer> professorIds);
	/****************************************************************************
	 * @Description 查询本次大纲下attendanceTime等于所选届的学生（没有导师）数量
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public int findStudentByAttendanceTimeCount(Integer theYear);
	/****************************************************************************
	 * @Description 根据届找到同届的未关闭的大纲
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-01-15
	 ****************************************************************************/
	public List<ChoseTheme> UncloseSameOutlineList(Integer theYear);
	/****************************************************************************
	 * @Description 找到关闭大纲下的没有导师学生的列表
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-01-15
	 ****************************************************************************/
	public List<User> findNoProfessorUserByAttendancTime(String attendanceTime);
	/***************************************************************************
	 * @Description 根据username查找学年论文
	 * @return List<TeachingEvaluation>
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	public List<ChoseDessitationForYear> findChoseDisserationForYears(String username, int currpage, int pageSize, int state);
	/***************************************************************************
	 * @Description 根据username查找学年论文数量
	 * @return int
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	public int findChoseDisserationForYearsCount(String username);
	/***************************************************************************
	 * @Description 保存学年论文
	 * @return ChoseDessitationForYear
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	public ChoseDessitationForYear saveChoseDessitationForYear(ChoseDessitationForYear choseDessitationForYear);
	/***************************************************************************
	 * @Description 是否已发布学年论文
	 * @return ChoseDessitationForYear
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	public boolean isAllreadyReleasedDessitationForYear(String teacher, String student, Integer year);
	/***************************************************************************
	 * @Description 发布学年论文
	 * @return ChoseDessitationForYear
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	public ChoseDessitationForYear releasedDessitationForYear(ChoseDessitationForYear dessitationForYear);
	/***************************************************************************
	 * @Description 保存年度论文，返回存放的相对路径
	 * @return String
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	public List<String> uploadDessistationForYear(HttpServletRequest request, HttpServletResponse response, int id);
	/***************************************************************************
	 * @Description 保存年度论文对应的commonDocument
	 * @return CommonDocument
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	public CommonDocument saveDessitationDocument(List<String> saveFileAddressName);
	/***************************************************************************
	 * @Description 下载学年论文
	 * @return void
	 * @author 孙虎
	 * @date 2018-1-23
	 **************************************************************************/
	public void downloadDessistationForYear(HttpServletRequest request, HttpServletResponse response, Integer id, Integer dessistationId);
	/***************************************************************************
	 * @Description 根据类型找到注意事项列表
	 * @return List<ChoseAttention>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseAttention> findChoseAttentionByType(Integer type);
	/***************************************************************************
	 * @Description 根据类型数组找到注意事项列表
	 * @return List<ChoseAttention>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseAttention> findChoseAttentionByTypeArray(Integer[] types);
	/***************************************************************************
	 * @Description 根据大纲id和用户username找到注意事项记录
	 * @return List<ChoseAttention>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseAttentionRecord> findChoseAttentinRecordByThemIdAndUsername(
            String username, Integer themeId);
	/***************************************************************************
	 * @Description 找到user里的所有不同的批次
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2018-1-27
	 **************************************************************************/
	public List<Object> findAllAttendanceTime();
	/***************************************************************************
	 * @Description 根据大纲id找到未填写期望学生数量的导师
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-28
	 **************************************************************************/
	public List<ChoseProfessor> findNoExpectNumberBythemeId(Integer themeId);
	/***************************************************************************
	 * @Description 根据所属届找到本届的所有学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-28
	 **************************************************************************/
	public List<User> findStudentListByAttendanceTime(Integer theYear);
	/***************************************************************************
	 * @Description 根据所属届找到非本届的所有学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-29
	 **************************************************************************/
	public List<User> findOhterStudentListByRealAttendanceTime(int attendanceTime);
}