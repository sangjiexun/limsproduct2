package net.gvsun.lims.vo.labRoom;

import java.io.Serializable;

public class ParamLabSettingVO implements Serializable{

    private Integer labRoomId;
    private Integer page;
    private Integer type;
    private Integer isAppointment;
    private Integer needAudit;
    private String[] realAllAudits;
    private String[] academies;
    private Integer labRoomWorker;

    public Integer getLabRoomId() {
        return labRoomId;
    }

    public void setLabRoomId(Integer labRoomId) {
        this.labRoomId = labRoomId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsAppointment() {
        return isAppointment;
    }

    public void setIsAppointment(Integer isAppointment) {
        this.isAppointment = isAppointment;
    }

    public Integer getNeedAudit() {
        return needAudit;
    }

    public void setNeedAudit(Integer needAudit) {
        this.needAudit = needAudit;
    }

    public String[] getRealAllAudits() {
        return realAllAudits;
    }

    public void setRealAllAudits(String[] realAllAudits) {
        this.realAllAudits = realAllAudits;
    }

    public String[] getAcademies() {
        return academies;
    }

    public void setAcademies(String[] academies) {
        this.academies = academies;
    }

    public Integer getLabRoomWorker() {
        return labRoomWorker;
    }

    public void setLabRoomWorker(Integer labRoomWorker) {
        this.labRoomWorker = labRoomWorker;
    }
}
