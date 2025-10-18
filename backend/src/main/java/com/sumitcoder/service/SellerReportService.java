package com.sumitcoder.service;

import com.sumitcoder.model.Seller;
import com.sumitcoder.model.SellerReport;
import java.util.List;
import java.util.Optional;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport( SellerReport sellerReport);

}
