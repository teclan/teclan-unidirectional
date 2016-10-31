package teclan.unidirectional.yin.server.udp.server.handler;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.unidirectional.yin.server.handler.YinApiServerHandler;

public class UdpServerHandler extends IoHandlerAdapter {
    private static final Logger LOGGER              = LoggerFactory
            .getLogger(UdpServerHandler.class);

    private YinApiServerHandler yinApiServerHandler = new YinApiServerHandler();

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
        session.closeNow();
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {

        String msg = "";

        if (message instanceof IoBuffer) {
            IoBuffer buffer = (IoBuffer) message;

            msg = buffer.getObject().toString();
            LOGGER.info("UDP receive : {}", msg);

        } else {
            LOGGER.error("message format was wrong !");
        }

        yinApiServerHandler.writeToClient(msg);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // System.out.println("session close :" + session.getRemoteAddress());
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // System.out.println("session create :" + session.getRemoteAddress());

    };

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // System.out.println("session open :" + session.getRemoteAddress());
    }

}
