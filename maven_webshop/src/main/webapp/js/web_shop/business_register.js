function checkUserName () {
    var user_flag=false;
    var username = $("#userName").val();
    var flag = /^[a-zA-Z][a-zA-Z0-9_]{3,19}$/.test(username);
    var blank=/(^\s+)|(\s+$)/g.test(username);
    if(blank){
        $("#userName").css("border","1px solid red");
        $("#J_tipUserName").empty();
        $("#J_tipUserName").removeClass("sure")
        $("#J_tipUserName").addClass("warn");
        $("#J_tipUserName").html("用户名中不能包含空格，只能输入字母、数字和下划线的组合！");
    }else if (username.length<4){
        $("#userName").css("border","1px solid red");
        $("#J_tipUserName").empty();
        $("#J_tipUserName").removeClass("sure")
        $("#J_tipUserName").addClass("warn");
        $("#J_tipUserName").html("最少4个字符，请输入您的用户名");
        $("#yes_username").remove();
    }else if(username.length>=4&&!blank){
        if(flag==true){
            /*这里必须设置请求为同步，不然等结果返回，前端代码早就执行完了*/
            $.ajaxSettings.async = false;
            $.post("user/isExistUserName.do",{employeeUser:username},function (data) {
                if(!data.flag){
                    $("#userName").css("border","1px solid red");
                    $("#J_tipUserName").empty();
                    $("#J_tipUserName").removeClass("sure");
                    $("#J_tipUserName").addClass("warn");
                    $("#J_tipUserName").html(data.errorMsg);
                }else{
                    $("#userName").css("border","");
                    $("#J_tipUserName").empty();
                    $("#J_tipUserName").removeClass("warn");
                    if($("#yes_username").length<1){
                        $("#userName").after('<b id="yes_username">√</b>')
                    }
                    user_flag=true;
                }
            },"json");
        }else if(!flag){
            $("#userName").css("border","1px solid red");
            $("#J_tipUserName").empty();
            $("#J_tipUserName").removeClass("sure")
            $("#J_tipUserName").addClass("warn");
            $("#J_tipUserName").html("用户名不能全由数字组成，必须以大写字母/小写字母开头！");
            $("#yes_username").remove();
        }
    }
    return user_flag;
}
function checkPassword () {
    var password = $("#employeePwd").val();
    var  flag = /^\w{6,20}$/.test(password);
    if (!flag){
        $("#employeePwd").css("border","1px solid red");
        $("#J_tipPassword").empty();
        $("#J_tipPassword").removeClass("sure")
        $("#J_tipPassword").addClass("warn");
        $("#J_tipPassword").html("密码长度为6-20个字符，请重新输入");
        $("#yes_password").remove();
    }else {
        if(isSimplePwd(password)<=1){
            $("#employeePwd").css("border","");
            $("#J_tipPassword").empty();
            $("#J_tipPassword").removeClass("warn");
            $("#J_tipPassword").addClass("sure");
            $("#J_tipPassword").html("密码过于简单");
        }else if(isSimplePwd(password)<=2){
            $("#employeePwd").css("border","");
            $("#J_tipPassword").empty();
            $("#J_tipPassword").removeClass("warn");
            $("#J_tipPassword").addClass("sure");
            $("#J_tipPassword").html("试试字母、符号、数字的组合更安全");
        }else{
            $("#employeePwd").css("border","");
            $("#J_tipPassword").empty();
            $("#J_tipPassword").removeClass("warn");
            $("#J_tipPassword").addClass("sure");
            $("#J_tipPassword").html("密码设置安全，放心使用");
        }
        if($("#yes_password").length<1){
            $("#employeePwd").after('<b id="yes_password">√</b>')
        }
    }
    return flag;
}
/**
 *简单验证密码强度
 *必须包含数字、小写字母、大写字母、特殊字符 其三
 *如果返回值小于3 则说明密码过于简单
 */
function isSimplePwd(s){
    var ls = 0;
    if(s.match(/([a-z])+/)){
        ls++;
    }
    if(s.match(/([0-9])+/)){
        ls++;
    }
    if(s.match(/([A-Z])+/)){
        ls++;
    }
    if(s.match(/[^a-zA-Z0-9]+/)){
        ls++;
    }
    return ls;
}
function confirmPassword(){
    var flag=false;
    var password=$("#employeePwd").val();
    var repassword=$("#Repassword").val();
    if(password==repassword&&repassword.length>0){
        flag=true;
        $("#Repassword").css("border","");
        $("#J_tipRePassword").empty();
        $("#J_tipRePassword").removeClass("warn");
        if($("#yes_repassword").length<1){
            $("#Repassword").after('<b id="yes_repassword">√</b>')
        }
    }else{
        $("#Repassword").css("border","1px solid red");
        $("#J_tipRePassword").empty();
        $("#J_tipRePassword").removeClass("sure");
        $("#J_tipRePassword").addClass("warn");
        $("#J_tipRePassword").html("两次输入的密码不一致，请重新输入");
        $("#yes_repassword").remove();
    }
    return flag;
}
/*邮箱验证*/
function email() {
    var businessEmail = $("#businessEmail").val();
    var flag = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/.test(businessEmail);
    if(!flag){
        $("#businessEmail").css("border","1px solid red");
        $("#J_tipBusinessEmail").empty();
        $("#J_tipBusinessEmail").removeClass("sure")
        $("#J_tipBusinessEmail").addClass("warn");
        $("#J_tipBusinessEmail").html("邮箱格式不正确，请重新输入");
        $("#yes_businessemail").remove();
    }else{
        /*这里必须设置请求为同步，不然等结果返回，前端代码早就执行完了*/
        $.ajaxSettings.async = false;
        $.post("user/isExistEmail.do",{businessEmail:businessEmail},function (data) {
            if(!data.flag){
                $("#businessEmail").css("border","1px solid red");
                $("#J_tipBusinessEmail").empty();
                $("#J_tipBusinessEmail").removeClass("sure");
                $("#J_tipBusinessEmail").addClass("warn");
                $("#J_tipBusinessEmail").html(data.errorMsg);
                $("#yes_businessemail").remove();
                flag=false;
            }else{
                $("#businesseEail").css("border","");
                $("#J_tipBusinessEmail").empty();
                $("#J_tipBusinessEmail").removeClass("warn");
                if($("#yes_businessemail").length<1){
                    $("#businessEmail").after('<b id="yes_businessemail">√</b>')
                }
                flag=true;
            }
        },"json");
    }
    return flag;
}
/*座机验证*/
function checkTel(){
    var businessPhone = $("#businessPhone").val();
    var flag = /^0\d{2,3}-?\d{7,8}$/.test(businessPhone);
    if(!flag){
        $("#businessPhone").css("border","1px solid red");
        $("#J_tipBusinessPhone").empty();
        $("#J_tipBusinessPhone").removeClass("sure")
        $("#J_tipBusinessPhone").addClass("warn");
        $("#J_tipBusinessPhone").html("公司固定座机输入不正确，请重新输入");
        $("#yes_businessphone").remove();
    }else{
        $("#businessPhone").css("border","");
        $("#J_tipBusinessPhone").empty();
        $("#J_tipBusinessPhone").removeClass("warn");
        if($("#yes_businessphone").length<1){
            $("#businessPhone").after('<b id="yes_businessphone">√</b>')
        }
    }
    return flag;
}
/*法人手机验证*/
function checkMemberPhone () {
    var legalPhone = $("#legalPhone").val();
    var flag = /^1[34578]\d{9}$/.test(legalPhone);
    if (!flag){
        $("#legalPhone").css("border","1px solid red");
        $("#J_tiplegalPhone").empty();
        $("#J_tiplegalPhone").removeClass("sure")
        $("#J_tiplegalPhone").addClass("warn");
        $("#J_tiplegalPhone").html("手机格式不正确，请重新输入");
        $("#phone_yes").remove();
    }else {
        /*这里必须设置请求为同步，不然等结果返回，前端代码早就执行完了*/
        $.ajaxSettings.async = false;
        $.post("user/isExistPhone.do",{legalPhone:legalPhone},function (data) {
            if(!data.flag){
                $("#legalPhone").css("border","1px solid red");
                $("#J_tiplegalPhone").empty();
                $("#J_tiplegalPhone").removeClass("sure");
                $("#J_tiplegalPhone").addClass("warn");
                $("#J_tiplegalPhone").html(data.errorMsg);
                flag=false;
            }else{
                $("#legalPhone").css("border","");
                $("#J_tiplegalPhone").empty();
                $("#J_tiplegalPhone").removeClass("warn");
                if($("#phone_yes").length<1){
                    $("#legalPhone").after('<b id="phone_yes">√</b>')
                }
                flag=true;
            }
        },"json");
    }
    return flag;
}
/*法人姓名是否符合要求*/
function checkName() {
    var legalName = $("#legalName").val();
    var flag =/^[\u4e00-\u9fa5]{2,4}$/.test(legalName);
    if (!flag) {
        $("#legalName").css("border","1px solid red");
        $("#J_tiplegalName").empty();
        $("#J_tiplegalName").removeClass("sure")
        $("#J_tiplegalName").addClass("warn");
        $("#J_tiplegalName").html("联系人姓名只能为汉字，不允许特殊符号");
        $("#yes_legalname").remove();
    } else {
        $("#legalName").css("border","");
        $("#J_tiplegalName").empty();
        $("#J_tiplegalName").removeClass("warn");
        if($("#yes_legalname").length<1){
            $("#legalName").after('<b id="yes_legalname">√</b>')
        }
    }
    return flag;
}
/*营业执照编码验证规则 15位和18位 正确返回true，错误则是false*/
function checkCode(code){
    if(code.length != 18 && code.length != 15){
        return false;
    }
    //十八位新码
    if(code.length == 18){
        var regex = /^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$/;
        if (!regex.test(code)) {
            return false;
        }
        var str = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        var ws =[1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28];
        var codes = new Array();
        codes[0] = code.substr(0, code.length-1);
        codes[1] = code.substr(code.length - 1, code.length);
        var  sum = 0;
        for (var  i = 0; i < 17; i++) {
            sum += str.indexOf(codes[0].charAt(i)) * ws[i];
        }
        var  c18 = 31 - (sum % 31);
        if (c18 == 31) {
            c18 = 'Y';
        } else if (c18 == 30) {
            c18 = '0';
        }
        if (str.charAt(c18) != codes[1].charAt(0)) {
            return false;
        }
        return true;

    }
    //十五位编码
    else{
        var ret=false;
        var sum=0;
        var s=[];
        var p=[];
        var a=[];
        var m=10;
        p[0]=m;
        for(var i=0;i<code.length;i++){
            a[i]=parseInt(code.substring(i,i+1),m);
            s[i]=(p[i]%(m+1))+a[i];
            if(0==s[i]%m){
                p[i+1]=10*2;
            }else{
                p[i+1]=(s[i]%m)*2;
            }
        }
        if(1==(s[14]%m)){
            ret=true;
        }else{
            ret=false;
        }
        return ret;
    }

}
/*入口函数*/
$(function () {
    /*
    *  校验企业员工用户名
    * */
    $("#userName").blur(checkUserName);
    $("#userName").focus(function () {
        $("#userName").removeAttr("placeholder");
        $("#userName").css("border", "");
        $("#J_tipUserName").empty();
        $("#J_tipUserName").removeClass("warn")
        $("#J_tipUserName").addClass("sure");
        $("#J_tipUserName").html("4-20个字符，可由小写字母、中文、数字组成");
        $("#phone_yes").remove();
    })
    /*
   *  校验密码
   *  	长度  6-20位字符 （字母或者数字）
   * */
    $("#employeePwd").blur(checkPassword);
    $("#employeePwd").focus(function () {
        $("#MemberPassword").css("border", "");
        $("#J_tipPassword").empty();
        $("#J_tipPassword").removeClass("warn");
        $("#J_tipPassword").addClass("sure");
        $("#J_tipPassword").html("密码为6-20个字符，可由英文、数字及符号组成");
        $("#yes_password").remove();
    });
    /*前端校验输入密码和确认密码是否一致*/
    $("#Repassword").blur(confirmPassword)
    $("#Repassword").focus(function () {
        $("#Repassword").css("border", "");
        $("#J_tipRePassword").empty();
        $("#J_tipRePassword").removeClass("warn");
        $("#J_tipRePassword").addClass("sure");
        $("#J_tipRePassword").html("请再次输入密码");
        $("#yes_repassword").remove();
    });
    /*前端验证公司名称是否正确*/
    $("#businessName").blur(function () {
        var businessname = $("#businessName").val();
        if (businessname.length != 0) {
            $("#J_tipBusinessName").empty();
            $("#J_tipBusinessName").removeClass("warn");
            $("#businessName").after('<b id="yes_businessname">√</b>');
        } else {
            $("#J_tipBusinessName").empty();
            $("#businessName").attr("placeholder", '请填写营业执照上的名称');
        }
    })
    $("#businessName").focus(function () {
        $("#yes_businessname").remove();
        $("#businessName").removeAttr("placeholder");
        $("#businessName").css("border", "");
        $("#J_tipBusinessName").empty();
        $("#J_tipBusinessName").removeClass("warn");
        $("#J_tipBusinessName").addClass("sure");
        $("#J_tipBusinessName").html("请填写营业执照上的名称，最长30个汉字（60个字符）");
    });
    /*省市区三级选择*/
    $(".rgform_right").citySelect({
        nodata: "none",
        required: false
    });
    /*填写公司地址*/
    $("#businessAddress").focus(function () {
        $("#yes_businessaddress").remove();
        $("#businessAddress").removeAttr("placeholder");
        $("#businessAddress").css("border", "");
        $("#J_tipBusinessAddress").empty();
        $("#J_tipBusinessAddress").removeClass("warn");
        $("#J_tipBusinessAddress").addClass("sure");
        $("#J_tipBusinessAddress").html("请选择并填写公司地址");
    })
    $("#businessAddress").blur(function () {
        var businessAddress = $("#businessAddress").val();
        if (businessAddress.length != 0) {
            $("#J_tipBusinessAddress").empty();
            $("#J_tipBusinessAddress").removeClass("warn");
            $("#businessAddress").after('<b id="yes_businessaddress">√</b>');
        } else {
            $("#J_tipBusinessAddress").empty();
            $("#businessAddress").attr("placeholder", '请填写街道地址');
        }
    })
    /*企业邮箱验证*/
    $("#businessEmail").blur(email);
    $("#businessEmail").focus(function () {
        $("#yes_businessemail").remove();
        $("#businessEmail").removeAttr("placeholder");
        $("#businessEmail").css("border", "");
        $("#J_tipBusinessEmail").empty();
        $("#J_tipBusinessEmail").removeClass("warn");
        $("#J_tipBusinessEmail").addClass("sure");
        $("#J_tipBusinessEmail").html("请输入邮箱，建议填写企业邮箱");
    })
    /*公司座机验证*/
    $("#businessPhone").blur(checkTel);
    $("#businessPhone").focus(function () {
        $("#yes_businessphone").remove();
        $("#businessPhone").removeAttr("placeholder");
        $("#businessPhone").css("border", "");
        $("#J_tipBusinessPhone").empty();
        $("#J_tipBusinessPhone").removeClass("warn");
        $("#J_tipBusinessPhone").addClass("sure");
        $("#J_tipBusinessPhone").html("请输入公司固定座机电话");
    })
    /*法人姓名验证*/
    $("#legalName").focus(function () {
        $("#yes_legalname").remove();
        $("#legalName").removeAttr("placeholder");
        $("#legalName").css("border", "");
        $("#J_tiplegalName").empty();
        $("#J_tiplegalName").removeClass("warn");
        $("#J_tiplegalName").addClass("sure");
        $("#J_tiplegalName").html("请输入企业合法人的中文姓名");
    })
    $("#legalName").blur(checkName)
    /*验证法人手机号是否存在*/
    $("#legalPhone").blur(checkMemberPhone);
    $("#legalPhone").focus(function () {
        $("#legalPhone").css("border","");
        $("#J_tiplegalPhone").empty();
        $("#J_tiplegalPhone").removeClass("warn")
        $("#J_tiplegalPhone").addClass("sure");
        $("#J_tiplegalPhone").html("请输入手机号码");
        $("#phone_yes").remove();
    })
    /*前端提示用户进行验证码信息输入*/
    $("#Checkcode").blur(function () {
        $("#J_tipCheckCode").empty();
        $("#Checkcode").attr("placeholder",'请输入验证码');
    })
    $("#Checkcode").focus(function () {
        $("#Checkcode").removeAttr("placeholder");
        $("#Checkcode").css("border","");
        $("#J_tipCheckCode").empty();
        $("#J_tipCheckCode").removeClass("warn");
        $("#J_tipCheckCode").addClass("sure");
        $("#J_tipCheckCode").html("请填写图片中的字符，不区分大小写");
    });
    /*点击立即注册异步提交表单*/
    $("#btn_register").click(function () {
        var userFlag= checkUserName();
        var passwordFlag = checkPassword ()
        var confirmPwdFlag = confirmPassword()
        var emailFlag = email()
        var checkTelFlag = checkTel()
        var legalPhoneFlag = checkMemberPhone ()
        var legalNameFlag = checkName()
        var formFlag = userFlag&&passwordFlag&&confirmPwdFlag&&emailFlag&&checkTelFlag&&legalPhoneFlag&&legalNameFlag;
        if (formFlag) {
            //通过ajax 提交表单数据到服务器
            // 原因：项目的基本架构是前后端分离，前端页面都是html页面 ，html页面中不能通过el表达式获取request中的数据
            // 所有服务器是不能将数据保持到request中传递到页面里面来的，但是ajax请求能够接受服务器响应回来的数据
            $.post("user/business_regist.do", $("#registerForm").serialize(), function (data) {
                /*
                    1. 如果服务器注册成功 跳转页面
                    2. 否则展示错误信息
                */
                console.log(456789)
                if (data.flag) {
                    console.log(12345)
                    alert("恭喜您，注册成功！！");
                    location.href = "login.html"
                } else {
                    if(data.identify.indexOf("CheckCodeError")!=-1){
                        $("#Checkcode").css("border","1px solid red");
                        $("#J_tipCheckCode").empty();
                        $("#J_tipCheckCode").removeClass("sure");
                        $("#J_tipCheckCode").addClass("warn");
                        $("#J_tipCheckCode").html(data.errorMsg);
                    }else if(data.identify.indexOf("RegistError")!=-1){
                        $("#userName").css("border","1px solid red");
                        $("#J_tipUserName").empty();
                        $("#J_tipUserName").removeClass("sure");
                        $("#J_tipUserName").addClass("warn");
                        $("#J_tipUserName").html(data.errorMsg);
                    }
                }
            }, "json");
        }
        return false;
    });
});