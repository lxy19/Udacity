package com.udacity.course3.reviews;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.udacity.course3.reviews.entity.mysql.Comment;
import com.udacity.course3.reviews.entity.mysql.Product;
import com.udacity.course3.reviews.entity.mysql.Review;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReviewsApplicationTests {
	private MongoTemplate mongoTemplate;

	private Comment comment = new Comment();
	private Review review = new Review();
	private Product product = new Product();
	private List<Comment> comments = new ArrayList<>();
	private List<Review> reviewList = new ArrayList<>();
	@Before
	public void setUp() throws IOException {
		String ip = "localhost";
		int port = 27017;

		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(ip, port, Network.localhostIsIPv6()))
				.build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");
	}

	@After
	public void clean(){
		mongoTemplate.dropCollection("product");
		mongoTemplate.dropCollection("review");
		mongoTemplate.dropCollection("comment");
	}

	@Test
	public void testMongoComment(){
		DBObject comment = BasicDBObjectBuilder.start()
				.add("content", "This is a positive feedback")
				.get();
		mongoTemplate.save(comment, "comment");
		assertThat(mongoTemplate.findAll(DBObject.class, "comment")).extracting("content")
				.containsOnly("This is a positive feedback");
	}

	@Test
	public void testMongoReview(){
	   DBObject review = BasicDBObjectBuilder.start()
			   .add("description", "This is about a product review")
			   .get();
		mongoTemplate.save(review, "review");
		assertThat(mongoTemplate.findAll(DBObject.class, "review")).extracting("description")
				.containsOnly("This is about a product review");
	}

	@Test
	public void testMongoProduct(){
		DBObject product = BasicDBObjectBuilder.start()
				.add("name", "sneakers")
				.get();
		mongoTemplate.save(product, "product");
		assertThat(mongoTemplate.findAll(DBObject.class, "product")).extracting("name")
				.containsOnly("sneakers");
	}
}