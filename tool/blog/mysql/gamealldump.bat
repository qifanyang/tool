@echo off
rem 注释
echo 开始导出游戏数据,包含配置数据,玩家数据表
echo.
set dumpdir=E:\sanguo\sqldump
echo.

echo dump目录:"%dumpdir%"

echo dump配置数据库,包含数据
mysqldump -uroot -p123456 pgame_system>%dumpdir%/pgame_system.sql
echo dump ok
echo.

echo dump日志数据库,不包含数据
mysqldump -uroot -p123456 -d pgame_log>%dumpdir%/pgame_log.sql
echo dump ok
echo.

echo dump玩家数据库,不包含数据
mysqldump -uroot -p123456 -d pgame_user>%dumpdir%/pgame_user.sql
echo dump ok
echo.

echo dump后台数据库,不包含数据
mysqldump -uroot -p123456 -d pgame_backend>%dumpdir%/pgame_backend.sql
echo dump ok
echo.

echo on
pause