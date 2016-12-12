package zdy;

public class ssh2_monitor {
public String name;
public String ip;
public String user_name;
public String passwd;

public String cmd;
public String cmd_param1;
public String cmd_param2;
public String cmd_param3;
public String sql;
public String sql_param1;
public String sql_param2;
public String sql_param3;
public String receiver;

public long cmd_threshold;
public int sql_type;
public int cmd_type;

public int id;
public int other_action;
public String cmd_action;


public void set_name(String name)
{
this.name = name;
}
public String get_name()
{
return this.name;
}

public void set_ip(String ip)
{
this.ip = ip;
}
public String get_ip()
{
return this.ip;
}


public void set_user_name(String user_name)
{
this.user_name = user_name;
}
public String get_user_name()
{
return this.user_name;
}

public void set_passwd(String passwd)
{
this.passwd = passwd;
}
public String get_passwd()
{
return this.passwd;
}

public void set_cmd(String cmd)
{
this.cmd = cmd;
}
public String get_cmd()
{
return this.cmd;
}

public void set_cmd_param1(String cmd_param1)
{
this.cmd_param1 = cmd_param1;
}
public String get_cmd_param1()
{
return this.cmd_param1;
}

public void set_cmd_param2(String cmd_param2)
{
this.cmd_param2 = cmd_param2;
}
public String get_cmd_param2()
{
return this.cmd_param2;
}

public void set_cmd_param3(String cmd_param3)
{
this.cmd_param3 = cmd_param3;
}
public String get_cmd_param3()
{
return this.cmd_param3;
}


public void set_sql(String sql)
{
this.sql = sql;
}
public String get_sql()
{
return this.sql;
}

public void set_sql_param1(String sql_param1)
{
this.sql_param1 = sql_param1;
}
public String get_sql_param1()
{
return this.sql_param1;
}

public void set_sql_param2(String sql_param2)
{
this.sql_param2 = sql_param2;
}
public String get_sql_param2()
{
return this.sql_param2;
}


public void set_sql_param3(String sql_param3)
{
this.sql_param3 = sql_param3;
}
public String get_sql_param3()
{
return this.sql_param3;
}


public void set_receiver(String receiver)
{
this.receiver = receiver;
}
public String get_receiver()
{
return this.receiver;
}

public void set_cmd_threshold(long cmd_threshold)
{
this.cmd_threshold = cmd_threshold;
}
public long get_cmd_threshold()
{
return this.cmd_threshold;
}

public void set_sql_type(int sql_type)
{
this.sql_type = sql_type;
}
public int get_sql_type()
{
return this.sql_type;
}

public void set_cmd_type(int cmd_type)
{
this.cmd_type = cmd_type;
}
public int get_cmd_type()
{
return this.cmd_type;
}

public void set_id(int id)
{
this.id = id;
}
public int get_id()
{
return this.id;
}

public void set_other_action(int other_action)
{
this.other_action = other_action;
}
public int get_other_action()
{
return this.other_action;
}


public void set_cmd_action(String cmd_action)
{
this.cmd_action = cmd_action;
}
public String get_cmd_action()
{
return this.cmd_action;
}

public void print()
{
	System.out.println(name + ": " + name);
  System.out.println(ip + ": " + ip);
	System.out.println(user_name + ": " + user_name);
	System.out.println(passwd + ": " + passwd);
	
	System.out.println(cmd + ": " + cmd);
  System.out.println(cmd_param1 + ": " + cmd_param1);
	System.out.println(cmd_param2 + ": " + cmd_param2);
	System.out.println(cmd_param3 + ": " + cmd_param3);
	System.out.println(sql + ": " + sql);
	
	System.out.println(sql_param1 + ": " + sql_param1);
  System.out.println(sql_param2 + ": " + sql_param2);
	System.out.println(sql_param3 + ": " + sql_param3);
	System.out.println(receiver + ": " + receiver);
	System.out.println(cmd_threshold + ": " + cmd_threshold);
	
	System.out.println(sql_type + ": " + sql_type);
	System.out.println(cmd_type + ": " + cmd_type);


}

}