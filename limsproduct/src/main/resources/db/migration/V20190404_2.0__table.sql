-- 插入物资管理相关数据
-- 吴奇臻
-- 2019/04/04
INSERT INTO `system_menu` (`id`, `name`, `project_name`, `order_number`, `identification`, `is_used`, `hyperlink`, `parent_id`) VALUES ('218', '物资名录', 'limsproduct', '216', '', '1', '/lims/api/material/listAssetsAPI', '42');
INSERT INTO `system_menu` (`id`, `name`, `project_name`, `order_number`, `identification`, `is_used`, `hyperlink`, `parent_id`) VALUES ('219', '物资申购', 'limsproduct', '217', NULL, '1', '/lims/api/material/listAssetsApplyAPI', '42');
INSERT INTO `system_menu` (`id`, `name`, `project_name`, `order_number`, `identification`, `is_used`, `hyperlink`, `parent_id`) VALUES ('220', '物资入库', 'limsproduct', '218', NULL, '1', '/lims/api/material/listAssetsInStorageAPI', '42');
INSERT INTO `system_menu` (`id`, `name`, `project_name`, `order_number`, `identification`, `is_used`, `hyperlink`, `parent_id`) VALUES ('221', '物资申领', 'limsproduct', '219', NULL, '1', '/lims/api/material/listAssetsReceiveAPI', '42');
