# WebTest
Java Selenium-Web自动化测试


这是一个自动化测试模块，为我的毕业设计《大学毕业生就业情况调查系统的》设计的


在\src\test\java\com\test\LoginTest.java中运行对应的方法


主要功能
登录测试 (testLogin)：


自动填写用户名、密码和验证码。


验证登录成功后跳转至 /home 或 /admin。


支持动态验证码提取与提交。
![login](https://github.com/user-attachments/assets/b29e5c78-4c18-4ccb-af10-ee3995d2f414)


注册测试 (testRegister)：


模拟切换到注册面板，填写随机生成的用户名、密码和验证码。



验证注册成功后跳转至登录页面。



处理动态面板切换与验证码同步。
![register](https://github.com/user-attachments/assets/a5cadfe1-5289-4897-a5a5-96501d7f63c2)


运行环境


操作系统：Windows 10


浏览器：Google Chrome（版本 133.0.6943.127）


驱动：ChromeDriver（匹配浏览器版本，位于 drivers/chromedriver.exe）



