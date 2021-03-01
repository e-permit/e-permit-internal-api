package epermit.data.messagehandlers;

import org.springframework.stereotype.Service;
import epermit.core.messages.ReceivedMessageHandler;

@Service("CREATE_PERMIT")
public class CreatePermitMessageHandler implements ReceivedMessageHandler {

    @Override
    public boolean execute(Long messageId) {
        // TODO Auto-generated method stub
        return false;
    }
}
