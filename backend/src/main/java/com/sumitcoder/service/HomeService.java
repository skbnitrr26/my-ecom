package com.sumitcoder.service;

import com.sumitcoder.model.Home;
import com.sumitcoder.model.HomeCategory;

import java.util.List;

public interface HomeService {

    Home creatHomePageData(List<HomeCategory> categories);

}
