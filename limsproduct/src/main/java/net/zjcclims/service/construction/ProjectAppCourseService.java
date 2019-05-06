package net.zjcclims.service.construction;



import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectAppCourse;
import net.zjcclims.domain.SchoolCourse;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAppCourse entities
 * 
 */
public interface ProjectAppCourseService {

	/**
	 */
	public ProjectAppCourse findProjectAppCourseByPrimaryKey(Integer id);

	/**
	 * Load an existing ProjectAppCourse entity
	 * 
	 */
	public Set<ProjectAppCourse> loadProjectAppCourses();

	/**
	 * Save an existing ProjectAppCourse entity
	 * 
	 */
	public void saveProjectAppCourse(ProjectAppCourse projectappcourse);

	/**
	 * Delete an existing SchoolCourse entity
	 * 
	 */
	public ProjectAppCourse deleteProjectAppCourseSchoolCourse(Integer projectappcourse_id, String related_schoolcourse_courseNo);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ProjectAppCourse deleteProjectAppCourseLabConstructApp(Integer projectappcourse_id_1, Integer related_labconstructapp_id);

	/**
	 * Return all ProjectAppCourse entity
	 * 
	 */
	public List<ProjectAppCourse> findAllProjectAppCourses(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing ProjectAppCourse entity
	 * 
	 */
	public void deleteProjectAppCourse(ProjectAppCourse projectappcourse_1);

	/**
	 * Save an existing SchoolCourse entity
	 * 
	 */
	public ProjectAppCourse saveProjectAppCourseSchoolCourse(Integer id_1, SchoolCourse related_schoolcourse);

	/**
	 * Return a count of all ProjectAppCourse entity
	 * 
	 */
	public Integer countProjectAppCourses();

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ProjectAppCourse saveProjectAppCourseLabConstructApp(Integer id_2, LabConstructApp related_labconstructapp);
}