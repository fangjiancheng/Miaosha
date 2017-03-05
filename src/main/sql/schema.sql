




DROP TABLE IF EXISTS seckill;
CREATE TABLE seckill(
`seckill_id` bigint not null auto_increment comment '商品库存id',
`name` VARCHAR(120) not NULL comment '商品名称',
`number` INT not NULL comment '商品数量',
`start_time` datetime NOT NULL comment '秒杀开启时间',
`end_time` datetime NOT NULL comment '秒杀结束时间',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
PRIMARY KEY (seckill_id),
KEY id_start_time(start_time),
KEY id_end_time(end_time),
key id_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment='秒杀系统库存表';
INSERT  INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒杀iphone6',100,'2017-03-03 00:00:00','2017-03-20 00:00:00'),
  ('800元秒杀ipad2',200,'2017-03-01 00:00:00','2017-03-02 00:00:00'),
  ('300元秒杀小米４',300,'2017-05-05 00:00:00','2017-05-12 00:00:00'),
  ('200元秒杀红米note',400,'2017-03-03 00:00:00','2017-03-12 00:00:00');







DROP TABLE IF EXISTS success_killed;
CREATE TABLE success_killed(
`seckill_id` bigint not NULL comment '秒杀商品id',
`user_phone` bigint NOT NULL comment '用户手机号码',
`state` tinyint not null DEFAULT -1 comment '状态标识-1:无效 0:成功',
`create_time` datetime not null  DEFAULT CURRENT_TIMESTAMP comment 'c创建时间',
PRIMARY KEY (seckill_id,user_phone),
KEY id_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='秒杀成功明细表';