package ru.example.sem6homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;


@Configuration
public class IntegrationConfiguration {
    // канал от входных данных до трансформера
    @Bean
    public MessageChannel messageChannelInput(){
        return new DirectChannel();
    }
    // канал передачи данных от трансформера в модуль, который сохраняет данные в файл
    @Bean
    public MessageChannel messageChannelFileWriter(){
        return new DirectChannel();
    }
    // трансформер
    @Bean
    @Transformer(inputChannel = "messageChannelInput", outputChannel = "messageChannelFileWriter")
    public GenericTransformer<String, String> myTransformer(){
        return text->{
            return text.toUpperCase();
        };
    }
    // выход из интеграции
    @Bean
    @ServiceActivator(inputChannel = "messageChannelFileWriter")
    public FileWritingMessageHandler myFileWriter() {

        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("note"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return  handler;
    }
}
