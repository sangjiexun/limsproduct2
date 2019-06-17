-- 功能：修改常规检查字段类型
-- 作者：顾延钊
-- 日期：2019-06-17
alter table routine_inspection MODIFY check_content text;
alter table routine_inspection MODIFY safety_management text;