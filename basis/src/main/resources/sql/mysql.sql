DROP TABLE IF EXISTS `db_user`;
DROP TABLE IF EXISTS `db_dept`;

-- ----------------------------
-- Table structure for db_dept
-- ----------------------------
CREATE TABLE `db_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(20) CHARACTER SET gbk NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for db_user
-- ----------------------------
CREATE TABLE `db_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `age` tinyint(4) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `dept_id` int(11) NULL DEFAULT NULL,
  `msg` blob,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pk_user_dept`(`dept_id`) USING BTREE,
  CONSTRAINT `pk_user_dept` FOREIGN KEY (`dept_id`) REFERENCES `db_dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4;


-- 存储过程 实现分页
CREATE PROCEDURE `my_procedure`(
IN pageNum int,
IN pageSize int,
OUT total int
)
BEGIN
		set @total=0;
		set @sql = 'select count(1) into @total from db_user';

    prepare stmt from @sql; /*预处理 自定义sql字符串*/
    execute stmt; /*执行自定义sql语句*/
    deallocate prepare stmt; /*释放预处理资源*/
		set total = @total;

		SET @sql = CONCAT('select * from db_user limit ', (pageNum-1) * pageSize, ',', pageSize);
		prepare stmt from @sql; /*预处理 自定义sql字符串*/
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END


INSERT INTO `db_dept`(`id`, `dept_name`) VALUES (1, '开发部');
INSERT INTO `db_dept`(`id`, `dept_name`) VALUES (2, '市场部');
INSERT INTO `db_user`(`id`, `user_name`, `age`, `status`, `create_at`, `dept_id`, `msg`) VALUES (1, '门那粒沙', 20, 1, '2020-06-06 20:11:26', 1, 0xC5A3B1C6);

