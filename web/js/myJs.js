/**
 * Created by 15172 on 12/24 0024.
 */
function formsubmit(obj) {
    obj.parentNode.parentNode.submit();
}
function autoFillRemain() {
//            alert("开始");
    $.ajax({
        type:"post",
        url:"schedule?page=fillAll",
        dataType:"json",
        success:function (data) {
            if(data.state == "12")  {
                layer.msg('操作成功！',{icon:1,time:1000},function () {
                    location.reload();
                });
            }
            else  {
                layer.msg('出现时间冲突',{icon:0,time:1000},function () {
                    location.reload();
                });
            }
        }
    });
}
function setting() {
    layer.prompt({title: '设置一天的课时', formType: 2}, function(text, index){
        layer.close(index);
        $.ajax({
            type:"post",
            url:"setting?num=" + encodeURI(text),dataType:"json",
            success:function (data) {
                layer.msg(data.state);
            }
        });
    });
}