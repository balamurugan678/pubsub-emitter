package com.sample.poc;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Bala on 04/07/2017.
 */
@RestController
public class PubSubEmitController {

    private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("columnfamily1");
    private static final byte[] COLUMN_NAME = Bytes.toBytes("exception");

    @RequestMapping("/")
    public String home() {
        return "Hello Generator!";
    }


    @RequestMapping("/generator")
    public String generator() throws Exception {
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId("exception-management").build();
        Storage storage = storageOptions.getService();

        String bucketName = "topic-source";
        Bucket bucket = storage.get(bucketName);
        Blob blob = bucket.create(
                "GMMOException.xml", IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("GMMOException.xml")), "text/xml");


        Blob readBlob = bucket.get("GMMOException.xml");
        String blobContent = new String(readBlob.getContent(), UTF_8);
        System.out.println("MY BLOB ===========" + blobContent);


        try (Connection connection = BigtableConfiguration.connect("exception-management", "interstellar-travel")) {
            Admin admin = connection.getAdmin();
            Table table = connection.getTable(TableName.valueOf("dunkirk"));
            Put put = new Put(Bytes.toBytes("MSG_1234567"));
            put.addColumn(COLUMN_FAMILY_NAME, COLUMN_NAME, Bytes.toBytes(blobContent));
            table.put(put);

        } catch (IOException e) {
            System.err.println("Exception while running GMMOException: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        Publisher publisher = null;
        try {
            TopicName topic = TopicName.create("exception-management", "CaptureTopic");
            publisher = Publisher.defaultBuilder(topic).build();
            ByteString data = ByteString.copyFromUtf8(blobContent);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            publisher.publish(pubsubMessage);
        } finally {
            if (publisher != null) {
                publisher.shutdown();
            }
        }
        return "Message has been Generated, stowed in Cloud Storage and published in PubSub!!!";
    }
}
