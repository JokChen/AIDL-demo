# AIDL-demo
# 这是一个简单的AIDL demo 实现功能：<br> 
#### 1、客户端设置服务端的data <br>
#### 2、客户端向服务端添加回调 <br>

## 步骤：<br>
#### 1、创建AIDL文件，直接在app右键->New->AIDL,会自动生成文件，在AIDL文件中添加接口<br>
#### 2、创建服务，新建AppServer服务，匿名内部类实现AIDL接口，OnBind中返回匿名内部类，onCreate中开一个线程做每隔3秒打印mData,如果客户端有注册回调则调用回调 <br>
#### 3、新建模块AntherApp，右键->New->Folder->ADL Folder创建AIDL文件夹，将App中的AIDL连包名一起拷贝到aidl文件夹中<br>
#### 4、AntherApp创建客户端文件AppClient,增加继承ServiceConnection的匿名内部类，用于绑定或者解除绑定app服务<br>

## 注意:<br>
#### 1、继承AIDL接口的时候不是直接继承我们写的AIDL接口名的而是需要继承.Stub()的<br>
#### 2、AppClient中onServiceConnected回调中的service需要用asInterface进行转化<br>
