#!/bin/sh
export JAVA_HOME="/opt/jdk1.6.0_20"
LANG="en_US.UTF-8"
PGAME_HOME="/home/server/sanguo/pgame_ydjd"
export LANG
D=$PGAME_HOME"/jar"
CP="$D"
for libfile in `ls $D`
do
          CP=$CP:$D/$libfile
done
env="java -Xmx512M -Xss512K -DLOGPATH=$PGAME_HOME/logs -DconfigPath=$PGAME_HOME -DrechargeConfigPath=$PGAME_HOME -cp $CP $1 $2 $3" 
$env