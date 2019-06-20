-- 功能：删除原耗材模块菜单记录
-- 作者：吴奇臻
-- 日期：2019-06-20
delete from authority_menu where menu_id in (SELECT id from system_menu where name='耗材字典');
DELETE from system_menu where name='耗材字典';
delete from authority_menu where menu_id in (SELECT id from system_menu where name='耗材申购');
DELETE from system_menu where name='耗材申购';
delete from authority_menu where menu_id in (SELECT id from system_menu where name='申购审核');
DELETE from system_menu where name='申购审核';
delete from authority_menu where menu_id in (SELECT id from system_menu where name='耗材入库');
DELETE from system_menu where name='耗材入库';
delete from authority_menu where menu_id in (SELECT id from system_menu where name='耗材申领');
DELETE from system_menu where name='耗材申领';
delete from authority_menu where menu_id in (SELECT id from system_menu where name='申领审核');
DELETE from system_menu where name='申领审核';