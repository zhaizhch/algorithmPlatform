package com.ehl.demo.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class FormatCheck {
    public Boolean imageNameCheck(String imageName){
        String compile="(^[0-9a-z]+)([0-9a-z./_-]*)([0-9a-z]+$)";
        if(imageName.matches(compile)){
            return true;
        }
        return false;
    }
    public Boolean tagCheck(String imageName){
        String compile="(^[0-9a-z]+)([0-9a-z./_-]*)([0-9a-z]+$)";
        if(imageName.matches(compile)){
            return true;
        }
        return false;
    }
    public Boolean algoDescCheck(String algoDesc){
        String compile="\\s+";
        if(algoDesc.matches(compile)){
            return false;
        }
        return true;
    }
    public Boolean imagePortsCheck(String imagePorts){
        if(imagePorts==null||imagePorts==""){
            return true;
        }
        ArrayList<String[]> splitResult=new ArrayList<>();
        String[] firstSplit=imagePorts.split("\\,");
        for(String each:firstSplit){
            if(each.split("\\:").length==3){
                continue;
            }
            splitResult.add(each.split("\\:"));
        }
        for(int i=0;i<splitResult.size();i++){
            String compileString="[a-z]+";
            String compileDigite="[0-9]+";
            if(splitResult.get(i).length!=2){
                return false;
            }
            if(!splitResult.get(i)[0].matches(compileString)){
                return false;
            }
            if(!splitResult.get(0)[1].matches(compileDigite)){
                return false;
            }
        }
        return true;
    }
    public Boolean imageMountCheck(String imageMount){
        if(imageMount==null||imageMount==""){
            return true;
        }
        String[] splitResult=imageMount.split("\\,");
        String compile="^/([A-Za-z0-9_-]+/?)+$";
        for(String each:splitResult){
            if(!each.matches(compile)){
                return false;
            }
        }
        return true;
    }
    public Boolean imageConfigCheck(String imageConfig){
        if(imageConfig==null||imageConfig==""){
            return true;
        }
        String[] splitResult=imageConfig.split("\\,");
        String compile="^/([A-Za-z0-9_-]+/?)+$";
        for(String each:splitResult){
            if(!each.matches(compile)){
                return false;
            }
        }
        return true;
    }
    public Boolean subPathMappingCheck(String subPath){
        if(subPath==""||subPath==null){
            return true;
        }
        String[] splitResult=subPath.split("\\,");
        String compile="(/[A-Za-z0-9_.-]+)*(.[a-z0-9])*";
        for(String each:splitResult){
            String[] files=each.split("\\:");
            if(files.length!=2){
                return false;
            }
            else{
                for(String file:files){
                    if(!file.matches(compile)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public Boolean hostPathMappingCheck(String hostPath){
        if(hostPath==""||hostPath==null){
            return true;
        }
        String[] splitResult=hostPath.split("\\,");
        String compile="(/[A-Za-z0-9_.-]+)*";
        for(String each:splitResult){
            String[] files=each.split("\\:");
            if(files.length!=2){
                return false;
            }
            else{
                for(String file:files){
                    if(!file.matches(compile)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public Boolean workingDirCheck(String workingDir){
        if(workingDir==null||workingDir==""){
            return true;
        }
        String compile="/[A-Za-z0-9_.-]*[/A-Za-z0-9_.-]*";
        if(!workingDir.matches(compile)){
            return false;
        }
        return true;
    }
    public Boolean algoTypeCheck(String algoType){
        if(algoType==null||algoType==""){
            return true;
        }
        String[] splitResult=algoType.split("\\,");
        for(String each:splitResult){
            if(!(each.equals("0")||each.equals("1")||each.equals("2"))){
                return false;
            }
        }
        return true;
    }
    public Boolean envCheck(String env){
        if(env==null||env==""||env.matches("\\s")) {
            return true;
        }
        ArrayList<String> envEach=new ArrayList<>();
        for(String each:env.split("\\,")){
            if(each.split("\\:").length==1){
                return false;
            }
            String[] eachSplit=each.split("\\:");
            if(envEach.size()!=2){
                return false;
            }
            if(envEach.get(0).matches("\\s")||envEach.get(1).matches("\\s")){
                return false;
            }

        }
        return true;
    }
    public Boolean imageNameTagCheck(String each){
        String[] splitResult=each.split("\\:");
        if(splitResult.length<2){
            return false;
        }
        return (imageNameCheck(splitResult[0])&&tagCheck(splitResult[1]));
    }
    public Boolean dependencyCheck(String dependencyServiceId){
        if(dependencyServiceId==null||dependencyServiceId==""||dependencyServiceId.matches("\\s")){
            return false;
        }
        String[] eachDep=dependencyServiceId.split("\\,");
        for(String each:eachDep){
            return imageNameTagCheck(each);
        }
        return true;
    }
}
