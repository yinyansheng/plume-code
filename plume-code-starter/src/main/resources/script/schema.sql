DROP TABLE IF EXISTS `smart_user`;

CREATE TABLE `smart_user`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`     varchar(32) DEFAULT NULL COMMENT '姓名',
    `password` varchar(32) DEFAULT NULL COMMENT '密码',
    `version`  bigint(20) DEFAULT 1000 COMMENT 'version',
    PRIMARY KEY (`id`)
);
