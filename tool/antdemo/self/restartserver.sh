[server@localhost pgame_ydjd]$ cat restartGame.sh 
. .lib.sh
#��ĿĿ¼
projectHome="/home/server/sanguo/pgame_ydjd"
#���ݿ�����
mysqlHost=db.webgame.com
mysqlUser=root
mysqlPasswd=liumang
db=pgame_system
dp_user=pgame_user
mysql="mysql --default-character-set=utf8 -h$mysqlHost -u$mysqlUser -p$mysqlPasswd"

if [[ $1 != "" ]]
then
   server_version=$1
   test -d $projectHome/jar$server_version 
   if [ $? != 0 ]
   then
		showRed "$server_version�汾������"
		exit 1
   fi
fi

$projectHome/shutDownServer.sh

count=`ps -ef|grep pgame_ydjd|grep -v "grep"|wc -l`
if [ "$count" == "0" ];then
   echo " "
else
   PID=`ps -ef|grep pgame_ydjd|grep -v "grep"|awk '{print $2}'`
   echo " kill $PID "
   kill $PID
fi

if [[ $1 != "" ]]
then
   server_version=$1
   echo "-------------------------------���·�����--------------------------"
   echo "����ϵͳ��������"
   test -f $projectHome/jar$server_version/sql/pgame_system.sql 
   if [ $? != 0 ]
   then
		echo "$projectHome/jar$server_version/sql/pgame_system.sql������,����Ҫ����ϵͳ��������"
   else
		$mysql -e "drop database $db"
		$mysql -e "create database $db"
		$mysql $db < $projectHome/jar$server_version/sql/pgame_system.sql
		showGreen "����ϵͳ�������"
   fi
   echo "�޸��û���"
   test -f $projectHome/jar$server_version/sql/modify.sql
   if [ $? != 0 ]
   then
		echo "$projectHome/jar$server_version/sql/modify.sql������,����Ҫ�����û���"
   else
		$mysql $dp_user < $projectHome/jar$server_version/sql/modify.sql
		showGreen "�޸��û������"
   fi
   echo "����config.props"
   test -f $projectHome/jar$server_version/config.props || exitBy "$projectHome/jar$server_version/config.props���ò�����"
   cp $projectHome/jar$server_version/config.props $projectHome/
   showGreen "����config.props���"
   echo "����jar"
   rm -rf $projectHome/jar/*
   cp $projectHome/jar$server_version/* $projectHome/jar/
   showGreen "����jar���"
   showGreen "-------------------------------���·��������--------------------------"
else
  echo  "------------------------------����������-------------------------"
fi
 echo "-----------------------------��ʼ����������--------------------------"

export JAVA_HOME="/opt/jdk1.6.0_20"

echo $JAVA_HOME

LANG="en_US.UTF-8"
export LANG
D=$projectHome"/jar"
CP="$D" 
for libfile in `ls $D`
do       
          CP=$CP:$D/$libfile
done     
mv /home/server/sanguo/pgame_ydjd/logs/server.log /home/server/sanguo/pgame_ydjd/logs/server.log.`date +%F_%T`
env="java -Xms1024M -Xmx1024M -XX:MaxNewSize=256M -XX:PermSize=256M -XX:MaxPermSize=256M -XX:ThreadStackSize=1024K -XX:+UseConcMarkSweepGC -DCONFIGPATH=$projectHome/config.xml -DLOGPATH=$projectHome/logs -DconfigPath=$projectHome -DrechargeConfigPath=$projectHome -cp $CP $2 $3"

$env com.kueem.pgame.net.netty.NettyServer $projectHome > /home/server/sanguo/pgame_ydjd/logs/server.log 2>&1 &