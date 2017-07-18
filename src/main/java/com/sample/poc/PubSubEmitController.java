package com.sample.poc;

import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        DataflowPipelineOptions options = PipelineOptionsFactory.as(DataflowPipelineOptions.class);
        options.as(DataflowPipelineOptions.class).setStreaming(true);
        options.setProject("gcp-f2brb");
        options.setStagingLocation("gs://f2brb-dataflow-staging/generator");
        options.setRunner(DataflowRunner.class);
        //options.setJobName("f2br-data-generation");
        Pipeline pipeline = Pipeline.create(options);

        pipeline.run().waitUntilFinish();
        return "Message has been Generated, stowed in Cloud Storage and published in PubSub!!!";
    }
}
