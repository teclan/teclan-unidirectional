package teclan.unidirectional.yin.client.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YinClientHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(YinClientHandler.class);

    @Override
    public void sessionOpened(IoSession session) {
        LOGGER.info("Session created,session : {}", session);
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        LOGGER.info("Receive message from yin-api server {} ---> {}",
                session.getRemoteAddress(), message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) {
        session.closeNow();
    }
}
