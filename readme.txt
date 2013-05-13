eclipse.ini add:
 

-showsplash
org.eclipse.platform
--launcher.XXMaxPermSize
128m
--launcher.defaultAction
openFile
-vmargs
-Dosgi.requiredJavaVersion=1.5
-Dfile.encoding=UTF-8
-Xms40m
-Xmx256m

2013-05-08 
修改单词的表示方式，现在用空格缩进，每个表示单元的宽度为最大宽度加上4个空格，shell用的字体是日语一个字符是英文字符的两倍宽度，按照这个来设置的缩进
增加st命令，设置类型
现在对几个set命令都实现了制定单词的功能，比如-st 123 副，将单词id为123的type制定为副词

2013-05-09
现在level支持模糊查询，用%，用sql的like实现的

2013-05-10
现在列表的对齐已经完美（原则是字体中非英数字的宽度是英数字的两倍）
给循环内执行命令包上了try catch，以防止意外退出

2013-05-13
想法：
用ProcessBuilder模拟command，以在程序中执行sql语句
增加一种状态：背诵，表示这个已经背诵过一次