#!/bin/sh
ps -ef|grep ganymed-ssh2 |grep -v grep
 ps -ef|grep ganymed-ssh2 |grep -v grep |awk '{print $2}'|xargs -i kill -9 {}
