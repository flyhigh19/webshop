$(function(){
    //查询用户信息
    $.get("user/FindLoginUser.do",{},function (data) {
        if(data){
            $("#nologin").hide();
            $("#welcomelogin").show();
            $(".icon_button").hide();
            $(".login_success").show();
            var phone=data.memberPhone.substr(0, 3) + '****' + data.memberPhone.substring(7, 11);
            var msg="<a style='margin-left: 28px;color: black'>"+"Hi,"+phone+"</a>";
            $("#welcomelogin").html(msg);
        }else{
            $("#nologin").show();
            $("#welcomelogin").hide();
            $(".icon_button").show();
            $(".login_success").hide();
        }
    });

    /*var tabTimer;

    //鼠标滑上选项标签停止自动播放，滑出时开始自动播放
    $(".tab .right_screen_btn ul li").hover(function(){
        clearInterval(tabTimer);
    },function(){
        tabTimer = window.setInterval(_foo(-1,4),3000);
    }).trigger("mouseleave");

    function foo(i,len)
    {
        $(".tab .right_screen_btn ul li").eq(i).addClass("on").siblings().removeClass("on");
        $(".right_screen_center>div").eq(i).show().siblings().hide();
    }
    function _foo(i,len)
    {
        return function()
        {
            if(i<len){
                i++
            }else {
                i=0;
            }
            foo(i);
        }
    }*/
    /*主模块的公告、论坛、酒店、机票的切换效果*/
    $(".tab .right_screen_btn ul li").hover(function(){
        $(this).addClass("on").siblings().removeClass("on"); //切换选中的按钮高亮状态
        var index=$(this).index(); //获取被按下按钮的索引值，需要注意index是从0开始的
        $(".right_screen_center>div").eq(index).show().siblings().hide(); //在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内容
    });
    /*第二个模块左部图书的切换效果*/
    $(".second_screen .second_screen_left .second_screen_left_top ul li").hover(function(){
        $(this).addClass("ontushu").siblings().removeClass("ontushu"); //切换选中的按钮高亮状态
        var index=$(this).index(); //获取被按下按钮的索引值，需要注意index是从0开始的
        $(".second_screen_left_bottom>div").eq(index).show().siblings().hide(); //在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内容
    });
    /*第二个模块图片排行榜的切换代码*/
    $(".second_screen_right_top ul li").hover(function(){
        $(this).addClass("ontushu_desc").siblings().removeClass("ontushu_desc"); //切换选中的按钮高亮状态
        var index=$(this).index(); //获取被按下按钮的索引值，需要注意index是从0开始的
        $(".second_screen_right_bottom>div").eq(index).show().siblings().hide(); //在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内容
    });
    /*第三个模块的服装的切换代码*/
    $(".three_screen_top ul li").hover(function(){
        $(this).addClass("fuzhuan").siblings().removeClass("fuzhuan"); //切换选中的按钮高亮状态
        var index=$(this).index(); //获取被按下按钮的索引值，需要注意index是从0开始的
        $(".three_screen_bottom_right>div").eq(index).show().siblings().hide(); //在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内容
    });
});