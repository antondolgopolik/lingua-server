package by.bsuir.linguaserver.dto;

import lombok.Data;

@Data
public class PlayerMessageDto {
    private PlayerMessageType messageType;
    private Object payload;
}
