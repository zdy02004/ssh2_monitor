import zdy.*;

import java.text.SimpleDateFormat;

import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Thread;
import java.io.*;


public class test  extends Thread{
	  public connect con;
	  public Vector<ssh2_monitor> config;
	  public int i;
	  public String Lock_talbe;
	  public static Map<String,test> map=new HashMap<String,test>();
	  public static Map<String,Integer> map_status=new HashMap<String,Integer>();
	  public int is_kill ;//知否执行kill
	  public void test()
	  {
	  	is_kill =0;
	  }
	  public void set_Lock_talbe(String Lock_talbe)
	  {
	  	this.Lock_talbe=Lock_talbe;
	  }
	  public String get_Lock_talbe()
	  {
	  	return this.Lock_talbe;
	  }
	  
	  public void set_index(int i)
	  {
	  	this.i=i;
	  }
	  public int get_index()
	  {
	  	return this.i;
	  }
	    public void set_con(connect con)
	  {
	  	this.con=con;
	  }
	    public void set_config(Vector<ssh2_monitor> config)
	  {
	  	this.config=config;
	  }
	  //打印代码行
	  public static String getLineInfo()
    {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return ste.getFileName() + ": Line " + ste.getLineNumber();
    }
    //打印系统时间
    public static String get_time(){
	  SimpleDateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss"); //设置日期格式
    return   df.format(new Date()) + ": "; // new Date()为获取当前系统时间
	  
	}


    public static String replaceBlank(String str) {
        String dest = "";

        if (str != null) {
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(str);
            m.find();
            dest = m.group();
        }
       

        return dest;
    }

    //执行当前动作 入参为 配置项 对象
    public static void curren_action(ssh2_monitor tmp_monitor,
        String str_threshold) {
        if (tmp_monitor.get_cmd_action() != null) {
            RmtShellExecutor exe0 = new RmtShellExecutor(tmp_monitor.get_ip(),
                    tmp_monitor.get_user_name(), tmp_monitor.get_passwd());

            //拼入检测到数值
            String alert_str2 = tmp_monitor.get_cmd_action();
            alert_str2 = alert_str2.replace("#", str_threshold);
            alert_str2 = alert_str2.replace("[]", tmp_monitor.get_name());
            System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 执行 CMD_ACTION命令: " + alert_str2);
            //System.out.print();

            try {
                System.out.print(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 执行 CMD_ACTION命令结果： "+exe0.exec(alert_str2) + "\n");
            } catch (java.lang.Exception e) {e.printStackTrace();
            } finally {
            }
        }
    }

    //执行关联动作    
    //start 为id 搜索起始点
    //tmp_monitor为 当前配置项 对象
    //config 配置vector
    //str_threshold 当前配置项阈值
    public static void Relate_action(int start, ssh2_monitor tmp_monitor,
        Vector<ssh2_monitor> config, connect con, String str_threshold,String str_lock) {
        if ((tmp_monitor.get_other_action() > 0) &&
                (tmp_monitor.get_other_action() != tmp_monitor.get_id())) {
            System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 寻找关联动作" + tmp_monitor.get_other_action());

            for (int j = start; j < config.size(); j++) {
                // 获得配置数据
                ssh2_monitor tmp_monitor2 = config.get(j);

                if (tmp_monitor2.get_id() != tmp_monitor.get_other_action()) {
                    continue;
                } else
                {
                	try {
                if(tmp_monitor2.get_cmd_type() == 3 ||tmp_monitor2.get_cmd_type() == 4 ){
                System.out.print(getLineInfo()+" " +get_time()+" "+tmp_monitor2.get_name()+"锁记录: " );
                //锁当前记录
                String lock_str="update "+str_lock +" set cmd_type = 10 where id = "+tmp_monitor2.get_id();
                System.out.print(lock_str+"\n");
                //锁记录sql
                con.do_update(lock_str);
                
                 //装入线程map 
                 System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor2.get_name() + " 装入 HashMap<String,test> \n");  	
                 
                map_status.put(tmp_monitor2.get_name(),tmp_monitor2.get_cmd_type());   
                 
                }
                 if (tmp_monitor2.get_cmd_type() == 3) {
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 找到关联动作" + tmp_monitor2.get_id());
                    System.out.println("\n"+tmp_monitor.get_name()+" "+getLineInfo()+" " +get_time()+" "+"===============" + "  开始执行  " +
                        tmp_monitor2.get_name() + "===============\n");

                    //登陆 ssh2
                    RmtShellExecutor exe2 = new RmtShellExecutor(tmp_monitor2.get_ip(),
                            tmp_monitor2.get_user_name(),
                            tmp_monitor2.get_passwd());

                    //拼接执行命令
                    String cmd_all2 = tmp_monitor2.get_cmd();

                    if (tmp_monitor2.get_cmd_param1() != null) {
                        cmd_all2 += (" " + tmp_monitor2.get_cmd_param1());
                    }

                    if (tmp_monitor2.get_cmd_param2() != null) {
                        cmd_all2 += (" " + tmp_monitor2.get_cmd_param2());
                    }

                    if (tmp_monitor2.get_cmd_param3() != null) {
                        cmd_all2 += (" " + tmp_monitor2.get_cmd_param3());
                    }

                    cmd_all2 = cmd_all2.replace("#", str_threshold);
                    cmd_all2 = cmd_all2.replace("[]", tmp_monitor2.get_name());

                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() +"执行 OTHER_ACTION关联命令： " + cmd_all2);

                    //执行命令
                    try {
                        String cmd_return2 = exe2.exec(cmd_all2);
                        System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() +" 执行关联命令返回值： " + cmd_return2 + "\n");
                        //执行当前动作
                        curren_action(tmp_monitor2, str_threshold);
                        //继续执行链接的动作
                        Relate_action(j, tmp_monitor2, config, con,
                            str_threshold, str_lock);
                    } catch (java.lang.Exception e) {e.printStackTrace();
                    } finally {
                    }
                }
                 if (tmp_monitor2.get_cmd_type() == 4) {
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() +" 找到关联动作" + tmp_monitor2.get_id());
                    System.out.println("\n"+tmp_monitor.get_name() +getLineInfo()+" " +get_time()+" "+"===============" + "  开始执行  " +
                        tmp_monitor2.get_name() + "===============\n");

                    //拼接执行命令
                    String cmd_all4 = tmp_monitor2.get_cmd();
                    cmd_all4 = cmd_all4.replace("#", str_threshold);
                    cmd_all4 = cmd_all4.replace("[]", tmp_monitor2.get_name());
                    
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() +" 执行 OTHER_ACTION的SQL： " + cmd_all4);
                    //执行sql
                    con.do_update(cmd_all4);
                    //执行当前动作
                    curren_action(tmp_monitor2, str_threshold);
                    //继续执行链接的动作
                    Relate_action(j, tmp_monitor2, config, con, str_threshold, str_lock);
                }
                
              }
              
              catch(java.lang.Exception e){e.printStackTrace();}
                finally{
                                
                   //装出线程map           
                 if(map_status.containsKey(tmp_monitor2.get_name()))
                 { 
                 	System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor2.get_name() + " 装出 HashMap<String,test> \n");  	
                 	tmp_monitor2.set_cmd_type(map_status.get(tmp_monitor2.get_name()));
                   map_status.remove(tmp_monitor2.get_name());
                   String unlock_str2="update "+str_lock +" set cmd_type = "+tmp_monitor2.get_cmd_type()+",receiver = null"+" where id = "+tmp_monitor2.get_id();
                   System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor2.get_name() + "解锁sql为" + ":"+unlock_str2);
                //解锁记录sql
                  con.do_update(unlock_str2);
                
                 
                }
                }
            }
        }
    }
  }

     public void run(){
            // 获得配置数据
            ssh2_monitor tmp_monitor = this.config.get(this.get_index());
           
           try{
           	if (tmp_monitor.get_cmd_type() == 10 &&tmp_monitor.get_receiver() !=null && tmp_monitor.get_receiver().equals("kill") ){
           		System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor.get_name() + " 需要终止 \n");  	
                 if(map.containsKey(tmp_monitor.get_name()) || map_status.containsKey(tmp_monitor.get_name()))
                 {
                 try{
                 //杀掉远端进程
                RmtShellExecutor exe = new RmtShellExecutor(tmp_monitor.get_ip(),
                tmp_monitor.get_user_name(), tmp_monitor.get_passwd());
                        
                tmp_monitor.set_cmd_type(map_status.get(tmp_monitor.get_name())*(-1));
                map_status.remove(tmp_monitor.get_name());
                map_status.put(tmp_monitor.get_name(),tmp_monitor.get_cmd_type());
                String unlock_str="update "+get_Lock_talbe() +" set cmd_type = "+tmp_monitor.get_cmd_type()+",receiver = null"+" where id = "+tmp_monitor.get_id();
                System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "解锁sql为" + ":"+unlock_str);
                //解锁记录sql
                  this.con.do_update(unlock_str);
                
                if(tmp_monitor.get_cmd_action()!=null){
                String cmd_kill ="ps -ef|grep "+tmp_monitor.get_cmd_action()+"  |grep -v grep|awk '{print $2}'|xargs -i kill -9 {}";
                System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor.get_name() + " 终止命令："+cmd_kill+"\n");
                String cmd_return = exe.exec(cmd_kill);
                  
                System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor.get_name() + " 终止命令返回结果："+cmd_return+"\n");
               }
               if(tmp_monitor.get_cmd()!=null && map_status.get(tmp_monitor.get_name())==-3 ){
                String cmd_kill ="ps -ef|grep "+tmp_monitor.get_cmd()+"  |grep -v grep|awk '{print $2}'|xargs -i kill -9 {}";
                System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor.get_name() + " 终止命令："+cmd_kill+"\n");
                String cmd_return = exe.exec(cmd_kill);  
                
                System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor.get_name() + " 终止命令返回结果："+cmd_return+"\n");
               }
               

                

                  
                 }catch (java.lang.Exception e) {e.printStackTrace();
                    } finally {
                    }
                    
                    
                try{                
                if(map.containsKey(tmp_monitor.get_name())){  
                //map.get(tmp_monitor.get_name()).stop();	        	
                map.get(tmp_monitor.get_name()).is_kill=1;
                }
                
                }catch (java.lang.Exception e) {e.printStackTrace();
                    } finally {
                    }
           	}
          }
           	
               ///////////////////////////////////////////////////
            if ((tmp_monitor.get_cmd_type() == 1) ||
                    (tmp_monitor.get_cmd_type() == 2)) {
                    	
                 //装入线程map 
                 System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor.get_name() + " 装入 HashMap<String,test> \n");  	
                 map.put(tmp_monitor.get_name(),this);
                 map_status.put(tmp_monitor.get_name(),tmp_monitor.get_cmd_type());   
                 	
                System.out.println("\n"+getLineInfo()+" " +get_time()+" "+"===============" + "  开始检查  " +
                    tmp_monitor.get_name() + "===============\n");

                SimpleDateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss"); //设置日期格式
                System.out.print(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+"锁记录: "); // new Date()为获取当前系统时间
                //锁当前记录
                String lock_str="update "+get_Lock_talbe() +" set cmd_type = 10 where id = "+tmp_monitor.get_id();
                System.out.print(lock_str+"\n");
                //锁记录sql
                        this.con.do_update(lock_str);
            }

            // 获得设置阈值	
            long cmd_threshold = tmp_monitor.get_cmd_threshold();

            //阈值的字符串形式
            String str_threshold = Long.toString(cmd_threshold);

            //执行监控条件命令
            if (tmp_monitor.get_cmd_type() == 1) {
                //登陆 ssh2
                RmtShellExecutor exe = new RmtShellExecutor(tmp_monitor.get_ip(),
                        tmp_monitor.get_user_name(), tmp_monitor.get_passwd());

                //拼接执行命令
                String cmd_all = tmp_monitor.get_cmd();

                if (tmp_monitor.get_cmd_param1() != null) {
                    cmd_all += (" " + tmp_monitor.get_cmd_param1());
                }

                if (tmp_monitor.get_cmd_param2() != null) {
                    cmd_all += (" " + tmp_monitor.get_cmd_param2());
                }

                if (tmp_monitor.get_cmd_param3() != null) {
                    cmd_all += (" " + tmp_monitor.get_cmd_param3());
                }

                
                String cmd_return="";
                //执行命令
                try{
                System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 执行检查命令： " + cmd_all + "\n");
                cmd_return = exe.exec(cmd_all);
                System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 执行返回值:{" + cmd_return + "}\n");
                ///////////////////////////////////////////////////
                  
                        
                 }
              catch (java.lang.Exception e) {e.printStackTrace();
                    } finally {
                    	                //筛选出数值
                    cmd_return = this.replaceBlank(cmd_return);
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 命令返回值的长度： " + cmd_return.length() + "\n");
                    // //如果查询的进程不存在则，返回值为0 
                    // if(tmp_monitor.get_cmd().indexOf("ps -ef")!=-1){
                    // 	cmd_return = "0";
                    //}
                     
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 命令返回值： " + cmd_return + "\n");
                   
                    if(cmd_return.length() == 0){
                                   cmd_return = "0";
                                   }
                if (cmd_threshold > 0) {
                    long return_value = Long.parseLong(cmd_return);
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "检测值为" + ":" +
                        cmd_return + "\n");
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "的阈值为" + ":" +
                        cmd_threshold + "\n");

                    //超出阈值告警
                    if (return_value >= cmd_threshold) {
                        System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "超出阈值:告警！" +
                            "\n");

                        String alert_str = tmp_monitor.get_sql();
                        if(alert_str !=null){
                        //拼入检测到数值
                        alert_str = alert_str.replace("#", str_threshold);
                        alert_str = alert_str.replace("[]",tmp_monitor.get_name());
                        System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 告警SQL如下：\n" + alert_str);
                        //执行sql告警动作
                        this.con.do_update(alert_str);
                      }
                        //执行当前动作
                        curren_action(tmp_monitor, str_threshold);
                        //执行关联动作
                        Relate_action(0, tmp_monitor, this.config, this.con, str_threshold,get_Lock_talbe());
                    }
                }
                if (cmd_threshold < 0) {
                	  cmd_threshold=cmd_threshold*(-1);
                    long return_value = Long.parseLong(cmd_return);
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "检测值为" + ":" +
                        cmd_return + "\n");
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "的阈值为" + ":" +
                        cmd_threshold + "\n");

                    //超出阈值告警
                    if (return_value < cmd_threshold) {
                        System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "超出阈值:告警！" +
                            "\n");

                        String alert_str = tmp_monitor.get_sql();
                        if(alert_str !=null){
                        //拼入检测到数值
                        alert_str = alert_str.replace("#", str_threshold);
                        alert_str = alert_str.replace("[]", tmp_monitor.get_name());
                        System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 告警SQL如下：\n" + alert_str);
                        //执行sql告警动作
                        this.con.do_update(alert_str);
                      }
                        //执行当前动作
                        curren_action(tmp_monitor, str_threshold);
                        //执行关联动作
                        Relate_action(0, tmp_monitor, this.config, this.con, str_threshold,get_Lock_talbe());
                    }
                }
                    }
            }

            //监控db队列表
            if (tmp_monitor.get_cmd_type() == 2) {
            	 System.out.println(getLineInfo()+" " +get_time()+" do_count("+tmp_monitor.get_cmd()+","+tmp_monitor.get_cmd_param1()+")");
                long cmd_return = this.con.do_count(tmp_monitor.get_cmd(),
                        tmp_monitor.get_cmd_param1());

                if (cmd_threshold > 0) {
                    String str_cmd_return = Long.toString(cmd_return);
                    String str_cmd_threshold = Long.toString(cmd_threshold);

                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "检测值为" + ":" +
                        str_cmd_return + "\n");
                    System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "的阈值为" + ":" +
                        str_cmd_threshold + "\n");

                    if (cmd_return >= cmd_threshold) {
                        System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "超出阈值:告警！");

                        String alert_str = tmp_monitor.get_sql();
                        alert_str = alert_str.replace("#", str_cmd_return);
                        alert_str = alert_str.replace("[]", tmp_monitor.get_name());
                        System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name()+" 告警SQL:" + "\n" + alert_str);
                        //告警sql
                        this.con.do_update(alert_str);
                        //执行当前动作
                        curren_action(tmp_monitor, str_threshold);

                        //执行关联动作
                        Relate_action(0, tmp_monitor, this.config, con, str_threshold,get_Lock_talbe());
                    }
                }
            }
          }catch (java.lang.Exception e){ e.printStackTrace();}
          	finally{
          //解锁当前记录
          if (true) {
          	     if(map.containsKey(tmp_monitor.get_name()))
                 { 
                 //装出线程map 
                 System.out.println("\n"+getLineInfo()+" " +get_time()+
                    tmp_monitor.get_name() + " 装出 HashMap<String,test> \n");  	
                 map.remove(tmp_monitor.get_name());
               }
                 if(map_status.containsKey(tmp_monitor.get_name()))
                 { 
                 	tmp_monitor.set_cmd_type(map_status.get(tmp_monitor.get_name()));
                   map_status.remove(tmp_monitor.get_name());
                   String unlock_str="update "+get_Lock_talbe() +" set cmd_type = "+tmp_monitor.get_cmd_type()+",receiver = null"+" where id = "+tmp_monitor.get_id();
                   System.out.println(getLineInfo()+" " +get_time()+" "+tmp_monitor.get_name() + "解锁sql为" + ":"+unlock_str);
                //解锁记录sql
                  this.con.do_update(unlock_str);
                }
               
          }
        }
        }
          
    public static void main(String[] args) throws Exception {
    	  //读配置文件，获得数据库连接信息
    	  InputStream inputStream = test.class.getResourceAsStream("db_config.properties");   
        Properties p = new Properties();   
        try {   
         p.load(inputStream);   
        } catch (IOException e1) {  e1.printStackTrace(); 
         e1.printStackTrace();   
        }   
    	  
    	  String ip=p.getProperty("ip");
    	  String port=p.getProperty("port");
    	  String service_name = p.getProperty("service_name");
    	  String config_table= p.getProperty("config_table");
    	  String during= p.getProperty("during");
    	  long lduring = Long.parseLong(during);
    	  test boss = new test();
    	  //初始话数据库连接
        boss.con = new connect();
        
        while(true){
        //连数据库并获得全部监控配置信息
        boss.config = boss.con.do_work(ip, port,
                service_name, "select * FROM "+config_table+" order by id");
        //并发执行所有监控
        for (int i = 0; i < boss.config.size(); i++) {
        test t = new test();
        t.set_index(i);
        t.set_Lock_talbe(config_table);
        t.set_config(boss.config);
        t.set_con(boss.con);
        t.start();
        //t.run();
    } 
    //睡眠
      boss.sleep(lduring);
  }
}
}
