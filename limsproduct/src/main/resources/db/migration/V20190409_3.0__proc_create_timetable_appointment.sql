-- 功能：实验室预约的保存方法
-- 作者：魏诚
-- 日期：2019-03-06
DROP PROCEDURE if exists `proc_create_timetable_appointment`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_create_timetable_appointment`(
IN in_username   varchar(255),
IN in_courseCode   varchar(255),
IN in_labroom  int,
IN in_status  int,
IN in_timetableStyle  int,
IN in_termId   int,
IN in_courseNumber   varchar(255),
IN in_selfCourseCode   int,
IN in_weekday int,
IN in_weeks varchar(255),
IN in_classes varchar(255),
IN in_createdDate   varchar(255),
IN in_updatedDate   varchar(255),
OUT out_timetable_id int
)
Begin
  
  DECLARE var_newId INT;
	DECLARE var_sameId INT;
	DECLARE i,j,k,l INT;
	DECLARE startClass,endClass INT;
	DECLARE startWeek,endWeek INT;
	DECLARE startTime,endTime,appDate,var_weekday LONGTEXT;
	SET i=0;
	SET j=0;
	SET k=0;
	SET l=0;
	
	SET @weeklength=1+(LENGTH(in_weeks) - LENGTH(REPLACE(in_weeks,',','')));
  SET @beforWeek = 0;
	SET @var_Weeks = '';
	
	
	START TRANSACTION;
	
	WHILE i<@weeklength DO
	  SET @currWeek = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(in_weeks,',',i)),',',1));
		
		If  @beforWeek = 0 then
		  SET @var_Weeks = Concat(@currWeek,',');
		ElseIf @beforWeek >0 then
		  If  @beforWeek < @currWeek-1 and i<@weeklength-1 then
			   SET @var_Weeks = Concat(@var_Weeks , @beforWeek ,';', @currWeek,',');
		  
			ElseIf @beforWeek < @currWeek-1 and i=@weeklength-1 then
			   SET @var_Weeks = Concat(@var_Weeks , @beforWeek ,';', @currWeek,',', @currWeek,';');
			
			ElseIf @beforWeek = @currWeek-1 and i=@weeklength-1 then
			   SET @var_Weeks = Concat(@var_Weeks, @currWeek,';');
			End If;
		Else
		  SET i=i;
		End If;
		SET @beforWeek = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(in_weeks,',',i)),',',1));
		SET i=i+1;
	END WHILE;
	
	If LENGTH(@var_Weeks)=2 then
	  SET @var_Weeks = Concat(@var_Weeks, REPLACE(@currWeek, ',', ''),';');
	End if;
	
	SET @beforClass = 0;
	SET @var_Classes = '';
	SET @classlength=1+(LENGTH(in_classes) - LENGTH(REPLACE(in_classes,',','')));
	
	WHILE j<@classlength DO
	  SET @currClass = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(in_classes,',',j)),',',1));
		
		If  @beforClass = 0 then
		  SET @var_Classes = Concat(@currClass,',');
		ElseIf @beforClass >0 then
		  If  @beforClass < @currClass-1 and j<@classlength-1 then
			   SET @var_Classes = Concat(@var_Classes , @beforClass ,';', @currClass,',');
		  
			ElseIf @beforClass < @currClass-1 and j=@classlength-1 then
			   SET @var_Classes = Concat(@var_Classes , @beforClass ,';', @currClass,',', @currClass,';');
			
			ElseIf @beforClass = @currClass-1 and j=@classlength-1 then
			   SET @var_Classes = Concat(@var_Classes, @currClass,';');
			End If;
		Else
		  SET j=j;
		End If;
		SET @beforClass = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(in_classes,',',j)),',',1));
		SET j=j+1;
	END WHILE;
	
	If LENGTH(@var_Classes)=2 then
	  SET @var_Classes = Concat(@var_Classes, REPLACE(@var_Classes, ',', ''),';');
	End if;
  
	
	
	insert into timetable_appointment(
		course_code	,
		created_date	,
		updated_date	,
		created_by	,
		status	,
		enabled	,
		weekday,
		timetable_style	,
		self_course_code	,
		course_number	,
		device_or_lab	,
		term	
		)
		VALUES(
		in_courseCode	,
		in_createdDate	,
		in_updatedDate	,
		in_username	,
		in_status	,
		1	,
		in_weekday,
		in_timetableStyle	,
		in_selfCourseCode	,
		in_courseNumber	,
		2	,
		in_termId	
		);
	
  SET var_newId = @@IDENTITY;
	SET out_timetable_id = var_newId;	
	
	insert into timetable_lab_related(
		appointment_id,
		lab_id
	)
	VALUES(
	  var_newId,
		in_labroom  
	);
	
	SET i=0;
	SET j=0;
	SET @weeklength=(LENGTH(@var_Weeks) - LENGTH(REPLACE(@var_Weeks,';','')));

	SET @classlength=(LENGTH(@var_Classes) - LENGTH(REPLACE(@var_Classes,';','')));
	WHILE i<@weeklength DO
		WHILE j<@classlength DO
			SET @currWeek = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(@var_Weeks,';',i+1)),';',1));
			SET @currClass = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(@var_Classes,';',j+1)),';',1));
		  
			SET @startWeek = SUBSTRING_INDEX(@currWeek,',',1);
			
			SET @endWeek = right(@currWeek,LOCATE(',',REVERSE(@currWeek))-1);
			
			SET @startClass = SUBSTRING_INDEX(@currClass,',',1);
			
			SET @endClass = right(@currClass,LOCATE(',',REVERSE(@currClass))-1);
			insert into timetable_appointment_same_number(
				created_date	,
				created_by	,
				weekday	,
				start_week	,
				end_week	,
				total_weeks,
				start_class	,
				end_class	,
				appointment_id	
				)
				VALUES(
				in_createdDate	,
				in_username	,
				in_weekday	,
				@startWeek	,
				@endWeek	,
				'',
				@startClass	,
				@endClass	,
				var_newId	
				);
			
      SET var_sameId = @@IDENTITY;
			
			
			select st.start_date into startTime from system_time st where st.section=@startClass;
			select st.end_date into endTime from system_time st where st.section=@endClass;
      
			WHILE k<=@endWeek-@startWeek DO
				select date,weekday into appDate,var_weekday from school_week where school_week.`week`=@startWeek+k and 
				       school_week.term_id= in_termId and school_week.weekday=in_weekday;
						 
				insert into timetable_appointment_datetime(
				 created_date,
				 created_by,
				 start_time,
				 end_time,
				 reservation_date,
				 appointment_id
				)
				VALUES(
				 in_createdDate	,
				 in_username	,
				 startTime,
				 endTime,
				 appDate,
				 var_newId
				);
				SET k=k+1;
			END WHILE;
			SET k=0;
			SET j=j+1;
		END WHILE;
		SET i=i+1;
		SET j=0;
		SET k=0;
		
	  
	
	END WHILE;
	COMMIT;  
	
End