package zdy;
import java.sql.*;  
import zdy.ssh2_monitor;
import java.util.*;

public class connect {
  public String ip;
  public String port;
  public String sercice_name;
 /**
  * @param args
  */
  public void connect(){}
  
  public Vector<ssh2_monitor> do_work(String ip,String port,String sercice_name,String config_table) {
  // TODO Auto-generated method stub
  this.ip = ip;
  this.port = port;
  this.sercice_name=sercice_name;
  
  Vector<ssh2_monitor> array=new Vector<ssh2_monitor>();
  
  try{
  Class.forName("oracle.jdbc.driver.OracleDriver");
  }catch(ClassNotFoundException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  Connection con = null;
  Statement stmt = null;
  ResultSet rs = null;
  
 try{
  String _url="jdbc:oracle:thin:@";
  String url=_url+ip+":"+port+":"+sercice_name  ;  
  
  String user="ad";
  String password="ad";
  //String str="INSERT INTO ZZZ_2 VALUES('041110018','JHDK')";
  
 con = java.sql.DriverManager.getConnection(url,user,password);
  // 创建状态
  stmt = con.createStatement();
  // 执行SQL语句，返回结果集
  //int rowcount = stmt.executeUpdate(str);
  //int j = stmt.executeUpdate("update ZZZ_2 set NAME='dbt' where id=21");
  //int k = stmt.executeUpdate("delete from ZZZ_2 where id=41110020");
  rs = stmt.executeQuery(config_table);
  // 对结果集进行处理
while (rs.next()) {
	ssh2_monitor tmp= new ssh2_monitor();
	
  tmp.set_name(rs.getString("NAME"));
  tmp.set_ip(rs.getString("ip"));
  tmp.set_user_name(rs.getString("user_name"));
  tmp.set_passwd(rs.getString("passwd"));
  tmp.set_cmd(rs.getString("cmd"));
  tmp.set_cmd_param1(rs.getString("cmd_param1"));
  tmp.set_cmd_param2(rs.getString("cmd_param2"));
  tmp.set_cmd_param3(rs.getString("cmd_param3"));
  tmp.set_sql(rs.getString("sql"));
  tmp.set_sql_param1(rs.getString("sql_param1"));
  tmp.set_sql_param2(rs.getString("sql_param2"));
  tmp.set_sql_param3(rs.getString("sql_param3"));
  tmp.set_receiver(rs.getString("receiver"));
  tmp.set_cmd_threshold(rs.getLong("cmd_threshold"));
  tmp.set_sql_type(rs.getInt("sql_type"));
  tmp.set_cmd_type(rs.getInt("cmd_type"));
  
  tmp.set_cmd_action(rs.getString("cmd_action"));
  tmp.set_other_action(rs.getInt("other_action"));
  tmp.set_id(rs.getInt("id"));
  array.add(tmp);
  //String name = rs.getString("NAME");
  //Integer age = rs.getObject("age") == null ? null : rs.getInt("age");
  //System.out.println(id + ": " + id);
  } 
  //返回数组
 
  
  }catch(SQLException e){
  e.printStackTrace();}

// 释放资源
  finally{
  try{
  rs.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  try{
  stmt.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  try{
  con.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  }
 return array;
} 


  public Vector<ssh2_monitor> do_update(String str) {
  // TODO Auto-generated method stub
  
  Vector<ssh2_monitor> array=new Vector<ssh2_monitor>();
  
  try{
  Class.forName("oracle.jdbc.driver.OracleDriver");
  }catch(ClassNotFoundException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  Connection con = null;
  Statement stmt = null;
  ResultSet rs = null;
  
 try{
  String _url="jdbc:oracle:thin:@";
  String url=_url+this.ip+":"+this.port+":"+this.sercice_name  ;  
  
  String user="ad";
  String password="ad";
  //String str="INSERT INTO ZZZ_2 VALUES('041110018','JHDK')";
  
 con = java.sql.DriverManager.getConnection(url,user,password);
  // 创建状态
  stmt = con.createStatement();
  // 执行SQL语句，返回结果集
  int rowcount = stmt.executeUpdate(str);

 
  
  }catch(SQLException e){
  e.printStackTrace();}

// 释放资源
  finally{
  
  try{
  stmt.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  try{
  con.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  }
 return array;
} 


  public long do_count (String str,String str1) {
  // TODO Auto-generated method stub

  
   long ret=0;  
  try{
  Class.forName("oracle.jdbc.driver.OracleDriver");
  }catch(ClassNotFoundException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  Connection con = null;
  Statement stmt = null;
  ResultSet rs = null;
  
 try{
  String _url="jdbc:oracle:thin:@";
  String url=_url+this.ip+":"+this.port+":"+this.sercice_name  ;  
  
  String user="ad";
  String password="ad";
  //String str="INSERT INTO ZZZ_2 VALUES('041110018','JHDK')";
  
 con = java.sql.DriverManager.getConnection(url,user,password);
  // 创建状态

  stmt = con.createStatement();
  if(stmt.execute(str))
  {
  rs = stmt.getResultSet();
  // 对结果集进行处理
  while (rs.next()) {
	
  ret = rs.getLong(str1);

  } 
}
  
  
  }catch(SQLException e){
  e.printStackTrace();}

// 释放资源
  finally{
  try{
  rs.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  try{
  stmt.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  try{
  con.close();
  }catch(SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  }
 return ret;
} 


  
    }