#!/bin/sh
rm ./classes/*
rm *.class *.out
rm ssh2_monitor.jar
rm -rf zdy
mkdir zdy

LIB=./lib
CC=javac
CC_CLASSPATH=$LIB/commons-io-2.4.jar:$LIB/ganymed-ssh2-build210.jar:$LIB/ojdbc14.jar
CLASSPATH=:$CC_CLASSPATH
CLASS=./classes

$CC -cp $CC_CLASSPATH RmtShellExecutor.java

$CC -cp $CC_CLASSPATH ssh2_monitor.java
cp ssh2_monitor.class zdy
jar -cvf ssh2_monitor.jar zdy


echo "$CC -cp $LIB/commons-io-2.4.jar:$LIB/ganymed-ssh2-build210.jar:$LIB/ojdbc14.jar:./ssh2_monitor.jar connect.java"
$CC -cp $LIB/commons-io-2.4.jar:$LIB/ganymed-ssh2-build210.jar:$LIB/ojdbc14.jar:./ssh2_monitor.jar connect.java

rm ssh2_monitor.jar
rm -rf zdy
mkdir zdy
cp ssh2_monitor.class zdy
cp RmtShellExecutor.class zdy
cp connect.class  zdy

jar -cvf ssh2_monitor.jar zdy
$CC -cp $LIB/commons-io-2.4.jar:$LIB/ganymed-ssh2-build210.jar:$LIB/ojdbc14.jar:./ssh2_monitor.jar test.java

#java -cp :$LIB/commons-io-2.4.jar:$LIB/ganymed-ssh2-build210.jar:$LIB/ojdbc14.jar:./ssh2_monitor.jar test

java -cp :./lib/commons-io-2.4.jar:./lib/ganymed-ssh2-build210.jar:./lib/ojdbc14.jar:./ssh2_monitor.jar test




