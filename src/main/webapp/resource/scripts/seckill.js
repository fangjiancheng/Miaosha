//存放主要交互逻辑的js代码
//javascripts的模块化
var seckill = {
    //封装秒杀相关的ajax的url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill' + '/'+seckillId + '/exposer';
        },
        execution:function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';

        }
    },
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    //执行秒杀的函数
    exscuteSeckill: function (seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            if(result&&result['success']){
                var exposer=result['data'];
                if(exposer['exposed']){
                    //执行秒杀
                    var md5=exposer['md5'];
                    var killUrl=seckill.URL.execution(seckillId,md5);
                    //http://localhost:8080/seckill/1011/d2b9437bf807886ce2b0862e47707858/execution
                    console.log("killurl="+killUrl);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function () {
                        //禁用按钮
                        $(this).addClass('disable');
                        $.post(killUrl,{},function (result) {
                            if(result&&result['success']){
                                var execution=result['data'];
                                var state=execution['status'];
                                var stateInfo=execution['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        })
                    });
                    node.show();

                }else{
                    //未开启秒杀
                    var now=exposer['now'];
                    var start=exposer['start'];
                    var end=exposer['end'];
                    //重新及时逻辑
                    seckill.countdown(seckillId,now,start,end);
                    console.log('未开启秒杀');
                }
            }else{
                console.log('result'+result);
            }
        });
    },
    //时间判断处理函数
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            //秒杀未开始
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀计时：%D 天%H小时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                //获取秒杀地址，控制实现逻辑，执行秒杀
                seckill.exscuteSeckill(seckillId, seckillBox);
            });
        } else {
            //秒杀开始
            seckill.exscuteSeckill(seckillId, seckillBox);
        }
    },
    //详情页面秒杀逻辑
    detail: {
        //详情页面初始化
        init: function (params) {
            //手机登录和验证,计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //验证手机号码
            if (!seckill.validatePhone(killPhone)) {
                //绑定手机
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    //显示弹出层
                    show: true,
                    //禁止位置关闭
                    backdrop: 'static',
                    //禁止键盘时间关闭
                    keyboard: false
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    //电话写入cookie
                    $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                    if (seckill.validatePhone(inputPhone)) {
                        //    刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号码输入有误</label>').show(300);
                    }
                });
            }
            //已经登录
            //计时交互
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result=' + result);
                }
            })
        }
    }
}
