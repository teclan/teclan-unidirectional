package teclan.unidirectional.yang.server.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.unidirectional.yang.server.Main;
import teclan.unidirectional.yang.server.udpclient.YangUdpClient;

public class YangApiServerHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(YangApiServerHandler.class);

    private YangUdpClient           udpClient;

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        udpClient = new YangUdpClient();
        udpClient.setUdpAddress(Main.udpHost, Main.udpPort);
        udpClient.run();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {

        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) {

        LOGGER.info("Receive message from tcp client {} ---> {}",
                session.getRemoteAddress(), message.toString());

        udpClient.sendToYin(message);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        // LOGGER.info("IDLE : {}", session.getIdleCount(status));

    }
}
