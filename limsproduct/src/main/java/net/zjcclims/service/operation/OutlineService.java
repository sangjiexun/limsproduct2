package net.zjcclims.service.operation;

import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.OperationOutline;

import java.util.List;
import java.util.Map;

public interface OutlineService {
    /**
     * Description 实验大纲查询
     * @param outline
     * @param currpage
     * @return
     * @author 陈乐为 2018-9-14
     */
    public List<OperationOutline> findOutlineByQuery(OperationOutline outline, int currpage, int pagesize);

    /**
     * Description 实验大纲查询数量
     * @param outline
     * @return
     * @author 陈乐为 2018-9-14
     */
    public int findOutlineByQueryCount(OperationOutline outline);

    /**
     * Description 查找对象
     * @param idKey
     * @return
     * @author 陈乐为 2018-9-14
     */
    public OperationOutline findOutlineByPrimaryKey(Integer idKey);

    /**
     * Description 获取学院课程
     * @param acno
     * @return
     * @author 陈乐为 2018-9-14
     */
    public Map getSchoolCourseInfo(String acno);

    /**
     * Description 大纲课程学分
     * @return
     * @author 陈乐为 2018-9-14
     */
    public Map getOutlineCreditMap();

    /**
     * Description 查询项目
     * @param str
     * @return
     * @author 陈乐为 2018-9-17
     */
    public List<OperationItem> getSaveGenerateperations(String str);

    /**
     * Description 保存大纲
     * @param operationOutline
     * @param schoolMajors
     * @param commencementnaturemap
     * @param projectitrms
     * @return
     * @author 陈乐为 2018-9-17
     */
    public OperationOutline saveOutline(OperationOutline operationOutline,
                                        String[] schoolMajors,String[] properties,String[] items, String[] teachers);
    /**
     * Description 查询大纲（不分页）
     * @return
     * @author 廖文辉 2018-10-29
     */
    public List<OperationOutline> getOperationOutlineNoPage();

}