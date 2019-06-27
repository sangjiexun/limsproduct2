-- 功能：更新lab_open_up_academy旧数据处理
-- 作者：贺照易
-- 日期：2019-06-27
UPDATE lab_open_up_academy l SET l.authority_id=-1 WHERE l.authority_id is NULL;




