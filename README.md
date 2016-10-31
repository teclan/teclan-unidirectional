
### 项目逻辑说明
 yangClinet 通过 tcp 将消息发送给 yangTcpServer,yangTcpServer 再通过 yangUdpClient 通过 udp 转发给 yinUdpServer,yinUdpServer 再将数据通过 tcp 转给 yinClient。
 项目的实际意义不大，但是可以扩展...
 
### 运行说明

 ##### 1、teclan.unidirectional.yin.server.Main
 ##### 2、teclan.unidirectional.yang.server.Main
 ##### 3、teclan.unidirectional.yang.client.Main
 ##### 4、teclan.unidirectional.yin.client.Main
 ##### 其中 3 和 4 顺序任意，需要的注意的是 yang 转发到 yin 的 udp 服务的 ip 和 port 是根据 yin 的设置而设置，设置不正确，将收不到数据
 
### 其他说明
 本来是四个独立的项目，为自己管理方便合成了一个项目，根据需要自己拆分。
 项目中功能有限，例如仅支持单连接等...