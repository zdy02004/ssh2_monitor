#!/bin/sh
path=`pwd`
cd $path
LIB=$path/lib

#java -cp :./lib/commons-io-2.4.jar:./lib/ganymed-ssh2-build210.jar:./lib/ojdbc14.jar:./ssh2_monitor.jar test
nohup java -cp :$LIB/commons-io-2.4.jar::$LIB/ganymed-ssh2-build210.jar:$LIB//ojdbc14.jar:/ssh2_monitor.jar test & 

tail -f nohup.out 
