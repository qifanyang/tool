@echo off
rem ע��
echo ��ʼ������Ϸ����,������������,������ݱ�
echo.
set dumpdir=E:\sanguo\sqldump
echo.

echo dumpĿ¼:"%dumpdir%"

echo dump�������ݿ�,��������
mysqldump -uroot -p123456 pgame_system>%dumpdir%/pgame_system.sql
echo dump ok
echo.

echo dump��־���ݿ�,����������
mysqldump -uroot -p123456 -d pgame_log>%dumpdir%/pgame_log.sql
echo dump ok
echo.

echo dump������ݿ�,����������
mysqldump -uroot -p123456 -d pgame_user>%dumpdir%/pgame_user.sql
echo dump ok
echo.

echo dump��̨���ݿ�,����������
mysqldump -uroot -p123456 -d pgame_backend>%dumpdir%/pgame_backend.sql
echo dump ok
echo.

echo on
pause