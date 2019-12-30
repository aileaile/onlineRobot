package ll.utils;

import ll.config.AppConfig;
import ll.pojo.PosPojo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class FileUtil {

    public static final String filePath  = "./settings.cfg";

    public static void initConfig(){
        Map<String,Object> cfgMap = AppConfig.map;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String lineStr = null;
            while ((lineStr = br.readLine()) != null){


                //#开头的是注释，跳过
                if(lineStr.startsWith("#")){
                    continue;
                }

                //尝试解析配置文件
                try{
                    String[] kv = lineStr.split("=");
                    switch (kv[0]){
                        //登录界面的Logo（黑底）起始坐标
                        case "wowLogoPosStart":
                            cfgMap.put("wowLogoPosStart",new PosPojo(kv[1].split(",")));
                            break;
                        //登录界面的Logo（黑底）结束坐标
                        case "wowLogoPosEnd" :
                            cfgMap.put("wowLogoPosEnd",new PosPojo(kv[1].split(",")));
                            break;
                        //人物选择界面左下角[插件][菜单]按钮起始坐标
                        case "characterButtonStart":
                            cfgMap.put("characterButtonStart",new PosPojo(kv[1].split(",")));
                            break;
                        //人物选择界面左下角[插件][菜单]按钮结束坐标
                        case "characterButtonEnd" :
                            cfgMap.put("characterButtonEnd",new PosPojo(kv[1].split(",")));
                            break;
                        //排队界面[选择服务器]按钮起始坐标
                        //注意了，在开启免费转服时这个坐标会变
                        case "queueSelectServerButtonStart":
                            cfgMap.put("queueSelectServerButtonStart",new PosPojo(kv[1].split(",")));
                            break;
                        //排队界面[选择服务器]按钮结束坐标
                        //注意了，在开启免费转服时这个坐标会变
                        case "queueSelectServerButtonEnd" :
                            cfgMap.put("queueSelectServerButtonEnd",new PosPojo(kv[1].split(",")));
                            break;
                        //断线界面[创建账号]按钮开始坐标
                        //如果你实在没有一个断线界面的截图，试着用游戏内输入账号密码界面替代
                        case "errorSignUpButtonStart" :
                            cfgMap.put("errorSignUpButtonStart",new PosPojo(kv[1].split(",")));
                            break;
                        //断线界面[创建账号]按钮结束坐标
                        //如果你实在没有一个断线界面的截图，试着用游戏内输入账号密码界面替代
                        case "errorSignUpButtonEnd" :
                            cfgMap.put("errorSignUpButtonEnd",new PosPojo(kv[1].split(",")));
                            break;
                        //服务器选择界面“服务器选择”字样开始坐标
                        case "serverStrStart" :
                            cfgMap.put("serverStrStart",new PosPojo(kv[1].split(",")));
                            break;
                        //服务器选择界面“服务器选择”字样结束坐标
                        case "serverStrEnd" :
                            cfgMap.put("serverStrEnd",new PosPojo(kv[1].split(",")));
                            break;
                        //程序开启时的等待时间
                        case "firstWaitTime" :
                            cfgMap.put("firstWaitTime",Integer.valueOf(kv[1]));
                            break;
                        //单击进入游戏按钮后的等待时间
                        case "waitAfterClickButton" :
                            cfgMap.put("waitAfterClickButton",Integer.valueOf(kv[1]));
                            break;
                        //打开战网的等待时间
                        case "waitAfterOpenBattleNet" :
                            cfgMap.put("waitAfterOpenBattleNet",Integer.valueOf(kv[1]));
                            break;
                        //打开战网的等待时间
                        case "waitAfterExitGame" :
                            cfgMap.put("waitAfterExitGame",Integer.valueOf(kv[1]));
                            break;
                        //图像识别判定百分比
                        case "imageRecognitionPercentage" :
                            cfgMap.put("imageRecognitionPercentage",Double.valueOf(kv[1]));
                            break;
                        //战网在桌面的位置
                        case "battleNetOnYourDeskPos" :
                            cfgMap.put("battleNetOnYourDeskPos",new PosPojo(kv[1].split(",")));
                            break;
                        //战网“进入游戏”的位置
                        case "enterGamePos" :
                            cfgMap.put("enterGamePos",new PosPojo(kv[1].split(",")));
                            break;
                        //rgb容忍值
                        case "tolerance" :
                            cfgMap.put("tolerance",Integer.valueOf(kv[1]));
                            break;
                        //超级安全模式（0-关闭 1-开启）
                        case "superSafeMode" :
                            cfgMap.put("superSafeMode",Integer.valueOf(kv[1]));
                            break;
                        //进入游戏模式（1-鼠标单击 2-回车键进入）
                        case "enterGameMode" :
                            cfgMap.put("enterGameMode",Integer.valueOf(kv[1]));
                            break;
                        //进入战网模式（1-鼠标移动并双击 2-cmd命令进入）
                        case "openBattleNetMode" :
                            cfgMap.put("openBattleNetMode",Integer.valueOf(kv[1]));
                            break;
                        //战网快捷方式地址(不支持中文！有中文要改成英文)
                        case "BattleNetPath" :
                            cfgMap.put("BattleNetPath",kv[1]);
                            break;
                        default :
                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            //没有文档，应该创建
            System.out.println("没有文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }
}
