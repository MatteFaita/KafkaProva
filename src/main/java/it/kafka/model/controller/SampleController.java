package it.kafka.model.controller;


import it.kafka.model.consumer.KafkaConsumerConfig;
import it.kafka.model.producer.KafkaProducerConfig;
import it.kafka.model.topic.TopicConfiguration;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletContext;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

@Slf4j
@RestController
public class SampleController {



    @Autowired
    ServletContext context;
    @Autowired
    KafkaConsumerConfig kafkaConsumerConfig;

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;




    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    @ResponseBody
    public String ping() {
        log.debug("executing ping method");


        String implementationVersion = getClass().getPackage().getImplementationVersion();

        try {
            if (implementationVersion == null) {
                Properties properties = new Properties();
                InputStream resourceAsStream = context.getResourceAsStream("/META-INF/MANIFEST.MF");

                if (resourceAsStream != null) {
                    properties.load(resourceAsStream);
                    implementationVersion = properties.getProperty("Implementation-Version");
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        sendMessage("vale");
        kafkaConsumerConfig.listen("vale");
        return "Pooooooooooooooooooooooooooong ... " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " version: " + implementationVersion+"\n";


        }
     String topicName ="butera";

    public void sendMessage(final String message) {

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }
}


