package teclan.unidirectional.yin.server.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YinApiServerHandler extends IoHandlerAdapter {
    private static final Logger LOGGER  = LoggerFactory
            .getLogger(YinApiServerHandler.class);

    private static IoSession    session = null;

    public void writeToClient(Object message) {
        if (session == null) {
            LOGGER.error("The connection is unavailable");
            return;
        }

        Object msg = message;
        session.write(msg);

        LOGGER.info("write message to yin client wit tcp : {}", msg);

    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        YinApiServerHandler.session = session;
    }

    @Override
    public void messageReceived(IoSession session, Object message) {

        LOGGER.info("Receive message from client {} ---> {}",
                session.getRemoteAddress(), message.toString());

        // TODO
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        // LOGGER.info("IDLE : {}", session.getIdleCount(status));

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {

        cause.printStackTrace();
    }
}
