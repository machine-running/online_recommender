package com.my.recommender.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.recommender.dao.DataDao;
import com.my.recommender.dao.RatingDao;
import com.my.recommender.model.Rating;
import com.my.recommender.service.RecommenderService;

@Component
public class RecommenderServiceImpl implements RecommenderService {

	@Autowired
	private RatingDao ratingDao;
	
	@Autowired
	private DataDao dataDao;

	@Override
	public File getParsedDataFile(){
		List<Rating> ratings = ratingDao.getAll();
		String path = "ratings.txt";
		FileWriter writer = null;
		try {
			writer = new FileWriter(path, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> data = new ArrayList<String>();
		String temp;
		for(Rating rating : ratings){
			temp = Integer.toString((int)rating.getUserId())+","+Integer.toString((int)rating.getFilmId())+","+Double.toString(rating.getRating());
			data.add(temp);
		}
		try {
			for(String element : data){

				writer.write(element);
				writer.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File file = new File(path);
		return file;
	}
	
	@Override
	public void insertToDatabase(File file){
		dataDao.insert(file);
	}
	
	@Override
	public File getLastFromDatabase(){
		String path = dataDao.getLast();
		File last = new File (path);
		return last;
	}

	@Override
	public double getPrediction(int userId, int filmId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}