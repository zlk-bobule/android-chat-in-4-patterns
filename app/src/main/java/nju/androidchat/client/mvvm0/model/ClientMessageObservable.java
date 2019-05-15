package nju.androidchat.client.mvvm0.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nju.androidchat.client.BR;
import nju.androidchat.client.mvvm0.model.Direction;
import nju.androidchat.client.mvvm0.model.State;
import nju.androidchat.shared.message.ClientSendMessage;
import nju.androidchat.shared.message.ServerSendMessage;

@AllArgsConstructor
@NoArgsConstructor
public class ClientMessageObservable extends BaseObservable {
    @Getter
    private UUID messageId;

    @Getter
    private LocalDateTime time;

    @Getter
    private String senderUsername;

    @Getter
    @Bindable
    private String message;

    @Getter
    private Direction direction;

    @Getter
    @Bindable
    private State state;

    public void setMessage(String message){
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    public ClientMessageObservable(ClientSendMessage clientSendMessage, String username){
        direction = Direction.SEND;
        messageId = clientSendMessage.getId();
        time = clientSendMessage.getTime();
        message = clientSendMessage.getMessage();
        senderUsername = username;
        state = State.SENT;
    }

    public ClientMessageObservable(ServerSendMessage serverSendMessage){
        direction = Direction.RECEIVE;
        messageId = serverSendMessage.getMessageId();
        time = serverSendMessage.getTime();
        message = serverSendMessage.getMessage();
        senderUsername = serverSendMessage.getSenderUsername();
        state = State.SENT;
    }
}
