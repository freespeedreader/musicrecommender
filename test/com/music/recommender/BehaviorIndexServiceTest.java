package com.music.recommender;

import java.io.IOException;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.junit.Test;

import com.music.recommender.index.BehaviorIndexService;
import com.music.recommender.index.UserBehavior;

public class BehaviorIndexServiceTest {

	@Test
	public void bhehaviorIndexTest() throws IOException {
		BehaviorIndexService behaviorIndexService = new BehaviorIndexService();
		UserBehavior userBehavior = new UserBehavior();
		userBehavior.setMusicId(1);
		userBehavior.setMusicName("test");
		userBehavior.setUserName("puneet");
		userBehavior.setNoOfTimesMusicPlayed(2);
		userBehavior.setRating(3);
		behaviorIndexService.index(userBehavior);

		behaviorIndexService.close();
	}

	@Test
	public void getBehaviorTest() throws IOException, QueryNodeException {
		BehaviorIndexService behaviorIndexService = new BehaviorIndexService();
		System.out.println(behaviorIndexService.getUserBehavior(1,"puneet"));
		behaviorIndexService.close();
	}
}
