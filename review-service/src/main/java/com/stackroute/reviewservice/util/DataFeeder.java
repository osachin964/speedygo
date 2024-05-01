package com.stackroute.reviewservice.util;

import com.stackroute.reviewservice.entity.Review;
import com.stackroute.reviewservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataFeeder implements CommandLineRunner {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public void run(String... args) throws Exception {
        reviewRepository.deleteAll();
        reviewRepository.save(Review.builder().rating(4).remark("Very professional men, did their job slowly without hurry n ensured there was no single damage...").
                transporterId("bluedart@gmail.com").customerName("Rachit Bategari").build());
        reviewRepository.save(Review.builder().rating(3).remark("Moved household items from Bengaluru  - Hyderabad, excellent top notch service from  Team members ").
                transporterId("bluedart@gmail.com").customerName("Shyam Singh").build());
        reviewRepository.save(Review.builder().rating(3).remark("I am very happy with their services.\n" + "They were very professional in their job").
                transporterId("bluedart@gmail.com").customerName("Vidhi Lokhande").build());
        reviewRepository.save(Review.builder().rating(5).remark("they took care of packing of all the items, loading, moving, unloading and unpacking the items safely. Their charge was also reasonable.").
                transporterId("quickliners@gmail.com").customerName("Sachin Ojha").build());
        reviewRepository.save(Review.builder().rating(5).remark("we did not see any damage at goods. Thanks so much owner  and your team members").
                transporterId("quickliners@gmail.com").customerName("Dinesh Shah").build());
        reviewRepository.save(Review.builder().rating(5).remark("service is great, friendly and extremely helpful.  We are very satisfied and will use again.").
                transporterId("quickliners@gmail.com").customerName("Sonali Ahuja Ojha").build());
        reviewRepository.save(Review.builder().rating(4).remark("i had to move my home goods they helped me a lot. All good").
                transporterId("localmovers@gmail.com").customerName("Prakash Ahuja").build());
        reviewRepository.save(Review.builder().rating(4).remark("I booked online from SPEEDYGO. I got very good service. Thanks").
                transporterId("localmovers@gmail.com").customerName("Raj Sharma").build());
        reviewRepository.save(Review.builder().rating(4).remark("I have got a wonderful service experience. Best transporters must say").
                transporterId("localmovers@gmail.com").customerName("Shweta Sharma").build());
        reviewRepository.save(Review.builder().rating(3).remark("nice, the unloading staff had come, he was very nice.  Delivery was done on time.").
                transporterId("shiftkaro@gmail.com").customerName("Aviraj Thakur").build());
        reviewRepository.save(Review.builder().rating(3).remark("Yes I will recommend you all for this mover,I shifted my goods from  Bangalore  To jharkhand its amazing the way they handled with care. ").
                transporterId("shiftkaro@gmail.com").customerName("Pratik Lokhande").build());
        reviewRepository.save(Review.builder().rating(4).remark("We used their services for a shifting from Bangalore to Pandavapura. They were very professional and the shifting activity was done as per commitment. ").
                transporterId("shiftkaro@gmail.com").customerName("Advika Sharma").build());
        reviewRepository.save(Review.builder().rating(3).remark("I really appreciate the team involved. They were punctual, packed and unpacked very well and no damages to any material.").
                transporterId("ramtransporter@gmail.com").customerName("Mukul Thakur").build());
        reviewRepository.save(Review.builder().rating(3).remark("My experience with this Logistics was very good.  Took service from Bangalore to Delhi.").
                transporterId("ramtransporter@gmail.com").customerName("Dhruv Katare").build());
        reviewRepository.save(Review.builder().rating(3).remark("Got the delivery done at the time which was said.  Everything was safe.").
                transporterId("ramtransporter@gmail.com").customerName("Purvi Sharma").build());
        reviewRepository.save(Review.builder().rating(4).remark("Used their service on two occasions from Bangalore to Hyderabad. They have handled all items with care and ensured little to no damages in transit. ").
                transporterId("jmd@gmail.com").customerName("Akash Pathade").build());
        reviewRepository.save(Review.builder().rating(4).remark("Good professional service. Would recommend.").
                transporterId("jmd@gmail.com").customerName("Ashish Singh").build());
        reviewRepository.save(Review.builder().rating(2).remark("My luggage went from Bangalore to Mumbai. Delivery was done on time without any follow up").
                transporterId("jmd@gmail.com").customerName("Shivangi Pawar").build());
        reviewRepository.save(Review.builder().rating(2).remark("staff friendly worked and packing done well.").
                transporterId("quickmovers@gmail.com").customerName("Rajat Sharma").build());
        reviewRepository.save(Review.builder().rating(3).remark("My household goods and car have been shifted from Bangalore to Pune. Good experience").
                transporterId("quickmovers@gmail.com").customerName("Rajesh Jangid").build());
        reviewRepository.save(Review.builder().rating(4).remark("Budget friendly packers and movers! I had them move my bike and it was well handled and delivery was also made in a timely manner. ").
                transporterId("quickmovers@gmail.com").customerName("Priya Ojha").build());
        reviewRepository.save(Review.builder().rating(3).remark("They adjusted the delivery time to fit my schedule. Overall a decent experience with no concerns.").
                transporterId("gltransporters@gmail.com").customerName("Sajid Khan").build());
        reviewRepository.save(Review.builder().rating(3).remark("Excellent service by them reasonable rate in Benguluru.").
                transporterId("gltransporters@gmail.com").customerName("Nayan Reddy").build());
        reviewRepository.save(Review.builder().rating(3).remark("I gave 5 star for their services and behaviour.").
                transporterId("gltransporters@gmail.com").customerName("Sunny Bategari").build());
        reviewRepository.save(Review.builder().rating(5).remark("I took a service from Bangalore to Kolkata. Very good service, my goods were delivered on time").
                transporterId("expressway@gmail.com").customerName("Rupesh Reddy").build());
        reviewRepository.save(Review.builder().rating(4).remark("Very organized and prompt. Trustworthy,reliable and consistent. Would definitely recommend this company").
                transporterId("expressway@gmail.com").customerName("Samay Shah").build());
        reviewRepository.save(Review.builder().rating(5).remark("Received timely delivery and safe goods.  finally had good services. ").
                transporterId("expressway@gmail.com").customerName("Dhanashri Adgurwar").build());

    }
}
