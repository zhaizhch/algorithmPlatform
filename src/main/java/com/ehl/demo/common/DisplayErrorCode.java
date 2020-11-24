package com.ehl.demo.common;

public enum DisplayErrorCode {
    imageNameCheck(221,"镜像名称格式：首尾字符为数字或小写字母，中间字符为数字或小写字母或特殊字符/.-_"),
    tagCheck(222,"tag格式：首尾字符为数字或小写字母，中间字符为数字或小写字母或特殊字符.-_"),
    formatCheck(202,"输入格式有误"),
    portsCheck(208,"端口格式：端口类型：端口号，端口类型：端口号,端口类型为a-z小写字母。例：ssh：22，jupyter：8888"),
    imageMountCheck(215,"请输入合法的路径名称。例/data-ym/vol_09"),
    configCheck(209,"请输入正确的配置文件信息，可包含：“A-Za-z0-9.-”例/data/gelin-k8s.config"),
    subPathCheck(210,"请输入合法的子目录映射。例/data-ym/vol.config:/data/vol.config"),
    hostPathCheck(223,"请输入合法的主机目录映射。例/data-ym/vol:/data/vol"),
    workingDirCheck(214,"请输入合法的路径名称。例/data-ym/vol_09"),
    envCheck(217,"请输入合法环境变量，例如 NAME:ZHANGSAN9527"),
    eventTypeCheck(211,"请输入合法的算法类型:0或1或2"),
    dependencyServiceIdCheck(216,"自研算法算法依赖不能为空"),
    uniqCheck(203,"已存在此镜像"),
    dependencyAlgoCheck(308,"请检查依赖算法是否正确：镜像名:标签名,镜像名:标签名"),
    imageQuerySuccess(0,"镜像查询成功"),
    updataImageNameTagCheck(219,"不支持算法名称和标签更改"),
    imageIdErrCheck(204,"镜像不存在"),
    uniqueCheck(203,"已存在此镜像"),
    imageDependencyErr(220,"无法删除被依赖镜像"),
    authorityErr(240,"权限异常");


    private final Integer val;
    private String message;
    DisplayErrorCode(Integer val) {
        this.val = val;
    }

    DisplayErrorCode(Integer val, String message) {
        this.val = val;
        this.message = message;
    }

    public Integer getVal() {
        return val;
    }

    public String getMessage() {
        return message;
    }

}
