/** 表单验证  **/
function validateForm(){
    if($("#username").val()==""){
        art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'用户名', ok:true,});
        return false;
    }
    if($("#password").val()==""){
        art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'密码', ok:true,});
        return false;
    }
    if($("#realName").val()==""){
        art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'真实姓名', ok:true,});
        return false;
    }
    if($("#mobile").val()==""){
        art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'电话号码', ok:true,});
        return false;
    }
    if($("#eamil").val()==""){
        art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'邮件地址', ok:true,});
        return false;
    }
    if($("#role").val()==""){
        art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'用户角色', ok:true,});
        return false;
    }
    return true;
}