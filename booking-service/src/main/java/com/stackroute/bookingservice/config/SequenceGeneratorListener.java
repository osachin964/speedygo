package com.stackroute.bookingservice.config;

import com.stackroute.bookingservice.dto.Counter;
import com.stackroute.bookingservice.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class SequenceGeneratorListener extends AbstractMongoEventListener<Booking> {

    private static final String PREFIX = "SG";
    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorListener(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String generateSequence(String seqName) {

        Counter counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                Counter.class);
        long l = !Objects.isNull(counter) ? counter.getSeq() : 1;
        return PREFIX+ LocalDate.now().toString().replace("-","")+"00"  +l;

    }
//    @Autowired
//    private static MongoTemplate mongoTemplate;

//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<Booking> event) {
//        Booking source = event.getSource();
//        if (source.getBookingId() == null || source.getBookingId().trim().length() == 0) {
//            source.setBookingId(PREFIX + generateSequence(SEQ_NAME));
//        }
//    }

//    public static String generateSequence(String seqName) {
//        Counter counter = mongoTemplate.findAndModify(query(where("_id").is(seqName)),
//                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
//                Counter.class);
//        if (counter == null) {
//            throw new RuntimeException("Unable to generate sequence for name: " + seqName + ". Counter collection does not exist.");
//        }
//        return PREFIX+counter.getSeq();
//    }
}


