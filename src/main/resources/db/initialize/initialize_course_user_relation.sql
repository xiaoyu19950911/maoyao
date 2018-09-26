DROP TABLE IF EXISTS `course_user_relation`;

CREATE TABLE `course_user_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `column_id` int(11) DEFAULT NULL COMMENT '专栏id',
  `course_id` int(11) DEFAULT NULL COMMENT '课程id',
  `create_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `price` decimal(19,2) DEFAULT NULL COMMENT '购买时的价格',
  `update_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  `user_id` int(11) DEFAULT NULL COMMENT '购买的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=730 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  PARTITION BY KEY (id)
  PARTITIONS 512 ;