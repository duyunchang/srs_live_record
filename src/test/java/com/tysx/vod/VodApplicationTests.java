package com.tysx.vod;

import java.util.Date;

import org.jboss.jandex.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.live.recording.util.DateHelper;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class VodApplicationTests {

	// @Test
	public void contextLoads() {
	}

	public static void main(String[] args) {
		Long parseLong = Long.parseLong("1504228198312");

		String dateStrTimeByDate = DateHelper.getDateStrTimeByDate(new Date(parseLong));

		System.out.println(dateStrTimeByDate);
	}

}
