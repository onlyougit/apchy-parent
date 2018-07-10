//表单校验
function isEmail(s) {
    if (s.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
        return true;
    else
        return false;
}
function onUserNameValidation(e) {
    if (e.isValid) {
        if (isEmail(e.value) == false) {
            e.errorText = "必须输入邮件地址";
            e.isValid = false;
        }
    }
}